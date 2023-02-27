package com.cenoa.history.consumer;

import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.WithdrawEvent;
import com.cenoa.history.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionEventsConsumer {

    @Autowired
    private HistoryService historyService;

    @KafkaListener(
        topics = "deposit-event-topic",
        groupId = "deposit-events-listener-history-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onDepositMessage(ConsumerRecord<String, DepositEvent> consumerRecord) {
        log.info("consumerRecord : {}", consumerRecord);

        try {
            historyService.save(consumerRecord.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(
        topics = "withdraw-event-topic",
        groupId = "withdraw-events-listener-history-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onWithdrawMessage(ConsumerRecord<String, WithdrawEvent> consumerRecord) {
        log.info("consumerRecord : {}", consumerRecord);

        try {
            historyService.save(consumerRecord.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
