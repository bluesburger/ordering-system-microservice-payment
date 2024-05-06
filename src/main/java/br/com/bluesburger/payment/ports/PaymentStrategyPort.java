package br.com.bluesburger.payment.ports;

import br.com.bluesburger.payment.core.domain.Payment;

public interface PaymentStrategyPort {

    Payment checkoutPayment(Payment payment);
}
