package com.cenoa.history.config;

import com.cenoa.common.event.AccountEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers: localhost:29092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id: account-events-listener-history-group}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, AccountEvent> consumerFactory() {
        Map<String, Object> consumerConfigProps = new HashMap<>();
        consumerConfigProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfigProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerConfigProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfigProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerConfigProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumerConfigProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(consumerConfigProps, new StringDeserializer(), new JsonDeserializer<>(AccountEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountEvent> kafkaContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaContainerFactory.setConsumerFactory(consumerFactory());

        return kafkaContainerFactory;
    }

}
