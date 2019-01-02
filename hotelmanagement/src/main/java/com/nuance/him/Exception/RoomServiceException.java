

package com.nuance.him.Exception;

public class RoomServiceException extends Exception {


    /**
     *
     * @param ex string message passed by exception class
     * @param exceptionMessage instance of {@link RoomDaoException}
     */
    public RoomServiceException(String ex, RoomDaoException exceptionMessage) {
        super(ex,exceptionMessage);
    }
}
