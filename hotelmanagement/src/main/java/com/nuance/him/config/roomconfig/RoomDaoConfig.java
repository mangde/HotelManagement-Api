/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config.roomconfig;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.nuance.him.config.DBConnectionConfig;
import com.nuance.him.dao.RoomDao;
import com.nuance.him.dao.RoomDaoImpl;
import static org.springframework.util.Assert.notNull;

/**
 * Configuration for {RoomDao}
 */
@PropertySource(value = {"classpath:room-sql-queries.xml"})
@Import(DBConnectionConfig.class)
@Configuration
public class RoomDaoConfig {
    private static final Logger logger = LoggerFactory.getLogger(RoomDaoConfig.class);

    private static  final String GET_ALL_ROOM="RoomDaoImpl.getAllRooms";
    private static final String GET_AVAILABLE_ROOM="RoomDaoImpl.getAllAvailableRoom";
    private static final String GET_ROOM_BY_ID="RoomDaoImpl.getRoomById";
    private static final String GET_ROOM_STATUS="RoomDAoImpl.getRoomStatus";
    private static  final String INSERT_INTO_ROOM="RoomDaoImpl.insertIntoRoom";
    private static final String UPDATE_ROOM="RoomDaoImpl.updateRoom";
    private static final String DELETE_ROOM="RoomDaoImpl.deleteRoom";

    @Value("${"+GET_ALL_ROOM+"}")
    private String getGetAllRoom;

    @Value("${"+GET_AVAILABLE_ROOM+"}")
    private String getGetAvailableRoom;

    @Value("${"+GET_ROOM_BY_ID+"}")
    private String getGetRoomById;

    @Value("${"+GET_ROOM_STATUS+"}")
    private String getGetRoomStatus;

    @Value("${"+INSERT_INTO_ROOM+"}")
    private String getInsertIntoRoom;

    @Value("${"+UPDATE_ROOM+"}")
    private String getUpdateRoom;

    @Value("${"+DELETE_ROOM+"}")
    private String getDeleteRoom;

    @Autowired
    private JdbcTemplate jdbcTemplate;




    /**
     * validate the dependencies
     */
    @PostConstruct
   public void  postConstruct(){
        logger.info("validating bean postConstruct from RoomDaoConfig");
        notNull(jdbcTemplate,"required bean JdbcTemplate missing");
        notNull(getGetAllRoom,"required getAllRoom missing");
        notNull(getGetAvailableRoom,"requires getAvailable bean missing");
        notNull(getGetRoomById,"required getRoomById Bean Missing");
        notNull(getDeleteRoom,"required bean DeleteRoom Missing");
        notNull(getGetRoomStatus,"required bean RoomStatus Missing");
        notNull(getInsertIntoRoom,"Required bean InsertRoom Missing");
        notNull(getUpdateRoom,"required bean getUpdate missing ");
    }



    /**
     * Factory for SQLQueries
     * @return instance of RoomDaoImpl which return the queries
     */
    @Bean
    public  RoomDao roomDaoQuery(){
        logger.info("Inside RoomDaoQuery method of RoomDaoConfig class");
        return new  RoomDaoImpl(jdbcTemplate,getGetAllRoom,getGetAvailableRoom,getGetRoomById,getGetRoomStatus,getUpdateRoom,getDeleteRoom,getInsertIntoRoom);
    }
}
