package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.PaymentStatusEnum;

import java.math.BigDecimal;

public class PaymentMock {
    private PaymentMock() { }

    public static Payment getPaymentMock() {
        return Payment.builder()
                .id("123")
                .orderId("456")
                .referenceId("456")
                .orderCreatedTime("2024-05-05")
                .totalAmount(BigDecimal.valueOf(100))
                .paymentStatus(PaymentStatusEnum.PENDING.name())
                .build();
    }
}
