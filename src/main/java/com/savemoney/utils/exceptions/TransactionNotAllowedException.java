package com.savemoney.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class TransactionNotAllowedException extends RuntimeException {

    public TransactionNotAllowedException(String message) {
        super(message);
    }
}
