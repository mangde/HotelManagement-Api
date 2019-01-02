/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuance.him.Exception.BookingDaoException;
import com.nuance.him.Exception.BookingServiceException;
import com.nuance.him.dao.BookingDao;
import com.nuance.him.model.Booking;

@Service
public class BookingServiceImpl implements  BookingService {
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private final BookingDao bookingDao;

    /**
     *
     * @param bookingDao instance of {@link com.nuance.him.dao.BookingDao}
     */
    public BookingServiceImpl(BookingDao bookingDao){
        this.bookingDao=bookingDao;
    }

    @Override
    public int bookingRoom(Booking booking) throws BookingServiceException {
        logger.info("inside BookingServiceImpl");
       try{
           return bookingDao.bookingRoom(booking);
       }catch (BookingDaoException e){
           throw  new BookingServiceException("Exception in BookingServiceImpl",e);
       }
    }

    @Override
    public int updateBooking(Booking booking) throws BookingServiceException {
        logger.info("inside BookingServiceImpl for updating ");
        try{
            return bookingDao.updateBooking(booking);
        }catch (BookingDaoException e){
            throw  new BookingServiceException("Exception in BookingServiceImpl from update",e);
        }}
}
