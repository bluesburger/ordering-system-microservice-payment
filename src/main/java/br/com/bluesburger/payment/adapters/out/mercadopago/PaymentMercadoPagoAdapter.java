package br.com.bluesburger.payment.adapters.out.mercadopago;

import br.com.bluesburger.payment.adapters.out.exception.HttpRequestException;
import br.com.bluesburger.payment.adapters.out.mercadopago.request.PaymentRequestMP;
import br.com.bluesburger.payment.adapters.out.mercadopago.response.PaymentResponseMP;
import br.com.bluesburger.payment.adapters.out.mercadopago.response.PaymentResponseMapper;
import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static br.com.bluesburger.payment.adapters.out.mercadopago.request.PaymentRequestFactory.buildPaymentRequest;
import static java.util.Objects.requireNonNull;


@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!local")
public class PaymentMercadoPagoAdapter implements PaymentMercadoPagoPort {

    @Value("${mercado.pago.endpoint.payment}")
    private String urlPayment;

    @Value("${mercado.pago.endpoint.qr.code}")
    private String urlQrCode;

    @Value("${mercado.pago.access_token}")
    private String accessToken;

    @Value("${mercado.pago.notification.url}")
    private String notificationUrl;

    private final RestTemplate restTemplate;

    @Override
    public PaymentResponseMP generatePaymentQRCode(Payment payment) {
        try {
            HttpHeaders headers = buildHeaders();
            HttpEntity<PaymentRequestMP> requestEntity = buildRequest(payment, headers);
            log.info("Request generate QR Code mercado pago: {}", requestEntity);

            var response = restTemplate.exchange(urlQrCode, HttpMethod.POST, requestEntity, PaymentResponseMP.class);

            log.info("Response generate QR Code mercado pago: {}", response.getBody());
            return response.getBody();

        } catch (Exception e) {
            log.error("Ocorreu algum erro na geração do QR code. ", e);
            throw new HttpRequestException("Ocorreu algum erro na geração do QR code. ", e);
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        try {
            HttpHeaders headers = buildHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.info("Request payment mercado pago: {}", urlPayment);

            var response = restTemplate.exchange(
                    urlPayment.replace("{payment_id}", paymentId),
                    HttpMethod.GET,
                    entity,
                    PaymentResponseMP.class);

            log.info("Response payment mercado pago: {}", response.getBody());
            final var responseBody = requireNonNull(response.getBody());
            return PaymentResponseMapper.mapperPaymentToPaymentResponse(responseBody);

        } catch (Exception e) {
            log.error("Ocorreu algum erro ao buscar dados do pagamento ", e);
            throw new HttpRequestException("Ocorreu algum erro ao buscar dados do pagamento. ", e);
        }
    }

    private HttpHeaders buildHeaders() {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        return headers;
    }

    private HttpEntity<PaymentRequestMP> buildRequest(Payment payment, HttpHeaders headers) {
        var requestEntity = buildPaymentRequest(payment);
        requestEntity.setNotificationUrl(notificationUrl);

        return new HttpEntity<>(requestEntity, headers);
    }
}
