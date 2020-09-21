package software.amazon.sns;

import java.util.function.Consumer;

import software.amazon.awssdk.core.exception.*;
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
     * (Firebase Cloud Messaging) and APNS. For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
     * </p>
     * <br/>
     * <p>
     * This is a convenience which creates an instance of the {@link GetEndpointAttributesRequest.Builder} avoiding the
     * need to create one manually via {@link GetEndpointAttributesRequest#builder()}
     * </p>
     *
     * @param getEndpointAttributesRequest
     *        A {@link Consumer} that will call methods on {@link GetEndpointAttributesInput.Builder} to create a
     *        request. Input for GetEndpointAttributes action.
     * @return Result of the GetEndpointAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.GetEndpointAttributes
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.AddPermission
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.CheckIfPhoneNumberIsOptedOut
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
     * @throws FilterPolicyLimitExceededException Indicates that the number of filter polices in your AWS account exceeds the limit. To add more filter
     *                                            polices, submit an SNS Limit Increase case in the AWS Support Center.
     * @throws SdkException                       Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                            catch all scenarios.
     * @throws SdkClientException                 If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                       Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ConfirmSubscription
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ConfirmSubscription" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public ConfirmSubscriptionResponse confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return amazonSNSToBeExtended.confirmSubscription(confirmSubscriptionRequest);
    }

    /**
     * <p>
     * Creates a platform application object for one of the supported push notification services, such as APNS and GCM
     * (Firebase Cloud Messaging), to which devices and mobile apps may register. You must specify
     * <code>PlatformPrincipal</code> and <code>PlatformCredential</code> attributes when using the
     * <code>CreatePlatformApplication</code> action.
     * </p>
     * <p>
     * <code>PlatformPrincipal</code> and <code>PlatformCredential</code> are received from the notification service.
     * </p>
     * <ul>
     * <li>
     * <p>
     * For <code>ADM</code>, <code>PlatformPrincipal</code> is <code>client id</code> and
     * <code>PlatformCredential</code> is <code>client secret</code>.
     * </p>
     * </li>
     * <li>
     * <p>
     * For <code>Baidu</code>, <code>PlatformPrincipal</code> is <code>API key</code> and
     * <code>PlatformCredential</code> is <code>secret key</code>.
     * </p>
     * </li>
     * <li>
     * <p>
     * For <code>APNS</code> and <code>APNS_SANDBOX</code>, <code>PlatformPrincipal</code> is
     * <code>SSL certificate</code> and <code>PlatformCredential</code> is <code>private key</code>.
     * </p>
     * </li>
     * <li>
     * <p>
     * For <code>GCM</code> (Firebase Cloud Messaging), there is no <code>PlatformPrincipal</code> and the
     * <code>PlatformCredential</code> is <code>API key</code>.
     * </p>
     * </li>
     * <li>
     * <p>
     * For <code>MPNS</code>, <code>PlatformPrincipal</code> is <code>TLS certificate</code> and
     * <code>PlatformCredential</code> is <code>private key</code>.
     * </p>
     * </li>
     * <li>
     * <p>
     * For <code>WNS</code>, <code>PlatformPrincipal</code> is <code>Package Security Identifier</code> and
     * <code>PlatformCredential</code> is <code>secret key</code>.
     * </p>
     * </li>
     * </ul>
     * <p>
     * You can use the returned <code>PlatformApplicationArn</code> as an attribute for the
     * <code>CreatePlatformEndpoint</code> action.
     * </p>
     *
     * @param createPlatformApplicationRequest Input for CreatePlatformApplication action.
     * @return Result of the CreatePlatformApplication operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.CreatePlatformApplication
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
     * (Firebase Cloud Messaging) and APNS. <code>CreatePlatformEndpoint</code> requires the
     * <code>PlatformApplicationArn</code> that is returned from <code>CreatePlatformApplication</code>. You can use the
     * returned <code>EndpointArn</code> to send a message to a mobile app or by the <code>Subscribe</code> action for
     * subscription to a topic. The <code>CreatePlatformEndpoint</code> action is idempotent, so if the requester
     * already owns an endpoint with the same device token and attributes, that endpoint's ARN is returned without
     * creating a new endpoint. For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
     * </p>
     * <p>
     * When using <code>CreatePlatformEndpoint</code> with Baidu, two attributes must be provided: ChannelId and UserId.
     * The token field must also contain the ChannelId. For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePushBaiduEndpoint.html">Creating an Amazon SNS Endpoint
     * for Baidu</a>.
     * </p>
     *
     * @param createPlatformEndpointRequest Input for CreatePlatformEndpoint action.
     * @return Result of the CreatePlatformEndpoint operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.CreatePlatformEndpoint
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
     * information, see <a href="http://aws.amazon.com/sns/">https://aws.amazon.com/sns</a>. This action is idempotent,
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
     * @throws InvalidSecurityException    The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                     Signature Version 4.
     * @throws TagLimitExceededException   Can't add more than 50 tags to a topic.
     * @throws StaleTagException           A tag has been added to a resource with the same ARN as a deleted resource. Wait a short while and then
     *                                     retry the operation.
     * @throws TagPolicyException          The request doesn't comply with the IAM tag policy. Correct your request and then retry it.
     * @throws ConcurrentAccessException   Can't perform multiple operations on a tag simultaneously. Perform the operations sequentially.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.CreateTopic
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
     * information, see <a href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.DeleteEndpoint
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/DeleteEndpoint" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public DeleteEndpointResponse deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
        return amazonSNSToBeExtended.deleteEndpoint(deleteEndpointRequest);
    }

    /**
     * <p>
     * Deletes a platform application object for one of the supported push notification services, such as APNS and GCM
     * (Firebase Cloud Messaging). For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
     * </p>
     *
     * @param deletePlatformApplicationRequest Input for DeletePlatformApplication action.
     * @return Result of the DeletePlatformApplication operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.DeletePlatformApplication
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
     * @throws StaleTagException           A tag has been added to a resource with the same ARN as a deleted resource. Wait a short while and then
     *                                     retry the operation.
     * @throws TagPolicyException          The request doesn't comply with the IAM tag policy. Correct your request and then retry it.
     * @throws ConcurrentAccessException   Can't perform multiple operations on a tag simultaneously. Perform the operations sequentially.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.DeleteTopic
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.GetPlatformApplicationAttributes
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.GetSMSAttributes
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.GetSubscriptionAttributes
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
     * @throws InvalidSecurityException    The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                     Signature Version 4.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.GetTopicAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/GetTopicAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public GetTopicAttributesResponse getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return amazonSNSToBeExtended.getTopicAttributes(getTopicAttributesRequest);
    }

    /**
     * <p>
     * Lists the endpoints and endpoint attributes for devices in a supported push notification service, such as GCM
     * (Firebase Cloud Messaging) and APNS. The results for <code>ListEndpointsByPlatformApplication</code> are
     * paginated and return a limited list of endpoints, up to 100. If additional records are available after the first
     * page results, then a NextToken string will be returned. To receive the next page, you call
     * <code>ListEndpointsByPlatformApplication</code> again using the NextToken string received from the previous call.
     * When there are no more records to return, NextToken will be null. For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListEndpointsByPlatformApplication
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListPhoneNumbersOptedOut
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListPhoneNumbersOptedOut" target="_top">AWS
     * API Documentation</a>
     */
    @Override
    public ListPhoneNumbersOptedOutResponse listPhoneNumbersOptedOut(ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
        return amazonSNSToBeExtended.listPhoneNumbersOptedOut(listPhoneNumbersOptedOutRequest);
    }

    /**
     * <p>
     * Lists the platform application objects for the supported push notification services, such as APNS and GCM
     * (Firebase Cloud Messaging). The results for <code>ListPlatformApplications</code> are paginated and return a
     * limited list of applications, up to 100. If additional records are available after the first page results, then a
     * NextToken string will be returned. To receive the next page, you call <code>ListPlatformApplications</code> using
     * the NextToken string received from the previous call. When there are no more records to return,
     * <code>NextToken</code> will be null. For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListPlatformApplications
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListSubscriptions
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListSubscriptionsByTopic
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListTopics
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.OptInPhoneNumber
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/OptInPhoneNumber" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public OptInPhoneNumberResponse optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
        return amazonSNSToBeExtended.optInPhoneNumber(optInPhoneNumberRequest);
    }

    /**
     * <p>
     * Sends a message to an Amazon SNS topic, a text message (SMS message) directly to a phone number, or a message to
     * a mobile platform endpoint (when you specify the <code>TargetArn</code>).
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
     * href="https://docs.aws.amazon.com/sns/latest/dg/mobile-push-send-custommessage.html">Send Custom
     * Platform-Specific Payloads in Messages to Mobile Devices</a>.
     * </p>
     * <important>
     * <p>
     * You can publish messages only to topics and endpoints in the same AWS Region.
     * </p>
     * </important>
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
     * @throws KmsDisabledException                 The request was rejected because the specified customer master key (CMK) isn't enabled.
     * @throws KmsInvalidStateException             The request was rejected because the state of the specified resource isn't valid for this request. For
     *                                              more information, see <a href="https://docs.aws.amazon.com/kms/latest/developerguide/key-state.html">How
     *                                              Key State Affects Use of a Customer Master Key</a> in the <i>AWS Key Management Service Developer
     *                                              Guide</i>.
     * @throws KmsNotFoundException                 The request was rejected because the specified entity or resource can't be found.
     * @throws KmsOptInRequiredException            The AWS access key ID needs a subscription for the service.
     * @throws KmsThrottlingException               The request was denied due to request throttling. For more information about throttling, see <a
                                                    href="https://docs.aws.amazon.com/kms/latest/developerguide/limits.html#requests-per-second">Limits</a>
     *                                              in the <i>AWS Key Management Service Developer Guide.</i>
     * @throws KmsAccessDeniedException             The ciphertext references a key that doesn't exist or that you don't have access to.
     * @throws InvalidSecurityException             The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                              Signature Version 4.
     * @throws SdkException                         Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                              catch all scenarios.
     * @throws SdkClientException                   If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                         Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.Publish
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.RemovePermission
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
     * (Firebase Cloud Messaging) and APNS. For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>.
     * </p>
     *
     * @param setEndpointAttributesRequest Input for SetEndpointAttributes action.
     * @return Result of the SetEndpointAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.SetEndpointAttributes
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
     * and GCM (Firebase Cloud Messaging). For more information, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/SNSMobilePush.html">Using Amazon SNS Mobile Push
     * Notifications</a>. For information on configuring attributes for message delivery status, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/sns-msg-status.html">Using Amazon SNS Application Attributes for
     * Message Delivery Status</a>.
     * </p>
     *
     * @param setPlatformApplicationAttributesRequest Input for SetPlatformApplicationAttributes action.
     * @return Result of the SetPlatformApplicationAttributes operation returned by the service.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws InternalErrorException      Indicates an internal service error.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws NotFoundException           Indicates that the requested resource does not exist.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.SetPlatformApplicationAttributes
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
     * href="https://docs.aws.amazon.com/sns/latest/dg/sms_publish-to-phone.html">Sending an SMS Message</a> in the
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
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.SetSMSAttributes
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
     * @throws SdkException                       Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                            catch all scenarios.
     * @throws SdkClientException                 If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                       Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.SetSubscriptionAttributes
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
     * @throws InvalidSecurityException    The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                     Signature Version 4.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.SetTopicAttributes
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/SetTopicAttributes" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public SetTopicAttributesResponse setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return amazonSNSToBeExtended.setTopicAttributes(setTopicAttributesRequest);
    }

    /**
     * <p>
     * Subscribes an endpoint to an Amazon SNS topic. If the endpoint type is HTTP/S or email, or if the endpoint and
     * the topic are not in the same AWS account, the endpoint owner must the <code>ConfirmSubscription</code> action to
     * confirm the subscription.
     * </p>
     * <p>
     * You call the <code>ConfirmSubscription</code> action with the token from the subscription response. Confirmation
     * tokens are valid for three days.
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
     * @throws InvalidSecurityException           The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                            Signature Version 4.
     * @throws SdkException                       Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                            catch all scenarios.
     * @throws SdkClientException                 If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                       Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.Subscribe
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
     * @throws InvalidSecurityException    The credential signature isn't valid. You must use an HTTPS endpoint and sign your request using
     *                                     Signature Version 4.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.Unsubscribe
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/Unsubscribe" target="_top">AWS API
     * Documentation</a>
     */
    @Override
    public UnsubscribeResponse unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return amazonSNSToBeExtended.unsubscribe(unsubscribeRequest);
    }

    /**
     * <p>
     * List all tags added to the specified Amazon SNS topic. For an overview, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/sns-tags.html">Amazon SNS Tags</a> in the <i>Amazon Simple
     * Notification Service Developer Guide</i>.
     * </p>
     *
     * @param listTagsForResourceRequest
     * @return Result of the ListTagsForResource operation returned by the service.
     * @throws ResourceNotFoundException   Can't tag resource. Verify that the topic exists.
     * @throws TagPolicyException          The request doesn't comply with the IAM tag policy. Correct your request and then retry it.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws ConcurrentAccessException   Can't perform multiple operations on a tag simultaneously. Perform the operations sequentially.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.ListTagsForResource
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/ListTagsForResource" target="_top">AWS API
     *      Documentation</a>     */
    @Override
    public ListTagsForResourceResponse listTagsForResource(ListTagsForResourceRequest listTagsForResourceRequest) {
        return amazonSNSToBeExtended.listTagsForResource(listTagsForResourceRequest);
    }

    /**
     * <p>
     * Add tags to the specified Amazon SNS topic. For an overview, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/sns-tags.html">Amazon SNS Tags</a> in the <i>Amazon SNS Developer
     * Guide</i>.
     * </p>
     * <p>
     * When you use topic tags, keep the following guidelines in mind:
     * </p>
     * <ul>
     * <li>
     * <p>
     * Adding more than 50 tags to a topic isn't recommended.
     * </p>
     * </li>
     * <li>
     * <p>
     * Tags don't have any semantic meaning. Amazon SNS interprets tags as character strings.
     * </p>
     * </li>
     * <li>
     * <p>
     * Tags are case-sensitive.
     * </p>
     * </li>
     * <li>
     * <p>
     * A new tag with a key identical to that of an existing tag overwrites the existing tag.
     * </p>
     * </li>
     * <li>
     * <p>
     * Tagging actions are limited to 10 TPS per AWS account, per AWS region. If your application requires a higher
     * throughput, file a <a
     * href="https://console.aws.amazon.com/support/home#/case/create?issueType=technical">technical support
     * request</a>.
     * </p>
     * </li>
     * </ul>
     *
     * @param tagResourceRequest
     * @return Result of the TagResource operation returned by the service.
     * @throws ResourceNotFoundException   Can't tag resource. Verify that the topic exists.
     * @throws TagLimitExceededException   Can't add more than 50 tags to a topic.
     * @throws StaleTagException           A tag has been added to a resource with the same ARN as a deleted resource. Wait a short while and then
     *                                     retry the operation.
     * @throws TagPolicyException          The request doesn't comply with the IAM tag policy. Correct your request and then retry it.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws ConcurrentAccessException   Can't perform multiple operations on a tag simultaneously. Perform the operations sequentially.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.TagResource
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/TagResource" target="_top">AWS API
     *      Documentation</a>     */
    @Override
    public TagResourceResponse tagResource(TagResourceRequest tagResourceRequest) {
        return amazonSNSToBeExtended.tagResource(tagResourceRequest);
    }

    /**
     * <p>
     * Remove tags from the specified Amazon SNS topic. For an overview, see <a
     * href="https://docs.aws.amazon.com/sns/latest/dg/sns-tags.html">Amazon SNS Tags</a> in the <i>Amazon SNS Developer
     * Guide</i>.
     * </p>
     *
     * @param untagResourceRequest
     * @return Result of the UntagResource operation returned by the service.
     * @throws ResourceNotFoundException   Can't tag resource. Verify that the topic exists.
     * @throws TagLimitExceededException   Can't add more than 50 tags to a topic.
     * @throws StaleTagException           A tag has been added to a resource with the same ARN as a deleted resource. Wait a short while and then
     *                                     retry the operation.
     * @throws TagPolicyException          The request doesn't comply with the IAM tag policy. Correct your request and then retry it.
     * @throws InvalidParameterException   Indicates that a request parameter does not comply with the associated constraints.
     * @throws AuthorizationErrorException Indicates that the user has been denied access to the requested resource.
     * @throws ConcurrentAccessException   Can't perform multiple operations on a tag simultaneously. Perform the operations sequentially.
     * @throws SdkException                Base class for all exceptions that can be thrown by the SDK (both service and client). Can be used for
     *                                     catch all scenarios.
     * @throws SdkClientException          If any client side error occurs such as an IO related failure, failure to get credentials, etc.
     * @throws SnsException                Base class for all service exceptions. Unknown exceptions will be thrown as an instance of this type.
     * @sample SnsClient.UntagResource
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/sns-2010-03-31/UntagResource" target="_top">AWS API
     *      Documentation</a>     */
    @Override
    public UntagResourceResponse untagResource(UntagResourceRequest untagResourceRequest) {
        return amazonSNSToBeExtended.untagResource(untagResourceRequest);
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
