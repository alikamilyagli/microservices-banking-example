package com.cenoa.transaction.config;

import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.WithdrawEvent;
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

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers: localhost:29092}")
    private String bootstrapServers;

    private static final String depositTopic = "deposit-event-topic";
    private static final String withdrawTopic = "withdraw-event-topic";

    @Bean
    public NewTopic depositEventTopic() {
        return TopicBuilder.name(depositTopic)
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic withdrawEventTopic() {
        return TopicBuilder.name(withdrawTopic)
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public ProducerFactory<String, DepositEvent> depositEventProducerFactory() {
        Map<String, Object> producerConfigProps = new HashMap<>();
        producerConfigProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfigProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(producerConfigProps);
    }

    @Bean
    public KafkaTemplate<String, DepositEvent> kafkaDepositTemplate() {
        return new KafkaTemplate<>(depositEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, WithdrawEvent> withdrawEventProducerFactory() {
        Map<String, Object> producerConfigProps = new HashMap<>();
        producerConfigProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfigProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(producerConfigProps);
    }

    @Bean
    public KafkaTemplate<String, WithdrawEvent> kafkaWithdrawTemplate() {
        return new KafkaTemplate<>(withdrawEventProducerFactory());
    }

}
