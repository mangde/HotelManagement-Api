/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.nuance.him.Exception.RoomDaoException;
import com.nuance.him.model.Room;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class RoomDaoImpl implements RoomDao {

    private static final Logger logger = LoggerFactory.getLogger(RoomDaoImpl.class);
    private static String GET_ALL_ROOM;
    private static String GET_AVAILABLE_ROOM;
    private static String GET_ROOM_BY_ID;
    private static String GET_ROOM_STATUS;
    private static String INSERT_INTO_ROOM;
    private static String UPDATE_ROOM;
    private static String DELETE_ROOM;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor of a RoomDaoImpl class for access values of queries and assign to static Variable
     * This constructor called from Configuration class
     *
     * @param jdbcTemplate instance of {@link JdbcTemplate}
     * @param getGetAllRoom holds query for getAllRooms from database
     * @param getAvailable holds query for getAllAvailable Rooms which having Status is available
     * @param getRoomsById Query for GetRoom Details From Its Id
     * @param getStatus Query For GetRoomStatus By Id
     * @param insertRoom Query for insert room details
     * @param updateQuery Query for Update Room details
     * @param deleteQuery Query for delete room By id
     */
    public RoomDaoImpl(JdbcTemplate jdbcTemplate, String getGetAllRoom, String getAvailable, String getRoomsById, String getStatus, String updateQuery, String deleteQuery, String insertRoom) {
        this.jdbcTemplate = jdbcTemplate;
        GET_ALL_ROOM = getGetAllRoom;
        GET_AVAILABLE_ROOM = getAvailable;
        GET_ROOM_BY_ID = getRoomsById;
        GET_ROOM_STATUS = getStatus;
        UPDATE_ROOM = updateQuery;
        DELETE_ROOM = deleteQuery;
        INSERT_INTO_ROOM = insertRoom;
    }



    @Override
    public List<Room> getAllRooms() throws RoomDaoException {
        logger.info("executing getAllRooms Method from dao");
        try {
            return jdbcTemplate.query(GET_ALL_ROOM, (resultSet, i) -> toRoom(resultSet));
        }
        catch (DataAccessException ex) {
            throw new RoomDaoException("Exception in getAllRoom Method", ex);
        }
    }

    @Override
    public List<Room> getAllAvailableRooms() throws RoomDaoException {
        logger.info("executing getAllAvailableRooms Method");
        try {
            return jdbcTemplate.query(GET_AVAILABLE_ROOM, new Object[] { "available" }, (resultSet, i) -> toRoom(resultSet));
        }
        catch (DataAccessException ex) {
            throw new RoomDaoException("Exception in getAvailable Method", ex);
        }
    }

    @Override
    public Room findRoomById(final long id) throws RoomDaoException {
        logger.info("executing findRoomById Method from RoomDaoImpl");
        Room room;
        try {
            room = jdbcTemplate.queryForObject(GET_ROOM_BY_ID, new Object[] { id }, (resultSet, i) -> toRoom(resultSet));
        }
        catch (DataAccessException ex) {
            logger.error("Room with id {} not found in Exception Dao.", id);
            throw new RoomDaoException("Exception in getRoomById Method form RoomDaoImpl", ex);
        }
        return room;
    }

    @Override
    public int addRoom(Room room) throws RoomDaoException {
        logger.info("executing addRoom Method from RoomDao");
        // return jdbcTemplate.update(INSERT_INTO_ROOM, room.getType(), room.getRate(), room.getStatus());
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            int res=jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_INTO_ROOM, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,room.getType());
                ps.setInt(2, room.getRate());
                ps.setString(3, room.getStatus());
                return ps;
            }, holder);
            return holder.getKey().intValue();
        }
        catch (DataAccessException ex) {
            throw new RoomDaoException("Exception in DAO addRoom Method", ex);
        }
    }

    @Override
    public int deleteRoom(final int id) throws RoomDaoException {
        logger.info("executing deleteRoom Method");
        try {
            return jdbcTemplate.update(DELETE_ROOM, id);
        }
        catch (DataAccessException ex) {
            throw new RoomDaoException("Exception in DAO deleteRoom Method", ex);
        }
    }

    @Override
    public int updateRoom(Room room) throws RoomDaoException {
        logger.info("executing update Method");
        try {
            return jdbcTemplate.update(UPDATE_ROOM, room.getType(), room.getRate(), room.getStatus(), room.getRoomId());
        }
        catch (DataAccessException ex) {
            throw new RoomDaoException("Exception in updateRoom Method", ex);
        }
    }

    @Override
    public String getStatus(final int id) throws RoomDaoException {
        logger.info("executing DAO getStatus Method");
        Room room;
        try {

            room = jdbcTemplate.queryForObject(GET_ROOM_STATUS, new Object[] { id }, (resultSet, i) -> toStatus(resultSet));
            return  room.getStatus();

        }
        catch (DataAccessException ex) {
            throw new RoomDaoException("Exception catch in DAO getStatus Method", ex);
        }

    }

    /**
     * @param rs resultSet Object for getting values form database
     * @return room object with status values
     * @throws SQLException invalidColumnName
     */
    public Room toStatus(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("roomId"));
        room.setStatus(rs.getString("status"));
        return room;
    }

    /**
     * @param resultSet object for accessing data from database
     * @return room Object with roomData
     * @throws SQLException if invalid column name
     */
    public Room toRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setRoomId(resultSet.getInt("roomId"));
        room.setType(resultSet.getString("type"));
        room.setRate(resultSet.getInt("rate"));
        room.setStatus(resultSet.getString("status"));
        return room;
    }
}
