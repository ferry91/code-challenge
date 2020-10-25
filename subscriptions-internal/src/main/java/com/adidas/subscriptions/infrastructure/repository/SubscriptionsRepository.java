package com.adidas.subscriptions.infrastructure.repository;

import com.adidas.subscriptions.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionsRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByEmailAndNewsletterId(String email, UUID newsletterId);
}
