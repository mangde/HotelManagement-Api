/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.Exception;

public class BookingServiceException extends Exception {
    private  String exMessage;

    /**
     *
     * @param message exception message
     */
    public BookingServiceException(final String message){
        this.exMessage=message;
    }

    /**
     *  @param ex string message passed by exception class
     * @param exceptionMessage instance of {@link RoomDaoException}
     */
    public BookingServiceException(String ex, BookingDaoException exceptionMessage) {
        super(ex,exceptionMessage);
    }

    /**
     *
     * @return exception message
     */
    public String getExMessage() {
        return exMessage;
    }
}
