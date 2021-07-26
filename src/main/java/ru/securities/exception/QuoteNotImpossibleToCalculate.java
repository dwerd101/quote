package ru.securities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuoteNotImpossibleToCalculate extends RuntimeException{
    public QuoteNotImpossibleToCalculate() {
    }

    public QuoteNotImpossibleToCalculate(String message) {
        super(message);
    }

    public QuoteNotImpossibleToCalculate(String message, Throwable cause) {
        super(message, cause);
    }

    public QuoteNotImpossibleToCalculate(Throwable cause) {
        super(cause);
    }

    public QuoteNotImpossibleToCalculate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
