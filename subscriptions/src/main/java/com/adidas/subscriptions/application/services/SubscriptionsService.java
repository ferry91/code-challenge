package com.adidas.subscriptions.application.services;

import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;

public interface SubscriptionsService {

    SubscriptionId createSubscription(SubscriptionData data);
}
