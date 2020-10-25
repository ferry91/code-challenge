package com.adidas.subscriptions.utils;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class OffsetDateTimeProvider {
    public OffsetDateTimeProvider() {
    }

    public OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}