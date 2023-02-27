package com.cenoa.account.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.cenoa.common.event.AccountEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers: localhost:29092}")
    private String bootstrapServers;

    private static final String topic = "account-event-topic";

    @Bean
    public NewTopic accountEventTopic() {
        return TopicBuilder.name(topic)
            .partitions(3)
            .replicas(1)
            .build();
    }

    @Bean
    public ProducerFactory<String, AccountEvent> accountEventProducerFactory() {
        Map<String, Object> producerConfigProps = new HashMap<>();
        producerConfigProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfigProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(producerConfigProps);
    }

    @Bean
    public KafkaTemplate<String, AccountEvent> kafkaAccountTemplate() {
        return new KafkaTemplate<>(accountEventProducerFactory());
    }

}
