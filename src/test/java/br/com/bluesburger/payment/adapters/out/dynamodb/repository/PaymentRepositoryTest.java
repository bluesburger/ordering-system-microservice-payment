package br.com.bluesburger.payment.adapters.out.dynamodb.repository;

import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;
import br.com.bluesburger.payment.adapters.out.dynamo.repository.PaymentRepository;
import br.com.bluesburger.payment.mock.PaymentEntityMock;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Captor
    private ArgumentCaptor<PaymentEntity> paymentEntityArgumentCaptor;

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository(dynamoDBMapper);
    }

    @Test
    void save_shouldSavePaymentEntity() {
        var paymentEntity = PaymentEntityMock.paymentEntityMock();
        doNothing().when(dynamoDBMapper).save(paymentEntity);

        paymentRepository.save(paymentEntity);

        verify(dynamoDBMapper, times(1)).save(paymentEntityArgumentCaptor.capture());
        PaymentEntity savedPaymentEntity = paymentEntityArgumentCaptor.getValue();
        assertEquals(paymentEntity, savedPaymentEntity);
    }

    @Test
    void findById_shouldReturnPaymentEntityById() {
        String id = "123";
        var paymentEntity = PaymentEntityMock.paymentEntityMock();
        when(dynamoDBMapper.load(PaymentEntity.class, id)).thenReturn(paymentEntity);

        PaymentEntity result = paymentRepository.findById(id);

        assertEquals(paymentEntity, result);
        verify(dynamoDBMapper, times(1)).load(PaymentEntity.class, id);
    }

    @Test
    void findByOrderId_shouldReturnPaymentEntityByOrderId() {
        String orderId = "456";
        var paymentEntity = PaymentEntityMock.paymentEntityMock();
        PaginatedQueryList<PaymentEntity> resultList = mock(PaginatedQueryList.class);
        when(resultList.isEmpty()).thenReturn(false);
        when(resultList.get(0)).thenReturn(paymentEntity);
        when(dynamoDBMapper.query(eq(PaymentEntity.class), any(DynamoDBQueryExpression.class))).thenReturn(resultList);

        PaymentEntity result = paymentRepository.findByOrderId(orderId);

        assertEquals(paymentEntity, result);
        verify(dynamoDBMapper, times(1)).query(eq(PaymentEntity.class), any(DynamoDBQueryExpression.class));
    }

    @Test
    void findByOrderId_NotFound() {
        PaginatedQueryList<PaymentEntity> resultList = mock(PaginatedQueryList.class);
        when(resultList.isEmpty()).thenReturn(true);
        when(dynamoDBMapper.query(eq(PaymentEntity.class), any(DynamoDBQueryExpression.class))).thenReturn(resultList);

        PaymentEntity result = paymentRepository.findByOrderId("order123");

        assertNull(result);
        verify(dynamoDBMapper, times(1)).query(eq(PaymentEntity.class), any(DynamoDBQueryExpression.class));
    }

    @Test
    void delete_shouldDeletePaymentEntityById() {
        String id = "1";

        paymentRepository.delete(id);

        verify(dynamoDBMapper, times(1)).delete(id);
    }

    @Test
    void update_shouldUpdatePaymentEntityById() {
        var paymentEntity = PaymentEntityMock.paymentEntityMock();

        paymentRepository.update(paymentEntity.getPaymentId(), paymentEntity);

        verify(dynamoDBMapper, times(1)).save(eq(paymentEntity), any(DynamoDBSaveExpression.class));
    }
}
