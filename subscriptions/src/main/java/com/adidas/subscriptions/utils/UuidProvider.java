package com.adidas.subscriptions.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@NoArgsConstructor
public class UuidProvider {

    public UUID randomUUID() {
        return UUID.randomUUID();
    }

    public UUID fromString(String name) {
        return UUID.fromString(name);
    }
}
