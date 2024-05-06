package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentResponse;
import br.com.bluesburger.payment.core.domain.Payment;

public class PaymentResponseMock {

    public static PaymentResponse getPaymentResponseMock() {
        return PaymentResponse.builder()
                .inStoreOrderId("qrCodeIdTest")
                .qrData("qrCodeTest")
                .build();
    }

    public static Payment getPaymentMock(){
        return Payment.builder()
                .qrCode("qrCodeTest")
                .qrCodeId("qrCodeIdTest")
                .build();
    }
}
