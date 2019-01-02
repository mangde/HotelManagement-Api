
package com.nuance.him.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nuance.him.Exception.BookingDaoException;
import com.nuance.him.Exception.BookingServiceException;
import com.nuance.him.Exception.RoomDaoException;
import com.nuance.him.Exception.RoomServiceException;
import com.nuance.him.customerror.CustomErrorType;
import com.nuance.him.model.Booking;
import com.nuance.him.model.Room;
import com.nuance.him.service.BookingService;
import com.nuance.him.service.RoomService;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/hotel")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private final BookingService bookingService;
    @Autowired
    private RoomService roomService;

    private Room room;



    /**
     * constructor for  bean of bookingService class
     *
     * @param bookingService instance of {@link BookingService}
     */
    public BookingController(final BookingService bookingService,final RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService=roomService;
    }



    @InitBinder
    public void initBinder(final WebDataBinder binder){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * method to addRoomBooking data
     *
     * @param customerId customerId
     * @param roomId roomId
     * @param checkIn checkIn date
     * @param checkOut checkOut Date
     */
    @RequestMapping(value = "/roomBook", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity roomBook(@RequestParam(value = "customerId") @Valid int customerId,
        @RequestParam(value = "roomId") @Valid int roomId,
        @DateTimeFormat(pattern = "dd/MM/yyyy")@RequestParam(value = "checkIn")   LocalDate checkIn,
        @DateTimeFormat(pattern = "dd/MM/yyyy") @RequestParam(value = "checkOut")  LocalDate checkOut ,
        @RequestParam(value = "bookedBy") @Valid int bookedBy)
   {
       if(roomId<0||customerId<0)
       {
           return new ResponseEntity(new RoomDaoException("Negetive Id please enter valid Id"),BAD_REQUEST);

       }
        Booking booking = new Booking(customerId, roomId, checkIn, checkOut,bookedBy);


       try {
            logger.info("checking status of room Id {}",roomId);
          String   status = roomService.getStatus(roomId);

           if (status== null||status.equalsIgnoreCase("NotAvailable")) {
               logger.info(" status of room is NotAvailable");
               return new ResponseEntity(new RoomDaoException("Failed to book Room with id " + roomId + " Status NotAvailable"), NOT_FOUND);
           }
          }
        catch (RoomServiceException e) {
            logger.error("exception in getStatus");
            return new ResponseEntity(new CustomErrorType("Failed to getStatus with id " +roomId+ " not found please Enter Valid Id"), HttpStatus.NOT_FOUND);
        }


            try {

                logger.info("Booking Room : {}", booking);
                int bookingId = bookingService.bookingRoom(booking);
                logger.info("id created {}", bookingId);
            }
            catch (BookingServiceException e) {
                return new ResponseEntity(new BookingDaoException("Failed to bookingRoom  " + " please try again"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(booking, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/updateBooking", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateBooking(@RequestParam(value = "roomId")int roomId,@RequestParam(value = "releasedBy")int releasedBy)
    {
        logger.info("Updating Booking with RoomId {}", roomId);
        try {
            logger.info("finding room by id");
            Room rooms = roomService.findRoomById(roomId);
        }
        catch (RoomServiceException r) {
            logger.info("Exception thrown by getting room by id");
            logger.error("Unable to update. Booking with id {} not found.", roomId);
            return new ResponseEntity(new CustomErrorType("Unable to update. Booking with id " + roomId + " not found."), HttpStatus.NOT_FOUND);
        }
        Booking booking=new Booking();
        booking.setRoomId(roomId);
        booking.setReleaseBy(releasedBy);
        try{
            logger.info("updating Booking ");
            bookingService.updateBooking(booking);
        }catch (BookingServiceException e){
            logger.info("Exception in controller updating Booking ");

            return new ResponseEntity(new CustomErrorType("Unable to update. Booking with id " + roomId+"NotModify"), HttpStatus.NOT_MODIFIED);

        }
        return new ResponseEntity(booking, HttpStatus.OK);

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}
