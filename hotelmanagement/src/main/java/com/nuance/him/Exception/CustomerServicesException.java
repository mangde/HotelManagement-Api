
package com.nuance.him.Exception;

/**
 * Customer exception class for customer services.
 */
public class CustomerServicesException extends Exception {
    private static final String MESSAGE="";

    /**
     * Constructor.
     *
     * @param MESSAGE message for the exception
     * @param e exception
     */
    public CustomerServicesException(String MESSAGE,CustomerDaoException e) {
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
