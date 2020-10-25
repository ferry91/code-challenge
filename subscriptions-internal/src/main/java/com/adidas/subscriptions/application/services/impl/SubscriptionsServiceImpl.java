package com.adidas.subscriptions.application.services.impl;

import com.adidas.subscriptions.application.services.SubscriptionsService;
import com.adidas.subscriptions.application.services.mapper.EmailDataMapper;
import com.adidas.subscriptions.domain.model.aggregates.Subscription;
import com.adidas.subscriptions.domain.model.vo.EmailData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionStatus;
import com.adidas.subscriptions.infrastructure.outbound.EmailSenderOutboundService;
import com.adidas.subscriptions.infrastructure.repository.SubscriptionsRepository;
import com.adidas.subscriptions.utils.OffsetDateTimeProvider;
import com.adidas.subscriptions.utils.UuidProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionsServiceImpl implements SubscriptionsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionsServiceImpl.class);
    private final UuidProvider uuidProvider;
    private final OffsetDateTimeProvider offsetDateTimeProvider;
    private final SubscriptionsRepository repository;
    private final EmailSenderOutboundService emailSenderOutboundService;
    private final EmailDataMapper emailDataMapper;
    private UUID subscriptionId;

    @Override
    public UUID createSubscription(SubscriptionData data) {
        this.repository.findByEmailAndNewsletterId(data.getEmail(), data.getNewsletterId())
                .ifPresentOrElse(x -> {
                            LOGGER.info("The email " + x.getEmail() + " is already registered to that particular campaign");
                            this.subscriptionId = null;
                        },
                        () -> {
                            final Subscription subscription = Subscription.builder()
                                    .id(uuidProvider.randomUUID())
                                    .email(data.getEmail())
                                    .firstName(data.getFirstName())
                                    .gender(data.getGender())
                                    .dateOfBirth(data.getDateOfBirth())
                                    .consentAccepted(data.getConsentAccepted())
                                    .newsletterId(data.getNewsletterId())
                                    .status(SubscriptionStatus.ACTIVE)
                                    .dateOfSubscription(offsetDateTimeProvider.now())
                                    .build();
                            this.repository.save(subscription);
                            EmailData emailData = this.emailDataMapper.mapToEmailData(data);
                            this.emailSenderOutboundService.sendWelcomeEmail(emailData);
                            LOGGER.info("Email successfully registered to the campaign");
                            this.subscriptionId = subscription.getId();
                        });
        return this.subscriptionId;
    }
}
