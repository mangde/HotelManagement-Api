
package com.nuance.him.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.nuance.him.Exception.BookingDaoException;
import com.nuance.him.model.Booking;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BookingDaoImpl implements BookingDao {

    private static final Logger logger = LoggerFactory.getLogger(BookingDaoImpl.class);
    private static String INSERT_INTO_BOOKING;
    private static String UPDATE_ROOM_STATUS_AVAIL;
    private static String UPDATE_ROOM_STATUS_NA;
    private static String UPDATE_BOOKING;

    private final JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate instance of {@link JdbcTemplate}
     * @param insertQuery String SqlQuery for accessing data
     * @param getUpdateBooking
     */
    public BookingDaoImpl(JdbcTemplate jdbcTemplate, String insertQuery, String updateRoom, String getUpdateBooking, String updateRoomStatus) {
        logger.info("inside Query constructor of BookingImpl");
        this.jdbcTemplate = jdbcTemplate;
        INSERT_INTO_BOOKING = insertQuery;
        UPDATE_ROOM_STATUS_AVAIL = updateRoomStatus;
        UPDATE_ROOM_STATUS_NA = updateRoom;
        UPDATE_BOOKING = getUpdateBooking;
    }

    /**
     * @param booking instance of {@link Booking}
     * @return 0 or 1; 1 for success 0 for fail
     * @throws DataAccessException to a CustomException class {@link BookingDaoException}
     */
    @Override
    public int bookingRoom(Booking booking) throws BookingDaoException {
        logger.info("executing BookingRoom Method from dao");
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            int res = jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_INTO_BOOKING, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, booking.getCustomerId());
                ps.setInt(2, booking.getRoomId());
                ps.setDate(3, Date.valueOf(booking.getCheckIn()));
                ps.setDate(4, Date.valueOf(booking.getCheckOut()));
                ps.setInt(5, booking.getBookedBy());
                return ps;
            }, holder);
            logger.info("Complete BookingRoom Method set Room Not Available");
            jdbcTemplate.update(UPDATE_ROOM_STATUS_NA, booking.getRoomId());
            return holder.getKey().intValue();
        }
        catch (DataAccessException e) {
            throw new BookingDaoException("Exception in BookingDaoImpl", e);
        }
    }

    @Override
    public int updateBooking(Booking booking) throws BookingDaoException {
        logger.info("executing UpdateBooking Method from dao");

        try {
            int key = jdbcTemplate.update(UPDATE_BOOKING, booking.getReleaseBy(), booking.getRoomId());

            if (key == 1) {
                logger.info("change room status to Available");
                jdbcTemplate.update(UPDATE_ROOM_STATUS_AVAIL, booking.getRoomId());
            }
            return key;
        }
        catch (DataAccessException ex) {
            logger.info("exception in  UpdateBooking Method from dao{}",ex.getMessage());

            throw new BookingDaoException("Exception in updateBooking Method", ex);
        }
    }
}
