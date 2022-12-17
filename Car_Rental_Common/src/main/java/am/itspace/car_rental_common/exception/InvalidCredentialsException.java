package am.itspace.car_rental_common.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, HttpStatus status) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }

    public InvalidCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
