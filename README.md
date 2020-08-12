## Amazon SNS Extended Client Library for Java

The **Amazon SNS Extended Client Library for Java** enables you to publish messages that are greater than the current SNS limit of 256 KB, up to a maximum of 2 GB.
It saves the actual payload in S3 and publishes the reference of the stored S3 object to the topic. Subscribed SQS queues can use [Amazon SQS Extended Client Library](https://github.com/awslabs/amazon-sqs-java-extended-client-lib) to dereference and retrieve the payload from S3. Other end-points, such as Lambda, can use [Payload Offloading Java Common Library for AWS](https://github.com/awslabs/payload-offloading-java-common-lib-for-aws) to dereference and retrieve the payload.


## Getting Started

* **Sign up for AWS** -- Before you begin, you need an AWS account. For more information about creating an AWS account, see [create and activate aws account](https://aws.amazon.com/premiumsupport/knowledge-center/create-and-activate-aws-account/).
* **Minimum requirements** Java 8 (or later) and [Maven 3](http://maven.apache.org/).
* **Download** -- Download the [latest preview release](https://github.com/awslabs/amazon-sns-java-extended-client-lib/releases) or pick it up from Maven:
```xml
  <dependency>
    <groupId>software.amazon.sns</groupId>
    <artifactId>sns-extended-client</artifactId>
    <version>1.0.0</version>
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
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.SetSubscriptionAttributesRequest;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import software.amazon.sns.AmazonSNSExtendedClient;

public class Example {

    public static void main(String[] args) {
        final String BUCKET_NAME = "extended-payload-storage";
        final String TOPIC_NAME = "extended-client-topic";
        final String QUEUE_NAME = "extended-client-queue";

        //Exceeding message threshold size (in bytes) will store message content in S3 and publish
        //the S3 reference to the content throught the SNS topic
        //For example, to store the content of message in S3 that exceed maximum allowed SQS
        //message size, use SQSExtendedClientConstants.DEFAULT_MESSAGE_SIZE_THRESHOLD
        final int EXTENDED_STORAGE_MESSAGE_SIZE_THRESHOLD = 8;

        //Initialize SNS, SQS and S3 clients
        final AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion("us-east-1").build();
        final AmazonSQS sqsClient = AmazonSQSClientBuilder.standard().withRegion("us-east-1").build();
        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();

        //Create bucket, topic, queue and subscription
        s3Client.createBucket(BUCKET_NAME);
        final String topicArn = snsClient.createTopic(
                new CreateTopicRequest().withName(TOPIC_NAME)
        ).getTopicArn();
        final String queueUrl = sqsClient.createQueue(
                new CreateQueueRequest().withQueueName(QUEUE_NAME)
        ).getQueueUrl();
        final String subscriptionArn = Topics.subscribeQueue(
                snsClient, sqsClient, topicArn, queueUrl
        );

        //To read message content stored in S3 transparently through SQS extended client,
        //set the RawMessageDelivery subscription attribute to TRUE
        final SetSubscriptionAttributesRequest subscriptionAttributesRequest = new SetSubscriptionAttributesRequest();
        subscriptionAttributesRequest.setSubscriptionArn(subscriptionArn);
        subscriptionAttributesRequest.setAttributeName("RawMessageDelivery");
        subscriptionAttributesRequest.setAttributeValue("TRUE");
        snsClient.setSubscriptionAttributes(subscriptionAttributesRequest);
        
        //Initialize SNS extended client
        //PayloadSizeThreshold triggers message content storage in S3 when the threshold is exceeded
        //To store all messages content in S3, use AlwaysThroughS3 flag
        final ExtendedClientConfiguration storageConfiguration = new ExtendedClientConfiguration()
                .withPayloadSupportEnabled(s3Client, BUCKET_NAME)
                .withPayloadSizeThreshold(EXTENDED_STORAGE_MESSAGE_SIZE_THRESHOLD);
        final AmazonSNSExtendedClient snsExtendedClient = new AmazonSNSExtendedClient(snsClient, storageConfiguration);

        final String message = "This message is stored in S3.";
        //Publish message via SNS with storage in S3
        snsExtendedClient.publish(topicArn, message);

        //Initialzie SQS extended client
        final ExtendedClientConfiguration sqsExtendedClientConfiguration = new ExtendedClientConfiguration()
                .withPayloadSupportEnabled(
                        storageConfiguration.getAmazonS3Client(), storageConfiguration.getS3BucketName())
                .withPayloadSizeThreshold(storageConfiguration.getPayloadSizeThreshold())
                .withLegacyReservedAttributeNameDisabled();
        final AmazonSQSExtendedClient sqsExtendedClient =
                new AmazonSQSExtendedClient(sqsClient, sqsExtendedClientConfiguration);

        //Read the message from the queue
        final ReceiveMessageResult result = sqsExtendedClient.receiveMessage(queueUrl);
        System.out.println("Receive message is " + result.getMessages().get(0).getBody());
    }
}

```

## Releases
You can download release builds through the [releases](https://github.com/awslabs/amazon-sns-java-extended-client-lib) section of this project.

## Contribution
See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License
This project is licensed under the Apache-2.0 License.
