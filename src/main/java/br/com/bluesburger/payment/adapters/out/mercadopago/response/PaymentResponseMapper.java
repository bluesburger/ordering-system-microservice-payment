package br.com.bluesburger.payment.adapters.out.mercadopago.response;

import br.com.bluesburger.payment.core.domain.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResponseMapper {

    private PaymentResponseMapper() { }

    public static Payment mapperPaymentToPaymentResponse(PaymentResponseMP paymentResponse) {
        return Payment.builder()
                .updatedDate(paymentResponse.getDateApproved())
                .paymentStatus(paymentResponse.getStatus())
                .referenceId(paymentResponse.getExternalReference())
                .build();
    }
 }
