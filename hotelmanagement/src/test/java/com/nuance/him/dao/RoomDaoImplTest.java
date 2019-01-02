/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.dao;

import javax.sql.DataSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.Exception.RoomDaoException;
import com.nuance.him.config.DBConnectionConfig;
import com.nuance.him.config.roomconfig.RoomDaoConfig;
import com.nuance.him.model.Room;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = { DBConnectionConfig.class , RoomDaoConfig.class})
@PropertySource(value = {"classpath:room-sql-queries.xml"})
@Transactional
class RoomDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(RoomDaoImplTest.class);

    private static  final String GET_ALL_ROOM="RoomDaoImpl.getAllRooms";
    private static final String GET_AVAILABLE_ROOM="RoomDaoImpl.getAllAvailableRoom";
    private static final String GET_ROOM_BY_ID="RoomDaoImpl.getRoomById";
    private static final String GET_ROOM_STATUS="RoomDAoImpl.getRoomStatus";
    private static  final String INSERT_INTO_ROOM="RoomDaoImpl.insertIntoRoom";
    private static final String UPDATE_ROOM="RoomDaoImpl.updateRoom";
    private static final String DELETE_ROOM="RoomDaoImpl.deleteRoom";
    private int key;
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

    @Autowired
    private DataSource dataSource;

    @Mock
    private JdbcTemplate mockJdbcTemplate;

    private RoomDao roomDao;
    private Room room;

    @BeforeMethod
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        roomDao = new RoomDaoImpl(jdbcTemplate,getGetAllRoom,getGetAvailableRoom,getGetRoomById,getGetRoomStatus,getUpdateRoom,getDeleteRoom,getInsertIntoRoom);
        RoomDao mockRoomDao = new RoomDaoImpl(mockJdbcTemplate, getGetAllRoom, getGetAvailableRoom, getGetRoomById, getGetRoomStatus, getUpdateRoom, getDeleteRoom, getInsertIntoRoom);

        room = new Room("NonAc", 503, "Available");
         key=roomDao.addRoom(room);
          room.setRoomId(key);
        assertNotNull(jdbcTemplate);
        assertNotNull(room);

    }

    /**
     *  All Rooms Details
     * @throws Exception instance of {@link RoomDaoException} if Empty data Results
     */
    @Test
        public void testGetAllRooms() throws Exception {
        log.info("getAllRoom test");
        List<Room> rooms = roomDao.getAllRooms();
        assertNotNull(rooms);

        Iterable<Room> r = roomDao.getAllRooms();
        int count = 0;
        for (Room p : r) {
            count++;
        }
        assertNotEquals(0,count);
    }


 /* @Test(expectedExceptions = DataAccessException.class)
    public void testAddRoom_Throw_RoomDaoException() throws Exception{
        doThrow(DataAccessException.class).when(mockJdbcTemplate).query(getGetAllRoom, (resultSet, i) -> toRoom(resultSet) );
        roomDao.getAllRooms();
        verify(mockJdbcTemplate).query(getGetAllRoom,(ResultSet::));

    }
*/

    /**
     * add new Room data in Database
     * @throws Exception instance of {@link RoomDaoException} if Not add New Room Details
     */
    @Test
    @Transactional
    public void testAddRoom() throws Exception {
        logger.info("before add");
        int key= roomDao.addRoom(room);
        logger.info("after add");
        Room room1 = roomDao.findRoomById(key);
        assertNotNull(room1);
        assertEquals(room1.getType(), this.room.getType());
        assertEquals(room1.getRate(), this.room.getRate());
        assertEquals(room1.getStatus(), this.room.getStatus());
    }

    /**
     * Find Room By Id form database
     *  instance of {@link Room}
     * @throws Exception throw instance of {@link RoomDaoException} if Room not present
     */
    @Test
    @Transactional
    public void testFindRoomById() throws Exception{
        Room room1=roomDao.findRoomById(key);
        assertNotNull(room1);
        assertEquals(room1.getType(), this.room.getType());
        assertEquals(room1.getRate(), this.room.getRate());
        assertEquals(room1.getStatus(), this.room.getStatus());

    }

    /**
     * Delete Room From Database using RoomId
     * return integer 0 or 1 ; 1 for success otherwise 0;
     * @throws Exception throw instance of {@link RoomDaoException} if Id Not Present
     */
    @Test
    @Transactional
    public void testDeleteRoom()throws Exception {
        int result=roomDao.deleteRoom(key);
        assertEquals(1,result);
    }

    /**
     * update Room Details
     * return int 0 or 1 ;1 for success otherwise 0
     * @throws Exception instance of {@link RoomDaoException} if Id Not Present
     */
    @Test
    @Transactional
    public void testUpdateRoom()throws Exception {

        int result=roomDao.updateRoom(room);
        assertEquals(1,result);
    }

    /**
     * access the status of room is available or not
     * @throws Exception if Room id not present
     */
    @Test
    @Transactional
    public void testGetStatus() throws Exception{
        String status=roomDao.getStatus(key);
        assertNotNull(status);
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

