package br.com.bluesburger.payment.adapters.out.dynamo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Profile("!local")
public class PaymentRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public PaymentEntity save(PaymentEntity paymentEntity) {
        dynamoDBMapper.save(paymentEntity);
        return paymentEntity;
    }

    public PaymentEntity findById(String id) {
        return dynamoDBMapper.load(PaymentEntity.class, id);
    }

    public PaymentEntity findByOrderId(String orderId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(orderId));

        DynamoDBQueryExpression<PaymentEntity> queryExpression = new DynamoDBQueryExpression<PaymentEntity>()
                .withIndexName("orderId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression("orderId = :val1")
                .withExpressionAttributeValues(eav);

        List<PaymentEntity> results = dynamoDBMapper.query(PaymentEntity.class, queryExpression);

        return results.isEmpty() ? null : results.get(0);
    }

    public void delete(String paymentId) {
        dynamoDBMapper.delete(paymentId);
    }

    public String update(String paymentId, PaymentEntity paymentEntity) {
        paymentEntity.setPaymentId(paymentId);
        dynamoDBMapper.save(paymentEntity, new DynamoDBSaveExpression()
                .withExpectedEntry("paymentId", new ExpectedAttributeValue(
                        new AttributeValue().withS(paymentId))));

        return paymentId;
    }
}
