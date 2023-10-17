package by.it_academy.jd2.service;

import by.it_academy.jd2.service.api.kafka.IAuditSenderKafkaClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AuditSenderKafkaClient implements IAuditSenderKafkaClient<String, Object> {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public AuditSenderKafkaClient(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public CompletableFuture<SendResult<String, Object>> send(String topic, Object data) {
        return kafkaTemplate.send(topic, data);
    }
}
