package br.com.bluesburger.payment.adapters.out.dynamo.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;

@Repository
@Profile("!prod")
public class PaymentFakeRepository extends PaymentRepository {
	
	public static Map<String, PaymentEntity> PAYMENTS = new HashMap<>();
	
	public PaymentFakeRepository() {
		super(null);
	}

	@Override
    public PaymentEntity save(PaymentEntity paymentEntity) {
		paymentEntity.setPaymentId(UUID.randomUUID().toString());
		PAYMENTS.putIfAbsent(paymentEntity.getPaymentId(), paymentEntity);
        return paymentEntity;
    }

	@Override
    public PaymentEntity findById(String id) {
		return Optional.ofNullable(PAYMENTS.get(id)).orElse(null);
    }

	@Override
    public PaymentEntity findByOrderId(String orderId) {
		try {
		return PAYMENTS.entrySet().stream()
				.filter(e -> e.getValue().getOrderId().equalsIgnoreCase(orderId))
				.findFirst()
				.get().getValue();
		} catch(Exception e) {
			return null;
		}
    }

	@Override
    public void delete(String paymentId) {
    	PAYMENTS.remove(paymentId);
    }

	@Override
    public String update(String paymentId, PaymentEntity paymentEntity) {
		PAYMENTS.remove(paymentId);
		PAYMENTS.put(paymentId, paymentEntity);
        return paymentId;
    }
}
