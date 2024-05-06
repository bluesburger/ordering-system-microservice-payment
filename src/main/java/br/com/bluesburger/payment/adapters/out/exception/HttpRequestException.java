package br.com.bluesburger.payment.adapters.out.exception;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
