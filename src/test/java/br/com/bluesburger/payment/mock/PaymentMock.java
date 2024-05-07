package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.PaymentStatusEnum;

import java.math.BigDecimal;
import java.util.List;

public class PaymentMock {
    private PaymentMock() {
    }

    public static Payment getPaymentMock() {
        return Payment.builder()
                .id("123")
                .orderId("456")
                .referenceId("456")
                .orderCreatedTime("2024-05-05")
                .totalAmount(BigDecimal.ZERO)
                .paymentStatus(PaymentStatusEnum.PENDING.name())
                .dishes(getDishesMock())
                .drinks(getDrinksMock())
                .desserts(getDessertsMock())
                .build();
    }

    private static List<Payment.Item> getDishesMock() {
        return List.of(
                Payment.Item.builder()
                        .id(1L)
                        .name("Gazpacho")
                        .description("sopa fria à base de hortaliças, com destaque para o tomate, o pepino e o pimentão")
                        .category("DISH")
                        .price(BigDecimal.TEN)
                        .quantity(2)
                        .build(),

                Payment.Item.builder()
                        .id(1L)
                        .name("Falafel")
                        .description("consiste em bolinhos fritos de grão-de-bico ou fava moída, normalmente misturados com condimentos como alho, cebolinha, salsa, coentro e cominho")
                        .category("DISH")
                        .price(BigDecimal.TEN)
                        .quantity(2)
                        .build()
        );
    }

    private static List<Payment.Item> getDrinksMock() {
        return List.of(
                Payment.Item.builder()
                        .id(1L)
                        .name("Floresta tropical")
                        .description("Drink feito com gelo, abacaxi, laranja e acerola")
                        .category("DRINK")
                        .price(BigDecimal.valueOf(20))
                        .quantity(1)
                        .build(),

                Payment.Item.builder()
                        .id(1L)
                        .name("Jhony B.")
                        .description("Uma mistura refrescante de chá gelado de frutas vermelhas e suco de limão, adoçado com um toque de mel e servido com gelo.")
                        .category("DRINK")
                        .price(BigDecimal.valueOf(15.99))
                        .quantity(5)
                        .build()
        );
    }

    private static List<Payment.Item> getDessertsMock() {
        return List.of(
                Payment.Item.builder()
                        .id(1L)
                        .name("Blues Berry Pie")
                        .description("Uma deliciosa torta de frutas silvestres, com destaque para mirtilos, amoras e morangos, coberta com uma crosta de massa folhada e servida com uma bola de sorvete de baunilha.")
                        .category("DESSERT")
                        .price(BigDecimal.valueOf(12.99))
                        .quantity(5)
                        .build(),

                Payment.Item.builder()
                        .id(3L)
                        .name("Jazz Jello Shots")
                        .description("Pequenas porções de gelatina com sabor de frutas, infundidas com um toque de bourbon, para dar um sabor especial que faz jus à alma do blues.")
                        .category("DESSERT")
                        .price(BigDecimal.valueOf(8.99))
                        .quantity(6)
                        .build()
        );
    }
}
