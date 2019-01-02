/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config.roomconfig;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.controller.RoomController;
import com.nuance.him.service.RoomService;
import static org.springframework.util.Assert.notNull;

/**
 * Configuration for {roomController}
 */
@Configuration
@Import(RoomServiceConfig.class)
public class RoomControllerConfig {

    @Autowired
     private RoomService roomService;
    /**
     * validate the dependencies.
     */
    @PostConstruct
    public void postConstruct(){
        notNull(roomService,"required bean roomService missing");
    }

    /**
     * factory for { roomController}
     * @return  instance of {@link RoomController}
     */
    @Bean
    public RoomController roomController(){
        return new RoomController(roomService);
    }
}
