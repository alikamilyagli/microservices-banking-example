package com.cenoa.history.consumer;

import com.cenoa.common.event.AccountEvent;
import com.cenoa.history.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountEventsConsumer {

    @Autowired
    private HistoryService historyService;

    @KafkaListener(
        topics = "account-event-topic",
        groupId = "account-events-listener-history-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onAccountMessage(ConsumerRecord<String, AccountEvent> consumerRecord) {
        log.info("consumerRecord : {}", consumerRecord);

        try {
            historyService.save(consumerRecord.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
