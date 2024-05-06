package br.com.bluesburger.payment.adapters.out.dynamo.entities;

import br.com.bluesburger.payment.core.domain.Payment;

public class EntityMapper {

    private EntityMapper() {
    }

    public static PaymentEntity mapperPaymentEntityToPayment(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setUserId(payment.getUserId());
        paymentEntity.setOrderId(payment.getOrderId());
        paymentEntity.setPaymentStatus(payment.getPaymentStatus());
        paymentEntity.setPaymentMethod(payment.getPaymentMethod());
        paymentEntity.setCreatedDate(payment.getCreatedDate());
        paymentEntity.setQrCodeId(payment.getQrCodeId());
        paymentEntity.setQrCode(payment.getQrCode());
        paymentEntity.setTotalAmount(payment.getTotalAmount());

        return paymentEntity;
    }

    public static Payment mapperPaymentToPaymentEntity(PaymentEntity paymentEntity) {
        Payment payment = new Payment();

        payment.setId(paymentEntity.getPaymentId());
        payment.setReferenceId(paymentEntity.getOrderId());
        payment.setUserId(paymentEntity.getUserId());
        payment.setOrderId(paymentEntity.getOrderId());
        payment.setPaymentStatus(paymentEntity.getPaymentStatus());
        payment.setPaymentMethod(paymentEntity.getPaymentMethod());
        payment.setCreatedDate(paymentEntity.getCreatedDate());
        payment.setQrCodeId(paymentEntity.getQrCodeId());
        payment.setQrCode(paymentEntity.getQrCode());
        payment.setTotalAmount(paymentEntity.getTotalAmount());

        return payment;
    }
}
