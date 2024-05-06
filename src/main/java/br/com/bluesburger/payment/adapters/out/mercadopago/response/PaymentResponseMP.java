package br.com.bluesburger.payment.adapters.out.mercadopago.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentResponseMP {

    @JsonProperty("id")
    private String id;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("date_approved")
    private String dateApproved;

    @JsonProperty("status")
    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("in_store_order_id")
    private String orderId;

    @JsonProperty("qr_data")
    private String qrData;

}
