package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest;
import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest.DataWrapper;
import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest.Dessert;
import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest.Dish;
import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest.Drink;

import java.math.BigDecimal;
import java.util.List;

public class PaymentRequestMock {

    public static PaymentRequest getPaymentRequestMock() {
        return PaymentRequest.builder()
                .data(DataWrapper.builder()
                        .orderId("4f72acca-3a80-4f2a-b2bb-f114da0a4fa9")
                        .orderStatus("Pedido Recebido")
                        .orderCreatedTime("2024-05-05T00:00:00")
                        .userId("43436b7e-cd00-48a2-b380-ef253c5502d4")
                        .paymentMethod("PIX")
                        .dishes(getDishesMock())
                        .drinks(getDrinksMock())
                        .desserts(getDessertsMock())
                        .build())
                .build();

    }

    private static List<Dish> getDishesMock() {
        return List.of(
                Dish.builder()
                        .id(1L)
                        .name("Gazpacho")
                        .description("sopa fria à base de hortaliças, com destaque para o tomate, o pepino e o pimentão")
                        .category("DISH")
                        .price(BigDecimal.TEN)
                        .quantity(2)
                        .build(),

                Dish.builder()
                        .id(1L)
                        .name("Falafel")
                        .description("consiste em bolinhos fritos de grão-de-bico ou fava moída, normalmente misturados com condimentos como alho, cebolinha, salsa, coentro e cominho")
                        .category("DISH")
                        .price(BigDecimal.TEN)
                        .quantity(2)
                        .build()
        );
    }

    private static List<Drink> getDrinksMock() {
        return List.of(
                Drink.builder()
                        .id(1L)
                        .name("Floresta tropical")
                        .description("Drink feito com gelo, abacaxi, laranja e acerola")
                        .category("DRINK")
                        .price(BigDecimal.valueOf(20))
                        .quantity(1)
                        .build(),

                Drink.builder()
                        .id(1L)
                        .name("Jhony B.")
                        .description("Uma mistura refrescante de chá gelado de frutas vermelhas e suco de limão, adoçado com um toque de mel e servido com gelo.")
                        .category("DRINK")
                        .price(BigDecimal.valueOf(15.99))
                        .quantity(5)
                        .build()
        );
    }

    private static List<Dessert> getDessertsMock() {
        return List.of(
                Dessert.builder()
                        .id(1L)
                        .name("Blues Berry Pie")
                        .description("Uma deliciosa torta de frutas silvestres, com destaque para mirtilos, amoras e morangos, coberta com uma crosta de massa folhada e servida com uma bola de sorvete de baunilha.")
                        .category("DESSERT")
                        .price(BigDecimal.valueOf(12.99))
                        .quantity(5)
                        .build(),

                Dessert.builder()
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
