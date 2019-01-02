
package com.nuance.him.dao;

import com.nuance.him.Exception.BookingDaoException;
import com.nuance.him.model.Booking;

public interface BookingDao {

    /**
     *
     * @param booking instance of {@link Booking}
     * @return integer value 0 or 1; 1 for success otherwise 0
     * @throws  BookingDaoException customException class
     */
    int bookingRoom(Booking booking) throws BookingDaoException;

    int updateBooking(Booking booking)throws BookingDaoException;
    }
