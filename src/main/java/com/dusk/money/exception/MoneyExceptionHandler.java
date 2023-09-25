package com.dusk.money.exception;

import com.dusk.money.dto.response.MoneyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.util.Objects.nonNull;

@ControllerAdvice
@SuppressWarnings("unused")
public class MoneyExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(MoneyExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MoneyResponse<Void>> methodArgumentNotValidException(Errors errors) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        var fieldErrors = errors.getFieldError();
        var message = nonNull(fieldErrors) ? fieldErrors.getDefaultMessage() : null;
        logger.error(message);

        var moneyResponse = new MoneyResponse<Void>(status.value(),
                message,
                null);
        return ResponseEntity.status(status).body(moneyResponse);
    }

    @ExceptionHandler(MoneyException.class)
    public ResponseEntity<MoneyResponse<Void>> moneyException(MoneyException moneyException) {
        logger.error(moneyException.getMessage());
        var moneyResponse = new MoneyResponse<Void>(moneyException.getHttpStatus().value(),
                moneyException.getHttpStatus().getReasonPhrase(),
                null);
        return ResponseEntity.status(moneyException.httpStatus).body(moneyResponse);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void unknownException(Exception ex) {
        logger.error(ex.getMessage());
    }
}
