package br.com.bluesburger.payment.adapters.out.exception;

public class SQSIntegrationException extends RuntimeException {

    public SQSIntegrationException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
