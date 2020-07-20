package software.amazon.sns;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.amazonaws.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import software.amazon.payloadoffloading.*;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class AmazonSNSExtendedClient extends AmazonSNSExtendedClientBase {
    static final String MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE = "json";
    private static final Log LOGGER = LogFactory.getLog(AmazonSNSExtendedClient.class);
    private static final String USER_AGENT_HEADER = Util.getUserAgentHeader(AmazonSNSExtendedClient.class.getSimpleName());
    private PayloadStore payloadStore;
    private PayloadStorageConfiguration payloadStorageConfiguration;

    public AmazonSNSExtendedClient(AmazonSNS amazonSNSToBeExtended) {
        super(amazonSNSToBeExtended);
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
     * @param payloadStorageConfiguration The extended client configuration options controlling the
     *                                    functionality of this client.
     */
    public AmazonSNSExtendedClient(AmazonSNS snsClient, PayloadStorageConfiguration payloadStorageConfiguration) {
        super(snsClient);
        this.payloadStorageConfiguration = new PayloadStorageConfiguration(payloadStorageConfiguration);
        S3Dao s3Dao = new S3Dao(this.payloadStorageConfiguration.getAmazonS3Client());
        this.payloadStore = new S3BackedPayloadStore(s3Dao, this.payloadStorageConfiguration.getS3BucketName());
    }

    /**
     * <p>
     * Sends a message to an Amazon SNS topic or sends a text message (SMS message) directly to a phone number.
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
     * href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-send-custommessage.html">Send Custom Platform-Specific
     * Payloads in Messages to Mobile Devices</a>.
     * </p>
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
     * @sample AmazonSNS.Publish
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Publish" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public PublishResult publish(PublishRequest publishRequest) {
        if (publishRequest == null || StringUtils.isNullOrEmpty(publishRequest.getMessage())) {
            return super.publish(publishRequest);
        }

        publishRequest.getRequestClientOptions().appendUserAgent(USER_AGENT_HEADER);

        if (!shouldExtendedStoreBeUsed(publishRequest)) {
            return super.publish(publishRequest);
        }

        //Check message attributes for ExtendedClient related constraints 
        checkMessageAttributes(publishRequest.getMessageAttributes());

        if (payloadStorageConfiguration.isAlwaysThroughS3() || isLarge(publishRequest)) {
            PublishRequest clonedPublishRequest = copyPublishRequest(publishRequest);
            publishRequest = storeMessageInExtendedStore(clonedPublishRequest);
        }
        return super.publish(publishRequest);
    }

    private boolean shouldExtendedStoreBeUsed(PublishRequest publishRequest) {
        boolean shouldExtendedStoreBeUsed = false;
        if (payloadStorageConfiguration.isPayloadSupportEnabled()) {
            shouldExtendedStoreBeUsed = true;
        }
        if (!StringUtils.isNullOrEmpty(publishRequest.getMessageStructure())) {
            shouldExtendedStoreBeUsed = shouldExtendedStoreBeUsed && !publishRequest.getMessageStructure().equals(MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE);
        }
        return shouldExtendedStoreBeUsed;
    }

    private void checkMessageAttributes(Map<String, MessageAttributeValue> messageAttributes) {
        checkSizeOfMessageAttributes(messageAttributes);

        int messageAttributesNum = messageAttributes.size();
        if (messageAttributesNum > SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES) {
            String errorMessage = "Number of message attributes [" + messageAttributesNum
                    + "] exceeds the maximum allowed for large-payload messages ["
                    + SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + "].";
            LOGGER.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        MessageAttributeValue largePayloadAttributeName = messageAttributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);

        if (largePayloadAttributeName != null) {
            String errorMessage = "Message attribute name " + SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME
                    + " is reserved for use by SNS extended client.";
            LOGGER.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }
    }

    private void checkSizeOfMessageAttributes(Map<String, MessageAttributeValue> messageAttributes) {
        int msgAttributesSize = getMsgAttributesSize(messageAttributes);
        if (msgAttributesSize > payloadStorageConfiguration.getPayloadSizeThreshold()) {
            String errorMessage = "Total size of Message attributes is " + msgAttributesSize
                    + " bytes which is larger than the threshold of " + payloadStorageConfiguration.getPayloadSizeThreshold()
                    + " Bytes. Consider including the payload in the message body instead of message attributes.";
            LOGGER.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }
    }

    private boolean isLarge(PublishRequest publishRequest) {
        int msgAttributesSize = getMsgAttributesSize(publishRequest.getMessageAttributes());
        long msgBodySize = Util.getStringSizeInBytes(publishRequest.getMessage());
        long totalMsgSize = msgAttributesSize + msgBodySize;
        return (totalMsgSize > payloadStorageConfiguration.getPayloadSizeThreshold());
    }

    private int getMsgAttributesSize(Map<String, MessageAttributeValue> msgAttributes) {
        int totalMsgAttributesSize = 0;
        for (Map.Entry<String, MessageAttributeValue> entry : msgAttributes.entrySet()) {
            totalMsgAttributesSize += Util.getStringSizeInBytes(entry.getKey());

            MessageAttributeValue entryVal = entry.getValue();
            if (entryVal.getDataType() != null) {
                totalMsgAttributesSize += Util.getStringSizeInBytes(entryVal.getDataType());
            }

            String stringVal = entryVal.getStringValue();
            if (stringVal != null) {
                totalMsgAttributesSize += Util.getStringSizeInBytes(stringVal);
            }

            ByteBuffer binaryVal = entryVal.getBinaryValue();
            if (binaryVal != null) {
                totalMsgAttributesSize += binaryVal.array().length;
            }
        }

        return totalMsgAttributesSize;
    }

    private PublishRequest storeMessageInExtendedStore(PublishRequest publishRequest) {
        String messageContentStr = publishRequest.getMessage();
        Long messageContentSize = Util.getStringSizeInBytes(messageContentStr);

        String largeMessagePointer = payloadStore.storeOriginalPayload(messageContentStr,
                messageContentSize);
        publishRequest.setMessage(largeMessagePointer);

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.setDataType("Number");
        messageAttributeValue.setStringValue(messageContentSize.toString());
        publishRequest.addMessageAttributesEntry(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME,
                messageAttributeValue);

        checkSizeOfMessageAttributes(publishRequest.getMessageAttributes());

        return publishRequest;
    }

    private PublishRequest copyPublishRequest(PublishRequest publishRequest) {
        // We only modify Message and MessageAttributes, to avoid performance impact let's shallow-copy 
        // the request and then copy the MessageAttributes map.
        PublishRequest clonedPublishRequest = publishRequest.clone();
        Map<String, MessageAttributeValue> attributes = new HashMap<>(publishRequest.getMessageAttributes());
        clonedPublishRequest.setMessageAttributes(attributes);
        return clonedPublishRequest;
    }
}
