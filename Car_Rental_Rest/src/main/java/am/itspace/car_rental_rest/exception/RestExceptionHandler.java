package am.itspace.car_rental_rest.exception;

import am.itspace.car_rental_common.exception.DuplicateEmailException;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.exception.InvalidCredentialsException;
import am.itspace.car_rental_common.exception.InvalidOrderDateException;
import am.itspace.car_rental_rest.dto.RestErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception exception, WebRequest webRequest) {
        RestErrorDto restErrorDto = RestErrorDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, restErrorDto, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }
    @ExceptionHandler(value = DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException(Exception exception, WebRequest webRequest) {
        RestErrorDto restErrorDto = RestErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, restErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialException(Exception exception, WebRequest webRequest) {
        RestErrorDto restErrorDto = RestErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, restErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
    @ExceptionHandler(value = InvalidOrderDateException.class)
    public ResponseEntity<Object> handleInvalidOrderDateException(Exception exception, WebRequest webRequest) {
        RestErrorDto restErrorDto = RestErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, restErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(ex,errors,new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }
}
