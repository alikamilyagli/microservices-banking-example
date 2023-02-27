package com.cenoa.transaction.producer;

import com.cenoa.common.event.DepositEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class DepositEventsProducer {

    private static final String topic = "deposit-event-topic";

    @Autowired
    private KafkaTemplate<String, DepositEvent> kafkaTemplate;

    public void sendMessage(DepositEvent depositEvent) {
        ProducerRecord<String, DepositEvent> producerRecord = new ProducerRecord<>(topic, depositEvent.getUuid().toString(), depositEvent);

        CompletableFuture<SendResult<String, DepositEvent>> sendResultCompletableFuture = kafkaTemplate.send(producerRecord);
        sendResultCompletableFuture.whenComplete((successResult, exception) -> {
            if (exception == null) {
                handleSuccess(successResult, depositEvent);
            } else {
                handleFailure(exception, depositEvent);
            }
        });
    }

    private void handleSuccess(SendResult<String, DepositEvent> result, DepositEvent event) {
        log.info("Message sent successfully: key : {}, value: {}, partition: {}", event.getUuid(), event, result.getRecordMetadata().partition());
    }

    private void handleFailure(Throwable ex, DepositEvent event) {
        log.error("Error while sending the message for {} and the exception is {}", event, ex);
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

}
