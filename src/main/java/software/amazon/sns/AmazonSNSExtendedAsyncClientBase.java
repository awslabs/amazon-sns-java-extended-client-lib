package software.amazon.sns;

import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.AddPermissionRequest;
import software.amazon.awssdk.services.sns.model.AddPermissionResponse;
import software.amazon.awssdk.services.sns.model.CheckIfPhoneNumberIsOptedOutRequest;
import software.amazon.awssdk.services.sns.model.CheckIfPhoneNumberIsOptedOutResponse;
import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionRequest;
import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionResponse;
import software.amazon.awssdk.services.sns.model.CreatePlatformApplicationRequest;
import software.amazon.awssdk.services.sns.model.CreatePlatformApplicationResponse;
import software.amazon.awssdk.services.sns.model.CreatePlatformEndpointRequest;
import software.amazon.awssdk.services.sns.model.CreatePlatformEndpointResponse;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.DeleteEndpointRequest;
import software.amazon.awssdk.services.sns.model.DeleteEndpointResponse;
import software.amazon.awssdk.services.sns.model.DeletePlatformApplicationRequest;
import software.amazon.awssdk.services.sns.model.DeletePlatformApplicationResponse;
import software.amazon.awssdk.services.sns.model.DeleteTopicRequest;
import software.amazon.awssdk.services.sns.model.DeleteTopicResponse;
import software.amazon.awssdk.services.sns.model.GetEndpointAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetEndpointAttributesResponse;
import software.amazon.awssdk.services.sns.model.GetPlatformApplicationAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetPlatformApplicationAttributesResponse;
import software.amazon.awssdk.services.sns.model.GetSmsAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetSmsAttributesResponse;
import software.amazon.awssdk.services.sns.model.GetSubscriptionAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetSubscriptionAttributesResponse;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.ListEndpointsByPlatformApplicationRequest;
import software.amazon.awssdk.services.sns.model.ListEndpointsByPlatformApplicationResponse;
import software.amazon.awssdk.services.sns.model.ListPhoneNumbersOptedOutRequest;
import software.amazon.awssdk.services.sns.model.ListPhoneNumbersOptedOutResponse;
import software.amazon.awssdk.services.sns.model.ListPlatformApplicationsRequest;
import software.amazon.awssdk.services.sns.model.ListPlatformApplicationsResponse;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicResponse;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsResponse;
import software.amazon.awssdk.services.sns.model.ListTagsForResourceRequest;
import software.amazon.awssdk.services.sns.model.ListTagsForResourceResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.OptInPhoneNumberRequest;
import software.amazon.awssdk.services.sns.model.OptInPhoneNumberResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.RemovePermissionRequest;
import software.amazon.awssdk.services.sns.model.RemovePermissionResponse;
import software.amazon.awssdk.services.sns.model.SetEndpointAttributesRequest;
import software.amazon.awssdk.services.sns.model.SetEndpointAttributesResponse;
import software.amazon.awssdk.services.sns.model.SetPlatformApplicationAttributesRequest;
import software.amazon.awssdk.services.sns.model.SetPlatformApplicationAttributesResponse;
import software.amazon.awssdk.services.sns.model.SetSmsAttributesRequest;
import software.amazon.awssdk.services.sns.model.SetSmsAttributesResponse;
import software.amazon.awssdk.services.sns.model.SetSubscriptionAttributesRequest;
import software.amazon.awssdk.services.sns.model.SetSubscriptionAttributesResponse;
import software.amazon.awssdk.services.sns.model.SetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.SetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.TagResourceRequest;
import software.amazon.awssdk.services.sns.model.TagResourceResponse;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;
import software.amazon.awssdk.services.sns.model.UnsubscribeResponse;
import software.amazon.awssdk.services.sns.model.UntagResourceRequest;
import software.amazon.awssdk.services.sns.model.UntagResourceResponse;
import software.amazon.awssdk.services.sns.paginators.ListEndpointsByPlatformApplicationPublisher;
import software.amazon.awssdk.services.sns.paginators.ListPlatformApplicationsPublisher;
import software.amazon.awssdk.services.sns.paginators.ListSubscriptionsByTopicPublisher;
import software.amazon.awssdk.services.sns.paginators.ListSubscriptionsPublisher;
import software.amazon.awssdk.services.sns.paginators.ListTopicsPublisher;

