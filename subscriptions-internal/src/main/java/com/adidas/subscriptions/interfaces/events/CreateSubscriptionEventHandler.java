package com.adidas.subscriptions.interfaces.events;

import com.adidas.subscriptions.application.services.SubscriptionsService;
import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;
import com.adidas.subscriptions.exception.AdidasErrorCode;
import com.adidas.subscriptions.exception.BusinessException;
import com.adidas.subscriptions.interfaces.events.mapper.CreateSubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateSubscriptionEventHandler {

    private final SubscriptionsService subscriptionsService;
    private final CreateSubscriptionMapper createSubscriptionMapper;

    @KafkaListener(topics = "create_subscription", containerFactory = "kafkaJsonListenerContainerFactory")
    @SendTo("create_subscription_response")
    public SubscriptionId createSubscription(Message<SubscriptionData> message) {
        UUID subscriptionId = this.subscriptionsService.createSubscription(message.getPayload());
        return createSubscriptionMapper.mapToSubscriptionId(subscriptionId);
    }
}
