package models.room;

import models.room.enums.RoomType;

public class FreeRoom extends Room {

    public FreeRoom(final String roomNumber, final RoomType roomType) {
        super(roomNumber, 0.0d, roomType);
    }

    @Override
    public String toString() {
        return "Free Room => " + super.toString();
    }
}