package software.amazon.sns;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;

import java.util.List;

abstract class AmazonSNSExtendedClientBase implements AmazonSNS {
    static final int SNS_DEFAULT_MESSAGE_SIZE = 262144;

    private final AmazonSNS amazonSNSToBeExtended;

    public AmazonSNSExtendedClientBase(AmazonSNS amazonSNSToBeExtended) {
        this.amazonSNSToBeExtended = amazonSNSToBeExtended;
    }

    /**
     * <p>
     * Retrieves the endpoint attributes for a device on one of the supported push notification services, such as GCM
     * and APNS. For more information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using
     * Amazon SNS Mobile Push Notifications</a>.
     * </p>
     *
     * @param getEndpointAttributesRequest Input for GetEndpointAttributes action.
     * @return Result of the GetEndpointAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.GetEndpointAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetEndpointAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetEndpointAttributesResult getEndpointAttributes(GetEndpointAttributesRequest getEndpointAttributesRequest) {
        return amazonSNSToBeExtended.getEndpointAttributes(getEndpointAttributesRequest);
    }

    /**
     * Overrides the default endpoint for this client ("https://sns.us-east-1.amazonaws.com"). Callers can use this
     * method to control which AWS region they want to work with.
     * <p>
     * Callers can pass in just the endpoint (ex: "sns.us-east-1.amazonaws.com") or a full URL, including the protocol
     * (ex: "https://sns.us-east-1.amazonaws.com"). If the protocol is not specified here, the default protocol from
     * this client's {@link ClientConfiguration} will be used, which by default is HTTPS.
     * <p>
     * For more information on using AWS regions with the AWS SDK for Java, and a complete list of all available
     * endpoints for all AWS services, see: <a
     * href="http://developer.amazonwebservices.com/connect/entry.jspa?externalID=3912">
     * http://developer.amazonwebservices.com/connect/entry.jspa?externalID=3912</a>
     * <p>
     * <b>This method is not threadsafe. An endpoint should be configured when the client is created and before any
     * service requests are made. Changing it afterwards creates inevitable race conditions for any service requests in
     * transit or retrying.</b>
     *
     * @param endpoint The endpoint (ex: "sns.us-east-1.amazonaws.com") or a full URL, including the protocol (ex:
     *                 "https://sns.us-east-1.amazonaws.com") of the region specific AWS endpoint this client will communicate
     *                 with.
     * @deprecated use {@link AwsClientBuilder#setEndpointConfiguration(AwsClientBuilder.EndpointConfiguration)} for
     * example:
     * {@code builder.setEndpointConfiguration(new EndpointConfiguration(endpoint, signingRegion));}
     */
    @Override
    @Deprecated
    public void setEndpoint(String endpoint) {
        amazonSNSToBeExtended.setEndpoint(endpoint);
    }

    /**
     * An alternative to {@link AmazonSNS#setEndpoint(String)}, sets the regional endpoint for this client's service
     * calls. Callers can use this method to control which AWS region they want to work with.
     * <p>
     * By default, all service endpoints in all regions use the https protocol. To use http instead, specify it in the
     * {@link ClientConfiguration} supplied at construction.
     * <p>
     * <b>This method is not threadsafe. A region should be configured when the client is created and before any service
     * requests are made. Changing it afterwards creates inevitable race conditions for any service requests in transit
     * or retrying.</b>
     *
     * @param region The region this client will communicate with. See {@link Region#getRegion(Regions)}
     *               for accessing a given region. Must not be null and must be a region where the service is available.
     * @see Region#getRegion(Regions)
     * @see Region#createClient(Class, AWSCredentialsProvider, ClientConfiguration)
     * @see Region#isServiceSupported(String)
     * @deprecated use {@link AwsClientBuilder#setRegion(String)}
     */
    @Override
    @Deprecated
    public void setRegion(Region region) {
        amazonSNSToBeExtended.setRegion(region);
    }

    /**
     * <p>
     * Adds a statement to a topic's access control policy, granting access for the specified AWS accounts to the
     * specified actions.
     * </p>
     *
     * @param addPermissionRequest
     * @return Result of the AddPermission operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.AddPermission
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/AddPermission" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public AddPermissionResult addPermission(AddPermissionRequest addPermissionRequest) {
        return amazonSNSToBeExtended.addPermission(addPermissionRequest);
    }

    /**
     * Simplified method form for invoking the AddPermission operation.
     *
     * @param topicArn
     * @param label
     * @param aWSAccountIds
     * @param actionNames
     * @see #addPermission(AddPermissionRequest)
     */
    @Override
    public AddPermissionResult addPermission(String topicArn, String label, List<String> aWSAccountIds, List<String> actionNames) {
        return amazonSNSToBeExtended.addPermission(topicArn, label, aWSAccountIds, actionNames);
    }

