package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.out.mercadopago.response.PaymentResponseMP;

public class PaymentResponseMPMock {
    private PaymentResponseMPMock() {
    }

    public static PaymentResponseMP getPaymentResponseMPMock() {
        return PaymentResponseMP.builder()
                .orderId("456")
                .externalReference("456")
                .qrData("testQrCodeData")
                .status("APPROVED")
                .build();
    }
}
