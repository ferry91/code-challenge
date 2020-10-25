package com.adidas.subscriptions.infrastructure.outbound;

import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface SubscriptionsOutboundService {
    SubscriptionId createSubscription(SubscriptionData subscriptionData) throws ExecutionException, InterruptedException;
}
