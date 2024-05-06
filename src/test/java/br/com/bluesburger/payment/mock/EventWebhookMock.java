package br.com.bluesburger.payment.mock;

import br.com.bluesburger.payment.adapters.in.webhook.dto.DataEvent;
import br.com.bluesburger.payment.adapters.in.webhook.dto.Event;

public class EventWebhookMock {
    private EventWebhookMock() {
    }

    public static Event getEventMock() {
        return Event.builder()
                .action("payment.created")
                .apiVersion("v1")
                .data(DataEvent.builder().id("paymentId").build())
                .dateCreated("2024-05-06T14:05:37Z")
                .id(113114114310L)
                .type("payment")
                .userId("1613065352")
                .build();
    }

    public static Event getEventNoDataMock() {
        return Event.builder()
                .action("payment.created")
                .apiVersion("v1")
                .dateCreated("2024-05-06T14:05:37Z")
                .id(113114114310L)
                .type("payment")
                .userId("1613065352")
                .build();
    }
}
