package software.amazon.sns;

import software.amazon.awssdk.annotations.NotThreadSafe;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.payloadoffloading.PayloadStorageAsyncConfiguration;
import software.amazon.payloadoffloading.ServerSideEncryptionStrategy;

import static software.amazon.sns.SNSExtendedClientConstants.SNS_DEFAULT_MESSAGE_SIZE;

@NotThreadSafe
public class SNSExtendedAsyncClientConfiguration extends PayloadStorageAsyncConfiguration {

    public SNSExtendedAsyncClientConfiguration() {
        this.setPayloadSizeThreshold(SNS_DEFAULT_MESSAGE_SIZE);
    }

    public SNSExtendedAsyncClientConfiguration(SNSExtendedAsyncClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    @Override
    public SNSExtendedAsyncClientConfiguration withAlwaysThroughS3(boolean alwaysThroughS3) {
        this.setAlwaysThroughS3(alwaysThroughS3);
        return this;
    }

    @Override
    public SNSExtendedAsyncClientConfiguration withPayloadSupportEnabled(S3AsyncClient s3, String s3BucketName) {
        this.setPayloadSupportEnabled(s3, s3BucketName);
        return this;
    }

    @Override
    public SNSExtendedAsyncClientConfiguration withServerSideEncryption(ServerSideEncryptionStrategy serverSideEncryptionStrategy) {
        this.setServerSideEncryptionStrategy(serverSideEncryptionStrategy);
        return this;
    }

    @Override
    public SNSExtendedAsyncClientConfiguration withPayloadSizeThreshold(int payloadSizeThreshold) {
        this.setPayloadSizeThreshold(payloadSizeThreshold);
        return this;
    }

    @Override
    public SNSExtendedAsyncClientConfiguration withPayloadSupportDisabled() {
        this.setPayloadSupportDisabled();
        return this;
    }
}
