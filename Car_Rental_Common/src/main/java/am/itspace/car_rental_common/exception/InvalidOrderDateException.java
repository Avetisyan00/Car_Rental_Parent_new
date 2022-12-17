package am.itspace.car_rental_common.exception;

/**
 * I've created this exception for order date check.
 * It's possible, that client will set start date of order,
 * which will be after end date.
 */
public class InvalidOrderDateException extends Exception {
    public InvalidOrderDateException() {
    }

    public InvalidOrderDateException(String message) {
        super(message);
    }

    public InvalidOrderDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOrderDateException(Throwable cause) {
        super(cause);
    }

    public InvalidOrderDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
