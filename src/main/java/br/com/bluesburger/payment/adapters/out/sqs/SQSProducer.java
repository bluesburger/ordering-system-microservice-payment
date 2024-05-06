package br.com.bluesburger.payment.adapters.out.sqs;

import br.com.bluesburger.payment.ports.SQSPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SQSProducer implements SQSPort {

    private final QueueMessagingTemplate messagingTemplate;

    @Value("${aws.sqs.production.name}")
    private String queueName;

    @Override
    public void sendMessage(Object message) {
        log.info("Sending message: {} to queue: {}", message, queueName);
        try {
            messagingTemplate.convertAndSend(queueName, message);
        }
        catch (Exception e) {
            log.error("Error sending message: {} to queue: {}", message, queueName, e);
            throw new RuntimeException("Error sending message: " + message + " to queue: " + queueName, e);
        }
    }
}
