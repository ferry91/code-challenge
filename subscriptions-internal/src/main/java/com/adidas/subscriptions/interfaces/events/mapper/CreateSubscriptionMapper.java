package com.adidas.subscriptions.interfaces.events.mapper;

import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateSubscriptionMapper {

    public SubscriptionId mapToSubscriptionId(UUID source) {
        return new SubscriptionId(source);
    }
}
