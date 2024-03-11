package software.amazon.sns;

import com.amazon.sqs.javamessaging.SQSExtendedClientConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.payloadoffloading.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.amazon.sns.SNSExtendedClientConstants.MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE;
import static software.amazon.sns.SNSExtendedClientConstants.SNS_DEFAULT_MESSAGE_SIZE;
import static software.amazon.sns.SNSExtendedClientConstants.USER_AGENT_HEADER_NAME;

public class AmazonSNSExtendedAsyncClientTest {

    private static final String S3_BUCKET_NAME = "test-bucket-name";
    private static final String SNS_TOPIC_ARN = "test-topic-arn";
    private static final int LESS_THAN_SNS_SIZE_LIMIT = 3;
    private static final int MORE_THAN_SNS_SIZE_LIMIT = SNS_DEFAULT_MESSAGE_SIZE + 1;
    // should be > 1 and << SNSExtendedAsyncClientConfiguration.SNS_DEFAULT_MESSAGE_SIZE
    private static final int ARBITRARY_SMALLER_THRESHOLD = 500;

    private SnsAsyncClient extendedSnsWithDefaultConfig;
    private SnsAsyncClient mockSnsBackend;
    private S3AsyncClient mockS3;

    @Before
    public void setupClient() {
        mockS3 = mock(S3AsyncClient.class);
        mockSnsBackend = mock(SnsAsyncClient.class);
        when(mockS3.putObject(any(PutObjectRequest.class), isA(AsyncRequestBody.class))).thenReturn(
                CompletableFuture.completedFuture(null));

        SNSExtendedAsyncClientConfiguration SNSExtendedAsyncClientConfiguration = new SNSExtendedAsyncClientConfiguration()
                .withPayloadSupportEnabled(mockS3, S3_BUCKET_NAME)
                .withPayloadSizeThreshold(SNS_DEFAULT_MESSAGE_SIZE);

        extendedSnsWithDefaultConfig = spy(new AmazonSNSExtendedAsyncClient(mockSnsBackend, SNSExtendedAsyncClientConfiguration));
    }

