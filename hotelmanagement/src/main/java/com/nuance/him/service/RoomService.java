/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.service;

import org.springframework.stereotype.Service;
import com.nuance.him.Exception.RoomServiceException;
import com.nuance.him.model.Room;
import java.util.List;

@Service
public interface RoomService {

    /**
     * @return All rooms data which is available
     * @throws RoomServiceException if Empty result to {@link RoomServiceException}
     */
    List<Room> getAllRooms() throws RoomServiceException;

    /**
     * @return only rooms which having status is available
     * @throws RoomServiceException to custom class  {@link RoomServiceException}
     */
    List<Room> getAllAvailableRooms() throws RoomServiceException;

    /**
     * @param id pass Room id
     * @return room object with room detailed as per room id passed to method argument
     * @throws RoomServiceException if not found customException class {@link RoomServiceException}
     */
    Room findRoomById(final int id) throws RoomServiceException;

    /**
     * @param room object of Room class contains room details
     * @return int 0 or 1 ; 1 for successful add else 0
     * @throws RoomServiceException if fail to add or {@link RoomServiceException}
     */
    int addRoom(Room room) throws RoomServiceException;

    /**
     * @param id of room which you want to delete
     * @return int 0 or 1 ; 1 for successful deletion otherwise 0
     * @throws RoomServiceException if Failed to delete
     */
    int deleteRoom(final int id) throws RoomServiceException;

    /**
     * @param room object of Room class contains room details
     * @return int 0 or 1 ; 1 for successful update else 0
     * @throws RoomServiceException if fail to update
     */
    int updateRoom(Room room) throws RoomServiceException;

    /**
     * @param id if the room for accessing status
     * @return status of the room is available or not
     * @throws RoomServiceException if id not found
     */
    String getStatus(final int id) throws RoomServiceException;
}
