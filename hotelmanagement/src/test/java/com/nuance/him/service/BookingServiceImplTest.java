/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.service;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.Exception.BookingDaoException;
import com.nuance.him.Exception.BookingServiceException;
import com.nuance.him.dao.BookingDao;
import com.nuance.him.model.Booking;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BookingServiceImplTest {

    @Mock
    private BookingDao bookingDao;

    private BookingService bookingService;

    private Booking booking;

    @BeforeMethod
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        bookingService=new BookingServiceImpl(bookingDao);
        LocalDate date1 = LocalDate.of(2014,8,28);
        LocalDate date2 = LocalDate.of(2018, 12, 25);
        booking = new Booking(1, 1, date1, date2,1);

    }

    @Test
    public void testBookingRoom() throws Exception {
        when(bookingDao.bookingRoom(booking)).thenReturn(anyInt());
        int key=bookingService.bookingRoom(booking);
        assertEquals(0,key);
        Mockito.verify(bookingDao).bookingRoom(booking);

    }
    @Test(expectedExceptions = BookingServiceException.class)
    public void testBookingException()throws Exception{
        doThrow(BookingDaoException.class).when(bookingDao).bookingRoom(booking);
        try{
            bookingService.bookingRoom(booking);
        }catch (BookingServiceException e){
            assertEquals(BookingDaoException.class, e.getCause().getClass());
            Mockito.verify(bookingDao).bookingRoom(booking);
            throw e;
        }
    }
}