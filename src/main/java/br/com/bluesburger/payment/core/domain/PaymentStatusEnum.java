package br.com.bluesburger.payment.core.domain;

public enum PaymentStatusEnum {
    PENDING(1),
    APPROVED(2),
    REFUSED(3),
    CANCELED(4);

    private final int code;

    PaymentStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentStatusEnum fromCode(int code) {
        for (PaymentStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid payment status code: " + code);
    }
}
