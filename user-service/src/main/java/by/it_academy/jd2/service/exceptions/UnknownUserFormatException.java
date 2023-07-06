package by.it_academy.jd2.service.exceptions;



public class UnknownUserFormatException extends RuntimeException{

    public UnknownUserFormatException() {
        super();
    }

    public UnknownUserFormatException(String message) {
        super(message);
    }

    public UnknownUserFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownUserFormatException(Throwable cause) {
        super(cause);
    }

    protected UnknownUserFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
