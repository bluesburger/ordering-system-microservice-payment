package br.com.bluesburger.payment.ports;


import br.com.bluesburger.payment.core.domain.Payment;

public interface PaymentPort {

    Payment processPayment(Payment payment);

    String updateStatusPayment(String paymentId);
}
