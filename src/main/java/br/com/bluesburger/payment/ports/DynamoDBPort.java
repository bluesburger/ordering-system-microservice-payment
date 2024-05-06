package br.com.bluesburger.payment.ports;

import br.com.bluesburger.payment.core.domain.Payment;

public interface DynamoDBPort {

     void save(Payment payment);

     Payment findById(String paymentId);
     Payment findByOrderId(String orderId);

     String update(String paymentId, Payment payment);

     void delete(String paymentId);
}
