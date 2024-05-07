package br.com.bluesburger.payment.core.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String id;
    private String referenceId;
    private String orderId;
    private String orderStatus;
    private String orderCreatedTime;
    private String userId;
    private String paymentStatus;
    private String paymentMethod;
    private String createdDate;
    private String updatedDate;
    private String qrCode;
    private String qrCodeId;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountDishes;
    private BigDecimal totalAmountDrinks;
    private BigDecimal totalAmountDesserts;
    private List<Item> dishes = new ArrayList<>();
    private List<Item> drinks = new ArrayList<>();
    private List<Item> desserts = new ArrayList<>();


    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private String name;
        private String description;
        private String category;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal totalAmount;
    }

    public void addDish(Item dish) {
        dishes.add(dish);
    }

    public void addDrink(Item drink) {
        drinks.add(drink);
    }

    public void addDessert(Item dessert) {
        desserts.add(dessert);
    }

    public void setTotalAmountDishes(BigDecimal totalAmount) {
        this.totalAmountDishes = totalAmount;
    }

    public void setTotalAmountDrinks(BigDecimal totalAmount) {
        this.totalAmountDrinks = totalAmount;
    }

    public void setTotalAmountDesserts(BigDecimal totalAmount) {
        this.totalAmountDesserts = totalAmount;
    }

    public void completePaymentWithQrCode(String qrCode, String qrCodeId) {
        this.qrCode = qrCode;
        this.qrCodeId = qrCodeId;
    }
    public void updateStatus(String status) {
        this.paymentStatus = status;
    }
}
