package com.cenoa.transaction.producer;

import com.cenoa.common.event.WithdrawEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WithdrawEventsProducer {

    private static final String topic = "withdraw-event-topic";

    @Autowired
    private KafkaTemplate<String, WithdrawEvent> kafkaTemplate;

    public void sendMessage(WithdrawEvent withdrawEvent) {
        ProducerRecord<String, WithdrawEvent> producerRecord = new ProducerRecord<>(topic, withdrawEvent.getUuid().toString(), withdrawEvent);

        CompletableFuture<SendResult<String, WithdrawEvent>> sendResultCompletableFuture = kafkaTemplate.send(producerRecord);
        sendResultCompletableFuture.whenComplete((successResult, exception) -> {
            if (exception == null) {
                handleSuccess(successResult, withdrawEvent);
            } else {
                handleFailure(exception, withdrawEvent);
            }
        });
    }

    private void handleSuccess(SendResult<String, WithdrawEvent> result, WithdrawEvent event) {
        log.info("Message sent successfully: key : {}, value: {}, partition: {}", event.getUuid(), event, result.getRecordMetadata().partition());
    }

    private void handleFailure(Throwable ex, WithdrawEvent event) {
        log.error("Error while sending the message for {} and the exception is {}", event, ex);
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

}
