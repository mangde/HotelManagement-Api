
package com.nuance.him.service;

import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuance.him.Exception.RoomDaoException;
import com.nuance.him.Exception.RoomServiceException;
import com.nuance.him.dao.RoomDao;
import com.nuance.him.model.Room;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private final RoomDao roomDao;

    /**
     * Constructor of RoomDao used to initialise roomDao Object
     * @param roomDao instance of RoomDao
     */
    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao=roomDao;
    }

    @Override
    public List<Room> getAllRooms() throws RoomServiceException {
        logger.info("inside roomServiceImpl getAllRoom Called");
        try{
            return roomDao.getAllRooms();
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);
        }
    }

    @Override
    public List<Room> getAllAvailableRooms() throws RoomServiceException {
        logger.info("inside roomServiceImpl getAllAvailableRoom Called");

        try{
            return roomDao.getAllAvailableRooms();
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);

        }
    }

    @Override
    public Room findRoomById(final int id) throws RoomServiceException {
        logger.info("inside roomServiceImpl findRoomById Called");

        try{
            return roomDao.findRoomById(id);
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);
        }
    }

    @Override
    public int addRoom(Room room) throws RoomServiceException {
        logger.info("inside roomServiceImpl addRoom Called");
        try{
            return roomDao.addRoom(room);
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);
        }catch(ConstraintViolationException ee){
            logger.info(ee.getConstraintViolations().getClass().getName());
        }
        return 0;
    }

    @Override
    public int deleteRoom(final int id) throws RoomServiceException {
        logger.info("inside roomServiceImpl Delete Called");

        try{
            return roomDao.deleteRoom(id);
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);
        }
    }

    @Override
    public int updateRoom(Room room) throws RoomServiceException {
        logger.info("inside roomServiceImpl update Called");

        try{
            return roomDao.updateRoom(room);
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);
        }
    }

    @Override
    public String getStatus(final int id) throws RoomServiceException {
        logger.info("inside roomServiceImpl getStatus Called");

        try{
            return roomDao.getStatus(id);
        }catch (RoomDaoException r){
            throw  new RoomServiceException("Exception ",r);
        }
    }
}
