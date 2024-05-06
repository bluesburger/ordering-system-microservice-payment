package br.com.bluesburger.payment.adapters.out.dynamodb;

import br.com.bluesburger.payment.adapters.out.dynamo.DynamoDBAdapter;
import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;
import br.com.bluesburger.payment.adapters.out.dynamo.repository.PaymentRepository;
import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.PaymentStatusEnum;
import br.com.bluesburger.payment.mock.PaymentEntityMock;
import br.com.bluesburger.payment.mock.PaymentMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamoDBAdapterTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private DynamoDBAdapter dynamoDBAdapter;

    @Captor
    private ArgumentCaptor<PaymentEntity> paymentEntityArgumentCaptor;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = PaymentMock.getPaymentMock();
    }

    @Test
    void save_shouldSavePaymentToRepository() {
        dynamoDBAdapter.save(payment);

        verify(paymentRepository, times(1)).save(paymentEntityArgumentCaptor.capture());
        PaymentEntity savedPaymentEntity = paymentEntityArgumentCaptor.getValue();
        assertEquals(payment.getOrderId(), savedPaymentEntity.getOrderId());
        assertEquals(payment.getTotalAmount(), savedPaymentEntity.getTotalAmount());
        assertEquals(PaymentStatusEnum.PENDING.name(), savedPaymentEntity.getPaymentStatus());
        assertNotNull(savedPaymentEntity.getCreatedDate());
    }

    @Test
    void findById_shouldReturnPaymentFromRepository() {
        var paymentEntity = PaymentEntityMock.paymentEntityMock();
        when(paymentRepository.findById(payment.getId())).thenReturn(paymentEntity);

        Payment result = dynamoDBAdapter.findById(payment.getId());

        verify(paymentRepository, times(1)).findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getTotalAmount(), result.getTotalAmount());
        assertEquals(PaymentStatusEnum.PENDING.name(), result.getPaymentStatus());
    }

    @Test
    void findByOrderId_shouldReturnPaymentFromRepository() {
        var paymentEntity = PaymentEntityMock.paymentEntityMock();
        when(paymentRepository.findByOrderId(payment.getOrderId())).thenReturn(paymentEntity);

        Payment foundPayment = dynamoDBAdapter.findByOrderId(payment.getOrderId());

        verify(paymentRepository, times(1)).findByOrderId(payment.getOrderId());
        assertEquals(payment.getId(), foundPayment.getId());
        assertEquals(payment.getOrderId(), foundPayment.getOrderId());
        assertEquals(payment.getTotalAmount(), foundPayment.getTotalAmount());
        assertEquals(PaymentStatusEnum.PENDING.name(), foundPayment.getPaymentStatus());
    }

    @Test
    void update_shouldUpdatePaymentInRepository() {
        dynamoDBAdapter.update(payment.getId(), payment);

        verify(paymentRepository, times(1)).update(eq(payment.getId()), paymentEntityArgumentCaptor.capture());
        PaymentEntity updatedPaymentEntity = paymentEntityArgumentCaptor.getValue();
        assertEquals(payment.getOrderId(), updatedPaymentEntity.getOrderId());
        assertEquals(payment.getTotalAmount(), updatedPaymentEntity.getTotalAmount());
        assertEquals(PaymentStatusEnum.PENDING.name(), updatedPaymentEntity.getPaymentStatus());
    }

    @Test
    void delete_shouldDeletePaymentFromRepository() {
        dynamoDBAdapter.delete(payment.getId());

        verify(paymentRepository, times(1)).delete(payment.getId());
    }
}
