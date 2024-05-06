package br.com.bluesburger.payment.adapters.in.payment.dto;

import br.com.bluesburger.payment.core.domain.Payment;

import java.util.Objects;

public class PaymentMapper {

    private PaymentMapper() {
    }

    public static Payment mapperPaymentToPaymentRequest(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        var dataWrapper = paymentRequest.getData();

        payment.setOrderId(dataWrapper.getOrderId());
        payment.setOrderStatus(dataWrapper.getOrderStatus());
        payment.setOrderCreatedTime(dataWrapper.getOrderCreatedTime());
        payment.setUserId(dataWrapper.getUserId());
        payment.setPaymentMethod(dataWrapper.getPaymentMethod());

        if (Objects.nonNull(dataWrapper.getDishes())) {
            for (PaymentRequest.Dish dish : dataWrapper.getDishes()) {
                Payment.Item item = new Payment.Item();
                item.setId(dish.getId());
                item.setName(dish.getName());
                item.setDescription(dish.getDescription());
                item.setCategory(dish.getCategory());
                item.setQuantity(dish.getQuantity());
                item.setPrice(dish.getPrice());
                payment.addDish(item);
            }
        }

        if (Objects.nonNull(dataWrapper.getDrinks())) {
            for (PaymentRequest.Drink drink : dataWrapper.getDrinks()) {
                Payment.Item item = new Payment.Item();
                item.setName(drink.getName());
                item.setDescription(drink.getDescription());
                item.setCategory(drink.getCategory());
                item.setQuantity(drink.getQuantity());
                item.setPrice(drink.getPrice());
                payment.addDrink(item);
            }
        }

        if (Objects.nonNull(dataWrapper.getDesserts())) {
            for (PaymentRequest.Dessert dessert : dataWrapper.getDesserts()) {
                Payment.Item item = new Payment.Item();
                item.setName(dessert.getName());
                item.setDescription(dessert.getDescription());
                item.setCategory(dessert.getCategory());
                item.setQuantity(dessert.getQuantity());
                item.setPrice(dessert.getPrice());
                payment.addDessert(item);
            }
        }

        return payment;
    }

    public static PaymentResponse mapperPaymentToPaymentRequest(Payment payment) {
        return PaymentResponse.builder()
                .inStoreOrderId(payment.getQrCodeId())
                .qrData(payment.getQrCode())
                .build();
    }
}