    /**
     * <p>
     * Accepts a phone number and indicates whether the phone holder has opted out of receiving SMS messages from your
     * account. You cannot send SMS messages to a number that is opted out.
     * </p>
     * <p>
     * To resume sending messages, you can opt in the number by using the <code>OptInPhoneNumber</code> action.
     * </p>
     *
     * @param checkIfPhoneNumberIsOptedOutRequest The input for the <code>CheckIfPhoneNumberIsOptedOut</code> action.
     * @return Result of the CheckIfPhoneNumberIsOptedOut operation returned by the service.
     * @throws ThrottledException          Indicates that the rate at which requests have been submitted for this action exceeds the limit for your
     *                                     account.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @sample AmazonSNS.CheckIfPhoneNumberIsOptedOut
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CheckIfPhoneNumberIsOptedOut"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public CheckIfPhoneNumberIsOptedOutResult checkIfPhoneNumberIsOptedOut(CheckIfPhoneNumberIsOptedOutRequest checkIfPhoneNumberIsOptedOutRequest) {
        return amazonSNSToBeExtended.checkIfPhoneNumberIsOptedOut(checkIfPhoneNumberIsOptedOutRequest);
    }

    /**
     * <p>
     * Verifies an endpoint owner's intent to receive messages by validating the token sent to the endpoint by an
     * earlier <code>Subscribe</code> action. If the token is valid, the action creates a new subscription and returns
     * its Amazon Resource Name (ARN). This call requires an AWS signature only when the
     * <code>AuthenticateOnUnsubscribe</code> flag is set to "true".
     * </p>
     *
     * @param confirmSubscriptionRequest Input for ConfirmSubscription action.
     * @return Result of the ConfirmSubscription operation returned by the service.
     * @throws SubscriptionLimitExceededException Indicates that the customer already owns the maximum allowed number of subscriptions.
     * @throws InvalidParameterException          Indicates that a request parameter does not comply with the associated constraints.
     * @throws NotFoundException                  Indicates that the requested resource does not exist.
     * @throws InternalErrorException             Indicates an internal service error.
     * @throws AuthorizationErrorException        Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.ConfirmSubscription
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ConfirmSubscription" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ConfirmSubscriptionResult confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return amazonSNSToBeExtended.confirmSubscription(confirmSubscriptionRequest);
    }

    /**
     * Simplified method form for invoking the ConfirmSubscription operation.
     *
     * @param topicArn
     * @param token
     * @param authenticateOnUnsubscribe
     * @see #confirmSubscription(ConfirmSubscriptionRequest)
     */
    @Override
    public ConfirmSubscriptionResult confirmSubscription(String topicArn, String token, String authenticateOnUnsubscribe) {
        return amazonSNSToBeExtended.confirmSubscription(topicArn, token, authenticateOnUnsubscribe);
    }

    /**
     * Simplified method form for invoking the ConfirmSubscription operation.
     *
     * @param topicArn
     * @param token
     * @see #confirmSubscription(ConfirmSubscriptionRequest)
     */
    @Override
    public ConfirmSubscriptionResult confirmSubscription(String topicArn, String token) {
        return amazonSNSToBeExtended.confirmSubscription(topicArn, token);
    }

