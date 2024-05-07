package br.com.bluesburger.payment.core.service.strategy;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.ports.PaymentStrategyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDebitCardStrategy implements PaymentStrategyPort {

    @Override
    public Payment checkoutPayment(Payment payment) {

        // Essa implementacao sera desenvolvida futuramente.
        return payment;
    }
}
