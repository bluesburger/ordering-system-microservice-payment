package br.com.bluesburger.payment.adapters.in.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    @JsonProperty(value = "in_store_order_id")
    private String inStoreOrderId;

    @JsonProperty(value = "qr_data")
    private String qrData;
}
