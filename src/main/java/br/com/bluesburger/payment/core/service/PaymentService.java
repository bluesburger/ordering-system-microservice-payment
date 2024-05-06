package br.com.bluesburger.payment.core.service;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.service.strategy.PaymentContext;
import br.com.bluesburger.payment.ports.DynamoDBPort;
import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
import br.com.bluesburger.payment.ports.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentPort {

    private static final String ITEMS_PAYMENT_DISHES_KEY = "dishes";
    private static final String ITEMS_PAYMENT_DRINKS_KEY = "drinks";
    private static final String ITEMS_PAYMENT_DESSERTS_KEY = "desserts";

    private final PaymentContext paymentContext;

    private final PaymentMercadoPagoPort paymentMercadoPagoPort;

    private final DynamoDBPort dynamoDBPort;

    @Override
    public void updateStatusPayment(String paymentId) {
        var paymentMercadoPago = getPayment(paymentId);
        var payment = getPaymentByOrderId(paymentMercadoPago.getReferenceId());
        payment.updateStatus(paymentMercadoPago.getPaymentStatus().toUpperCase());

        dynamoDBPort.update(payment.getId(), payment);
    }

    @Override
    public Payment processPayment(Payment payment) {
        Map<String, List<Payment.Item>> items = itemsToPayment(payment);
        completePaymentWithValues(payment, items);

        log.info("Payment calculed: {}", payment);
        return paymentContext.processPayment(payment);
    }

    private void completePaymentWithValues(Payment payment, Map<String, List<Payment.Item>> items) {
        payment.setTotalAmountDishes(calculateTotalAmountItems(items.get(ITEMS_PAYMENT_DISHES_KEY)));
        payment.setTotalAmountDrinks(calculateTotalAmountItems(items.get(ITEMS_PAYMENT_DRINKS_KEY)));
        payment.setTotalAmountDesserts(calculateTotalAmountItems(items.get(ITEMS_PAYMENT_DESSERTS_KEY)));
        payment.setTotalAmount(calculateTotalAmountPayment(payment));
    }

    private BigDecimal calculateTotalAmountPayment(Payment payment) {
        var listTotalAmountItems = List.of(payment.getTotalAmountDishes(), payment.getTotalAmountDrinks(),
                payment.getTotalAmountDesserts());

        return listTotalAmountItems.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalAmountItems(List<Payment.Item> items) {
        items.forEach(item -> item.setTotalAmount(item.getPrice().multiply(new BigDecimal(item.getQuantity()))));

        return items.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, List<Payment.Item>> itemsToPayment(Payment payment) {
        Map<String, List<Payment.Item>> items = new HashMap<>();

        items.put(ITEMS_PAYMENT_DISHES_KEY, payment.getDishes());
        items.put(ITEMS_PAYMENT_DRINKS_KEY, payment.getDrinks());
        items.put(ITEMS_PAYMENT_DESSERTS_KEY, payment.getDesserts());

        return items;
    }

    private Payment getPayment(String paymentId) {
        return paymentMercadoPagoPort.getPayment(paymentId);
    }

    private Payment getPaymentByOrderId(String referenceId) {
        return dynamoDBPort.findByOrderId(referenceId);
    }
}
