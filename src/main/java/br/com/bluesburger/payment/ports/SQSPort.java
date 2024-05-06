package br.com.bluesburger.payment.ports;

public interface SQSPort {

    void sendMessage(Object message);
}
