package com.adidas.subscriptions.application.services.mapper;

import com.adidas.subscriptions.domain.model.vo.EmailData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailDataMapper {
    private final ModelMapper modelMapper;

    public EmailData mapToEmailData(SubscriptionData source){
        return this.modelMapper.map(source, EmailData.class);
    }
}
