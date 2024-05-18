package br.com.bluesburger.payment.adapters.out.sqs;

import br.com.bluesburger.payment.adapters.out.exception.SQSIntegrationException;
import br.com.bluesburger.payment.adapters.out.sqs.dto.OrderPaidQueueDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SQSProducerTest {

    @Mock
    private QueueMessagingTemplate messagingTemplate;

    @Captor
    private ArgumentCaptor<Message<OrderPaidQueueDTO>> messageCaptor;

    @InjectMocks
    private SQSProducer sqsProducer;

    private final String queueName = "test-queue";
    private final String messageGroupId = "test-group-id";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(sqsProducer, "queueName", queueName);
        ReflectionTestUtils.setField(sqsProducer, "messageGroupId", messageGroupId);
    }

    @Test
    void sendMessage_shouldSendValidMessage_whenNoExceptionIsThrown() {
        String messageContent = "test-message";
        sqsProducer.sendMessage(messageContent);

        verify(messagingTemplate).send(eq(queueName), messageCaptor.capture());
        Message<OrderPaidQueueDTO> capturedMessage = messageCaptor.getValue();

        assertEquals(messageContent, capturedMessage.getPayload().getOrderId());
        assertEquals(messageGroupId, capturedMessage.getHeaders().get(SqsMessageHeaders.SQS_GROUP_ID_HEADER));
        assertEquals(messageContent, capturedMessage.getHeaders().get(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER));
    }

    @Test
    void testSendMessage_Success() {
        String message = "test-message";
        doNothing().when(messagingTemplate).send(any(String.class), any(Message.class));

        sqsProducer.sendMessage(message);

        verify(messagingTemplate, times(1)).send(eq("test-queue"), any(Message.class));
    }

    @Test
    void testSendMessage_Exception() {
        String message = "test-message";
        doThrow(new RuntimeException("AWS error")).when(messagingTemplate).send(any(String.class), any(Message.class));

        SQSIntegrationException exception = assertThrows(SQSIntegrationException.class, () ->
                sqsProducer.sendMessage(message));

        assertEquals("Error sending message: test-message to queue: test-queue", exception.getMessage());
        verify(messagingTemplate, times(1)).send(eq("test-queue"), any(Message.class));
    }
}
