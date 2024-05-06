package br.com.bluesburger.payment.adapters.in.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {

    @JsonProperty(value = "data")
    private DataWrapper data;

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataWrapper {
        @JsonProperty(value = "order_id")
        private String orderId;

        @JsonProperty(value = "order_status")
        private String orderStatus;

        @JsonProperty(value = "order_created_time")
        private String orderCreatedTime;

        @JsonProperty(value = "user_id")
        private String userId;

        @JsonProperty(value = "payment_method")
        private String paymentMethod;

        @JsonProperty(value = "dishes")
        private List<Dish> dishes;

        @JsonProperty(value = "drinks")
        private List<Drink> drinks;

        @JsonProperty(value = "desserts")
        private List<Dessert> desserts;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Dish {
        private Long id;
        private String name;
        private String description;
        private String category;
        private Integer quantity;
        private BigDecimal price;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Drink {
        private Long id;
        private String name;
        private String description;
        private String category;
        private Integer quantity;
        private BigDecimal price;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Dessert {
        private Long id;
        private String name;
        private String description;
        private String category;
        private Integer quantity;
        private BigDecimal price;
    }
}
