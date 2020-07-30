## Amazon SNS Extended Client Library for Java

The **Amazon SNS Extended Client Library for Java** enables you to publish messages that are greater than the current SNS limit of 256 KB, up to a maximum of 2 GB.
It saves the actual payload in S3 and publishes the reference of the stored S3 object to the topic. Subscribed SQS queues can use SQSExtendedClient to dereference and retrieve the payload from S3.

You can download release builds through the [releases section of this](https://github.com/awslabs/amazon-sns-java-extended-client-lib) project.

For more information on using the amazon-sns-java-extended-client-lib, see our getting started guide [here](blog post url will be added here).

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
* **Further information about Amazon SNS** - Read the [API documentation](http://aws.amazon.com/documentation/sns/).

## Contribution
See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License
This project is licensed under the Apache-2.0 License.
