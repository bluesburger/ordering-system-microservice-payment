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
class PaymentDebitCardStrategyTest {

    @Mock
    private PaymentStrategyPort paymentStrategyPort;

    @InjectMocks
    private PaymentDebitCardStrategy paymentDebitCardStrategy;

    @Test
    void testCheckoutPayment() {
        var payment = Payment.builder().paymentMethod("debit_card").build();

        final var result = paymentDebitCardStrategy.checkoutPayment(payment);

        assertNotNull(result);
    }
}
