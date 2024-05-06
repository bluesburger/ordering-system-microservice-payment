package br.com.bluesburger.payment.adapters.out.mercadopago.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentRequestMP {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("items")
    private List<ItemRequestMP> items;

    @JsonProperty("notification_url")
    private String notificationUrl;

    @JsonProperty("total_amount")
    private final BigDecimal totalAmount;
}