    /**
     * <p>
     * Creates a platform application object for one of the supported push notification services, such as APNS and GCM,
     * to which devices and mobile apps may register. You must specify PlatformPrincipal and PlatformCredential
     * attributes when using the <code>CreatePlatformApplication</code> action. The PlatformPrincipal is received from
     * the notification service. For APNS/APNS_SANDBOX, PlatformPrincipal is "SSL certificate". For GCM,
     * PlatformPrincipal is not applicable. For ADM, PlatformPrincipal is "client id". The PlatformCredential is also
     * received from the notification service. For WNS, PlatformPrincipal is "Package Security Identifier". For MPNS,
     * PlatformPrincipal is "TLS certificate". For Baidu, PlatformPrincipal is "API key".
     * </p>
     * <p>
     * For APNS/APNS_SANDBOX, PlatformCredential is "private key". For GCM, PlatformCredential is "API key". For ADM,
     * PlatformCredential is "client secret". For WNS, PlatformCredential is "secret key". For MPNS, PlatformCredential
     * is "private key". For Baidu, PlatformCredential is "secret key". The PlatformApplicationArn that is returned when
     * using <code>CreatePlatformApplication</code> is then used as an attribute for the
     * <code>CreatePlatformEndpoint</code> action. For more information, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>. For more information about obtaining the PlatformPrincipal and PlatformCredential for each of
     * the supported push notification services, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-apns.html">Getting Started with Apple Push
     * Notification Service</a>, <a href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-adm.html">Getting Started
     * with Amazon Device Messaging</a>, <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-baidu.html">Getting Started with Baidu Cloud Push</a>,
     * <a href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-gcm.html">Getting Started with Google Cloud
     * Messaging for Android</a>, <a href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-mpns.html">Getting
     * Started with MPNS</a>, or <a href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-wns.html">Getting Started
     * with WNS</a>.
     * </p>
     *
     * @param createPlatformApplicationRequest Input for CreatePlatformApplication action.
     * @return Result of the CreatePlatformApplication operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.CreatePlatformApplication
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CreatePlatformApplication" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public CreatePlatformApplicationResult createPlatformApplication(CreatePlatformApplicationRequest createPlatformApplicationRequest) {
        return amazonSNSToBeExtended.createPlatformApplication(createPlatformApplicationRequest);
    }

    /**
     * <p>
     * Creates an endpoint for a device and mobile app on one of the supported push notification services, such as GCM
     * and APNS. <code>CreatePlatformEndpoint</code> requires the PlatformApplicationArn that is returned from
     * <code>CreatePlatformApplication</code>. The EndpointArn that is returned when using
     * <code>CreatePlatformEndpoint</code> can then be used by the <code>Publish</code> action to send a message to a
     * mobile app or by the <code>Subscribe</code> action for subscription to a topic. The
     * <code>CreatePlatformEndpoint</code> action is idempotent, so if the requester already owns an endpoint with the
     * same device token and attributes, that endpoint's ARN is returned without creating a new endpoint. For more
     * information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile
     * Push Notifications</a>.
     * </p>
     * <p>
     * When using <code>CreatePlatformEndpoint</code> with Baidu, two attributes must be provided: ChannelId and UserId.
     * The token field must also contain the ChannelId. For more information, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePushBaiduEndpoint.html">Creating an Amazon SNS Endpoint
     * for Baidu</a>.
     * </p>
     *
     * @param createPlatformEndpointRequest Input for CreatePlatformEndpoint action.
     * @return Result of the CreatePlatformEndpoint operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.CreatePlatformEndpoint
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CreatePlatformEndpoint" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public CreatePlatformEndpointResult createPlatformEndpoint(CreatePlatformEndpointRequest createPlatformEndpointRequest) {
        return amazonSNSToBeExtended.createPlatformEndpoint(createPlatformEndpointRequest);
    }

    /**
     * <p>
     * Creates a topic to which notifications can be published. Users can create at most 100,000 topics. For more
     * information, see <a href="http://aws.amazon.com/sns/">http://aws.amazon.com/sns</a>. This action is idempotent,
     * so if the requester already owns a topic with the specified name, that topic's ARN is returned without creating a
     * new topic.
     * </p>
     *
     * @param createTopicRequest Input for CreateTopic action.
     * @return Result of the CreateTopic operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws TopicLimitExceededException Indicates that the customer already owns the maximum allowed number of topics.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.CreateTopic
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CreateTopic" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public CreateTopicResult createTopic(CreateTopicRequest createTopicRequest) {
        return amazonSNSToBeExtended.createTopic(createTopicRequest);
    }

    /**
     * Simplified method form for invoking the CreateTopic operation.
     *
     * @param name
     * @see #createTopic(CreateTopicRequest)
     */
    @Override
    public CreateTopicResult createTopic(String name) {
        return amazonSNSToBeExtended.createTopic(name);
    }

    /**
     * <p>
     * Deletes the endpoint for a device and mobile app from Amazon SNS. This action is idempotent. For more
     * information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile
     * Push Notifications</a>.
     * </p>
     * <p>
     * When you delete an endpoint that is also subscribed to a topic, then you must also unsubscribe the endpoint from
     * the topic.
     * </p>
     *
     * @param deleteEndpointRequest Input for DeleteEndpoint action.
     * @return Result of the DeleteEndpoint operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.DeleteEndpoint
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeleteEndpoint" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public DeleteEndpointResult deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
        return amazonSNSToBeExtended.deleteEndpoint(deleteEndpointRequest);
    }

    /**
     * <p>
     * Deletes a platform application object for one of the supported push notification services, such as APNS and GCM.
     * For more information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS
     * Mobile Push Notifications</a>.
     * </p>
     *
     * @param deletePlatformApplicationRequest Input for DeletePlatformApplication action.
     * @return Result of the DeletePlatformApplication operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.DeletePlatformApplication
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeletePlatformApplication" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public DeletePlatformApplicationResult deletePlatformApplication(DeletePlatformApplicationRequest deletePlatformApplicationRequest) {
        return amazonSNSToBeExtended.deletePlatformApplication(deletePlatformApplicationRequest);
    }

    /**
     * <p>
     * Deletes a topic and all its subscriptions. Deleting a topic might prevent some messages previously sent to the
     * topic from being delivered to subscribers. This action is idempotent, so deleting a topic that does not exist
     * does not result in an error.
     * </p>
     *
     * @param deleteTopicRequest
     * @return Result of the DeleteTopic operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.DeleteTopic
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeleteTopic" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public DeleteTopicResult deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        return amazonSNSToBeExtended.deleteTopic(deleteTopicRequest);
    }

    /**
     * Simplified method form for invoking the DeleteTopic operation.
     *
     * @param topicArn
     * @see #deleteTopic(DeleteTopicRequest)
     */
    @Override
    public DeleteTopicResult deleteTopic(String topicArn) {
        return amazonSNSToBeExtended.deleteTopic(topicArn);
    }

