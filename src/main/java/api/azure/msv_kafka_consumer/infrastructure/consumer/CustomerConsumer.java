package api.azure.msv_kafka_consumer.infrastructure.consumer;

import api.azure.kafka.user;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerConsumer {

    @KafkaListener(topics = "${spring.kafka.topics.customerNotification}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumerMsg(ConsumerRecord<String, user> customer) {
        try {
            log.info("Consuming the Customer message from topic -> : '{}' :: '{}'", "customer-notification", customer);
        } catch (Exception e) {
            log.error("Error consuming message: ", e);
        }
    }
}
