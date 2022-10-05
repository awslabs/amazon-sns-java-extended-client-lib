package software.amazon.sns;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.payloadoffloading.PayloadStorageConfiguration;
import software.amazon.payloadoffloading.ServerSideEncryptionStrategy;

public class SNSExtendedClientConfiguration extends PayloadStorageConfiguration {
    static final int SNS_DEFAULT_MESSAGE_SIZE = 262144;

    public SNSExtendedClientConfiguration() {
        super();
        setPayloadSizeThreshold(SNS_DEFAULT_MESSAGE_SIZE);
    }

    public SNSExtendedClientConfiguration(SNSExtendedClientConfiguration snsExtendedClientConfiguration) {
        super(snsExtendedClientConfiguration);
    }

    @Override
    public SNSExtendedClientConfiguration withAlwaysThroughS3(boolean alwaysThroughS3) {
        this.setAlwaysThroughS3(alwaysThroughS3);
        return this;
    }

    @Override
    public SNSExtendedClientConfiguration withPayloadSupportEnabled(S3Client s3, String s3BucketName) {
        this.setPayloadSupportEnabled(s3, s3BucketName);
        return this;
    }

    @Override
    public SNSExtendedClientConfiguration withServerSideEncryption(ServerSideEncryptionStrategy serverSideEncryptionStrategy) {
        this.setServerSideEncryptionStrategy(serverSideEncryptionStrategy);
        return this;
    }

    @Override
    public SNSExtendedClientConfiguration withPayloadSizeThreshold(int payloadSizeThreshold) {
        this.setPayloadSizeThreshold(payloadSizeThreshold);
        return this;
    }

    @Override
    public SNSExtendedClientConfiguration withPayloadSupportDisabled() {
        this.setPayloadSupportDisabled();
        return this;
    }
}
