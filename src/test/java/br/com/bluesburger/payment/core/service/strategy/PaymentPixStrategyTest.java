package br.com.bluesburger.payment.core.service.strategy;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.mock.PaymentMock;
import br.com.bluesburger.payment.mock.PaymentResponseMPMock;
import br.com.bluesburger.payment.ports.DynamoDBPort;
import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentPixStrategyTest {

    @Mock
    private PaymentMercadoPagoPort paymentMercadoPagoPort;

    @Mock
    private DynamoDBPort dynamoDBPort;

    @InjectMocks
    private PaymentPixStrategy paymentPixStrategy;

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = PaymentMock.getPaymentMock();
    }

    @Test
    void testCheckoutPayment_Success() {
        payment.setQrCodeId("qrCodeId");
        payment.setOrderId("orderId");
        var paymentResponse = PaymentResponseMPMock.getPaymentResponseMPMock();
        when(paymentMercadoPagoPort.generatePaymentQRCode(payment)).thenReturn(paymentResponse);

        final var result = paymentPixStrategy.checkoutPayment(payment);

        assertEquals(payment, result);
        verify(dynamoDBPort, times(1)).save(payment);
    }
}