package com.github.brendandw.atm.exceptions;

public class TechnicalException extends Exception {

    public TechnicalException() {
        super();
    }

    public TechnicalException(String message) {
        super(message);

    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);

    }
}