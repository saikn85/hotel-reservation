package models.room;

import models.room.enums.RoomType;

import java.util.Objects;

public class Room implements IRoom {
    //region Static Setup
    private static int _maxRoomNumLength = 11;
    private static int _maxRoomLength = 0;
    private static String _roomFormat = "%s => %" + Room.getMaxRoomNumLength() + "s | %14s | %s";
    //endregion

    //region Instance Variables
    private final String _roomNumber;
    private final Double _price;
    private final RoomType _roomType;
    //endregion

    //region Constructor
    public Room(final String roomNumber, final Double price, final RoomType roomType) {
        super();

        _roomNumber = roomNumber;
        _price = price;
        _roomType = roomType;

        this.computeLength();
    }
    //endregion

    //region Getters - Static Variables
    public static int getMaxRoomNumLength() {
        return Room._maxRoomNumLength;
    }
    public static int getMaxRoomLength() {
        return Room._maxRoomLength;
    }
    public static String getRoomFormat() {
        return Room._roomFormat;
    }
    //endregion

    //region Getters
    public String getRoomNumber() {
        return _roomNumber;
    }
    public Double getRoomPrice() {
        return _price;
    }
    public RoomType getRoomType() {
        return _roomType;
    }
    public boolean isFree() {
        return _price != null && _price.equals(0.0d);
    }
    //endregion

    //region Overrides
    @Override
    public String toString() {
        return String.format(Room.getRoomFormat(), "Paid Room", _roomNumber,
                String.format("%12.2f", _price), _roomType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Room room)) return false;
        return Objects.equals(_roomNumber, room._roomNumber) &&
                Objects.equals(_price, room._price) &&
                _roomType == room._roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_roomNumber, _price, _roomType);
    }
    //endregion

    //region Private Helpers
    private void computeLength() {
        if (this._roomNumber.length() > Room._maxRoomNumLength)
            Room._maxRoomNumLength = this._roomNumber.length();

        Room._roomFormat = "%s => %" + Room.getMaxRoomNumLength() + "s | %14s | %s";

        int sum = 13 + Room._maxRoomNumLength + 20;
        if (sum > Room._maxRoomLength)
            Room._maxRoomLength = sum + 6;
    }
    //endregion
}