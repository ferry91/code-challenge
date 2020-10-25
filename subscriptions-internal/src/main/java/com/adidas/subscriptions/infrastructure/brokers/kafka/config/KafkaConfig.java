package com.adidas.subscriptions.infrastructure.brokers.kafka.config;

import com.adidas.subscriptions.infrastructure.brokers.kafka.config.properties.AdidasKafkaProperties;
import com.adidas.subscriptions.infrastructure.brokers.kafka.config.properties.SpringKafkaProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KafkaConfig configuration class, used to configure producer, consumer and
 * topics
 */
@Configuration
@EnableConfigurationProperties(value = { SpringKafkaProperties.class, AdidasKafkaProperties.class })
@EnableKafka
@AllArgsConstructor
@Getter
@Setter
public class KafkaConfig implements WebMvcConfigurer {

	@Autowired
	private AdidasKafkaProperties adidasKafkaProperties;

	@Autowired
	private SpringKafkaProperties springKafkaProperties;
	
	@Autowired(required = false)
	private GenericWebApplicationContext context;

	@Autowired
	private Environment env;

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

	private static final String SECURITY_PROTOCOL = "security.protocol";

	@PostConstruct
	public void createTopics() {
		if (adidasKafkaProperties.getTopics() != null && Arrays.asList(env.getActiveProfiles()).contains("local")
				&& !adidasKafkaProperties.getTopics().isEmpty()) {
			this.initializeBeans(adidasKafkaProperties.getTopics());
		}
	}

	private void initializeBeans(List<AdidasKafkaProperties.Topics> topics) {
		topics.forEach(t -> context.registerBean(t.getName(), NewTopic.class, t::toNewTopic));
	}

	/**
	 * Bean to configure Kafka Admin concerns.
	 *
	 * @return KafkaAdmin instance
	 */
	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
				springKafkaProperties.getConsumer().getBootstrapServers());
		if (springKafkaProperties.getJaas().isEnabled()) {
			configs.put(SaslConfigs.SASL_JAAS_CONFIG,
					springKafkaProperties.getProperties().getSasl().getJaas().getConfig());
			configs.put(SaslConfigs.SASL_MECHANISM, springKafkaProperties.getProperties().getSasl().getMechanism());
			configs.put(SECURITY_PROTOCOL, springKafkaProperties.getProperties().getSecurity().getProtocol());
		}
		return new KafkaAdmin(configs);
	}

	/**
	 * Bean that configure the kafka producer
	 *
	 * @return Properties Map
	 */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		// list of host:port pairs used for establishing the initial connections to the
		// Kakfa cluster
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, springKafkaProperties.getProducer().getBootstrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

		return setJaasProperties(props);
	}

	private Map<String, Object> setJaasProperties(Map<String, Object> props) {
		if (springKafkaProperties.getJaas().isEnabled()) {
			props.put(SaslConfigs.SASL_JAAS_CONFIG,
					springKafkaProperties.getProperties().getSasl().getJaas().getConfig());
			props.put(SaslConfigs.SASL_MECHANISM, springKafkaProperties.getProperties().getSasl().getMechanism());
			props.put(SECURITY_PROTOCOL, springKafkaProperties.getProperties().getSecurity().getProtocol());
		}

		return props;
	}

	/**
	 * Bean that configure the json kafka consumer
	 *
	 * @return Properties Map
	 */
	@Bean
	public Map<String, Object> jsonConsumerConfigs() {
		// Properties
		Map<String, Object> props = new HashMap<>();
		ConsumerConfig.configDef().configKeys();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, springKafkaProperties.getConsumer().getBootstrapServers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, springKafkaProperties.getConsumer().getGroupId());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, springKafkaProperties.getConsumer().getAutoOffsetReset());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return setJaasProperties(props);
	}

	@Bean
	public ConsumerFactory<String, Message> jsonConsumerFactory() {
		JsonDeserializer<Message> deserializer = new JsonDeserializer<>(Message.class);
		deserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(jsonConsumerConfigs(), new StringDeserializer(), deserializer);
	}

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		try {
			LOGGER.info(new ObjectMapper().writeValueAsString(producerConfigs()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
		KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
		return kafkaTemplate;
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> kafkaJsonListenerContainerFactory(
			ConsumerFactory<String, Message> jsonConsumerFactory, KafkaTemplate<String, Object> kafkaTemplate) {
		ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(jsonConsumerFactory);
		factory.setReplyTemplate(kafkaTemplate);
		factory.getContainerProperties().setAckOnError(false);
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
		return factory;
	}

}
