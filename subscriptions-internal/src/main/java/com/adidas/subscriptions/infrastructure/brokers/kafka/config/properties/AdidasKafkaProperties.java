package com.adidas.subscriptions.infrastructure.brokers.kafka.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;


/**
 * The Class SpringApplicationProperties.
 */
@NoArgsConstructor
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "adidas.kafka")
public class AdidasKafkaProperties {

    private SecurityManager securityManager;

    private Error error;

    private List<Topics> topics;

    private List<String> consumerInterceptors;

    private List<String> producerInterceptors;

    @NoArgsConstructor
    @Validated
    @Getter
    @Setter
    public static class SecurityManager {

        /**
         * Security Manager URL
         */
        private String url;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Topics {
        /**
         * name
         */
        private String name;

        /**
         * numPartitions
         */
        private Integer numPartitions;

        /**
         * replicationFactorL
         */
        private Short replicationFactor;

        public NewTopic toNewTopic() {
            return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
        }
    }

}



