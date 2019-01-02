

package com.nuance.him.Exception;

import org.springframework.dao.DataAccessException;

/**
 * Custom exception for the query failure.
 */
public class CustomerDaoException extends Exception {
    private static final String MESSAGE="";

    /**
     * Constructor.
     *
     * @param MESSAGE message for the exception
     * @param e exception
     */
    public CustomerDaoException(String MESSAGE,DataAccessException e) {
        super(MESSAGE,e);
    }

    /**
     * Gets the exception messages.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
