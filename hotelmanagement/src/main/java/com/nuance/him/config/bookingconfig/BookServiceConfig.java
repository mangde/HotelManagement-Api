/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config.bookingconfig;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.dao.BookingDao;
import com.nuance.him.service.BookingService;
import com.nuance.him.service.BookingServiceImpl;
import static org.springframework.util.Assert.notNull;

@Configuration
@Import(BookDaoConfig.class)
public class BookServiceConfig {

    @Autowired
    private BookingDao bookingDao;

    @PostConstruct
    public void postConstruct(){
        notNull(bookingDao,"Not Null bean bookingDao Exception");
    }
    /**
     *
     * @return instance of {@link com.nuance.him.dao.BookingDaoImpl}
     */
    @Bean
    public BookingService bookingService(){
        return new BookingServiceImpl(bookingDao);
    }



}
