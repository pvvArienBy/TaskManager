package by.it_academy.jd2.service.api.kafka;

import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;

import java.util.concurrent.CompletableFuture;

public interface IAuditSenderKafkaClient<K,V> {

     CompletableFuture<SendResult<K, V>> send(String topic, @Nullable V data);

}
