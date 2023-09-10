package models.room;

import models.room.enums.RoomType;

public class FreeRoom extends Room {

    public FreeRoom(final String roomNumber, final RoomType enumeration) {
        super(roomNumber, 0.0d, enumeration);
    }

    @Override
    public String toString() {
        return "FreeRoom => " + super.toString();
    }
}