/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.dao;

import com.nuance.him.Exception.RoomDaoException;
import com.nuance.him.model.Room;
import java.util.List;

public interface RoomDao  {
    /**
     *
     * @return All rooms data which is available
     * @throws RoomDaoException if Empty result
     */
    List<Room> getAllRooms() throws RoomDaoException;

    /**
     *
     * @return only rooms which having status is available
     * @throws RoomDaoException to custom class {@link RoomDaoException}
     */
    List<Room> getAllAvailableRooms() throws RoomDaoException;
    /**
     *
     * @param id pass Room id
     * @return room object with room detailed as per room id passed to method argument
     * @throws RoomDaoException to custom class {@link RoomDaoException}
     */
    Room findRoomById(final long id) throws RoomDaoException;
    /**
     *
     * @param room object of Room class contains room details
     * @return int 0 or 1 ; 1 for successful add else 0
     * @throws RoomDaoException to custom class {@link RoomDaoException}
     */
    int addRoom(Room room) throws RoomDaoException;
    /**
     *
     * @param id of room which you want to delete
     * @return int 0 or 1 ; 1 for successful deletion otherwise 0
     * @throws RoomDaoException to custom class {@link RoomDaoException}
     */
    int deleteRoom(final int id) throws RoomDaoException;
    /**
     *
     * @param room object of Room class contains room details
     * @return int 0 or 1 ; 1 for successful update else 0
     * @throws RoomDaoException to custom class {@link RoomDaoException}
     */
    int updateRoom(Room room) throws RoomDaoException;
    /**
     *
     * @param id if the room for accessing status
     * @return status of the room is available or not
     * @throws RoomDaoException to custom class {@link RoomDaoException}
     */
    String getStatus(final int id) throws RoomDaoException;


}
