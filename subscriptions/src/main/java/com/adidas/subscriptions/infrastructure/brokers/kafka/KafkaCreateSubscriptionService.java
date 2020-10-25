package com.adidas.subscriptions.infrastructure.brokers.kafka;

import com.adidas.subscriptions.domain.model.vo.SubscriptionData;
import com.adidas.subscriptions.domain.model.vo.SubscriptionId;
import com.adidas.subscriptions.infrastructure.outbound.SubscriptionsOutboundService;
import com.adidas.subscriptions.utils.UuidProvider;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KafkaCreateSubscriptionService implements SubscriptionsOutboundService {

    private final ReplyingKafkaTemplate<String, Object, Object> replyKafkaTemplatesubscription;
    private final UuidProvider uuidProvider;

    @Override
    public SubscriptionId createSubscription(SubscriptionData subscriptionData) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord("create_subscription", uuidProvider.randomUUID().toString(), subscriptionData);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "create_subscription_response".getBytes()));
        RequestReplyFuture<String, Object, Object> sendAndReceive = replyKafkaTemplatesubscription.sendAndReceive(record, Duration.ofSeconds(30));
        ConsumerRecord<String, Object> consumerRecord = sendAndReceive.get();
        Object response = consumerRecord.value();
        return (SubscriptionId) response;
    }
}
