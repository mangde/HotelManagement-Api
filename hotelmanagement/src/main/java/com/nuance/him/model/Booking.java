/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */
package com.nuance.him.model;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

/**
 * Booking class used to store the Booking details
 */
public class Booking {

    @Id
    @Positive(message = "please enter positive Id")
    private int bookingId;
    @Positive(message = "positive Id Only")
    @NotNull
    private int customerId;
    @Positive(message = "enter positive only")
    @NotNull
    private int roomId;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;


    @NotNull
    private int bookedBy;
    private int releaseBy;

    /**
     * constructor for {@link Booking}
     * @param customerId customerId of customer
     * @param roomId roomId Of Room
     * @param checkIn Hotel Booking date
     * @param checkOut checkOut from Hotel
     */
    public Booking(int customerId, int roomId,    @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,    @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,int bookedBy) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookedBy=bookedBy;
    }

    public Booking() {

    }

    /**
     * @return bookingId
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * @param bookingId setBookingId
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId setCustomerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @param roomId setRoomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return checkId Details
     */
    public LocalDate getCheckIn() {
        return checkIn;
    }

    /**
     * @param checkIn set checkIn
     */
    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * @return checkOut
     */
    public LocalDate getCheckOut() {
        return checkOut;
    }

    /**
     * @param checkOut setCheckOut
     */
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }

    public int getReleaseBy() {
        return releaseBy;
    }

    public void setReleaseBy(int releaseBy) {
        this.releaseBy = releaseBy;
    }


    /**
     * @return Booking Details
     */
    @Override
    public String toString() {
        return "Booking{" + "customerId=" + customerId + ", roomId=" + roomId + ", checkIn=" + checkIn + ", checkOut=" + checkOut + '}';
    }

}
