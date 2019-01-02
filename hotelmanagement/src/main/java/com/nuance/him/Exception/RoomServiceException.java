/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

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
