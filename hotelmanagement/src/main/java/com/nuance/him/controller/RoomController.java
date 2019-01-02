
package com.nuance.him.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nuance.him.Exception.RoomServiceException;
import com.nuance.him.customerror.CustomErrorType;
import com.nuance.him.model.Room;
import com.nuance.him.service.RoomService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RoomController class having Api calls
 */
@RestController
@RequestMapping("/hotel")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
   // private static final Log logger=LogFactory.getLog(RoomController.class);
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Add new Room Method
     *
     * @param type room type
     * @param rate room rate
     * @param status room status available or not
     * @return any int 0 or 1; 1 for success otherwise 0
     */
    @RequestMapping(value = "/addRoom", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createRoom( @RequestParam(value = "type" )@Valid String type,  @RequestParam(value = "rate")
    @Valid int rate, @RequestParam(value = "status")@Valid String status) {
        Room room = new Room(type, rate, status);
        logger.info("Creating Room : {}", room);
        try {
            int id =roomService.addRoom(room);
            logger.info("id created {}",id);
        }
        catch (RoomServiceException e) {
            e.printStackTrace();
            return new ResponseEntity(new CustomErrorType("No  room created"), HttpStatus.NOT_MODIFIED);

        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Room>(headers, HttpStatus.CREATED);
    }

    /**
     * getAllRoom Method
     *
     * @return All Room Details
     */
    // ------------------- Get All Room Details-------------------------------------//
    @RequestMapping(value = "/getAllRoom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllRoomProcess() {
        List<Room> list;
        try {
            list = roomService.getAllRooms();

        }
        catch (RoomServiceException r) {
            r.printStackTrace();
            return new ResponseEntity(new CustomErrorType("No  rooms NotFound"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * getAllAvailableRoom method
     *
     * @return only available room details
     */
    // ------------------- Get All Available Room Details-------------------------------------//
    @RequestMapping(value = "/getAllAvailableRoom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllAvailableRoom() {
        List<Room> list;
        try {
            list = roomService.getAllAvailableRooms();

        }
        catch (RoomServiceException r) {
            r.printStackTrace();
            return new ResponseEntity<>(new CustomErrorType ("No available room NotFound"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Find-Room-By-Id Method
     *
     * @param id roomId for find roomDetail
     * @return instance of class Room contains room info
     */
    // ------------------- Find Room By ID-------------------------------------//
    @RequestMapping(value = "/findRoom/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRoom( @PathVariable("id") @Valid  int id) {
        logger.info("Fetching Room with id {}", id);
        Room room;
        try {
            room = roomService.findRoomById(id);
        }
        catch (RoomServiceException e) {
            logger.error("Room with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Room with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    /**
     * Update Room Method
     *
     * @param id room id
     * @param type room type
     * @param rate room rate
     * @param status room status
     * @return Response fail to update or success
     */
    // ------------------- Update Room-------------------------------------//
    @RequestMapping(value = "/updateRoom/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateRoom(@PathVariable("id")@Valid  int id, @RequestParam(value = "type" )@Valid String type,
        @RequestParam(value = "rate")  @Valid int rate,
        @RequestParam(value = "status") @Valid String status) {
        logger.info("Updating Room with id {}", id);
        Room rooms;
        try {
            logger.info("finding room by id");
            rooms = roomService.findRoomById(id);
        }
        catch (RoomServiceException r) {
            logger.info("Exception thrown by getting room by id");
            logger.error("Unable to update. Room with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Room with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        Room room = new Room(type, rate, status);
        room.setRoomId(id);
         try {
            logger.info("updating room");
            roomService.updateRoom(room);
        }
        catch (RoomServiceException e) {
            logger.error("Exception in updating ");
            return new ResponseEntity(new CustomErrorType("Unable to update. Room with id " + id), HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity(rooms, HttpStatus.OK);
    }

    /**
     * Delete room method
     *
     * @param id roomId for deletion
     * @return 0 or 1; 1 for success otherwise 0
     */
    // ------------------- Delete Room-------------------------------------//
    @RequestMapping(value = "/deleteRoom/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomErrorType> deleteRoom( @PathVariable("id") @Valid int id) {
        logger.info("Fetching & Deleting Room with id {}", id);
        Room room = null;
        try {
            roomService.findRoomById(id);
        }
        catch (RoomServiceException r) {
            logger.error("Unable to delete. Room with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. Room with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        try {
            roomService.deleteRoom(id);
            logger.info("delete Room successful");
        }
        catch (RoomServiceException r) {
            logger.error("exception in delete");
            return new ResponseEntity<>(new CustomErrorType("Unable to Delete. Room with id " + id), HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * getStatus method
     *
     * @param roomId roomId
     * @return room status is available or not
     */
    //-----------------------------------------------Get Status---------------------//
    @RequestMapping(value = "/getStatus/{roomId}", method = RequestMethod.GET)
    public ResponseEntity statusRoom( @PathVariable("roomId") @Valid int roomId) {
        logger.info("Fetching  Room Available Status  with id {}", roomId);
        try {
            String result = roomService.getStatus(roomId);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (RoomServiceException r) {
            logger.error("exception in getStatus");
            return new ResponseEntity(new CustomErrorType("Unable to getStatus. Room with id " + roomId+"Not Found"), HttpStatus.NOT_FOUND);
        }
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}
