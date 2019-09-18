package com.mexpedition.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExpeditionExistanteException extends RuntimeException {

    public ExpeditionExistanteException(String message) {
        super(message);
    }
}

