package by.it_academy.jd2.service.exceptions;



public class UnknownDepFormatException extends RuntimeException{
    public UnknownDepFormatException() {
        super();
    }

    public UnknownDepFormatException(String message) {
        super(message);
    }

    public UnknownDepFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDepFormatException(Throwable cause) {
        super(cause);
    }

    protected UnknownDepFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
