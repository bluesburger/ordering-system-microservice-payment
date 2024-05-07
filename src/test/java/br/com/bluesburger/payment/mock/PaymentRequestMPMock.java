package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.out.mercadopago.request.ItemRequestMP;
import br.com.bluesburger.payment.adapters.out.mercadopago.request.PaymentRequestMP;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentRequestMPMock {

    public static PaymentRequestMP getPaymentRequestMPMock() {
        var items = new ArrayList<ItemRequestMP>();
        items.addAll(getDishesMock());
        items.addAll(getDrinksMock());
        items.addAll(getDessertsMock());

        return PaymentRequestMP.builder()
                .title("Payment for order 456")
                .description("Payment for order 456 placed 2024-05-05")
                .externalReference("456")
                .items(items)
                .totalAmount(BigDecimal.ZERO)
                .build();
    }

    private static List<ItemRequestMP> getDishesMock() {
        return List.of(
                ItemRequestMP.builder()
                        .title("Gazpacho")
                        .description("sopa fria à base de hortaliças, com destaque para o tomate, o pepino e o pimentão")
                        .category("DISH")
                        .unitMeasure("UNIT")
                        .unitPrice(BigDecimal.TEN)
                        .quantity(2)
                        .build(),

                ItemRequestMP.builder()
                        .title("Falafel")
                        .description("consiste em bolinhos fritos de grão-de-bico ou fava moída, normalmente misturados com condimentos como alho, cebolinha, salsa, coentro e cominho")
                        .category("DISH")
                        .unitMeasure("UNIT")
                        .unitPrice(BigDecimal.TEN)
                        .quantity(2)
                        .build()
        );
    }

    private static List<ItemRequestMP> getDrinksMock() {
        return List.of(
                ItemRequestMP.builder()
                        .title("Floresta tropical")
                        .description("Drink feito com gelo, abacaxi, laranja e acerola")
                        .category("DRINK")
                        .unitMeasure("UNIT")
                        .unitPrice(BigDecimal.valueOf(20))
                        .quantity(1)
                        .build(),

                ItemRequestMP.builder()
                        .title("Jhony B.")
                        .description("Uma mistura refrescante de chá gelado de frutas vermelhas e suco de limão, adoçado com um toque de mel e servido com gelo.")
                        .category("DRINK")
                        .unitMeasure("UNIT")
                        .unitPrice(BigDecimal.valueOf(15.99))
                        .quantity(5)
                        .build()
        );
    }

    private static List<ItemRequestMP> getDessertsMock() {
        return List.of(
                ItemRequestMP.builder()
                        .title("Blues Berry Pie")
                        .description("Uma deliciosa torta de frutas silvestres, com destaque para mirtilos, amoras e morangos, coberta com uma crosta de massa folhada e servida com uma bola de sorvete de baunilha.")
                        .category("DESSERT")
                        .unitMeasure("UNIT")
                        .unitPrice(BigDecimal.valueOf(12.99))
                        .quantity(5)
                        .build(),

                ItemRequestMP.builder()
                        .title("Jazz Jello Shots")
                        .description("Pequenas porções de gelatina com sabor de frutas, infundidas com um toque de bourbon, para dar um sabor especial que faz jus à alma do blues.")
                        .category("DESSERT")
                        .unitMeasure("UNIT")
                        .unitPrice(BigDecimal.valueOf(8.99))
                        .quantity(6)
                        .build()
        );
    }
}
