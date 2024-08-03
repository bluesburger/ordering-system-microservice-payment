package br.com.bluesburger.payment.adapters.out.mercadopago;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.bluesburger.payment.adapters.out.dynamo.repository.PaymentRepository;
import br.com.bluesburger.payment.adapters.out.mercadopago.response.PaymentResponseMP;
import br.com.bluesburger.payment.core.domain.Payment;
import br.com.bluesburger.payment.core.domain.Payment.Item;
import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!prod")
public class PaymentMercadoPagoFakeAdapter implements PaymentMercadoPagoPort {
	
	private static final String QR_CODE = "qwertyqwertyqwerty";

    @Override
    public PaymentResponseMP generatePaymentQRCode(Payment payment) {
        return new PaymentResponseMP("1", payment.getOrderId(), "2024-08-01T00:37:54", "PAID", "Paid", payment.getOrderId(), QR_CODE);
    }

    @Override
    public Payment getPayment(String paymentId) {
    	List<Item> dishes = List.of();
    	List<Item> drinks = List.of();
    	List<Item> desserts = List.of();
    	
    	var totalAmount = new BigDecimal(100);
    	var totalAmountDishes = new BigDecimal(10);
    	var totalAmountDrinks = new BigDecimal(1);
    	var totalAmountDesserts = new BigDecimal(1);
    	return new Payment(paymentId, paymentId, "qwerty", "PAID", "2024-08-01T00:37:54", "qwerty", "PAID", "method", "2024-08-01T00:40:15", null, 
    			QR_CODE, "qwerty", totalAmount, totalAmountDishes, totalAmountDrinks, totalAmountDesserts, dishes, drinks, desserts);
    }
}
