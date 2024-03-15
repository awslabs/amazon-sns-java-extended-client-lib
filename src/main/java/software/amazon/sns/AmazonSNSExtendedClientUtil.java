package software.amazon.sns;

import com.amazon.sqs.javamessaging.SQSExtendedClientConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.payloadoffloading.Util;

import java.util.Map;

public class AmazonSNSExtendedClientUtil {
    private static final Log LOGGER = LogFactory.getLog(AmazonSNSExtendedClientUtil.class);
    private static final String S3_KEY = "S3Key";


    public static void checkMessageAttributes(Map<String, MessageAttributeValue> messageAttributes) {
        int messageAttributesNum = messageAttributes.size();
        if (messageAttributesNum > SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES) {
            String errorMessage = "Number of message attributes [" + messageAttributesNum
                    + "] exceeds the maximum allowed for large-payload messages ["
                    + SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + "].";
            LOGGER.error(errorMessage);
            throw SdkClientException.create(errorMessage);
        }

        MessageAttributeValue largePayloadAttributeName = messageAttributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);

        if (largePayloadAttributeName != null) {
            String errorMessage = "Message attribute name " + SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME
                    + " is reserved for use by SNS extended client.";
            LOGGER.error(errorMessage);
            throw SdkClientException.create(errorMessage);
        }
    }

    public static void checkSizeOfMessageAttributes(int payloadSizeThreshold, long messageAttributeSize) {
        if (messageAttributeSize > payloadSizeThreshold) {
            String errorMessage = "Total size of Message attributes is " + messageAttributeSize
                    + " bytes which is larger than the threshold of " + payloadSizeThreshold
                    + " Bytes. Consider including the payload in the message body instead of message attributes.";
            LOGGER.error(errorMessage);
            throw SdkClientException.create(errorMessage);
        }
    }

    public static boolean isTotalMessageSizeLargerThanThreshold(int payloadSizeThreshold, long totalMessageSize) {
        return (totalMessageSize > payloadSizeThreshold);
    }

    public static int getMsgAttributesSize(Map<String, MessageAttributeValue> msgAttributes) {
        int totalMsgAttributesSize = 0;

        for (Map.Entry<String, MessageAttributeValue> entry : msgAttributes.entrySet()) {
            totalMsgAttributesSize += (int) getMessageAttributeSize(entry.getKey(), entry.getValue());
        }

        return totalMsgAttributesSize;
    }

    public static long getMessageAttributeSize(String MessageAttributeKey, MessageAttributeValue value) {
        long messageAttributeSize = Util.getStringSizeInBytes(MessageAttributeKey);

        if (value.dataType() != null) {
            messageAttributeSize += Util.getStringSizeInBytes(value.dataType());
        }

        String stringVal = value.stringValue();
        if (stringVal != null) {
            messageAttributeSize += Util.getStringSizeInBytes(stringVal);
        }

        SdkBytes binaryVal = value.binaryValue();
        if (binaryVal != null) {
            messageAttributeSize += binaryVal.asByteArray().length;
        }

        return messageAttributeSize;
    }

    public static String getS3keyAttribute(Map<String, MessageAttributeValue> messageAttributes) {
        if (messageAttributes != null && messageAttributes.containsKey(S3_KEY)) {
            MessageAttributeValue attributeS3KeyValue = messageAttributes.get(S3_KEY);
            return (attributeS3KeyValue == null) ? null : attributeS3KeyValue.stringValue();
        }
        return null;
    }
}