import java.util.concurrent.CompletableFuture;

public abstract class AmazonSNSExtendedAsyncClientBase implements SnsAsyncClient {
    private final SnsAsyncClient snsClientToBeExtended;

    public AmazonSNSExtendedAsyncClientBase(SnsAsyncClient snsClientToBeExtended) {
        this.snsClientToBeExtended = snsClientToBeExtended;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<GetEndpointAttributesResponse> getEndpointAttributes(
            GetEndpointAttributesRequest getEndpointAttributesRequest) {
        return snsClientToBeExtended.getEndpointAttributes(getEndpointAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<AddPermissionResponse> addPermission(AddPermissionRequest addPermissionRequest) {
        return snsClientToBeExtended.addPermission(addPermissionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<CheckIfPhoneNumberIsOptedOutResponse> checkIfPhoneNumberIsOptedOut(
            CheckIfPhoneNumberIsOptedOutRequest checkIfPhoneNumberIsOptedOutRequest) {
        return snsClientToBeExtended.checkIfPhoneNumberIsOptedOut(checkIfPhoneNumberIsOptedOutRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ConfirmSubscriptionResponse> confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return snsClientToBeExtended.confirmSubscription(confirmSubscriptionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<CreatePlatformApplicationResponse> createPlatformApplication(CreatePlatformApplicationRequest createPlatformApplicationRequest) {
        return snsClientToBeExtended.createPlatformApplication(createPlatformApplicationRequest);
    }

    @Override
    public CompletableFuture<CreatePlatformEndpointResponse> createPlatformEndpoint(CreatePlatformEndpointRequest createPlatformEndpointRequest) {
        return  snsClientToBeExtended.createPlatformEndpoint(createPlatformEndpointRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<CreateTopicResponse> createTopic(CreateTopicRequest createTopicRequest) {
        return snsClientToBeExtended.createTopic(createTopicRequest);
    }

    @Override
    public CompletableFuture<DeleteEndpointResponse> deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
        return snsClientToBeExtended.deleteEndpoint(deleteEndpointRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<DeletePlatformApplicationResponse> deletePlatformApplication(
            DeletePlatformApplicationRequest deletePlatformApplicationRequest) {
        return snsClientToBeExtended.deletePlatformApplication(deletePlatformApplicationRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<DeleteTopicResponse> deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        return snsClientToBeExtended.deleteTopic(deleteTopicRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<GetPlatformApplicationAttributesResponse> getPlatformApplicationAttributes(
            GetPlatformApplicationAttributesRequest getPlatformApplicationAttributesRequest) {
        return snsClientToBeExtended.getPlatformApplicationAttributes(getPlatformApplicationAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<GetSmsAttributesResponse> getSMSAttributes(GetSmsAttributesRequest getSMSAttributesRequest) {
        return snsClientToBeExtended.getSMSAttributes(getSMSAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<GetSubscriptionAttributesResponse> getSubscriptionAttributes(
            GetSubscriptionAttributesRequest getSubscriptionAttributesRequest) {
        return snsClientToBeExtended.getSubscriptionAttributes(getSubscriptionAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<GetTopicAttributesResponse> getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return snsClientToBeExtended.getTopicAttributes(getTopicAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListEndpointsByPlatformApplicationResponse> listEndpointsByPlatformApplication(
            ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
        return snsClientToBeExtended.listEndpointsByPlatformApplication(listEndpointsByPlatformApplicationRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListEndpointsByPlatformApplicationPublisher listEndpointsByPlatformApplicationPaginator(
            ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
        return snsClientToBeExtended.listEndpointsByPlatformApplicationPaginator(listEndpointsByPlatformApplicationRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListPhoneNumbersOptedOutResponse> listPhoneNumbersOptedOut(
            ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
        return snsClientToBeExtended.listPhoneNumbersOptedOut(listPhoneNumbersOptedOutRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListPlatformApplicationsResponse> listPlatformApplications(
            ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return snsClientToBeExtended.listPlatformApplications(listPlatformApplicationsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListPlatformApplicationsPublisher listPlatformApplicationsPaginator(
            ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return snsClientToBeExtended.listPlatformApplicationsPaginator(listPlatformApplicationsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListSubscriptionsResponse> listSubscriptions(ListSubscriptionsRequest listSubscriptionsRequest) {
        return snsClientToBeExtended.listSubscriptions(listSubscriptionsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListSubscriptionsPublisher listSubscriptionsPaginator(ListSubscriptionsRequest listSubscriptionsRequest) {
        return snsClientToBeExtended.listSubscriptionsPaginator(listSubscriptionsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListSubscriptionsByTopicResponse> listSubscriptionsByTopic(
            ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return snsClientToBeExtended.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListSubscriptionsByTopicPublisher listSubscriptionsByTopicPaginator(
            ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return snsClientToBeExtended.listSubscriptionsByTopicPaginator(listSubscriptionsByTopicRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListTopicsResponse> listTopics(ListTopicsRequest listTopicsRequest) {
        return snsClientToBeExtended.listTopics(listTopicsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListTopicsPublisher listTopicsPaginator(ListTopicsRequest listTopicsRequest) {
        return snsClientToBeExtended.listTopicsPaginator(listTopicsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<OptInPhoneNumberResponse> optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
        return snsClientToBeExtended.optInPhoneNumber(optInPhoneNumberRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<PublishResponse> publish(PublishRequest publishRequest) {
        return snsClientToBeExtended.publish(publishRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<RemovePermissionResponse> removePermission(RemovePermissionRequest removePermissionRequest) {
        return snsClientToBeExtended.removePermission(removePermissionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<SetEndpointAttributesResponse> setEndpointAttributes(
            SetEndpointAttributesRequest setEndpointAttributesRequest) {
        return snsClientToBeExtended.setEndpointAttributes(setEndpointAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<SetPlatformApplicationAttributesResponse> setPlatformApplicationAttributes(
            SetPlatformApplicationAttributesRequest setPlatformApplicationAttributesRequest) {
        return snsClientToBeExtended.setPlatformApplicationAttributes(setPlatformApplicationAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<SetSmsAttributesResponse> setSMSAttributes(SetSmsAttributesRequest setSMSAttributesRequest) {
        return snsClientToBeExtended.setSMSAttributes(setSMSAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<SetSubscriptionAttributesResponse> setSubscriptionAttributes(
            SetSubscriptionAttributesRequest setSubscriptionAttributesRequest) {
        return snsClientToBeExtended.setSubscriptionAttributes(setSubscriptionAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<SetTopicAttributesResponse> setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return snsClientToBeExtended.setTopicAttributes(setTopicAttributesRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<SubscribeResponse> subscribe(SubscribeRequest subscribeRequest) {
        return snsClientToBeExtended.subscribe(subscribeRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<UnsubscribeResponse> unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return snsClientToBeExtended.unsubscribe(unsubscribeRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ListTagsForResourceResponse> listTagsForResource(
            ListTagsForResourceRequest listTagsForResourceRequest) {
        return snsClientToBeExtended.listTagsForResource(listTagsForResourceRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<TagResourceResponse> tagResource(TagResourceRequest tagResourceRequest) {
        return snsClientToBeExtended.tagResource(tagResourceRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<UntagResourceResponse> untagResource(UntagResourceRequest untagResourceRequest) {
        return snsClientToBeExtended.untagResource(untagResourceRequest);
    }

    @Override
    public String serviceName() {
        return snsClientToBeExtended.serviceName();
    }

    @Override
    public void close() {
        snsClientToBeExtended.close();
    }
}
