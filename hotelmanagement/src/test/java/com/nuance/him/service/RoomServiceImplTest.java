/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.service;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.Exception.RoomDaoException;
import com.nuance.him.Exception.RoomServiceException;
import com.nuance.him.dao.RoomDao;
import com.nuance.him.model.Room;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class RoomServiceImplTest {

    @Mock
    private RoomDao roomDao;

    private RoomService roomService;
    private List<Room> rooms;
    private Room room;

    /**
     * Method to set Execution environment of test class
     * create instance of {@link Room}
     * init instance of {@link Mock}
     */
    @BeforeMethod
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        roomService = new RoomServiceImpl(roomDao);
        room = new Room("Ac", 500, "Available");
        room.setRoomId(1);
    }

    /**
     * Test method GetAllRooms
     * @result instance of {@link java.util.List} with rooms data
     */
    @Test
    public void testGetAllRooms() throws Exception {

        when(roomDao.getAllRooms()).thenReturn(rooms);
        List<Room> list = roomService.getAllRooms();
        AssertJUnit.assertEquals(rooms, list);
        Mockito.verify(roomDao).getAllRooms();
    }

    /**
     * Test method GetAllRooms Exception
     * if  it is throw {@link RoomServiceException} then pass otherwise fail
     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testGetAllRoomsFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).getAllRooms();
        try {
            roomService.getAllRooms();
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            verify(roomDao).getAllRooms();
            throw e;
        }
    }

    /**
     * test method GetAllAvailable
     * only return Rooms having status Available
     * @result instance of {@link java.util.List} with rooms  otherwise Null if Room notAvailable
     */
    @Test
    public void testGetAllAvailableRooms() throws Exception {

        when(roomDao.getAllAvailableRooms()).thenReturn(rooms);
        List<Room> list = roomService.getAllAvailableRooms();
        AssertJUnit.assertEquals(rooms, list);
        Mockito.verify(roomDao).getAllAvailableRooms();
    }

    /**
     * Test GetAllAvailableFailure method
     * if  it is throw {@link RoomServiceException} then pass otherwise fail
     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testGetAllAvailableRoomsFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).getAllAvailableRooms();
        try {
            roomService.getAllAvailableRooms();
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            Mockito.verify(roomDao).getAllAvailableRooms();
            throw e;
        }
    }

    /**
     * test method findRoomById
     * @result instance of {@link Room} cannot be NULL if Room is present otherwise NULL
     */
    @Test
    public void testFindRoomById() throws Exception {

        when(roomDao.findRoomById(1)).thenReturn(room);
        Room room1 = roomService.findRoomById(1);
        AssertJUnit.assertEquals(room, room1);
        Mockito.verify(roomDao).findRoomById(1);
    }

    /**
     *    test findById() method is throw appropriate exception or not
     * @result throw {@link RoomServiceException}

     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testFindRoomByIdFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).findRoomById(1);
        try {
            roomService.findRoomById(1);
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            Mockito.verify(roomDao).findRoomById(1);
            throw e;
        }
    }
    /**
     *  Create a valid Room Entry.
     * @result Room will be persisted without any errors,
     *
     */
    @Test
    public void testAddRoom() throws Exception {

        when(roomDao.addRoom(room)).thenReturn(anyInt());
        int result = roomService.addRoom(room);
        assertEquals(0, result);
        Mockito.verify(roomDao).addRoom(room);
    }


    /**
     * check addRoomFailure
     * @result throw {@link RoomServiceException}
     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testAddRoomFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).addRoom(room);
        try {
            roomService.addRoom(room);
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            Mockito.verify(roomDao).addRoom(room);
            throw e;
        }
    }
    /**
     * @result delete Room_with_Id and return 1 otherwise 0
     */
    @Test
    public void testDeleteRoom() throws Exception {

        when(roomDao.deleteRoom(1)).thenReturn(1);
        int result = roomService.deleteRoom(1);
        assertEquals(1, result);
        Mockito.verify(roomDao).deleteRoom(1);
    }
    /**
     * @result throw {@link RoomServiceException}
     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testDeleteRoomFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).deleteRoom(1);
        try {
            roomService.deleteRoom(1);
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            Mockito.verify(roomDao).deleteRoom(1);
            throw e;
        }
    }

    /**
     * @result update roomData  and return 1 otherwise 0 for fail
     */
    @Test
    public void testUpdateRoom() throws Exception {

        when(roomDao.updateRoom(room)).thenReturn(1);
        int result = roomService.updateRoom(room);
        assertEquals(1, result);
        Mockito.verify(roomDao).updateRoom(room);
    }
    /**
     *         @result throw {@link RoomServiceException}

     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testUpdateRoomFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).updateRoom(room);
        try {
            roomService.updateRoom(room);
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            Mockito.verify(roomDao).updateRoom(room);
            throw e;
        }
    }
    /**
     * @result return roomStatus Available or NotAvailable
     */
    @Test
    public void testGetStatus() throws Exception {

        when(roomDao.getStatus(1)).thenReturn("Available");
        String result = roomService.getStatus(1);
        assertEquals(("Available"), result);
        Mockito.verify(roomDao).getStatus(1);
    }

    /**
     *         @result throw {@link RoomServiceException}

     */
    @Test(expectedExceptions = RoomServiceException.class)
    public void testGetStatusFailure() throws Exception {

        doThrow(RoomDaoException.class).when(roomDao).getStatus(1);
        try {
            roomService.getStatus(1);
        }
        catch (RoomServiceException e) {
            assertEquals(RoomDaoException.class, e.getCause().getClass());
            Mockito.verify(roomDao).getStatus(1);
            throw e;
        }
    }
}