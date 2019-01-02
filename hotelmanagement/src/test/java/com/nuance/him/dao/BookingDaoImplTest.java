/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.dao;

import javax.sql.DataSource;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.config.DBConnectionConfig;
import com.nuance.him.config.bookingconfig.BookDaoConfig;
import com.nuance.him.model.Booking;
import java.time.LocalDate;

@PropertySource(value = { "classpath:room-sql-queries.xml" })
@ContextConfiguration(classes = { DBConnectionConfig.class, BookDaoConfig.class })
@Transactional
public class BookingDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(BookingDaoImplTest.class);
    private static final String BOOK_ROOM = "BookingDaoImpl.bookRoom";
    private static  final String UPDATE_ROOM_STATUS_NA="BookingDaoImpl.updateRoomStatusNotAvail";
    private static final String UPDATE_BOOKING="BookingDaoImpl.updateBookingStatus";
    private static final String UPDATE_ROOM_STATUS_AVAIL = "BookingDaoImpl.updateStatusAvail";



    BookingDao bookingDao;
    Booking booking;
    private int key;

    @Value("${" + BOOK_ROOM + "}")
    private String getBookRoom;

    @Value("${"+UPDATE_ROOM_STATUS_NA+"}")
    private String getUpdateRoomStatusNa;

    @Value("${"+UPDATE_BOOKING+"}")
    private String getUpdateBooking;

    @Value("${" + UPDATE_ROOM_STATUS_AVAIL + "}")
    private String getStatusAvail;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @BeforeMethod
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        bookingDao = new BookingDaoImpl(jdbcTemplate, getBookRoom,getUpdateRoomStatusNa, getUpdateBooking,getStatusAvail);
        LocalDate date1 = LocalDate.of(2014,8,28);
        LocalDate date2 = LocalDate.of(2018, 12, 25);
        booking = new Booking(1, 1, date1, date2,1);
    }

    @Test
    public void testBookingRoom() throws Exception {
        logger.info("before booking");
        int key= bookingDao.bookingRoom(booking);
        logger.info("after booking");
        Assert.assertNotEquals(0,key);

    }

    @Test
    public void testUpdateBooking()throws Exception{
        log.info("test before updateBooking");
        int key=bookingDao.updateBooking(booking);
        Assert.assertEquals(0,key);
    }
}