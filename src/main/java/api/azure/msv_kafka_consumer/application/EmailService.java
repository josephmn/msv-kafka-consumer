package api.azure.msv_kafka_consumer.application;

public interface EmailService {
    void sendCustomerNotification(String to, String subject, String text);
}