    @Test
    public void testPublishLargeMessageS3IsUsed() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        PublishRequest publishRequest = PublishRequest.builder().topicArn(SNS_TOPIC_ARN).message(messageBody).build();
        extendedSnsWithDefaultConfig.publish(publishRequest);

        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class), any(AsyncRequestBody.class));
        ArgumentCaptor<PublishRequest> publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(mockSnsBackend, times(1)).publish(publishRequestCaptor.capture());

        Map<String, MessageAttributeValue> attributes = publishRequestCaptor.getValue().messageAttributes();
        Assert.assertEquals("Number", attributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME).dataType());

        Assert.assertEquals(messageBody.length(), Integer.parseInt(attributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME).stringValue()));
    }

    @Test
    public void testPublishLargeMessageS3IsUsedWithS3Key() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        PublishRequest.Builder publishRequestBuilder = PublishRequest.builder().topicArn(SNS_TOPIC_ARN).message(messageBody);
        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();
        attrs.put("S3Key", MessageAttributeValue.builder().stringValue("value").build());
        publishRequestBuilder.messageAttributes(attrs);
        extendedSnsWithDefaultConfig.publish(publishRequestBuilder.build());

        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class), any(AsyncRequestBody.class));
        ArgumentCaptor<PublishRequest> publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(mockSnsBackend, times(1)).publish(publishRequestCaptor.capture());

        Map<String, MessageAttributeValue> attributes = publishRequestCaptor.getValue().messageAttributes();
        Assert.assertEquals("Number", attributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME).dataType());

        Assert.assertEquals(messageBody.length(), Integer.parseInt(attributes.get(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME).stringValue()));
    }

    @Test
    public void testPublishSmallMessageS3IsNotUsed() {
        String messageBody = generateStringWithLength(SNS_DEFAULT_MESSAGE_SIZE);

        PublishRequest publishRequest = PublishRequest.builder().topicArn(SNS_TOPIC_ARN).message(messageBody).build();
        extendedSnsWithDefaultConfig.publish(publishRequest);

        verify(mockS3, never()).putObject(any(PutObjectRequest.class), any(AsyncRequestBody.class));
        ArgumentCaptor<PublishRequest> publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(mockSnsBackend, times(1)).publish(publishRequestCaptor.capture());

        Map<String, MessageAttributeValue> attributes = publishRequestCaptor.getValue().messageAttributes();
        Assert.assertTrue(attributes.isEmpty());
    }

    @Test
    public void testPublishMessageWithLargePayloadSupportDisabledS3IsNotUsedAndSqsBackendIsResponsibleToFailIt() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        SNSExtendedAsyncClientConfiguration SNSExtendedAsyncClientConfiguration = new SNSExtendedAsyncClientConfiguration()
                .withPayloadSupportDisabled();
        SnsAsyncClient snsExtended = spy(new AmazonSNSExtendedAsyncClient(mockSnsBackend, SNSExtendedAsyncClientConfiguration));

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .overrideConfiguration(
                AwsRequestOverrideConfiguration.builder()
                    .putHeader(USER_AGENT_HEADER_NAME, AmazonSNSExtendedAsyncClient.USER_AGENT_HEADER)
                    .build())
            .build();
        snsExtended.publish(publishRequest);

        verify(mockS3, never()).putObject(any(PutObjectRequest.class), any(AsyncRequestBody.class));
        verify(mockSnsBackend).publish(eq(publishRequest));
    }

    @Test
    public void testPublishMessageWithJSONMessageStructureThrowsAmazonClientException() {
        String messageBody = "{\"key1\":\"value1\",\"key2\":8.0}";

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .messageStructure(MULTIPLE_PROTOCOL_MESSAGE_STRUCTURE)
            .build();

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown.");
        } catch (SdkClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("SNS extended client does not support sending JSON messages"));
        }
    }

    @Test
    public void testPublishMessageWithAlwaysThroughS3AndSmallMessageS3IsUsed() {
        String messageBody = generateStringWithLength(LESS_THAN_SNS_SIZE_LIMIT);
        SNSExtendedAsyncClientConfiguration SNSExtendedAsyncClientConfiguration = new SNSExtendedAsyncClientConfiguration()
                .withPayloadSupportEnabled(mockS3, S3_BUCKET_NAME)
                .withAlwaysThroughS3(true);

        SnsAsyncClient snsExtended = spy(new AmazonSNSExtendedAsyncClient(mock(SnsAsyncClient.class), SNSExtendedAsyncClientConfiguration));

        snsExtended.publish(PublishRequest.builder().topicArn(SNS_TOPIC_ARN).message(messageBody).build());

        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class), any(AsyncRequestBody.class));
    }

    @Test
    public void testPublishMessageWithSetMessageSizeThresholdThresholdIsHonored() {
        String messageBody = generateStringWithLength(ARBITRARY_SMALLER_THRESHOLD * 2);
        SNSExtendedAsyncClientConfiguration SNSExtendedAsyncClientConfiguration = new SNSExtendedAsyncClientConfiguration()
                .withPayloadSupportEnabled(mockS3, S3_BUCKET_NAME)
                .withPayloadSizeThreshold(ARBITRARY_SMALLER_THRESHOLD);

        SnsAsyncClient snsExtended = spy(new AmazonSNSExtendedAsyncClient(mock(SnsAsyncClient.class), SNSExtendedAsyncClientConfiguration));

        snsExtended.publish(PublishRequest.builder().topicArn(SNS_TOPIC_ARN).message(messageBody).build());
        verify(mockS3, times(1)).putObject(any(PutObjectRequest.class), any(AsyncRequestBody.class));
    }

    @Test
    public void testPublishRequestDoesNotAlterPublishRequest() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();
        attrs.put("SampleKey", MessageAttributeValue.builder().stringValue("value").build());

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .messageAttributes(attrs)
            .build();
        extendedSnsWithDefaultConfig.publish(publishRequest);

        Assert.assertEquals(messageBody, publishRequest.message());
        Assert.assertEquals(attrs, publishRequest.messageAttributes());
    }

    @Test
    public void testThrowAmazonClientExceptionWhenReservedAttributeNameIsAlreadyUsed() {
        String messageBody = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        MessageAttributeValue messageAttributeValue = MessageAttributeValue.builder()
            .dataType("Number")
            .stringValue(Util.getStringSizeInBytes(messageBody) + "")
            .build();
        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();
        attrs.put(SQSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .messageAttributes(attrs)
            .build();

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");
        } catch (SdkClientException exception) {
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
            attrs.put("key" + index, MessageAttributeValue.builder().dataType("String").stringValue("value" + index).build());
        }

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .messageAttributes(attrs)
            .build();

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (SdkClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Number of message attributes [" + attributeNumber
                    + "] exceeds the maximum allowed for large-payload messages ["
                    + SQSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + "]."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenSizeOfMessageAttributeKeyIsLargerThanThreshold() {
        String messageBody = generateStringWithLength(SNS_DEFAULT_MESSAGE_SIZE);
        String attributeKey = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);
        String attributeValue = generateStringWithLength(LESS_THAN_SNS_SIZE_LIMIT);

        MessageAttributeValue messageAttributeValue = MessageAttributeValue.builder().stringValue(attributeValue).build();
        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();
        attrs.put(attributeKey, messageAttributeValue);

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .messageAttributes(attrs)
            .build();

        long expectedSize = Util.getStringSizeInBytes(attributeKey) + Util.getStringSizeInBytes(attributeValue);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (SdkClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Total size of Message attributes is "
                    + expectedSize + " bytes which is larger than the threshold of " + SNS_DEFAULT_MESSAGE_SIZE
                    + " Bytes. Consider including the payload in the message body instead of message attributes."));
        }
    }

    @Test
    public void testThrowAmazonClientExceptionWhenSizeOfMessageAttributeValueIsLargerThanThreshold() {
        String messageBody = generateStringWithLength(SNS_DEFAULT_MESSAGE_SIZE);
        String attributeKey = generateStringWithLength(LESS_THAN_SNS_SIZE_LIMIT);
        String attributeValue = generateStringWithLength(MORE_THAN_SNS_SIZE_LIMIT);

        MessageAttributeValue messageAttributeValue = MessageAttributeValue.builder().stringValue(attributeValue).build();
        HashMap<String, MessageAttributeValue> attrs = new HashMap<>();
        attrs.put(attributeKey, messageAttributeValue);

        PublishRequest publishRequest = PublishRequest.builder()
            .topicArn(SNS_TOPIC_ARN)
            .message(messageBody)
            .messageAttributes(attrs)
            .build();

        long expectedSize = Util.getStringSizeInBytes(attributeKey) + Util.getStringSizeInBytes(attributeValue);

        try {
            extendedSnsWithDefaultConfig.publish(publishRequest);
            Assert.fail("An exception should have been thrown");

        } catch (SdkClientException exception) {
            Assert.assertTrue(exception.getMessage().contains("Total size of Message attributes is "
                    + expectedSize + " bytes which is larger than the threshold of " + SNS_DEFAULT_MESSAGE_SIZE
                    + " Bytes. Consider including the payload in the message body instead of message attributes."));
        }
    }

    private String generateStringWithLength(int messageLength) {
        char[] charArray = new char[messageLength];
        Arrays.fill(charArray, 'x');
        return new String(charArray);
    }
}
