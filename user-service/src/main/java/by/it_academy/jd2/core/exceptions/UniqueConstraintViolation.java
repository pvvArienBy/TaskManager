package by.it_academy.jd2.core.exceptions;

public class UniqueConstraintViolation extends RuntimeException {
    private final String messageField;

    public UniqueConstraintViolation(String message, Throwable cause, String messageField) {
        super(message, cause);
        this.messageField = messageField;
    }

    public UniqueConstraintViolation(String message, String messageField) {
        super(message);
        this.messageField = messageField;
    }

    public String getMessageField() {
        return messageField;
    }
}
