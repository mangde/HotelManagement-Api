
package com.nuance.him.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.data.annotation.Id;

public class Room {

    @Id
    @Positive(message = "please enter valid Id it should be Positive")
    @Min(1)
    private int roomId;
    @NotNull
    private String type;
    @NotNull
    @Positive
    @Min(value = 1, message = "Rate should be positive")
    private int rate;
    @NotNull
    private String status;

    /**
     * default constructor
     */
    public Room() {
    }

    /**
     * @param type is a Room type
     * @param rate is a room rate
     * @param status of room is available or notAvailable
     */
    public Room(String type, int rate, String status) {
        this.type = type;
        this.rate = rate;
        this.status = status;
    }

    /**
     * @return roomID
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @param roomId set roomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return room type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type setRoomType
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return room rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * @param rate set room rate
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    /**
     * @return room status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status set room status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return room information like roomId,type,rate and status
     */
    @Override
    public String toString() {
        return "Room{" + "roomId=" + roomId + ", type='" + type + '\'' + ", rate=" + rate + ", Status='" + status + '\'' + '}';
    }
}