    /**
     * <p>
     * Retrieves the attributes of the platform application object for the supported push notification services, such as
     * APNS and GCM. For more information, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
     * </p>
     *
     * @param getPlatformApplicationAttributesRequest Input for GetPlatformApplicationAttributes action.
     * @return Result of the GetPlatformApplicationAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.GetPlatformApplicationAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetPlatformApplicationAttributes"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public GetPlatformApplicationAttributesResult getPlatformApplicationAttributes(GetPlatformApplicationAttributesRequest getPlatformApplicationAttributesRequest) {
        return amazonSNSToBeExtended.getPlatformApplicationAttributes(getPlatformApplicationAttributesRequest);
    }

    /**
     * <p>
     * Returns the settings for sending SMS messages from your account.
     * </p>
     * <p>
     * These settings are set with the <code>SetSMSAttributes</code> action.
     * </p>
     *
     * @param getSMSAttributesRequest The input for the <code>GetSMSAttributes</code> request.
     * @return Result of the GetSMSAttributes operation returned by the service.
     * @throws ThrottledException          Indicates that the rate at which requests have been submitted for this action exceeds the limit for your
     *                                     account.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @sample AmazonSNS.GetSMSAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetSMSAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetSMSAttributesResult getSMSAttributes(GetSMSAttributesRequest getSMSAttributesRequest) {
        return amazonSNSToBeExtended.getSMSAttributes(getSMSAttributesRequest);
    }

    /**
     * <p>
     * Returns all of the properties of a subscription.
     * </p>
     *
     * @param getSubscriptionAttributesRequest Input for GetSubscriptionAttributes.
     * @return Result of the GetSubscriptionAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.GetSubscriptionAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetSubscriptionAttributes" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public GetSubscriptionAttributesResult getSubscriptionAttributes(GetSubscriptionAttributesRequest getSubscriptionAttributesRequest) {
        return amazonSNSToBeExtended.getSubscriptionAttributes(getSubscriptionAttributesRequest);
    }

    /**
     * Simplified method form for invoking the GetSubscriptionAttributes operation.
     *
     * @param subscriptionArn
     * @see #getSubscriptionAttributes(GetSubscriptionAttributesRequest)
     */
    @Override
    public GetSubscriptionAttributesResult getSubscriptionAttributes(String subscriptionArn) {
        return amazonSNSToBeExtended.getSubscriptionAttributes(subscriptionArn);
    }

    /**
     * <p>
     * Returns all of the properties of a topic. Topic properties returned might differ based on the authorization of
     * the user.
     * </p>
     *
     * @param getTopicAttributesRequest Input for GetTopicAttributes action.
     * @return Result of the GetTopicAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.GetTopicAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetTopicAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetTopicAttributesResult getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return amazonSNSToBeExtended.getTopicAttributes(getTopicAttributesRequest);
    }

    /**
     * Simplified method form for invoking the GetTopicAttributes operation.
     *
     * @param topicArn
     * @see #getTopicAttributes(GetTopicAttributesRequest)
     */
    @Override
    public GetTopicAttributesResult getTopicAttributes(String topicArn) {
        return amazonSNSToBeExtended.getTopicAttributes(topicArn);
    }

