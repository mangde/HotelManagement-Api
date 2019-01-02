/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.controller;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.app.SpringHotelApp;
import com.nuance.him.model.Booking;
import com.nuance.him.service.BookingService;
import com.nuance.him.service.RoomService;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringHotelApp.class)
@PropertySource(value = { "classpath:application.properties" })
public class BookingControllerTest extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(BookingControllerTest.class);
    private static final String BOOK_ROOM_URL = "Booking.bookRoom";
    private static final String UPDATE_ROOM_URL = "Booking.updateRoom";

    private final String CUST_ID = "1";
    private final String ROOM_ID = "9";
    private final String USER_ID = "1";
    private final String IN_DATE = "26/12/2018";
    private final String OUT_DATE = "30/12/2018";
    private final String REQUEST_PARAM_IN_DATE = "checkIn";
    private final String REQUEST_PARAM_OUT_DATE = "checkOut";
    @Value("${" + BOOK_ROOM_URL + "}")
    private String getBookRoomUrl;
    @Value("${" + UPDATE_ROOM_URL + "}")
    private String updateRoomUrl;

    @Mock
    private BookingService bookingService;
    @Mock
    private RoomService roomService;
    private MockMvc mockMvc;
    private Booking booking;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new BookingController(bookingService, roomService)).build();
        LocalDate date1 = LocalDate.of(2018, 12, 26);
        LocalDate date2 = LocalDate.of(2018, 12, 30);
        booking = new Booking(Integer.valueOf(CUST_ID), Integer.parseInt(ROOM_ID), date1, date2, Integer.parseInt(USER_ID));
        booking.setReleaseBy(Integer.parseInt(CUST_ID));
    }

    @Test
    public void testRoomBook() throws Exception {
        when(bookingService.bookingRoom(booking)).thenReturn(anyInt());
        this.mockMvc.perform(MockMvcRequestBuilders.post(getBookRoomUrl).param("customerId", CUST_ID).param("roomId", ROOM_ID).param(REQUEST_PARAM_IN_DATE, IN_DATE).param(REQUEST_PARAM_OUT_DATE, OUT_DATE).param("bookedBy", USER_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(booking)).thenReturn(anyInt());
        this.mockMvc.perform(MockMvcRequestBuilders.put(updateRoomUrl)
            .param("roomId", ROOM_ID)
            .param("releasedBy", USER_ID))
            .andExpect(status().isOk());
    }
}