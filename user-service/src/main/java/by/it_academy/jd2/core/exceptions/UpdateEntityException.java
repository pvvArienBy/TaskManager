package by.it_academy.jd2.core.exceptions;

public class UpdateEntityException extends RuntimeException{

    public UpdateEntityException() {
        super();
    }

    public UpdateEntityException(String message) {
        super(message);
    }

    public UpdateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateEntityException(Throwable cause) {
        super(cause);
    }

    protected UpdateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
