package com.adidas.subscriptions.infrastructure.outbound;

import com.adidas.subscriptions.domain.model.vo.EmailData;

public interface EmailSenderOutboundService {
    void sendWelcomeEmail(EmailData data);
}
