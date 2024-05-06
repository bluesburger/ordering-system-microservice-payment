package br.com.bluesburger.payment.adapters.out.dynamo;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.EntityMapper;
import br.com.bluesburger.payment.adapters.out.dynamo.repository.PaymentRepository;
import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.PaymentStatusEnum;
import br.com.bluesburger.payment.ports.DynamoDBPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamoDBAdapter implements DynamoDBPort {

    private final PaymentRepository paymentRepository;

    @Override
    public void save(Payment payment) {
        log.info("saving payment {}", payment);

        final var paymentEntity = EntityMapper.mapperPaymentEntityToPayment(payment);
        paymentEntity.setPaymentStatus(PaymentStatusEnum.PENDING.name());
        paymentEntity.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        paymentRepository.save(paymentEntity);
        log.info("saved payment {}", paymentEntity);
    }

    @Override
    public Payment findById(String paymentId) {
        log.info("get payment: {}", paymentId);

        final var paymentEntity = paymentRepository.findById(paymentId);
        Payment payment = EntityMapper.mapperPaymentToPaymentEntity(paymentEntity);

        log.info("payment: {}", paymentEntity);
        return payment;
    }

    @Override
    public Payment findByOrderId(String orderId) {
        log.info("get payment by orderID: {}", orderId);

        final var paymentEntity = paymentRepository.findByOrderId(orderId);
        Payment payment = EntityMapper.mapperPaymentToPaymentEntity(paymentEntity);

        log.info("payment: {}", paymentEntity);
        return payment;
    }

    @Override
    public String update(String paymentId, Payment payment) {
        log.info("update payment: {}", paymentId);
        final var paymentEntity = EntityMapper.mapperPaymentEntityToPayment(payment);
        paymentRepository.update(paymentId, paymentEntity);
        return paymentId;
    }

    @Override
    public void delete(String paymentId) {
        log.info("delete payment: {}", paymentId);

        paymentRepository.delete(paymentId);

        log.info("payment deleted");

    }
}
