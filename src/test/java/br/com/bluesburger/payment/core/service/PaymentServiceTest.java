//package br.com.bluesburger.payment.core.service;
//
//import br.com.bluesburger.payment.core.domain.Payment;
//import br.com.bluesburger.payment.core.service.strategy.PaymentContext;
//import br.com.bluesburger.payment.ports.DynamoDBPort;
//import br.com.bluesburger.payment.ports.PaymentMercadoPagoPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//public class PaymentServiceTest {
//
//    @Mock
//    private PaymentContext paymentContext;
//
//    @Mock
//    private PaymentMercadoPagoPort paymentMercadoPagoPort;
//
//    @Mock
//    private DynamoDBPort dynamoDBPort;
//
//    @InjectMocks
//    private PaymentService paymentService;
//
//    private Payment payment;
//
//    @BeforeEach
//    public void setUp() {
//        payment = new Payment();
//        payment.setDishes(List.of(new Payment.Item("Dish 1", 10, 2)));
//        payment.setDrinks(List.of(new Payment.Item("Drink 1", 5, 1)));
//        payment.setDesserts(List.of(new Payment.Item("Dessert 1", 8, 3)));
//    }
//
//    @Test
//    public void testCompletePaymentWithValues() {
//        paymentService.completePaymentWithValues(payment, itemsToPayment(payment));
//
//        assertEquals(BigDecimal.valueOf(20), payment.getTotalAmountDishes());
//        assertEquals(BigDecimal.valueOf(5), payment.getTotalAmountDrinks());
//        assertEquals(BigDecimal.valueOf(24), payment.getTotalAmountDesserts());
//        assertEquals(BigDecimal.valueOf(49), payment.getTotalAmount());
//    }
//
//    @Test
//    public void testCalculateTotalAmountPayment() {
//        BigDecimal totalAmountPayment = paymentService.calculateTotalAmountPayment(payment);
//
//        assertEquals(BigDecimal.valueOf(49), totalAmountPayment);
//    }
//
//    @Test
//    public void testCalculateTotalAmountItems() {
//        BigDecimal totalAmountItems = paymentService.calculateTotalAmountItems(payment.getDishes());
//
//        assertEquals(BigDecimal.valueOf(20), totalAmountItems);
//    }
//
//    private Map<String, List<Payment.Item>> itemsToPayment(Payment payment) {
//        Map<String, List<Payment.Item>> items = new HashMap<>();
//
//        items.put(ITEMS_PAYMENT_DISHES_KEY, payment.getDishes());
//        items.put(ITEMS_PAYMENT_DRINKS_KEY, payment.getDrinks());
//        items.put(ITEMS_PAYMENT_DESSERTS_KEY, payment.getDesserts());
//
//        return items;
//    }
//}
