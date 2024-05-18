package br.com.bluesburger.payment.adapters.in.webhook;

import br.com.bluesburger.payment.ports.PaymentPort;
import br.com.bluesburger.payment.ports.SQSPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.bluesburger.payment.mock.EventWebhookMock.getEventMock;
import static br.com.bluesburger.payment.mock.EventWebhookMock.getEventNoDataMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentWebHookControllerTest {

    @Mock
    private PaymentPort paymentPort;

    @Mock
    private SQSPort sqsPort;

    @Captor
    private ArgumentCaptor<String> orderIdCaptor;

    @InjectMocks
    private PaymentWebHookController paymentWebHookController;


    @Test
    void processPayment_whenDataIsNotNull_shouldUpdatePaymentStatusAndSendMessageToSqs() {
        var event = getEventMock();
        when(paymentPort.updateStatusPayment(anyString())).thenReturn(event.getData().getId());

        ResponseEntity<String> responseEntity = paymentWebHookController.processPayment(event);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(sqsPort).sendMessage(orderIdCaptor.capture());
        assertEquals("paymentId", orderIdCaptor.getValue());
    }

    @Test
    void processPayment_whenDataIsNull_shouldReturnNoContent() {
        var event = getEventNoDataMock();

        ResponseEntity<String> responseEntity = paymentWebHookController.processPayment(event);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(paymentPort, never()).updateStatusPayment(any());
        verify(sqsPort, never()).sendMessage(any());
    }
}
