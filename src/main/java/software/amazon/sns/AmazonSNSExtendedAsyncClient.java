package software.amazon.sns;

import com.amazon.sqs.javamessaging.SQSExtendedClientConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.utils.StringUtils;
import software.amazon.payloadoffloading.PayloadStoreAsync;
import software.amazon.payloadoffloading.S3AsyncDao;
import software.amazon.payloadoffloading.S3BackedPayloadStoreAsync;
import software.amazon.payloadoffloading.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static software.amazon.sns.AmazonSNSExtendedClientUtil.checkMessageAttributes;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.checkSizeOfMessageAttributes;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.getMessageAttributeSize;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.getMsgAttributesSize;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.getS3keyAttribute;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.isTotalMessageSizeLargerThanThreshold;
import static software.amazon.sns.SNSExtendedClientConstants.MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE;
import static software.amazon.sns.SNSExtendedClientConstants.USER_AGENT_HEADER_NAME;

public class AmazonSNSExtendedAsyncClient extends AmazonSNSExtendedAsyncClientBase implements SnsAsyncClient {

    static final String USER_AGENT_HEADER = Util.getUserAgentHeader(AmazonSNSExtendedAsyncClient.class.getSimpleName());
    private static final Log LOGGER = LogFactory.getLog(AmazonSNSExtendedAsyncClient.class);
    private SNSExtendedAsyncClientConfiguration snsExtendedClientConfiguration;
    private PayloadStoreAsync payloadStore;

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object.
     *
     * <p>
     * All service calls made using this client are asynchronous, and will return
     * immediately with a {@link CompletableFuture} that completes when the operation
     * completes or when an exception is thrown. Argument validation exception are thrown
     * immediately, and not through the future.
     * </p>
     *
     * @param snsClient
     *              The Amazon SNS client to use to connect to Amazon SNS.
     */
    public AmazonSNSExtendedAsyncClient(SnsAsyncClient snsClient) {
        this(snsClient, new SNSExtendedAsyncClientConfiguration());
    }

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object.
     *
     * <p>
     * All service calls made using this client are asynchronous, and will return
     * immediately with a {@link CompletableFuture} that completes when the operation
     * completes or when an exception is thrown. Argument validation exceptions are thrown
     * immediately, and not through the future.
     * </p>
     *
     * @param snsClient
     *            The Amazon SNS async client to use to connect to Amazon SNS.
     * @param clientConfig
     *            The extended client configuration options controlling the
     *            functionality of this client.
     */
    public AmazonSNSExtendedAsyncClient(SnsAsyncClient snsClient, SNSExtendedAsyncClientConfiguration clientConfig) {
        super(snsClient);
        this.snsExtendedClientConfiguration = new SNSExtendedAsyncClientConfiguration(clientConfig);
        S3AsyncDao s3Dao = new S3AsyncDao(snsExtendedClientConfiguration.getS3AsyncClient());
        this.payloadStore = new S3BackedPayloadStoreAsync(s3Dao, snsExtendedClientConfiguration.getS3BucketName());
    }

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object and Payload Store object.
     * <p>
     * All service calls made using this client are asynchronous, and will return
     * immediately with a {@link CompletableFuture} that completes when the operation
     * completes or when an exception is thrown. Argument validation exceptions are thrown
     * immediately, and not through the future.
     * </p>
     *
     * @param snsClient                   The Amazon SNS client to use to connect to Amazon SNS.
     * @param clientConfig The sns extended client configuration options controlling the
     *                                    functionality of this client.
     * @param payloadStore                The Payload Store that handles logic for saving to the desired
     *                                    extended storage.
     */
    public AmazonSNSExtendedAsyncClient(SnsAsyncClient snsClient, SNSExtendedAsyncClientConfiguration clientConfig,
                                        PayloadStoreAsync payloadStore) {
        super(snsClient);

        this.snsExtendedClientConfiguration = clientConfig;
        this.payloadStore = payloadStore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<PublishResponse> publish(PublishRequest publishRequest) throws S3Exception {
        if (publishRequest == null || StringUtils.isEmpty(publishRequest.message())) {
            return super.publish(publishRequest);
        }

        if (!StringUtils.isEmpty(publishRequest.messageStructure()) &&
                publishRequest.messageStructure().equals(MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE)) {
            String errorMessage = "SNS extended client does not support sending JSON messages.";
            LOGGER.error(errorMessage);
            throw SdkClientException.create(errorMessage);
        }

        PublishRequest.Builder publishRequestBuilder = publishRequest.toBuilder();
        publishRequestBuilder.overrideConfiguration(AwsRequestOverrideConfiguration.builder().putHeader(USER_AGENT_HEADER_NAME, USER_AGENT_HEADER).build());
        publishRequest = publishRequestBuilder.build();

        long messageAttributesSize = getMsgAttributesSize(publishRequest.messageAttributes());
        long messageBodySize = Util.getStringSizeInBytes(publishRequest.message());

        if (!shouldExtendedStoreBeUsed(messageAttributesSize + messageBodySize)) {
            return super.publish(publishRequest);
        }

        checkMessageAttributes(publishRequest.messageAttributes());
        checkSizeOfMessageAttributes(snsExtendedClientConfiguration.getPayloadSizeThreshold(), messageAttributesSize);

        PublishRequest clonedPublishRequest = copyPublishRequest(publishRequest);

        return storeMessageInExtendedStore(clonedPublishRequest, messageAttributesSize).thenCompose(super::publish);
    }

    private CompletableFuture<PublishRequest> storeMessageInExtendedStore(PublishRequest publishRequest, long messageAttributeSize) throws S3Exception {
        String messageContentStr = publishRequest.message();
        long messageContentSize = Util.getStringSizeInBytes(messageContentStr);
        String s3Key = getS3keyAttribute(publishRequest.messageAttributes());

        PublishRequest.Builder publishRequestBuilder = publishRequest.toBuilder();

        MessageAttributeValue.Builder messageAttributeValueBuilder = MessageAttributeValue.builder();
        messageAttributeValueBuilder.dataType("Number");
        messageAttributeValueBuilder.stringValue(String.valueOf(messageContentSize));
        MessageAttributeValue messageAttributeValue = messageAttributeValueBuilder.build();

        Map<String, MessageAttributeValue> attributes = new HashMap<>(publishRequest.messageAttributes());
        attributes.put(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);
        publishRequestBuilder.messageAttributes(attributes);

        messageAttributeSize += getMessageAttributeSize(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);
        checkSizeOfMessageAttributes(snsExtendedClientConfiguration.getPayloadSizeThreshold(), messageAttributeSize);

        CompletableFuture<String> largeMessagePointerFuture = (s3Key != null)? payloadStore.storeOriginalPayload(messageContentStr, s3Key): payloadStore.storeOriginalPayload(messageContentStr);

        return largeMessagePointerFuture.thenApply(largeMessagePointer -> {
            publishRequestBuilder.message(largeMessagePointer);
            return publishRequestBuilder.build();
        });
    }

    private PublishRequest copyPublishRequest(PublishRequest publishRequest) {
        // We only modify Message and MessageAttributes, to avoid performance impact, let's shallow-copy
        // the request and then copy the MessageAttributes map.
        PublishRequest.Builder publishRequestBuilder = publishRequest.toBuilder();
        Map<String, MessageAttributeValue> attributes = new HashMap<>(publishRequest.messageAttributes());
        publishRequestBuilder.messageAttributes(attributes);
        return publishRequestBuilder.build();
    }

    private boolean shouldExtendedStoreBeUsed(long totalMessageSize) {
        return snsExtendedClientConfiguration.isAlwaysThroughS3() ||
                (snsExtendedClientConfiguration.isPayloadSupportEnabled() &&
                        isTotalMessageSizeLargerThanThreshold(
                                snsExtendedClientConfiguration.getPayloadSizeThreshold(),totalMessageSize));
    }
}
