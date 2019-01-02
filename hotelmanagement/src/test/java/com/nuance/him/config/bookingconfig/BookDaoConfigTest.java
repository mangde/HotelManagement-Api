/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.config.bookingconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.config.DBConnectionConfig;
import com.nuance.him.dao.BookingDao;
import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = { BookDaoConfig.class, DBConnectionConfig.class })
@TestPropertySource(value = { "classpath:application.properties" })
public class BookDaoConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BookingDao bookingDao;

    @Test
    public void testBookingDaoQuery()  {
        assertNotNull(bookingDao);
    }
}