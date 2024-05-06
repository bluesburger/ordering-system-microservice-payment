package br.com.bluesburger.payment.adapters.out.sqs;

import br.com.bluesburger.payment.adapters.out.sqs.SQSProducer;
import br.com.bluesburger.payment.ports.SQSPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
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
    private ArgumentCaptor<Object> messageCaptor;

    @InjectMocks
    private SQSProducer sqsProducer;

    private final String queueName = "sqs-test-queue-name";
    private final String queueMessage = "Test message send to queue";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(sqsProducer, "queueName", queueName);
    }

    @Test
    void shouldSendMessageSuccessfully() {
        doNothing().when(messagingTemplate).convertAndSend(queueName, queueMessage);

        sqsProducer.sendMessage(queueMessage);

        verify(messagingTemplate, times(1)).convertAndSend(eq(queueName), messageCaptor.capture());
        assertEquals(queueMessage, messageCaptor.getValue());
    }

    @Test
    void shouldThrowExceptionWhenSendingMessageFails() {
        Exception expectedException = new RuntimeException("Test exception");
        doThrow(expectedException).when(messagingTemplate).convertAndSend(queueName, queueMessage);

        assertThrows(RuntimeException.class, () -> sqsProducer.sendMessage(queueMessage));

        verify(messagingTemplate, times(1)).convertAndSend(eq(queueName), messageCaptor.capture());
        assertEquals(queueMessage, messageCaptor.getValue());
    }
}
