package br.com.bluesburger.payment.adapters.out.dynamo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.EntityMapper;
import br.com.bluesburger.payment.adapters.out.dynamo.repository.PaymentRepository;
import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.PaymentStatusEnum;
import br.com.bluesburger.payment.ports.DynamoDBPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!prod")
public class DynamoDBFakeAdapter implements DynamoDBPort {
	
	private final PaymentRepository paymentRepository;

    @Override
    public void save(Payment payment) {
        log.info("saving payment {}", payment);

        final var paymentEntity = EntityMapper.mapperPaymentEntityToPayment(payment);
        paymentEntity.setPaymentStatus(PaymentStatusEnum.PENDING.name());
        paymentEntity.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        
        var entity = paymentRepository.save(paymentEntity);

        log.info("saved payment {}", entity);
    }

    @Override
    public Payment findById(String paymentId) {
        log.info("get payment: {}", paymentId);

        var paymentEntity = paymentRepository.findById(paymentId);
//        final var paymentEntity = new PaymentEntity();
//        paymentEntity.setPaymentStatus(PaymentStatusEnum.PENDING.name());
//        paymentEntity.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        Payment payment = EntityMapper.mapperPaymentToPaymentEntity(paymentEntity);

        log.info("payment: {}", paymentEntity);
        return payment;
    }

    @Override
    public Payment findByOrderId(String orderId) {
        log.info("get payment by orderID: {}", orderId);

        var paymentEntity = paymentRepository.findByOrderId(orderId);
//        final var paymentEntity = new PaymentEntity();
//        paymentEntity.setPaymentStatus(PaymentStatusEnum.PENDING.name());
//        paymentEntity.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        Payment payment = EntityMapper.mapperPaymentToPaymentEntity(paymentEntity);

        log.info("payment: {}", paymentEntity);
        return payment;
    }

    @Override
    public String update(String paymentId, Payment payment) {
        log.info("update payment: {}", paymentId);
        var paymentEntity = paymentRepository.findById(paymentId);
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
