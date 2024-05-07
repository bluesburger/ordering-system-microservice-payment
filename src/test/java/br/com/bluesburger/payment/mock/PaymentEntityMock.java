package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;
import br.com.bluesburger.payment.core.domain.PaymentStatusEnum;

import java.math.BigDecimal;

public class PaymentEntityMock {
    private PaymentEntityMock() { }

    public static PaymentEntity paymentEntityMock() {
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setPaymentId("123");
        paymentEntity.setOrderId("456");
        paymentEntity.setTotalAmount(BigDecimal.ZERO);
        paymentEntity.setPaymentStatus(PaymentStatusEnum.PENDING.name());

        return paymentEntity;
    }
}

