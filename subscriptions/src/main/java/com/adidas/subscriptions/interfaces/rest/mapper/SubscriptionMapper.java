package com.adidas.subscriptions.interfaces.rest.mapper;

import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.interfaces.rest.model.CreateSubscription;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {

    private final ModelMapper modelMapper;

    public SubscriptionData mapToSubscriptionData(CreateSubscription source) {
        return this.modelMapper.map(source, SubscriptionData.class);
    }
}
