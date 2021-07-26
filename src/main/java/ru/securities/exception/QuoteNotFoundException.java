package ru.securities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuoteNotFoundException extends RuntimeException{
    public QuoteNotFoundException() {
    }

    public QuoteNotFoundException(String message) {
        super(message);
    }

    public QuoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuoteNotFoundException(Throwable cause) {
        super(cause);
    }

    public QuoteNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}