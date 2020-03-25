package acmebank.accountmanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity handle(CustomException ex) {
        return returnResponse(INTERNAL_SERVER_ERROR.value(),
                RestError.builder().errorCode(ex.getErrorCode()).errorMessage(ex.getMessage()).build());
    }

    private ResponseEntity<RestError> returnResponse(int statusCode, RestError body) {
        return status(statusCode).body(body);
    }
}
