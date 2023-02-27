package com.cenoa.account.producer;

import com.cenoa.common.event.AccountEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AccountEventsProducer {

    private static final String topic = "account-event-topic";

    @Autowired
    private KafkaTemplate<String, AccountEvent> kafkaTemplate;

    public void sendMessage(AccountEvent accountEvent) {
        ProducerRecord<String, AccountEvent> producerRecord = new ProducerRecord<>(topic, accountEvent.getUuid().toString(), accountEvent);

        CompletableFuture<SendResult<String, AccountEvent>> sendResultCompletableFuture = kafkaTemplate.send(producerRecord);
        sendResultCompletableFuture.whenComplete((successResult, exception) -> {
            if (exception == null) {
                handleSuccess(successResult, accountEvent);
            } else {
                handleFailure(exception, accountEvent);
            }
        });
    }

    private void handleSuccess(SendResult<String, AccountEvent> result, AccountEvent event) {
        log.info("Message sent successfully: key : {}, value: {}, partition: {}", event.getUuid(), event, result.getRecordMetadata().partition());
    }

    private void handleFailure(Throwable ex, AccountEvent event) {
        log.error("Error while sending the message for {} and the exception is {}", event, ex);
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

}
