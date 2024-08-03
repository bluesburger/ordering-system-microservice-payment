package br.com.bluesburger.payment.adapters.out.sqs;

import br.com.bluesburger.payment.adapters.out.exception.SQSIntegrationException;
import br.com.bluesburger.payment.adapters.out.sqs.dto.OrderPaidQueueDTO;
import br.com.bluesburger.payment.ports.SQSPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SQSProducer implements SQSPort {

    private final QueueMessagingTemplate messagingTemplate;

    @Value("${aws.sqs.production.name}")
    private String queueName;

    @Value("${aws.sqs.group.id}")
    private String messageGroupId;

    @Override
    public void sendMessage(Object message) {
        log.info("Sending message: {} to queue: {}", message, queueName);
        try {
            var messageQueue = new OrderPaidQueueDTO(message.toString(), "PAID");

            Message<OrderPaidQueueDTO> msg = MessageBuilder
                    .withPayload(messageQueue)
                    .setHeader(SqsMessageHeaders.SQS_GROUP_ID_HEADER, messageGroupId)
                    .setHeader(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, message.toString())
                    .build();

            messagingTemplate.send(queueName, msg);
            log.info("Message sent {}", msg);
        } catch (Exception e) {
            log.error("Error sending message: {} to queue: {}", message, queueName, e);
            throw new SQSIntegrationException("Error sending message: " + message + " to queue: " + queueName, e);
        }
    }
}
