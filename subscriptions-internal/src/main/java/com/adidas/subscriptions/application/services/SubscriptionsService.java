package com.adidas.subscriptions.application.services;

import com.adidas.subscriptions.domain.model.vo.SubscriptionData;

import java.util.UUID;

public interface SubscriptionsService {

    UUID createSubscription(SubscriptionData data);
}
