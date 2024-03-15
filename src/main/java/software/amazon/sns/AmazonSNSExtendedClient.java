package software.amazon.sns;

import com.amazon.sqs.javamessaging.SQSExtendedClientConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.AuthorizationErrorException;
import software.amazon.awssdk.services.sns.model.EndpointDisabledException;
import software.amazon.awssdk.services.sns.model.InternalErrorException;
import software.amazon.awssdk.services.sns.model.InvalidParameterException;
import software.amazon.awssdk.services.sns.model.InvalidParameterValueException;
import software.amazon.awssdk.services.sns.model.InvalidSecurityException;
import software.amazon.awssdk.services.sns.model.KmsAccessDeniedException;
import software.amazon.awssdk.services.sns.model.KmsDisabledException;
import software.amazon.awssdk.services.sns.model.KmsInvalidStateException;
import software.amazon.awssdk.services.sns.model.KmsNotFoundException;
import software.amazon.awssdk.services.sns.model.KmsOptInRequiredException;
import software.amazon.awssdk.services.sns.model.KmsThrottlingException;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.NotFoundException;
import software.amazon.awssdk.services.sns.model.PlatformApplicationDisabledException;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.utils.StringUtils;
import software.amazon.payloadoffloading.PayloadStore;
import software.amazon.payloadoffloading.S3BackedPayloadStore;
import software.amazon.payloadoffloading.S3Dao;
import software.amazon.payloadoffloading.Util;

import java.util.HashMap;
import java.util.Map;

import static software.amazon.sns.AmazonSNSExtendedClientUtil.checkMessageAttributes;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.checkSizeOfMessageAttributes;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.getMessageAttributeSize;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.getMsgAttributesSize;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.getS3keyAttribute;
import static software.amazon.sns.AmazonSNSExtendedClientUtil.isTotalMessageSizeLargerThanThreshold;
import static software.amazon.sns.SNSExtendedClientConstants.MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE;
import static software.amazon.sns.SNSExtendedClientConstants.USER_AGENT_HEADER_NAME;

public class AmazonSNSExtendedClient extends AmazonSNSExtendedClientBase implements SnsClient{

    static final String USER_AGENT_HEADER = Util.getUserAgentHeader(AmazonSNSExtendedClient.class.getSimpleName());
    
    private static final Log LOGGER = LogFactory.getLog(AmazonSNSExtendedClient.class);

    private PayloadStore payloadStore;
    private SNSExtendedClientConfiguration snsExtendedClientConfiguration;

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object.
     *
     * <p>
     * All service calls made using this new client object are blocking, and
     * will not return until the service call completes.
     *
     * @param snsClient
     *            The Amazon SNS client to use to connect to Amazon SNS.
     */
    public AmazonSNSExtendedClient(SnsClient snsClient) {
        this(snsClient, new SNSExtendedClientConfiguration());
    }

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object.
     * <p>
     * <p>
     * All service calls made using this new client object are blocking, and
     * will not return until the service call completes.
     *
     * @param snsClient                   The Amazon SNS client to use to connect to Amazon SNS.
     * @param snsExtendedClientConfiguration The sns extended client configuration options controlling the
     *                                    functionality of this client.
     */
    public AmazonSNSExtendedClient(SnsClient snsClient, SNSExtendedClientConfiguration snsExtendedClientConfiguration) {
        super(snsClient);

        this.snsExtendedClientConfiguration = snsExtendedClientConfiguration;
        S3Dao s3Dao = new S3Dao(this.snsExtendedClientConfiguration.getS3Client());
        this.payloadStore = new S3BackedPayloadStore(s3Dao, this.snsExtendedClientConfiguration.getS3BucketName());
    }

    /**
     * Constructs a new Amazon SNS extended client to invoke service methods on
     * Amazon SNS with extended functionality using the specified Amazon SNS
     * client object and Payload Store object.
     * <p>
     * <p>
     * All service calls made using this new client object are blocking, and
     * will not return until the service call completes.
     *
     * @param snsClient                   The Amazon SNS client to use to connect to Amazon SNS.
     * @param snsExtendedClientConfiguration The sns extended client configuration options controlling the
     *                                    functionality of this client.
     * @param payloadStore                The Payload Store that handles logic for saving to the desired
     *                                    extended storage.
     */
    public AmazonSNSExtendedClient(SnsClient snsClient, SNSExtendedClientConfiguration snsExtendedClientConfiguration, PayloadStore payloadStore) {
        super(snsClient);

        this.snsExtendedClientConfiguration = snsExtendedClientConfiguration;
        this.payloadStore = payloadStore;
    }

