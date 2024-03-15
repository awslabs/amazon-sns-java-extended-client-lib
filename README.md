## Amazon SNS Extended Client Library for Java

The **Amazon SNS Extended Client Library for Java** enables you to publish messages that are greater than the current SNS limit of 256 KB, up to a maximum of 2 GB.
It saves the actual payload in S3 and publishes the reference of the stored S3 object to the topic. Subscribed SQS queues can use [Amazon SQS Extended Client Library](https://github.com/awslabs/amazon-sqs-java-extended-client-lib) to dereference and retrieve the payload from S3. Other end-points, such as Lambda, can use [Payload Offloading Java Common Library for AWS](https://github.com/awslabs/payload-offloading-java-common-lib-for-aws) to dereference and retrieve the payload.


## Getting Started

* **Sign up for AWS** -- Before you begin, you need an AWS account. For more information about creating an AWS account, see [create and activate aws account](https://aws.amazon.com/premiumsupport/knowledge-center/create-and-activate-aws-account/).
* **Minimum requirements** Java 8 (or later) and [Maven 3](http://maven.apache.org/).
* **Download** -- Download the [latest preview release](https://github.com/awslabs/amazon-sns-java-extended-client-lib/releases) or pick it up from Maven:

### Version 2.x (AWS Java SDKv2)
```xml
  <dependency>
    <groupId>software.amazon.sns</groupId>
    <artifactId>sns-extended-client</artifactId>
    <version>2.1.0</version>
    <type>jar</type>
  </dependency>
```

### Version 1.x (AWS Java SDKv1)
```xml
  <dependency>
    <groupId>software.amazon.sns</groupId>
    <artifactId>sns-extended-client</artifactId>
    <version>1.1.2</version>
    <type>jar</type>
  </dependency>
```


## S3 Message Storage Configuration
The library relies on the Payload Offloading Java Common Library for AWS for message storage and retrieval. The following S3 message storage configuration options are available:
* Custom message size threshhold: messages with their payload and attribute size exceeding this limit will automatically be stored in S3. It is also possible to force all messages to be stored in S3.
* Custom KMS key configuration for server-side encryption
* Bucket name for storing message payloads


## Publishing messages to SNS topics and receiving them from SQS subscribers
Below is the code sample that creates a sample topic and queue, subscribes the queue to receive messages from the topic and publishes a test message. The message payload is stored in S3 and the reference to it is published. The SQS Extended Client is used to receive the message.

```java
import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SetSubscriptionAttributesRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.sns.AmazonSNSExtendedClient;
import software.amazon.sns.SNSExtendedClientConfiguration;

public class Example {

    public static void main(String[] args) {
        final String BUCKET_NAME = "extended-client-bucket";
        final String TOPIC_NAME = "extended-client-topic";
        final String QUEUE_NAME = "extended-client-queue";
        final Region region = Region.US_WEST_2;

        //Message threshold controls the maximum message size that will be allowed to be published
        //through SNS using the extended client. Payload of messages exceeding this value will be stored in
        //S3. The default value of this parameter is 256 KB which is the maximum message size in SNS (and SQS).
        final int EXTENDED_STORAGE_MESSAGE_SIZE_THRESHOLD = 32;

        //Initialize SNS, SQS and S3 clients
        final SnsClient snsClient = SnsClient.builder().region(region).build();
        final SqsClient sqsClient = SqsClient.builder().region(region).build();
        final S3Client s3Client = S3Client.builder().region(region).build();

        //Create bucket, topic, queue and subscription
        s3Client.createBucket(CreateBucketRequest.builder().bucket(BUCKET_NAME).build());
        final String topicArn = snsClient.createTopic(
            CreateTopicRequest.builder().name(TOPIC_NAME).build()
        ).topicArn();
        final String queueUrl = sqsClient.createQueue(
            CreateQueueRequest.builder().queueName(QUEUE_NAME).build()
        ).queueUrl();
        final String subscriptionArn = snsClient.subscribe(
            SubscribeRequest.builder().topicArn(topicArn).endpoint(queueUrl).build()
        ).subscriptionArn();

        //To read message content stored in S3 transparently through SQS extended client,
        //set the RawMessageDelivery subscription attribute to TRUE
        final SetSubscriptionAttributesRequest subscriptionAttributesRequest = SetSubscriptionAttributesRequest.builder()
            .subscriptionArn(subscriptionArn)
            .attributeName("RawMessageDelivery")
            .attributeValue("TRUE")
            .build();
        snsClient.setSubscriptionAttributes(subscriptionAttributesRequest);

        //Initialize SNS extended client
        //PayloadSizeThreshold triggers message content storage in S3 when the threshold is exceeded
        //To store all messages content in S3, use AlwaysThroughS3 flag
        final SNSExtendedClientConfiguration snsExtendedClientConfiguration = new SNSExtendedClientConfiguration()
            .withPayloadSupportEnabled(s3Client, BUCKET_NAME)
            .withPayloadSizeThreshold(EXTENDED_STORAGE_MESSAGE_SIZE_THRESHOLD);
        final AmazonSNSExtendedClient snsExtendedClient = new AmazonSNSExtendedClient(snsClient, snsExtendedClientConfiguration);

        //Publish message via SNS with storage in S3
        final String message = "This message is stored in S3 as it exceeds the threshold of 32 bytes set above.";
        snsExtendedClient.publish(PublishRequest.builder().topicArn(topicArn).message(message).build());

        //Initialize SQS extended client
        final ExtendedClientConfiguration sqsExtendedClientConfiguration = new ExtendedClientConfiguration()
            .withPayloadSupportEnabled(s3Client, BUCKET_NAME);
        final AmazonSQSExtendedClient sqsExtendedClient =
            new AmazonSQSExtendedClient(sqsClient, sqsExtendedClientConfiguration);

        //Read the message from the queue
        final ReceiveMessageResponse response = sqsExtendedClient.receiveMessage(ReceiveMessageRequest.builder().queueUrl(queueUrl).build());
        System.out.println("Received message is " + response.messages().get(0).body());
    }
}
```

Output:
``` 
Aug 12, 2020 12:42:31 PM software.amazon.payloadoffloading.PayloadStorageConfiguration setPayloadSupportEnabled
INFO: Payload support enabled.
Aug 12, 2020 12:42:32 PM software.amazon.payloadoffloading.S3BackedPayloadStore storeOriginalPayload
INFO: S3 object created, Bucket name: extended-client-bucket, Object key: 09900296-35fc-4927-91f6-768ecf8dafa4.
Aug 12, 2020 12:42:32 PM software.amazon.payloadoffloading.PayloadStorageConfiguration setPayloadSupportEnabled
INFO: Payload support enabled.
Received message is This message is stored in S3. This message is stored in S3.
```

## Releases
You can download release builds through the [releases](https://github.com/awslabs/amazon-sns-java-extended-client-lib) section of this project.

## Contribution
See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License
This project is licensed under the Apache-2.0 License.
