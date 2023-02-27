package com.cenoa.account.consumer;

import com.cenoa.account.domain.exception.DuplicateEventException;
import com.cenoa.account.service.AccountService;
import com.cenoa.common.event.DepositEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.security.oauthbearer.secured.Retryable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class IdempotentDepositEventsConsumer {

    private final AtomicInteger counter = new AtomicInteger();
    private final AccountService accountService;

    @KafkaListener(
        topics = "deposit-event-topic",
        groupId = "deposit-events-listener-account-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onDepositEvent(ConsumerRecord<String, DepositEvent> consumerRecord) throws JsonProcessingException {
        counter.getAndIncrement();
        log.debug("Received message [{}] - consumerRecord: {}", counter.get(), consumerRecord);
        try {
            accountService.updateOnDeposit(consumerRecord.value());
        } catch (DuplicateEventException e) {
            // Update consumer offsets to ensure event is not again redelivered.
            log.debug("Duplicate message received: "+ e.getMessage());
        } catch (Exception e) {
            if (e instanceof Retryable) {
                log.debug("Throwing retryable exception.");
                throw e;
            }
            log.error("Error processing message: " + e.getMessage());
        }
    }

}
