package br.com.bluesburger.payment.adapters.out.mercadopago;

import br.com.bluesburger.payment.adapters.out.exception.HttpRequestException;
import br.com.bluesburger.payment.adapters.out.mercadopago.request.PaymentRequestMP;
import br.com.bluesburger.payment.adapters.out.mercadopago.response.PaymentResponseMP;
import br.com.bluesburger.payment.core.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static br.com.bluesburger.payment.mock.PaymentMock.getPaymentMock;
import static br.com.bluesburger.payment.mock.PaymentRequestMPMock.getPaymentRequestMPMock;
import static br.com.bluesburger.payment.mock.PaymentResponseMPMock.getPaymentResponseMPMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMercadoPagoAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentMercadoPagoAdapter paymentMercadoPagoAdapter;

    private final String urlPayment = "mockedUrlPayment";
    private final String urlQrCode = "mockedUrlQrCode";
    private final String accessToken = "mockedAccessToken";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(paymentMercadoPagoAdapter, "urlPayment", urlPayment);
        ReflectionTestUtils.setField(paymentMercadoPagoAdapter, "urlQrCode", urlQrCode);
        ReflectionTestUtils.setField(paymentMercadoPagoAdapter, "accessToken", accessToken);
    }

    @Test
    void testGeneratePaymentQRCode_Success() {
        var payment = getPaymentMock();
        var headers = getHeadersMock();
        var request = getPaymentRequestMPMock();
        var response = getPaymentResponseMPMock();
        HttpEntity<PaymentRequestMP> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<PaymentResponseMP> responseEntity = ResponseEntity.ok(response);
        when(restTemplate.exchange(urlQrCode, HttpMethod.POST, requestEntity, PaymentResponseMP.class))
                .thenReturn(responseEntity);

        final var result = paymentMercadoPagoAdapter.generatePaymentQRCode(payment);

        assertEquals(responseEntity.getBody(), result);
    }

    @Test
    void testGeneratePaymentQRCode_Failure() {
        var payment = getPaymentMock();
        var headers = getHeadersMock();
        var request = getPaymentRequestMPMock();
        HttpEntity<PaymentRequestMP> requestEntity = new HttpEntity<>(request, headers);
        RuntimeException exception = new RuntimeException("Test Exception");

        when(restTemplate.exchange(urlQrCode, HttpMethod.POST, requestEntity, PaymentResponseMP.class)).thenThrow(exception);

        assertThrows(HttpRequestException.class, () -> paymentMercadoPagoAdapter.generatePaymentQRCode(payment));
    }

    @Test
    void testGetPayment_Success() {
        String paymentId = "mockedPaymentId";
        var headers = getHeadersMock();
        var response = getPaymentResponseMPMock();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<PaymentResponseMP> responseEntity = ResponseEntity.ok(response);
        when(restTemplate.exchange(eq(urlPayment.replace("{payment_id}", paymentId)), eq(HttpMethod.GET),
                eq(entity), eq(PaymentResponseMP.class))).thenReturn(responseEntity);

        Payment result = paymentMercadoPagoAdapter.getPayment(paymentId);

        assertEquals("APPROVED", result.getPaymentStatus());
    }

    @Test
    void testGetPayment_Failure() {
        String paymentId = "mockedPaymentId";
        var headers = getHeadersMock();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RuntimeException exception = new RuntimeException("Test Exception");
        when(restTemplate.exchange(eq(urlPayment.replace("{payment_id}", paymentId)), eq(HttpMethod.GET),
                eq(entity), eq(PaymentResponseMP.class))).thenThrow(exception);

        assertThrows(HttpRequestException.class, () -> paymentMercadoPagoAdapter.getPayment(paymentId));
    }

    private HttpHeaders getHeadersMock() {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        return headers;
    }
}