    /**
     * <p>
     * Sends a message to an Amazon SNS topic, a text message (SMS message) directly to a phone number, or a message to
     * a mobile platform endpoint (when you specify the <code>TargetArn</code>).
     * </p>
     * <p>
     * If you send a message to a topic, Amazon SNS delivers the message to each endpoint that is subscribed to the
     * topic. The format of the message depends on the notification protocol for each subscribed endpoint.
     * </p>
     * <p>
     * When a <code>messageId</code> is returned, the message has been saved and Amazon SNS will attempt to deliver it
     * shortly.
     * </p>
     * <p>
     * To use the <code>Publish</code> action for sending a message to a mobile endpoint, such as an app on a Kindle
     * device or mobile phone, you must specify the EndpointArn for the TargetArn parameter. The EndpointArn is returned
     * when making a call with the <code>CreatePlatformEndpoint</code> action.
     * </p>
     * <p>
     * For more information about formatting messages, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/mobile-push-send-custommessage.html">Send Custom
     * Platform-Specific Payloads in Messages to Mobile Devices</a>.
     * </p>
     * <important>
     * <p>
     * You can publish messages only to topics and endpoints in the same AWS Region.
     * </p>
     * </important>
     *
     * @param publishRequest Input for Publish action.
     * @return Result of the Publish operation returned by the service.
     * @throws InvalidParameterException            Indicates that a request parameter does not comply with the associated constraints.
     * @throws InvalidParameterValueException       Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException               Indicates an internal service error.
     * @throws NotFoundException                    Indicates that the requested resource does not exist.
     * @throws EndpointDisabledException            Exception error indicating endpoint disabled.
     * @throws PlatformApplicationDisabledException Exception error indicating platform application disabled.
     * @throws AuthorizationErrorException          Indicates that the user has been denied access to the requested resource.
     * @throws KmsDisabledException                 The request was rejected because the specified customer master key (CMK) isn't enabled.
     * @throws KmsInvalidStateException             The request was rejected because the state of the specified resource isn't valid for this request. For
     *                                              more information, see <a href="https://docs.aws.amazon.com/kms/latest/developerguide/key-state.html">How
     *                                              Key State Affects Use of a Customer Master Key</a> in the <i>AWS Key Management Service Developer
     *                                              Guide</i>.
     * @throws KmsNotFoundException                 The request was rejected because the specified entity or resource can't be found.
     * @throws KmsOptInRequiredException            The AWS access key ID needs a subscription for the service.
     * @throws KmsThrottlingException               The request was denied due to request throttling. For more information about throttling, see <a
     *                                              href="https://docs.aws.amazon.com/kms/latest/developerguide/limits.html#requests-per-second">Limits</a>
     *                                              in the <i>AWS Key Management Service Developer Guide.</i>
     * @throws KmsAccessDeniedException             The ciphertext references a key that doesn't exist or that you don't have access to.
     * @throws InvalidSecurityException             The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                              Signature Version 4.
     * @throws SdkException                         Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                              catch all scenarios.
     * @throws SdkClientException                   If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                         Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.Publish
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Publish" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public PublishResponse publish(PublishRequest publishRequest) throws InvalidParameterException,
            InvalidParameterValueException, InternalErrorException, NotFoundException, EndpointDisabledException,
            PlatformApplicationDisabledException, AuthorizationErrorException, KmsDisabledException, KmsInvalidStateException,
            KmsNotFoundException, KmsOptInRequiredException, KmsThrottlingException, KmsAccessDeniedException,
            InvalidSecurityException, AwsServiceException, SdkClientException, SnsException {
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
        publishRequest = storeMessageInExtendedStore(clonedPublishRequest, messageAttributesSize);

        return super.publish(publishRequest);
    }


    private PublishRequest storeMessageInExtendedStore(PublishRequest publishRequest, long messageAttributeSize) {
        String messageContentStr = publishRequest.message();
        long messageContentSize = Util.getStringSizeInBytes(messageContentStr);
        String s3Key = getS3keyAttribute(publishRequest.messageAttributes());

        PublishRequest.Builder publishRequestBuilder = publishRequest.toBuilder();
        String largeMessagePointer = (s3Key != null)? payloadStore.storeOriginalPayload(messageContentStr, s3Key): payloadStore.storeOriginalPayload(messageContentStr);
        publishRequestBuilder.message(largeMessagePointer);

        MessageAttributeValue.Builder messageAttributeValueBuilder = MessageAttributeValue.builder();
        messageAttributeValueBuilder.dataType("Number");
        messageAttributeValueBuilder.stringValue(String.valueOf(messageContentSize));
        MessageAttributeValue messageAttributeValue = messageAttributeValueBuilder.build();

        Map<String, MessageAttributeValue> attributes = new HashMap<>(publishRequest.messageAttributes());
        attributes.put(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);
        publishRequestBuilder.messageAttributes(attributes);

        messageAttributeSize += getMessageAttributeSize(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);
        checkSizeOfMessageAttributes(snsExtendedClientConfiguration.getPayloadSizeThreshold(), messageAttributeSize);

        return publishRequestBuilder.build();
    }

    private PublishRequest copyPublishRequest(PublishRequest publishRequest) {
        // We only modify Message and MessageAttributes, to avoid performance impact let's shallow-copy 
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

    @Override
    public void close() {
        super.close();
        this.snsExtendedClientConfiguration.getS3Client().close();
    }
}
