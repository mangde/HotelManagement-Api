
package com.nuance.him.service;

import org.springframework.stereotype.Service;
import com.nuance.him.Exception.BookingServiceException;
import com.nuance.him.model.Booking;

@Service
public interface BookingService {

        /**
         *
         * @return 0 or 1; 1 for successful booking otherwise 0
         * @throws BookingServiceException of {@link BookingServiceException} class
         */
        int bookingRoom(Booking booking) throws BookingServiceException;
        int updateBooking(Booking booking)throws BookingServiceException;

}
