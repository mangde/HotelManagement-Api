/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.config.bookingconfig;

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
import com.nuance.him.dao.BookingDao;
import com.nuance.him.dao.BookingDaoImpl;
import static org.springframework.util.Assert.notNull;

@PropertySource(value = { "classpath:room-sql-queries.xml" })
@Import(DBConnectionConfig.class)
@Configuration
public class BookDaoConfig {

    private static final Logger logger = LoggerFactory.getLogger(BookDaoConfig.class);
    private static final String INSERT_INTO_BOOKING = "BookingDaoImpl.bookRoom";
    private static final String UPDATE_ROOM_STATUS_NA = "BookingDaoImpl.updateRoomStatusNotAvail";
    private static final String UPDATE_ROOM_STATUS_AVAIL = "BookingDaoImpl.updateStatusAvail";
    private static final String UPDATE_BOOKING = "BookingDaoImpl.updateBookingStatus";
    @Value("${" + INSERT_INTO_BOOKING + "}")
    private String insertIntoBooking;
    @Value("${" + UPDATE_ROOM_STATUS_NA + "}")
    private String getStatusNotAvail;
    @Value("${" + UPDATE_ROOM_STATUS_AVAIL + "}")
    private String getStatusAvail;
    @Value("${" + UPDATE_BOOKING + "}")
    private String getUpdateBooking;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        logger.info("validating bean postConstruct from bookingDaoConfig");
        notNull(jdbcTemplate, "required bean JdbcTemplate missing form bookingConfig");
        notNull(insertIntoBooking, "required bean INSERT_INTO_BOOKING missing");
        notNull(getStatusNotAvail, "required bean UPDATE_ROOM_STATUS_NA missing");
        notNull(getStatusAvail, "required bean UPDATE_ROOM_STATUS_AVAIL missing");
        notNull(getUpdateBooking, "required bean UpdateBooking missing");
    }

    /**
     * Factory for SQL Queries
     *
     * @return instance of {@link BookingDaoImpl}
     */
    @Bean
    public BookingDao bookingDaoQuery() {
        return new BookingDaoImpl(jdbcTemplate, insertIntoBooking, getStatusNotAvail, getUpdateBooking, getStatusAvail);
    }
}
