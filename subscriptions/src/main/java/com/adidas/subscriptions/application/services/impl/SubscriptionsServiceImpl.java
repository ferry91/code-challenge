package com.adidas.subscriptions.application.services.impl;

import com.adidas.subscriptions.application.services.SubscriptionsService;
import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;
import com.adidas.subscriptions.exception.AdidasErrorCode;
import com.adidas.subscriptions.exception.BusinessException;
import com.adidas.subscriptions.infrastructure.outbound.SubscriptionsOutboundService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionsServiceImpl implements SubscriptionsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionsServiceImpl.class);
    private final SubscriptionsOutboundService subscriptionsOutboundService;

    @Value("${legalAge}")
    private long MIN_AGE;

    @Override
    public SubscriptionId createSubscription(SubscriptionData data) {
        this.validateSubscriptionAllowed(data);
        SubscriptionId subscriptionId;
        try {
             subscriptionId = this.subscriptionsOutboundService.createSubscription(data);
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.error("Error creating the subscription", e);
            throw new BusinessException(AdidasErrorCode.BUSI_002, "The subscription could not be processed");
        }
        if (subscriptionId.getId() == null)
            throw new BusinessException(AdidasErrorCode.BUSI_002, "The subscription could not be processed");

        return subscriptionId;
    }

    private void validateSubscriptionAllowed(SubscriptionData data) {
        if(!this.isConsentAccepted(data.getConsentAccepted()) || !this.isOfLegalAge(data.getDateOfBirth())){
            LOGGER.error("The customer is either not of Legal Age or has not accepted the consent");
            throw new BusinessException(AdidasErrorCode.BUSI_001, "The customer is either not of Legal Age or has not accepted the consent");
        }
    }

    private boolean isConsentAccepted(boolean consentAccepted) {
        return consentAccepted;
    }

    private boolean isOfLegalAge(LocalDate date) {
        final LocalDate current = LocalDate.now();
        return ChronoUnit.YEARS.between(date, current) >= MIN_AGE;
    }

}
