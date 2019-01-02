

package com.nuance.him.Exception;

import org.springframework.dao.DataAccessException;

public class BookingDaoException extends Exception {

    private  String exMessage;

    /**
     *
     * @param message Exception message
     */
    public BookingDaoException(String message){
    this.exMessage=message;
}
    /**
     *
     * @param message  exception custom message
     * @param DAE  instance of {@link DataAccessException}
     */
    public BookingDaoException(String message,DataAccessException DAE){
    super(message,DAE);
}

    /**
     *
     * @return exception message
     */
    public String getExceptionMessage() {
        return exMessage;
    }




}
