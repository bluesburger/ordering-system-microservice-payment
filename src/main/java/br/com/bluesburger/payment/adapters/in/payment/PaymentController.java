package br.com.bluesburger.payment.adapters.in.payment;

import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest;
import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentResponse;
import br.com.bluesburger.payment.adapters.out.dynamo.entities.PaymentEntity;
import br.com.bluesburger.payment.adapters.out.dynamo.repository.PaymentRepository;
import br.com.bluesburger.payment.ports.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.bluesburger.payment.adapters.in.payment.dto.PaymentMapper.mapperPaymentToPaymentRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentPort paymentPort;

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Payment request: {}", paymentRequest);
        var payment = mapperPaymentToPaymentRequest(paymentRequest);

        final var paymentProcessed = paymentPort.processPayment(payment);

        var paymentResponse = mapperPaymentToPaymentRequest(paymentProcessed);
        log.info("Payment response: {}", paymentResponse);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
