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
final String BUCKET_NAME = "payload-storage";
final String TOPIC_NAME = "extended-client-topic";
final String QUEUE_NAME = "extended-client-queue";

//Initialize SNS client and create a new topic
AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();
AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

//Create topic, queue and subscription
String topicArn = snsClient.createTopic(
        new CreateTopicRequest().withName(TOPIC_NAME)
).getTopicArn();
String queueUrl = sqsClient.createQueue(
        new CreateQueueRequest().withQueueName(QUEUE_NAME)
).getQueueUrl();
Topics.subscribeQueue(
        snsClient, sqsClient, topicArn, queueUrl
);

//Initialize SNS extended client
ExtendedClientConfiguration storageConfiguration = new ExtendedClientConfiguration()
        .withPayloadSupportEnabled(s3Client, BUCKET_NAME)
        .withAlwaysThroughS3(true);
AmazonSNSExtendedClient extendedClient = new AmazonSNSExtendedClient(snsClient, storageConfiguration);

//Publish message via SNS with storage in S3
extendedClient.publish(topicArn, "This message is stored in S3");

//Initialzie SQS extended client
ExtendedClientConfiguration sqsExtendedClientConfiguration = new ExtendedClientConfiguration()
        .withPayloadSupportEnabled(storageConfiguration.getAmazonS3Client(), storageConfiguration.getS3BucketName())
        .withAlwaysThroughS3(storageConfiguration.isAlwaysThroughS3())
        .withLegacyReservedAttributeNameDisabled();
AmazonSQSExtendedClient sqsExtendedClient = new AmazonSQSExtendedClient(sqsClient, sqsExtendedClientConfiguration);

//Read the message from the queue
ReceiveMessageResult message = sqsExtendedClient.receiveMessage(queueUrl);
System.out.println(message.getMessages().get(0).getBody());
```

## Releases
You can download release builds through the [releases](https://github.com/awslabs/amazon-sns-java-extended-client-lib) section of this project.

## Contribution
See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License
This project is licensed under the Apache-2.0 License.
