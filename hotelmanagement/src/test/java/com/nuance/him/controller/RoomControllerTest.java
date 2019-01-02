/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.controller;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.app.SpringHotelApp;
import com.nuance.him.model.Room;
import com.nuance.him.service.RoomService;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringHotelApp.class)
@PropertySource(value = {"classpath:application.properties"})
public class RoomControllerTest extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(RoomControllerTest.class);


    private static  final String GET_ALL_ROOM="Room.getAllRooms";
    private static final String GET_AVAILABLE_ROOM="Room.getAllAvailableRoom";
    private static final String GET_ROOM_BY_ID="Room.getRoomById";
    private static final String GET_ROOM_STATUS="Room.getRoomStatus";
    private static  final String INSERT_INTO_ROOM="Room.insertIntoRoom";
    private static final String UPDATE_ROOM="Room.updateRoom";
    private static final String DELETE_ROOM="Room.deleteRoom";
    private static final int ROOM_ID = 8;
    private static final String ROOM_TYPE = "AC";
    private static final String ROOM_RATE = "500";
    private static final String ROOM_STATUS = "Available";
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
    @Mock
    private RoomService roomService;

    private MockMvc mockMvc;
    private Room room;
    private List<Room> rooms;


    @BeforeMethod
    private void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RoomController(roomService)).build();
        room = new Room(ROOM_TYPE, Integer.parseInt(ROOM_RATE), ROOM_STATUS);
    }

    /**
     * create new room details
     * @throws Exception if fail to create
     */

    @Test
    public void testCreateRoom() throws Exception {
        assertNotNull(this.roomService);
        when(roomService.addRoom(room)).thenReturn(anyInt());
        this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertIntoRoom)
            .param("type", ROOM_TYPE).param("rate", ROOM_RATE).param("status", ROOM_STATUS))
            .andExpect(status().isCreated());
    }

    /**
     * getAll room from database
     * @throws Exception if not getting results
     */
    @Test
    public void testGetAllRoomProcess() throws Exception {
        assertNotNull(this.roomService);

        when(roomService.getAllRooms()).thenReturn(rooms);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getGetAllRoom))
            .andExpect(status().isOk());

        verify(roomService).getAllRooms();


    }

    /**
     * Get All RoomS Which is Available for rent
     * @throws Exception If any
     */

    @Test
    public void testGetAllAvailableRoom()throws Exception {
        assertNotNull(this.roomService);

        when(roomService.getAllAvailableRooms()).thenReturn(rooms);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getGetAvailableRoom)).andExpect(status().isOk());

        verify(roomService).getAllAvailableRooms();

    }

    /**
     * Find Room by Id @return Room Details
     * @throws Exception if RoomId Not Found
     * pass parameter roomId
     */
    @Test
    public void testGetRoomById()throws Exception {
        assertNotNull(this.roomService);

        when(roomService.findRoomById(ROOM_ID)).thenReturn(room);
          this.mockMvc.perform(MockMvcRequestBuilders.get(getGetRoomById+"/"+ROOM_ID))
            .andExpect(status().isOk());

       verify(roomService).findRoomById(ROOM_ID);
    }



    /**
     * update Room By Id
     * @throws Exception if Room id Not present or Failed to update
     * passing parameters room Id,type,rate,status
     *
     **/

    @Test
    public void testUpdateRoom()throws Exception {
        when(roomService.updateRoom(room)).thenReturn(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.put(getUpdateRoom+"/" + ROOM_ID)
            .param("type", ROOM_TYPE).param("rate", "500").param("status", ROOM_STATUS))
            .andExpect(status().isOk());
    }


    /**
     * delete room by Id
     * @throws Exception if Room not Present
     */

    @Test
    public void testDeleteRoom()throws Exception {
        when(roomService.deleteRoom(ROOM_ID)).thenReturn(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.delete(getDeleteRoom+"/" + ROOM_ID)).andExpect(status().isOk());

    }

    /**
     * Get Status of room is Available or not
     * @throws Exception if RoomId not found
     */
    @Test
    public void testStatusRoom()throws Exception {
        when(roomService.getStatus(ROOM_ID)).thenReturn(anyString());
        mockMvc.perform(MockMvcRequestBuilders.get(getGetRoomStatus+"/" + ROOM_ID)).andExpect(status().isOk());

    }


}