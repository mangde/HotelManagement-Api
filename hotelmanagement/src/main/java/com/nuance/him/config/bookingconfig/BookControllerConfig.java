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
import com.nuance.him.config.roomconfig.RoomServiceConfig;
import com.nuance.him.controller.BookingController;
import com.nuance.him.service.BookingService;
import com.nuance.him.service.RoomService;
import static org.springframework.util.Assert.notNull;

@Configuration
@Import({BookServiceConfig.class,RoomServiceConfig.class})
public class BookControllerConfig {

    @Autowired
     private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    /**
     * validate dependencies
     */
    @PostConstruct
    public void postConstruct(){
        notNull(bookingService,"required bean bookingService missing");
        notNull(roomService,"required bean roomService missing");
    }

    /**
     *
     * @return new instance of {@link BookingController}
     */
    @Bean
    public BookingController bookingController(){
        return new BookingController(bookingService,roomService);
    }
}
