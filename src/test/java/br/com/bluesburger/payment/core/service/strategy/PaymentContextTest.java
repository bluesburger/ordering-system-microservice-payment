package br.com.bluesburger.payment.core.service.strategy;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.mock.PaymentMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentContextTest {

    @Mock
    private PaymentPixStrategy paymentPixStrategy;

    @Mock
    private PaymentDebitCardStrategy paymentDebitCardStrategy;

    @Mock
    private PaymentCreditCardStrategy paymentCreditCardStrategy;

    @InjectMocks
    private PaymentContext paymentContext;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = PaymentMock.getPaymentMock();
    }

    @Test
    void shouldProcessPaymentWithPixStrategy() {
        payment.setPaymentMethod("pix");
        when(paymentPixStrategy.checkoutPayment(any(Payment.class))).thenReturn(payment);

        final var result = paymentContext.processPayment(payment);

        assertEquals(payment, result);
        verify(paymentPixStrategy, times(1)).checkoutPayment(payment);

    }

    @Test
    void shouldProcessPaymentWithDebitCardStrategy() {
        payment.setPaymentMethod("debit_card");
        when(paymentDebitCardStrategy.checkoutPayment(any(Payment.class))).thenReturn(payment);

        final var result = paymentContext.processPayment(payment);

        assertEquals(payment, result);
        verify(paymentDebitCardStrategy, times(1)).checkoutPayment(payment);
    }

    @Test
    void shouldProcessPaymentWithCreditCardStrategy() {
        payment.setPaymentMethod("credit_card");
        when(paymentCreditCardStrategy.checkoutPayment(any(Payment.class))).thenReturn(payment);

        final var result = paymentContext.processPayment(payment);

        assertEquals(payment, result);
        verify(paymentCreditCardStrategy, times(1)).checkoutPayment(payment);

    }

    @Test
    void shouldThrowIllegalArgumentExceptionForUnsupportedPaymentMethod() {
        payment.setPaymentMethod("unknown");

        assertThrows(IllegalArgumentException.class, () -> paymentContext.processPayment(payment));
    }
}
