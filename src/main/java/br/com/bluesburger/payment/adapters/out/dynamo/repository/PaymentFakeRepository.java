package br.com.bluesburger.payment.adapters.out.dynamo.repository;

import java.math.BigDecimal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;

@Repository
@Profile("local")
public class PaymentFakeRepository extends PaymentRepository {
	
	public PaymentFakeRepository() {
		super(null);
	}

	private static final String QR_CODE = "qwertyqwertyqwerty";

	@Override
    public PaymentEntity save(PaymentEntity paymentEntity) {
        return paymentEntity;
    }

	@Override
    public PaymentEntity findById(String id) {
        var totalAmount = new BigDecimal(100.00);
        var orderId = "qwerty";
        var userId = "qwerty";
    	return new PaymentEntity(id, "qwerty", "method", "2024-08-01T00:37:54", "2024-08-01T00:50:12", "qwerty", QR_CODE, totalAmount, orderId, userId);
    }

	@Override
    public PaymentEntity findByOrderId(String orderId) {
        return null;
    }

	@Override
    public void delete(String paymentId) {
    	//
    }

	@Override
    public String update(String paymentId, PaymentEntity paymentEntity) {
        return paymentId;
    }
}
