package software.amazon.sns;

import com.amazon.sqs.javamessaging.SQSExtendedClientConstants;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.payloadoffloading.PayloadStorageConfiguration;
import software.amazon.payloadoffloading.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AmazonSNSExtendedClientTest {

    private static final String S3_BUCKET_NAME = "test-bucket-name";
    private static final String SNS_TOPIC_ARN = "test-topic-arn";
    private static final int LESS_THAN_SNS_SIZE_LIMIT = 3;
    private static final int MORE_THAN_SNS_SIZE_LIMIT = AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE + 1;
    // should be > 1 and << AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE
    private static final int ARBITRARY_SMALLER_THRESHOLD = 500;

    private AmazonSNS extendedSnsWithDefaultConfig;
    private AmazonSNS mockSnsBackend;
    private AmazonS3 mockS3;

    @Before
    public void setupClient() {
        mockS3 = mock(AmazonS3.class);
        mockSnsBackend = mock(AmazonSNS.class);
        when(mockS3.putObject(any(PutObjectRequest.class))).thenReturn(null);

        PayloadStorageConfiguration payloadStorageConfiguration = new PayloadStorageConfiguration()
                .withPayloadSupportEnabled(mockS3, S3_BUCKET_NAME)
                .withPayloadSizeThreshold(AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE);

        extendedSnsWithDefaultConfig = spy(new AmazonSNSExtendedClient(mockSnsBackend, payloadStorageConfiguration));
    }

    @Test
    public void testPublishLargeMessageS3IsUsed() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        extendedSnsWithDefaultConfig.publish(publishRequest);

        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class));
        ArgumentCaptor<PublishRequest> publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(mockSnsBackend, times(1)).publish(publishRequestCaptor.capture());

        Map<String, MessageAttributeValue> attributes = publishRequestCaptor.getValue().getMessageAttributes();
        Assert.assertEquals("Number", attributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME).getDataType());

        Assert.assertEquals(messageBody.length(), (int) Integer.valueOf(attributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME).getStringValue()));
    }

    @Test
    public void testPublishSmallMessageS3IsNotUsed() {
        String messageBody = generateStringWithLength(AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE);

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        extendedSnsWithDefaultConfig.publish(publishRequest);

        verify(mockS3, never()).putObject(any(PutObjectRequest.class));
        ArgumentCaptor<PublishRequest> publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(mockSnsBackend, times(1)).publish(publishRequestCaptor.capture());

        Map<String, MessageAttributeValue> attributes = publishRequestCaptor.getValue().getMessageAttributes();
        Assert.assertTrue(attributes.isEmpty());
    }

    @Test
    public void testPublishMessageWithLargePayloadSupportDisabledS3IsNotUsedAndSqsBackendIsResponsibleToFailIt() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        PayloadStorageConfiguration payloadStorageConfiguration = new PayloadStorageConfiguration()
                .withPayloadSupportDisabled();
        AmazonSNS snsExtended = spy(new AmazonSNSExtendedClient(mockSnsBackend, payloadStorageConfiguration));

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        snsExtended.publish(publishRequest);

        verify(mockS3, never()).putObject(any(PutObjectRequest.class));
        verify(mockSnsBackend).publish(eq(publishRequest));
    }

    @Test
    public void testPublishMessageWithJSONMessageStructureThrowsAmazonClientException() {
        String messageBody = "{\"key1\":\"value1\",\"key2\":8.0}";

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        publishRequest.setMessageStructure(AmazonSNSExtendedClient.MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown.");

        } catch (AmazonClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("SNS extended client does not support sending JSON messages"));
        }
    }

    @Test
    public void testPublishMessageWithAlwaysThroughS3AndSmallMessageS3IsUsed() {
        String messageBody = generateStringWithLength(LESS_THAN_SNS_SIZE_LIMIT);
        PayloadStorageConfiguration payloadStorageConfiguration = new PayloadStorageConfiguration()
                .withPayloadSupportEnabled(mockS3, S3_BUCKET_NAME)
                .withAlwaysThroughS3(true);

        AmazonSNS snsExtended = spy(new AmazonSNSExtendedClient(mock(AmazonSNSClient.class), payloadStorageConfiguration));

        snsExtended.publish(SNS_TOPIC_ARN, messageBody);

        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class));
    }

    @Test
    public void testPublishMessageWithSetMessageSizeThresholdThresholdIsHonored() {
        String messageBody = generateStringWithLength(ARBITRARY_SMALLER_THRESHOLD * 2);
        PayloadStorageConfiguration payloadStorageConfiguration = new PayloadStorageConfiguration()
                .withPayloadSupportEnabled(mockS3, S3_BUCKET_NAME)
                .withPayloadSizeThreshold(ARBITRARY_SMALLER_THRESHOLD);

        AmazonSNS snsExtended = spy(new AmazonSNSExtendedClient(mock(AmazonSNSClient.class), payloadStorageConfiguration));

        snsExtended.publish(SNS_TOPIC_ARN, messageBody);
        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class));
    }

    @Test
    public void testPublishRequestDoesNotAlterPublishRequest() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();
        attrs.put("SampleKey", new MessageAttributeValue().withStringValue("value"));

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        publishRequest.setMessageAttributes(attrs);
        extendedSnsWithDefaultConfig.publish(publishRequest);

        Assert.assertEquals(messageBody, publishRequest.getMessage());
        Assert.assertEquals(attrs, publishRequest.getMessageAttributes());
    }

    @Test
    public void testThrowAmazonServiceExceptionWhenS3ThrowsAmazonServiceException() {
        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(AmazonServiceException.class);

        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown.");

        } catch (AmazonServiceException exception) {
            Assert.assertTrue(exception.getMessage().contains("Failed to store the message content in an S3 object."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenS3ThrowsAmazonClientException() {
        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(AmazonClientException.class);

        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (AmazonClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Failed to store the message content in an S3 object."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenReservedAttributeNameIsAlreadyUsed() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.setDataType("Number");
        messageAttributeValue.setStringValue(Util.getStringSizeInBytes(messageBody) + "");
        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        publishRequest.addMessageAttributesEntry(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (AmazonClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Message attribute name " +
                    SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME + " is reserved for use by SNS extended client."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenThereAreMoreThanAllowedMessageAttributes() {
        int attributeNumber = SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + 1;
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();

        for (int index = 0; index < attributeNumber; index++) {
            attrs.put("key" + index, new MessageAttributeValue().withDataType("String").withStringValue("value" + index));
        }

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        publishRequest.setMessageAttributes(attrs);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (AmazonClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Number of message attributes [" + attributeNumber
                    + "] exceeds the maximum allowed for large-payload messages ["
                    + SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + "]."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenSizeOfMessageAttributeKeyIsLargerThanThreshold() {
        String messageBody = generateStringWithLength(AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE);
        String attributeKey = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        String attributeValue = generateStringWithLength(LESS_THAN_SNS_SIZE_LIMIT);

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withStringValue(attributeValue);

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        publishRequest.addMessageAttributesEntry(attributeKey, messageAttributeValue);

        Long expectedSize = Util.getStringSizeInBytes(attributeKey) + Util.getStringSizeInBytes(attributeValue);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (AmazonClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Total size of Message attributes is "
                    + expectedSize + " bytes which is larger than the threshold of " + AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE
                    + " Bytes. Consider including the payload in the message body instead of message attributes."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenSizeOfMessageAttributeValueIsLargerThanThreshold() {
        String messageBody = generateStringWithLength(AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE);
        String attributeKey = generateStringWithLength(LESS_THAN_SNS_SIZE_LIMIT);
        String attributeValue = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withStringValue(attributeValue);

        PublishRequest publishRequest = new PublishRequest(SNS_TOPIC_ARN, messageBody);
        publishRequest.addMessageAttributesEntry(attributeKey, messageAttributeValue);

        long expectedSize = Util.getStringSizeInBytes(attributeKey) + Util.getStringSizeInBytes(attributeValue);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (AmazonClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Total size of Message attributes is "
                    + expectedSize + " bytes which is larger than the threshold of " + AmazonSNSExtendedClient.SNS_DEFAULT_MESSAGE_SIZE
                    + " Bytes. Consider including the payload in the message body instead of message attributes."));
        }
    }

    private String generateStringWithLength(int messageLength) {
        char[] charArray = new char[messageLength];
        Arrays.fill(charArray, 'x');
        return new String(charArray);
    }
}
