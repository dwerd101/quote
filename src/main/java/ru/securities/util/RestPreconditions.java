/*
package ru.securities.util;

import ru.securities.exception.QuoteNotUpdateException;
*/
/**
 * Simple static methods to be called at the start of your own methods to verify correct arguments and state. If the Precondition fails, an {@link org.springframework.http.HttpStatus} code is thrown
 *//*

public class RestPreconditions {
    private RestPreconditions() {
        throw new AssertionError();
    }

    // API

    */
/**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     * @throws QuoteNotUpdateException
     *             if expression is false, means value not found.
     *//*

    public static void checkFound(final boolean expression) {
        if (!expression) {
            throw new QuoteNotUpdateException();
        }
    }

    */
/**
     * Check if some value was found, otherwise throw exception.
     *
     * @param resource
     *            has value true if found, otherwise false
     * @throws QuoteNotUpdateException
     *             if expression is false, means value not found.
     *//*

    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new QuoteNotUpdateException();
        }

        return resource;
    }
}
*/
