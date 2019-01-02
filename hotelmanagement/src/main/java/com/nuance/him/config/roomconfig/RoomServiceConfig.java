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
import com.nuance.him.dao.RoomDao;
import com.nuance.him.service.RoomService;
import com.nuance.him.service.RoomServiceImpl;
import static org.springframework.util.Assert.notNull;

/**
 * Configure for {roomDao}
 */
@Configuration
@Import(RoomDaoConfig.class)
public class RoomServiceConfig {

    @Autowired
    private RoomDao roomDao;

    /**
     * validate dependencies
     */
    @PostConstruct
    public  void postConstruct(){
        notNull(roomDao,"required bean roomDao missing");
    }

    /**
     * Factory for roomDao
     * @return instance og {@link RoomServiceImpl}
     */
    @Bean
    public RoomService roomService(){

        return new RoomServiceImpl(roomDao);
    }



}
