package com.adidas.subscriptions.interfaces.rest;

import com.adidas.subscriptions.application.services.SubscriptionsService;
import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;
import com.adidas.subscriptions.interfaces.rest.mapper.SubscriptionMapper;
import com.adidas.subscriptions.interfaces.rest.model.CreateSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionsService subscriptionsService;

    @PostMapping
    public ResponseEntity<SubscriptionId> createSubscription(
            @Valid @RequestBody CreateSubscription createSubscription
    ) {
        SubscriptionData subscriptionData = subscriptionMapper.mapToSubscriptionData(createSubscription);
        SubscriptionId subscriptionId = this.subscriptionsService.createSubscription(subscriptionData);
        return new ResponseEntity<>(subscriptionId, HttpStatus.CREATED);
    }
}
