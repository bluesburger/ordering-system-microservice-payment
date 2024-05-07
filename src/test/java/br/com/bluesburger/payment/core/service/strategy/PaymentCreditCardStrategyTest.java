package br.com.bluesburger.payment.core.service.strategy;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.ports.PaymentStrategyPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentCreditCardStrategyTest {

    @Mock
    private PaymentStrategyPort paymentStrategyPort;

    @InjectMocks
    private PaymentCreditCardStrategy paymentCreditCardStrategy;

    @Test
    void testCheckoutPayment() {
        var payment = Payment.builder().paymentMethod("debit_card").build();

        final var result = paymentCreditCardStrategy.checkoutPayment(payment);

        assertNotNull(result);
    }
}
