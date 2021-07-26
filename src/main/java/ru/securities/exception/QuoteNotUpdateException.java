package ru.securities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuoteNotUpdateException extends RuntimeException{
    public QuoteNotUpdateException() {
    }

    public QuoteNotUpdateException(String message) {
        super(message);
    }

    public QuoteNotUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuoteNotUpdateException(Throwable cause) {
        super(cause);
    }

    public QuoteNotUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
