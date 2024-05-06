package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.out.mercadopago.request.PaymentRequestMP;

import java.math.BigDecimal;
import java.util.Collections;

public class PaymentRequestMPMock {

    public static PaymentRequestMP getPaymentRequestMPMock() {
        return PaymentRequestMP.builder()
                .title("Payment for order 456")
                .description("Payment for order 456 placed 2024-05-05")
                .externalReference("456")
                .items(Collections.emptyList())
                .totalAmount(BigDecimal.valueOf(100))
                .build();
    }
}
