package by.it_academy.jd2.service.exceptions;


//todo actuality?
public class RemoveEntityException extends RuntimeException{

    public RemoveEntityException() {
        super();
    }

    public RemoveEntityException(String message) {
        super(message);
    }

    public RemoveEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoveEntityException(Throwable cause) {
        super(cause);
    }

    protected RemoveEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
