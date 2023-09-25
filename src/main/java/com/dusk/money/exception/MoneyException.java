package com.dusk.money.exception;

import org.springframework.http.HttpStatus;

public abstract class MoneyException extends RuntimeException {
    protected final HttpStatus httpStatus;

    protected MoneyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
}
