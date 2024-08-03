package br.com.bluesburger.payment.adapters.out.sqs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidQueueDTO {
    private String orderId;
    
    private String paymentStatus;

    @Override
    public String toString() {
        return "{\"orderId\":\""+ orderId +"\", \"paymentStatus\": \"" + paymentStatus + "\"}";
    }
}
