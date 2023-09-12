package models.room;

import java.util.Objects;
import models.room.enums.RoomType;

public class Room implements IRoom {

    private static int _maxRoomNumLength = 11;
    private static int _maxRoomLength = 0;
    private static String _roomFormat = "%s => %" + Room.getMaxRoomNumLength() + "s | %14s | %s";

    private final String _roomNumber;
    private final Double _price;
    private final RoomType _roomType;

    public Room(final String roomNumber, final Double price, final RoomType roomType) {
        super();

        this._roomNumber = roomNumber;
        this._price = price;
        this._roomType = roomType;

        this.computeLength();
    }

    // #region Getters

    public String getRoomNumber() {
        return this._roomNumber;
    }

    public Double getRoomPrice() {
        return this._price;
    }

    public RoomType getRoomType() {
        return this._roomType;
    }

    public boolean isFree() {
        return this._price != null && this._price.equals(0.0d);
    }

    public static int getMaxRoomNumLength() {
        return Room._maxRoomNumLength;
    }

    public static int getMaxRoomLength() {
        return Room._maxRoomLength;
    }

    public static String getRoomFormat() {
        return Room._roomFormat;
    }

    @Override
    public String toString() {
        return String.format(Room._roomFormat, "Paid Room", this._roomNumber,
                String.format("%12.2f", this._price), this._roomType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Room room)) {
            return false;
        }

        return Objects.equals(this._roomNumber, room._roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._roomNumber);
    }

    private void computeLength() {
        if (this._roomNumber.length() > Room._maxRoomNumLength)
            Room._maxRoomNumLength = this._roomNumber.length();

        Room._roomFormat = "%s => %" + Room.getMaxRoomNumLength() + "s | %14s | %s";

        int sum = 13 + Room._maxRoomNumLength + 20;
        if (sum > Room._maxRoomLength)
            Room._maxRoomLength = sum + 6;
    }
}