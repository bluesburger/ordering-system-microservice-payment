package br.com.bluesburger.payment.adapters.out.mercadopago.request;

import br.com.bluesburger.payment.core.domain.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class PaymentRequestFactory {

    private PaymentRequestFactory() {
    }

    public static PaymentRequestMP buildPaymentRequest(Payment payment) {
        return PaymentRequestMP.builder()
                .title(String.format("Payment for order %s", payment.getOrderId()))
                .description(String.format("Payment for order %s placed %s", payment.getOrderId(), payment.getOrderCreatedTime()))
                .externalReference(payment.getOrderId())
                .items(buildItemsPayment(payment))
                .totalAmount(payment.getTotalAmount())
                .build();
    }

    private static List<ItemRequestMP> buildItemsPayment(Payment payment) {
        var items = new ArrayList<ItemRequestMP>();
        var dishes = payment.getDishes();
        var drinks = payment.getDrinks();
        var desserts = payment.getDesserts();

        if (nonNull(dishes)) {
            items.addAll(dishes.stream().map(dishItem -> ItemRequestMP.builder()
                    .title(dishItem.getName())
                    .description(dishItem.getDescription())
                    .category(dishItem.getCategory())
                    .quantity(dishItem.getQuantity())
                    .unitPrice(dishItem.getPrice())
                    .unitMeasure(UnitMeasureEnum.UNIT.name())
                    .totalAmount(dishItem.getTotalAmount())
                    .build()).toList());
        }

        if (nonNull(drinks)) {
            items.addAll(drinks.stream().map(drinkItem -> ItemRequestMP.builder()
                    .title(drinkItem.getName())
                    .description(drinkItem.getDescription())
                    .category(drinkItem.getCategory())
                    .quantity(drinkItem.getQuantity())
                    .unitPrice(drinkItem.getPrice())
                    .unitMeasure(UnitMeasureEnum.UNIT.name())
                    .totalAmount(drinkItem.getTotalAmount())
                    .build()).toList());
        }

        if (nonNull(desserts)) {
            items.addAll(desserts.stream().map(dessertItem -> ItemRequestMP.builder()
                    .title(dessertItem.getName())
                    .description(dessertItem.getDescription())
                    .category(dessertItem.getCategory())
                    .quantity(dessertItem.getQuantity())
                    .unitPrice(dessertItem.getPrice())
                    .unitMeasure(UnitMeasureEnum.UNIT.name())
                    .totalAmount(dessertItem.getTotalAmount())
                    .build()).toList());
        }
        return items;
    }
}