    /**
     * <p>
     * Lists the endpoints and endpoint attributes for devices in a supported push notification service, such as GCM and
     * APNS. The results for <code>ListEndpointsByPlatformApplication</code> are paginated and return a limited list of
     * endpoints, up to 100. If additional records are available after the first page results, then a NextToken string
     * will be returned. To receive the next page, you call <code>ListEndpointsByPlatformApplication</code> again using
     * the NextToken string received from the previous call. When there are no more records to return, NextToken will be
     * null. For more information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using
     * Amazon SNS Mobile Push Notifications</a>.
     * </p>
     * <p>
     * This action is throttled at 30 transactions per second (TPS).
     * </p>
     *
     * @param listEndpointsByPlatformApplicationRequest Input for ListEndpointsByPlatformApplication action.
     * @return Result of the ListEndpointsByPlatformApplication operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.ListEndpointsByPlatformApplication
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListEndpointsByPlatformApplication"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public ListEndpointsByPlatformApplicationResult listEndpointsByPlatformApplication(ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
        return amazonSNSToBeExtended.listEndpointsByPlatformApplication(listEndpointsByPlatformApplicationRequest);
    }

    /**
     * <p>
     * Returns a list of phone numbers that are opted out, meaning you cannot send SMS messages to them.
     * </p>
     * <p>
     * The results for <code>ListPhoneNumbersOptedOut</code> are paginated, and each page returns up to 100 phone
     * numbers. If additional phone numbers are available after the first page of results, then a <code>NextToken</code>
     * string will be returned. To receive the next page, you call <code>ListPhoneNumbersOptedOut</code> again using the
     * <code>NextToken</code> string received from the previous call. When there are no more records to return,
     * <code>NextToken</code> will be null.
     * </p>
     *
     * @param listPhoneNumbersOptedOutRequest The input for the <code>ListPhoneNumbersOptedOut</code> action.
     * @return Result of the ListPhoneNumbersOptedOut operation returned by the service.
     * @throws ThrottledException          Indicates that the rate at which requests have been submitted for this action exceeds the limit for your
     *                                     account.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @sample AmazonSNS.ListPhoneNumbersOptedOut
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListPhoneNumbersOptedOut" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListPhoneNumbersOptedOutResult listPhoneNumbersOptedOut(ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
        return amazonSNSToBeExtended.listPhoneNumbersOptedOut(listPhoneNumbersOptedOutRequest);
    }

    /**
     * <p>
     * Lists the platform application objects for the supported push notification services, such as APNS and GCM. The
     * results for <code>ListPlatformApplications</code> are paginated and return a limited list of applications, up to
     * 100. If additional records are available after the first page results, then a NextToken string will be returned.
     * To receive the next page, you call <code>ListPlatformApplications</code> using the NextToken string received from
     * the previous call. When there are no more records to return, NextToken will be null. For more information, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
     * </p>
     * <p>
     * This action is throttled at 15 transactions per second (TPS).
     * </p>
     *
     * @param listPlatformApplicationsRequest Input for ListPlatformApplications action.
     * @return Result of the ListPlatformApplications operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.ListPlatformApplications
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListPlatformApplications" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListPlatformApplicationsResult listPlatformApplications(ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return amazonSNSToBeExtended.listPlatformApplications(listPlatformApplicationsRequest);
    }

    /**
     * Simplified method form for invoking the ListPlatformApplications operation.
     *
     * @see #listPlatformApplications(ListPlatformApplicationsRequest)
     */
    @Override
    public ListPlatformApplicationsResult listPlatformApplications() {
        return amazonSNSToBeExtended.listPlatformApplications();
    }

    /**
     * <p>
     * Returns a list of the requester's subscriptions. Each call returns a limited list of subscriptions, up to 100. If
     * there are more subscriptions, a <code>NextToken</code> is also returned. Use the <code>NextToken</code> parameter
     * in a new <code>ListSubscriptions</code> call to get further results.
     * </p>
     * <p>
     * This action is throttled at 30 transactions per second (TPS).
     * </p>
     *
     * @param listSubscriptionsRequest Input for ListSubscriptions action.
     * @return Result of the ListSubscriptions operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.ListSubscriptions
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListSubscriptions" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ListSubscriptionsResult listSubscriptions(ListSubscriptionsRequest listSubscriptionsRequest) {
        return amazonSNSToBeExtended.listSubscriptions(listSubscriptionsRequest);
    }

    /**
     * Simplified method form for invoking the ListSubscriptions operation.
     *
     * @see #listSubscriptions(ListSubscriptionsRequest)
     */
    @Override
    public ListSubscriptionsResult listSubscriptions() {
        return amazonSNSToBeExtended.listSubscriptions();
    }

    /**
     * Simplified method form for invoking the ListSubscriptions operation.
     *
     * @param nextToken
     * @see #listSubscriptions(ListSubscriptionsRequest)
     */
    @Override
    public ListSubscriptionsResult listSubscriptions(String nextToken) {
        return amazonSNSToBeExtended.listSubscriptions(nextToken);
    }

