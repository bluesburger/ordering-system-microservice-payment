package br.com.bluesburger.payment.core.service.strategy;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.PaymentMethodEnum;
import br.com.bluesburger.payment.ports.PaymentStrategyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentContext {

    private Map<PaymentMethodEnum, PaymentStrategyPort> paymentStrategies;
    private final PaymentPixStrategy paymentPixStrategy;
    private final PaymentDebitCardStrategy paymentDebitCardStrategy;
    private final PaymentCreditCardStrategy paymentCreditCardStrategy;

    public Payment processPayment(Payment payment) {
        buildStrategies();
        PaymentMethodEnum paymentMethod;

        try {
            paymentMethod = PaymentMethodEnum.valueOf(payment.getPaymentMethod().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("O metodo de pagamento nao e suportado: " + payment.getPaymentMethod());
        }

        PaymentStrategyPort strategy = paymentStrategies.get(paymentMethod);
        return strategy.checkoutPayment(payment);
    }

    private void buildStrategies() {
        paymentStrategies = new HashMap<>();

        paymentStrategies.put(PaymentMethodEnum.PIX, paymentPixStrategy);
        paymentStrategies.put(PaymentMethodEnum.DEBIT_CARD, paymentDebitCardStrategy);
        paymentStrategies.put(PaymentMethodEnum.CREDIT_CARD, paymentCreditCardStrategy);
    }
}
