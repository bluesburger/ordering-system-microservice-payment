package br.com.bluesburger.payment.core.service;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.service.strategy.PaymentContext;
import br.com.bluesburger.payment.mock.PaymentMock;
import br.com.bluesburger.payment.ports.DynamoDBPort;
import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentContext paymentContext;

    @Mock
    private PaymentMercadoPagoPort paymentMercadoPagoPort;

    @Mock
    private DynamoDBPort dynamoDBPort;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = PaymentMock.getPaymentMock();
    }

    @Test
    void testUpdateStatusPayment_Success() {
        when(paymentMercadoPagoPort.getPayment(any())).thenReturn(payment);
        when(dynamoDBPort.findByOrderId(any())).thenReturn(payment);

        paymentService.updateStatusPayment(payment.getId());

        verify(paymentMercadoPagoPort, times(1)).getPayment(payment.getId());
        verify(dynamoDBPort, times(1)).findByOrderId(payment.getOrderId());
        verify(dynamoDBPort, times(1)).update(eq(payment.getId()), any(Payment.class));
    }


    @Test
    void testProcessPayment_Succes() {
        when(paymentContext.processPayment(any())).thenReturn(payment);

        final var processedPayment = paymentService.processPayment(payment);

        assertEquals(BigDecimal.valueOf(40), processedPayment.getTotalAmountDishes());
        assertEquals(BigDecimal.valueOf(99.95), processedPayment.getTotalAmountDrinks());
        assertEquals(BigDecimal.valueOf(118.89), processedPayment.getTotalAmountDesserts());
        assertEquals(BigDecimal.valueOf(258.84), processedPayment.getTotalAmount());
        verify(paymentContext, times(1)).processPayment(payment);
    }

    @Test
    void testProcessPayment_EmptyPayment() {
        var paymentEmpty = Payment.builder().build();
        when(paymentContext.processPayment(any())).thenReturn(payment);

        final var processedPayment = paymentService.processPayment(paymentEmpty);

        assertEquals(BigDecimal.ZERO, processedPayment.getTotalAmount());
        verify(paymentContext, times(1)).processPayment(any());
    }
}
