package software.amazon.sns;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

abstract class AmazonSNSExtendedClientBase implements SnsClient {
    private final SnsClient amazonSNSToBeExtended;

    public AmazonSNSExtendedClientBase(SnsClient amazonSNSToBeExtended) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.GetEndpointAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetEndpointAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetEndpointAttributesResponse getEndpointAttributes(GetEndpointAttributesRequest getEndpointAttributesRequest) {
        return amazonSNSToBeExtended.getEndpointAttributes(getEndpointAttributesRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.AddPermission
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/AddPermission" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public AddPermissionResponse addPermission(AddPermissionRequest addPermissionRequest) {
        return amazonSNSToBeExtended.addPermission(addPermissionRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.CheckIfPhoneNumberIsOptedOut
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CheckIfPhoneNumberIsOptedOut"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public CheckIfPhoneNumberIsOptedOutResponse checkIfPhoneNumberIsOptedOut(CheckIfPhoneNumberIsOptedOutRequest checkIfPhoneNumberIsOptedOutRequest) {
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
     * @throws SdkClientException                 If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                       Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ConfirmSubscription
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ConfirmSubscription" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ConfirmSubscriptionResponse confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return amazonSNSToBeExtended.confirmSubscription(confirmSubscriptionRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.CreatePlatformApplication
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CreatePlatformApplication" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public CreatePlatformApplicationResponse createPlatformApplication(CreatePlatformApplicationRequest createPlatformApplicationRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.CreatePlatformEndpoint
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CreatePlatformEndpoint" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public CreatePlatformEndpointResponse createPlatformEndpoint(CreatePlatformEndpointRequest createPlatformEndpointRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.CreateTopic
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/CreateTopic" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public CreateTopicResponse createTopic(CreateTopicRequest createTopicRequest) {
        return amazonSNSToBeExtended.createTopic(createTopicRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.DeleteEndpoint
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeleteEndpoint" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public DeleteEndpointResponse deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.DeletePlatformApplication
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeletePlatformApplication" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public DeletePlatformApplicationResponse deletePlatformApplication(DeletePlatformApplicationRequest deletePlatformApplicationRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.DeleteTopic
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeleteTopic" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public DeleteTopicResponse deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        return amazonSNSToBeExtended.deleteTopic(deleteTopicRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.GetPlatformApplicationAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetPlatformApplicationAttributes"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public GetPlatformApplicationAttributesResponse getPlatformApplicationAttributes(GetPlatformApplicationAttributesRequest getPlatformApplicationAttributesRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.GetSMSAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetSMSAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetSmsAttributesResponse getSMSAttributes(GetSmsAttributesRequest getSMSAttributesRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.GetSubscriptionAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetSubscriptionAttributes" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public GetSubscriptionAttributesResponse getSubscriptionAttributes(GetSubscriptionAttributesRequest getSubscriptionAttributesRequest) {
        return amazonSNSToBeExtended.getSubscriptionAttributes(getSubscriptionAttributesRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.GetTopicAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetTopicAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetTopicAttributesResponse getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return amazonSNSToBeExtended.getTopicAttributes(getTopicAttributesRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ListEndpointsByPlatformApplication
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListEndpointsByPlatformApplication"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public ListEndpointsByPlatformApplicationResponse listEndpointsByPlatformApplication(ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ListPhoneNumbersOptedOut
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListPhoneNumbersOptedOut" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListPhoneNumbersOptedOutResponse listPhoneNumbersOptedOut(ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ListPlatformApplications
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListPlatformApplications" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListPlatformApplicationsResponse listPlatformApplications(ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return amazonSNSToBeExtended.listPlatformApplications(listPlatformApplicationsRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ListSubscriptions
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListSubscriptions" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ListSubscriptionsResponse listSubscriptions(ListSubscriptionsRequest listSubscriptionsRequest) {
        return amazonSNSToBeExtended.listSubscriptions(listSubscriptionsRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ListSubscriptionsByTopic
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListSubscriptionsByTopic" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListSubscriptionsByTopicResponse listSubscriptionsByTopic(ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return amazonSNSToBeExtended.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.ListTopics
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListTopics" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ListTopicsResponse listTopics(ListTopicsRequest listTopicsRequest) {
        return amazonSNSToBeExtended.listTopics(listTopicsRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.OptInPhoneNumber
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/OptInPhoneNumber" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public OptInPhoneNumberResponse optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
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
     * @throws SdkClientException                   If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                         Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.Publish
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Publish" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public PublishResponse publish(PublishRequest publishRequest) {
        return amazonSNSToBeExtended.publish(publishRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.RemovePermission
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/RemovePermission" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public RemovePermissionResponse removePermission(RemovePermissionRequest removePermissionRequest) {
        return amazonSNSToBeExtended.removePermission(removePermissionRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.SetEndpointAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetEndpointAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetEndpointAttributesResponse setEndpointAttributes(SetEndpointAttributesRequest setEndpointAttributesRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.SetPlatformApplicationAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetPlatformApplicationAttributes"
     * target="_top">AWS API Documentation</a>
     */
    @Override
    public SetPlatformApplicationAttributesResponse setPlatformApplicationAttributes(SetPlatformApplicationAttributesRequest setPlatformApplicationAttributesRequest) {
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.SetSMSAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetSMSAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetSmsAttributesResponse setSMSAttributes(SetSmsAttributesRequest setSMSAttributesRequest) {
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
     * @throws SdkClientException                 If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                       Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.SetSubscriptionAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetSubscriptionAttributes" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public SetSubscriptionAttributesResponse setSubscriptionAttributes(SetSubscriptionAttributesRequest setSubscriptionAttributesRequest) {
        return amazonSNSToBeExtended.setSubscriptionAttributes(setSubscriptionAttributesRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.SetTopicAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetTopicAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetTopicAttributesResponse setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return amazonSNSToBeExtended.setTopicAttributes(setTopicAttributesRequest);
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
     * @throws SdkClientException                 If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                       Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.Subscribe
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Subscribe" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SubscribeResponse subscribe(SubscribeRequest subscribeRequest) {
        return amazonSNSToBeExtended.subscribe(subscribeRequest);
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
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample AmazonSNS.Unsubscribe
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Unsubscribe" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public UnsubscribeResponse unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return amazonSNSToBeExtended.unsubscribe(unsubscribeRequest);
    }

    /**
     * List all tags added to the specified Amazon SNS topic.
     *
     * @param request The originally executed request
     * @return Result of the ListTagsForResource operation returned by the service
     */
    @Override
    public ListTagsForResourceResponse listTagsForResource(ListTagsForResourceRequest request) {
        return amazonSNSToBeExtended.listTagsForResource(request);
    }

    /**
     * Add tags to the specified Amazon SNS topic. For an overview, see Amazon SNS Tags in the Amazon SNS Developer Guide.
     *
     * @param request The originally executed request
     * @return Result of the TagResource operation returned by the service.
     */
    @Override
    public TagResourceResponse tagResource(TagResourceRequest request) {
        return amazonSNSToBeExtended.tagResource(request);
    }

    /**
     * Remove tags from the specified Amazon SNS topic. For an overview, see Amazon SNS Tags in the Amazon SNS Developer Guide.
     *
     * @param request The originally executed request
     * @return Result of the UntagResource operation returned by the service.
     */
    @Override
    public UntagResourceResponse untagResource(UntagResourceRequest request) {
        return amazonSNSToBeExtended.untagResource(request);
    }

    @Override
    public String serviceName() {
        return amazonSNSToBeExtended.serviceName();
    }

    @Override
    public void close() {
        amazonSNSToBeExtended.close();
    }
}
