package api.azure.msv_kafka_consumer.infrastructure.consumer;

import api.azure.kafka.user;
import api.azure.msv_kafka_consumer.application.EmailService;
import api.azure.msv_kafka_consumer.application.implementation.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.util.Utf8;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "${spring.kafka.topics.customerNotification}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumerMsg(ConsumerRecord<String, user> customer) {
        try {
            log.info("Consuming the Customer message from topic -> : '{}' :: '{}'", "customer-notification", customer);

            // Extraer el correo del mensaje
            String email = ((Utf8) customer.value().getEmail()).toString(); // Conversión de Utf8 a String
            String subject = "Notificación de Cliente";
            String text = "Hola " + customer.value().getName() + " " + customer.value().getLastName() + ", su mensaje ha sido recibido.";

            // Enviar correo
            emailService.sendCustomerNotification(email, subject, text);
            log.info("Correo enviado a: {}", email);

        } catch (Exception e) {
            log.error("Error consuming message: ", e);
        }
    }
}
