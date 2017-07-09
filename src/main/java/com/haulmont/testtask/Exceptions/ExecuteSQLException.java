package com.haulmont.testtask.Exceptions;

/**
 * Created by andrei on 09.07.17.
 */
public class ExecuteSQLException extends Exception {
    public ExecuteSQLException() {
    }

    public ExecuteSQLException(String message) {
        super(message);
    }
}
