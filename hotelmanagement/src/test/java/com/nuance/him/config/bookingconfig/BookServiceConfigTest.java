/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config.bookingconfig;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.service.BookingService;
import static org.testng.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BookServiceConfig.class})
public class BookServiceConfigTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private BookingService bookingService;

    @Test
    public void testBookingService()  {
        assertNotNull(bookingService);
    }
}