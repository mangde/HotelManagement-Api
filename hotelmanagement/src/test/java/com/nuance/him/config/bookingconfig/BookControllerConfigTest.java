/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config.bookingconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.controller.BookingController;
import com.nuance.him.service.RoomService;
import static org.testng.Assert.assertNotNull;

@Import(BookControllerConfig.class)
public class BookControllerConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BookingController bookingController;

    @Autowired
    private RoomService roomService;

    @Test
    public void testBookingController() {
    assertNotNull(bookingController);
        assertNotNull(roomService);

    }
}