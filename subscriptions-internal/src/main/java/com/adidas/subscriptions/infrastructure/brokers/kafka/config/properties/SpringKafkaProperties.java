package com.adidas.subscriptions.infrastructure.brokers.kafka.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class SpringApplicationProperties.
 */
@ConfigurationProperties(prefix = "spring.kafka")
@NoArgsConstructor
@Getter
@Setter
public class SpringKafkaProperties {

    private Consumer consumer;
    private Producer producer;
    private Properties properties;
    private Jaas jaas;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Consumer {

        /**
         * Consumer Bootstrap Servers
         */
        private String bootstrapServers;

        /**
         * Consumer Group
         */
        private String groupId;

        /**
         * Consumer AutoOffsetReset
         */
        private String autoOffsetReset;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Producer {

        /**
         * Producer Bootstrap Servers
         */
        private String bootstrapServers;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Properties {
        private Sasl sasl;
        private Security security;

        @NoArgsConstructor
        @Getter
        @Setter
        public static class Sasl {
            private Jaas jaas;
            /** sasl Mechanism */
            private String mechanism;

            @NoArgsConstructor
            @Getter
            @Setter
            public static class Jaas {
                /** sasl Config */
                private String config;
            }
        }

        @NoArgsConstructor
        @Getter
        @Setter
        public static class Security {
            /** security Protocol */
            private String protocol;
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Jaas {
        /**
         * jaas Security enabled
         */
        //@Value("${spring.kafka.jaas.enabled}")
        private boolean enabled;
    }

}
