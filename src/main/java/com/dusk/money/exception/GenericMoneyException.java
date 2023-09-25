package com.dusk.money.exception;

import org.springframework.http.HttpStatus;

public class GenericMoneyException extends MoneyException {

    public GenericMoneyException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public GenericMoneyException(String message, HttpStatus status) {
        super(message, status);
    }
}
