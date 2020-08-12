package software.amazon.sns;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.SSEAwsKeyManagementParams;
import software.amazon.payloadoffloading.PayloadStorageConfiguration;

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
    public SNSExtendedClientConfiguration withPayloadSupportEnabled(AmazonS3 s3, String s3BucketName) {
        this.setPayloadSupportEnabled(s3, s3BucketName);
        return this;
    }

    @Override
    public SNSExtendedClientConfiguration withSSEAwsKeyManagementParams(SSEAwsKeyManagementParams sseAwsKeyManagementParams) {
        this.setSSEAwsKeyManagementParams(sseAwsKeyManagementParams);
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
