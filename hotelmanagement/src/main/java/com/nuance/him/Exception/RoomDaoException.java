/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.Exception;


import org.springframework.dao.DataAccessException;

public class RoomDaoException extends  Exception{

    private  String exMessage;

    /**
     *
     * @param message exception message
     */

    public RoomDaoException(final String message){
        this.exMessage=message;
    }

    /**
     *
     * @param exception_in_method  exception message
     * @param DAE Type of Exception object
     */
    public RoomDaoException(final String exception_in_method, DataAccessException DAE) {
        super(exception_in_method,DAE);
    }

    /**
     *
     * @return exception message
     */
    public String getExceptionMessage() {
        return exMessage;
    }

}
