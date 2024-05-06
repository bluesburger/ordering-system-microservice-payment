package br.com.bluesburger.payment.core.service.strategy;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.ports.DynamoDBPort;
import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
import br.com.bluesburger.payment.ports.PaymentStrategyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentPixStrategy implements PaymentStrategyPort {

    private final PaymentMercadoPagoPort paymentMercadoPagoPort;

    private final DynamoDBPort dynamodbPort;

    public Payment checkoutPayment(Payment payment) {
        log.info("Iniciando pagamento Pix");

        final var qrCodeResponse = paymentMercadoPagoPort.generatePaymentQRCode(payment);
        payment.completePaymentWithQrCode(qrCodeResponse.getQrData(), qrCodeResponse.getOrderId());

        dynamodbPort.save(payment);

        log.info("Pagamento Pix gerado com sucesso: {}", qrCodeResponse);
        return payment;
    }
}
