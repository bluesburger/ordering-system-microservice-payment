package br.com.bluesburger.payment.adapters.in.webhook;

import br.com.bluesburger.payment.adapters.in.webhook.dto.Event;
import br.com.bluesburger.payment.ports.PaymentPort;
import br.com.bluesburger.payment.ports.SQSPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentWebHookController {

    private final PaymentPort paymentPort;

    private final SQSPort sqsPort;

    @PostMapping("/payment/webhook")
    public ResponseEntity<String> processPayment(@RequestBody Event event) {
        log.info("Processing webhook event: {}", event);
        var data = event.getData();

        if (isNull(data)) {
            return ResponseEntity.noContent().build();
        }
        final var orderId = paymentPort.updateStatusPayment(data.getId());
        sqsPort.sendMessage(orderId);

        log.info("Webhook event processed: {}", event);
        return ResponseEntity.ok().build();
    }
}