    /**
     * <p>
     * Returns a list of the subscriptions to a specific topic. Each call returns a limited list of subscriptions, up to
     * 100. If there are more subscriptions, a <code>NextToken</code> is also returned. Use the <code>NextToken</code>
     * parameter in a new <code>ListSubscriptionsByTopic</code> call to get further results.
     * </p>
     * <p>
     * This action is throttled at 30 transactions per second (TPS).
     * </p>
     *
     * @param listSubscriptionsByTopicRequest Input for ListSubscriptionsByTopic action.
     * @return Result of the ListSubscriptionsByTopic operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.ListSubscriptionsByTopic
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListSubscriptionsByTopic" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return amazonSNSToBeExtended.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
    }

    /**
     * Simplified method form for invoking the ListSubscriptionsByTopic operation.
     *
     * @param topicArn
     * @see #listSubscriptionsByTopic(ListSubscriptionsByTopicRequest)
     */
    @Override
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String topicArn) {
        return amazonSNSToBeExtended.listSubscriptionsByTopic(topicArn);
    }

    /**
     * Simplified method form for invoking the ListSubscriptionsByTopic operation.
     *
     * @param topicArn
     * @param nextToken
     * @see #listSubscriptionsByTopic(ListSubscriptionsByTopicRequest)
     */
    @Override
    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String topicArn, String nextToken) {
        return amazonSNSToBeExtended.listSubscriptionsByTopic(topicArn, nextToken);
    }

    /**
     * <p>
     * Returns a list of the requester's topics. Each call returns a limited list of topics, up to 100. If there are
     * more topics, a <code>NextToken</code> is also returned. Use the <code>NextToken</code> parameter in a new
     * <code>ListTopics</code> call to get further results.
     * </p>
     * <p>
     * This action is throttled at 30 transactions per second (TPS).
     * </p>
     *
     * @param listTopicsRequest
     * @return Result of the ListTopics operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.ListTopics
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListTopics" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ListTopicsResult listTopics(ListTopicsRequest listTopicsRequest) {
        return amazonSNSToBeExtended.listTopics(listTopicsRequest);
    }

    /**
     * Simplified method form for invoking the ListTopics operation.
     *
     * @see #listTopics(ListTopicsRequest)
     */
    @Override
    public ListTopicsResult listTopics() {
        return amazonSNSToBeExtended.listTopics();
    }

    /**
     * Simplified method form for invoking the ListTopics operation.
     *
     * @param nextToken
     * @see #listTopics(ListTopicsRequest)
     */
    @Override
    public ListTopicsResult listTopics(String nextToken) {
        return amazonSNSToBeExtended.listTopics(nextToken);
    }

    /**
     * <p>
     * Use this request to opt in a phone number that is opted out, which enables you to resume sending SMS messages to
     * the number.
     * </p>
     * <p>
     * You can opt in a phone number only once every 30 days.
     * </p>
     *
     * @param optInPhoneNumberRequest Input for the OptInPhoneNumber action.
     * @return Result of the OptInPhoneNumber operation returned by the service.
     * @throws ThrottledException          Indicates that the rate at which requests have been submitted for this action exceeds the limit for your
     *                                     account.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @sample AmazonSNS.OptInPhoneNumber
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/OptInPhoneNumber" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public OptInPhoneNumberResult optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
        return amazonSNSToBeExtended.optInPhoneNumber(optInPhoneNumberRequest);
    }

    /**
     * <p>
     * Sends a message to an Amazon SNS topic or sends a text message (SMS message) directly to a phone number.
     * </p>
     * <p>
     * If you send a message to a topic, Amazon SNS delivers the message to each endpoint that is subscribed to the
     * topic. The format of the message depends on the notification protocol for each subscribed endpoint.
     * </p>
     * <p>
     * When a <code>messageId</code> is returned, the message has been saved and Amazon SNS will attempt to deliver it
     * shortly.
     * </p>
     * <p>
     * To use the <code>Publish</code> action for sending a message to a mobile endpoint, such as an app on a Kindle
     * device or mobile phone, you must specify the EndpointArn for the TargetArn parameter. The EndpointArn is returned
     * when making a call with the <code>CreatePlatformEndpoint</code> action.
     * </p>
     * <p>
     * For more information about formatting messages, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/mobile-push-send-custommessage.html">Send Custom Platform-Specific
     * Payloads in Messages to Mobile Devices</a>.
     * </p>
     *
     * @param publishRequest Input for Publish action.
     * @return Result of the Publish operation returned by the service.
     * @throws InvalidParameterException            Indicates that a request parameter does not comply with the associated constraints.
     * @throws InvalidParameterValueException       Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException               Indicates an internal service error.
     * @throws NotFoundException                    Indicates that the requested resource does not exist.
     * @throws EndpointDisabledException            Exception error indicating endpoint disabled.
     * @throws PlatformApplicationDisabledException Exception error indicating platform application disabled.
     * @throws AuthorizationErrorException          Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.Publish
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Publish" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public PublishResult publish(PublishRequest publishRequest) {
        return amazonSNSToBeExtended.publish(publishRequest);
    }

    /**
     * Simplified method form for invoking the Publish operation.
     *
     * @param topicArn
     * @param message
     * @see #publish(PublishRequest)
     */
    @Override
    public PublishResult publish(String topicArn, String message) {
        return amazonSNSToBeExtended.publish(topicArn, message);
    }

    /**
     * Simplified method form for invoking the Publish operation.
     *
     * @param topicArn
     * @param message
     * @param subject
     * @see #publish(PublishRequest)
     */
    @Override
    public PublishResult publish(String topicArn, String message, String subject) {
        return amazonSNSToBeExtended.publish(topicArn, message, subject);
    }

    /**
     * <p>
     * Removes a statement from a topic's access control policy.
     * </p>
     *
     * @param removePermissionRequest Input for RemovePermission action.
     * @return Result of the RemovePermission operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.RemovePermission
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/RemovePermission" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public RemovePermissionResult removePermission(RemovePermissionRequest removePermissionRequest) {
        return amazonSNSToBeExtended.removePermission(removePermissionRequest);
    }

    /**
     * Simplified method form for invoking the RemovePermission operation.
     *
     * @param topicArn
     * @param label
     * @see #removePermission(RemovePermissionRequest)
     */
    @Override
    public RemovePermissionResult removePermission(String topicArn, String label) {
        return amazonSNSToBeExtended.removePermission(topicArn, label);
    }

    /**
     * <p>
     * Sets the attributes for an endpoint for a device on one of the supported push notification services, such as GCM
     * and APNS. For more information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using
     * Amazon SNS Mobile Push Notifications</a>.
     * </p>
     *
     * @param setEndpointAttributesRequest Input for SetEndpointAttributes action.
     * @return Result of the SetEndpointAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.SetEndpointAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetEndpointAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetEndpointAttributesResult setEndpointAttributes(SetEndpointAttributesRequest setEndpointAttributesRequest) {
        return amazonSNSToBeExtended.setEndpointAttributes(setEndpointAttributesRequest);
    }

    /**
     * <p>
     * Sets the attributes of the platform application object for the supported push notification services, such as APNS
     * and GCM. For more information, see <a href="http://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using
     * Amazon SNS Mobile Push Notifications</a>. For information on configuring attributes for message delivery status,
     * see <a href="http://docs.aws.amazon.com/sns/latest/dg/sns-msg-status.html">Using Amazon SNS Application
     * Attributes for Message Delivery Status</a>.
     * </p>
     *
     * @param setPlatformApplicationAttributesRequest Input for SetPlatformApplicationAttributes action.
     * @return Result of the SetPlatformApplicationAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.SetPlatformApplicationAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetPlatformApplicationAttributes"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public SetPlatformApplicationAttributesResult setPlatformApplicationAttributes(SetPlatformApplicationAttributesRequest setPlatformApplicationAttributesRequest) {
        return amazonSNSToBeExtended.setPlatformApplicationAttributes(setPlatformApplicationAttributesRequest);
    }

    /**
     * <p>
     * Use this request to set the default settings for sending SMS messages and receiving daily SMS usage reports.
     * </p>
     * <p>
     * You can override some of these settings for a single message when you use the <code>Publish</code> action with
     * the <code>MessageAttributes.entry.N</code> parameter. For more information, see <a
     * href="http://docs.aws.amazon.com/sns/latest/dg/sms_publish-to-phone.html">Sending an SMS Message</a> in the
     * <i>Amazon SNS Developer Guide</i>.
     * </p>
     *
     * @param setSMSAttributesRequest The input for the SetSMSAttributes action.
     * @return Result of the SetSMSAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws ThrottledException          Indicates that the rate at which requests have been submitted for this action exceeds the limit for your
     *                                     account.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.SetSMSAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetSMSAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetSMSAttributesResult setSMSAttributes(SetSMSAttributesRequest setSMSAttributesRequest) {
        return amazonSNSToBeExtended.setSMSAttributes(setSMSAttributesRequest);
    }

    /**
     * <p>
     * Allows a subscription owner to set an attribute of the subscription to a new value.
     * </p>
     *
     * @param setSubscriptionAttributesRequest Input for SetSubscriptionAttributes action.
     * @return Result of the SetSubscriptionAttributes operation returned by the service.
     * @throws InvalidParameterException          Indicates that a request parameter does not comply with the associated constraints.
     * @throws FilterPolicyLimitExceededException Indicates that the number of filter polices in your AWS account exceeds the limit. To add more filter
     *                                            polices, submit an SNS Limit Increase case in the AWS Support Center.
     * @throws InternalErrorException             Indicates an internal service error.
     * @throws NotFoundException                  Indicates that the requested resource does not exist.
     * @throws AuthorizationErrorException        Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.SetSubscriptionAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetSubscriptionAttributes" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public SetSubscriptionAttributesResult setSubscriptionAttributes(SetSubscriptionAttributesRequest setSubscriptionAttributesRequest) {
        return amazonSNSToBeExtended.setSubscriptionAttributes(setSubscriptionAttributesRequest);
    }

    /**
     * Simplified method form for invoking the SetSubscriptionAttributes operation.
     *
     * @param subscriptionArn
     * @param attributeName
     * @param attributeValue
     * @see #setSubscriptionAttributes(SetSubscriptionAttributesRequest)
     */
    @Override
    public SetSubscriptionAttributesResult setSubscriptionAttributes(String subscriptionArn, String attributeName, String attributeValue) {
        return amazonSNSToBeExtended.setSubscriptionAttributes(subscriptionArn, attributeName, attributeValue);
    }

    /**
     * <p>
     * Allows a topic owner to set an attribute of the topic to a new value.
     * </p>
     *
     * @param setTopicAttributesRequest Input for SetTopicAttributes action.
     * @return Result of the SetTopicAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.SetTopicAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetTopicAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetTopicAttributesResult setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return amazonSNSToBeExtended.setTopicAttributes(setTopicAttributesRequest);
    }

    /**
     * Simplified method form for invoking the SetTopicAttributes operation.
     *
     * @param topicArn
     * @param attributeName
     * @param attributeValue
     * @see #setTopicAttributes(SetTopicAttributesRequest)
     */
    @Override
    public SetTopicAttributesResult setTopicAttributes(String topicArn, String attributeName, String attributeValue) {
        return amazonSNSToBeExtended.setTopicAttributes(topicArn, attributeName, attributeValue);
    }

    /**
     * <p>
     * Prepares to subscribe an endpoint by sending the endpoint a confirmation message. To actually create a
     * subscription, the endpoint owner must call the <code>ConfirmSubscription</code> action with the token from the
     * confirmation message. Confirmation tokens are valid for three days.
     * </p>
     * <p>
     * This action is throttled at 100 transactions per second (TPS).
     * </p>
     *
     * @param subscribeRequest Input for Subscribe action.
     * @return Result of the Subscribe operation returned by the service.
     * @throws SubscriptionLimitExceededException Indicates that the customer already owns the maximum allowed number of subscriptions.
     * @throws FilterPolicyLimitExceededException Indicates that the number of filter polices in your AWS account exceeds the limit. To add more filter
     *                                            polices, submit an SNS Limit Increase case in the AWS Support Center.
     * @throws InvalidParameterException          Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException             Indicates an internal service error.
     * @throws NotFoundException                  Indicates that the requested resource does not exist.
     * @throws AuthorizationErrorException        Indicates that the user has been denied access to the requested resource.
     * @sample AmazonSNS.Subscribe
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Subscribe" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SubscribeResult subscribe(SubscribeRequest subscribeRequest) {
        return amazonSNSToBeExtended.subscribe(subscribeRequest);
    }

    /**
     * Simplified method form for invoking the Subscribe operation.
     *
     * @param topicArn
     * @param protocol
     * @param endpoint
     * @see #subscribe(SubscribeRequest)
     */
    @Override
    public SubscribeResult subscribe(String topicArn, String protocol, String endpoint) {
        return amazonSNSToBeExtended.subscribe(topicArn, protocol, endpoint);
    }

    /**
     * <p>
     * Deletes a subscription. If the subscription requires authentication for deletion, only the owner of the
     * subscription or the topic's owner can unsubscribe, and an AWS signature is required. If the
     * <code>Unsubscribe</code> call does not require authentication and the requester is not the subscription owner, a
     * final cancellation message is delivered to the endpoint, so that the endpoint owner can easily resubscribe to the
     * topic if the <code>Unsubscribe</code> request was unintended.
     * </p>
     * <p>
     * This action is throttled at 100 transactions per second (TPS).
     * </p>
     *
     * @param unsubscribeRequest Input for Unsubscribe action.
     * @return Result of the Unsubscribe operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @sample AmazonSNS.Unsubscribe
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Unsubscribe" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public UnsubscribeResult unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return amazonSNSToBeExtended.unsubscribe(unsubscribeRequest);
    }

    /**
     * Simplified method form for invoking the Unsubscribe operation.
     *
     * @param subscriptionArn
     * @see #unsubscribe(UnsubscribeRequest)
     */
    @Override
    public UnsubscribeResult unsubscribe(String subscriptionArn) {
        return amazonSNSToBeExtended.unsubscribe(subscriptionArn);
    }

    /**
     * Shuts down this client object, releasing any resources that might be held open. This is an optional method, and
     * callers are not expected to call it, but can if they want to explicitly release any open resources. Once a client
     * has been shutdown, it should not be used to make any more requests.
     */
    @Override
    public void shutdown() {
        amazonSNSToBeExtended.shutdown();
    }

    /**
     * Returns additional metadata for a previously executed successful request, typically used for debugging issues
     * where a service isn't acting as expected. This data isn't considered part of the result data returned by an
     * operation, so it's available through this separate, diagnostic interface.
     * <p>
     * Response metadata is only cached for a limited period of time, so if you need to access this extra diagnostic
     * information for an executed request, you should use this method to retrieve it as soon as possible after
     * executing a request.
     *
     * @param request The originally executed request.
     * @return The response metadata for the specified request, or null if none is available.
     */
    @Override
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return amazonSNSToBeExtended.getCachedResponseMetadata(request);
    }

    /**
     * List all tags added to the specified Amazon SNS topic.
     *
     * @param request The originally executed request
     * @return Result of the ListTagsForResource operation returned by the service
     */
    @Override
    public ListTagsForResourceResult listTagsForResource(ListTagsForResourceRequest request) {
        return amazonSNSToBeExtended.listTagsForResource(request);
    }

    /**
     * Add tags to the specified Amazon SNS topic. For an overview, see Amazon SNS Tags in the Amazon SNS Developer Guide.
     *
     * @param request The originally executed request
     * @return Result of the TagResource operation returned by the service.
     */
    @Override
    public TagResourceResult tagResource(TagResourceRequest request) {
        return amazonSNSToBeExtended.tagResource(request);
    }

    /**
     * Remove tags from the specified Amazon SNS topic. For an overview, see Amazon SNS Tags in the Amazon SNS Developer Guide.
     *
     * @param request The originally executed request
     * @return Result of the UntagResource operation returned by the service.
     */
    @Override
    public UntagResourceResult untagResource(UntagResourceRequest request) {
        return amazonSNSToBeExtended.untagResource(request);
    }
}
