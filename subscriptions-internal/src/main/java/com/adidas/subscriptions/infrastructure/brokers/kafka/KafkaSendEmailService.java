package com.adidas.subscriptions.infrastructure.brokers.kafka;

import com.adidas.subscriptions.domain.model.vo.EmailData;
import com.adidas.subscriptions.infrastructure.outbound.EmailSenderOutboundService;
import com.adidas.subscriptions.utils.UuidProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSendEmailService implements EmailSenderOutboundService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UuidProvider uuidProvider;

    @Override
    public void sendWelcomeEmail(EmailData data) {
        kafkaTemplate.send("welcome_email", uuidProvider.randomUUID().toString(), data);
    }
}
