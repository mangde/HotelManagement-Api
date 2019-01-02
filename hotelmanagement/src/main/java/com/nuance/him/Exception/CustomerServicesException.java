/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

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
