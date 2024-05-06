package br.com.bluesburger.payment.ports;


import br.com.bluesburger.payment.adapters.out.mercadopago.response.PaymentResponseMP;
import br.com.bluesburger.payment.core.domain.Payment;

public interface PaymentMercadoPagoPort {

    PaymentResponseMP generatePaymentQRCode(Payment payment);

    Payment getPayment(String paymentId);
}
