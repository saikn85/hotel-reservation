package models.room;

import java.util.Objects;
import models.room.enums.RoomType;

public class Room implements IRoom {

    private static int _maxRoomNumLength = 0;
    private static int _maxRoomLength = 0;

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

    public static int getmaxRoomNumLength() {
        return Room._maxRoomNumLength;
    }

    public static int getMaxRoomLength() {
        return Room._maxRoomLength;
    }

    // #endregion

    // #region Override(s)

    @Override
    public String toString() {
        String format = "%" + _maxRoomNumLength + "s | %6.2f | %s";
        return String.format("Paid Room => " + format, this._roomNumber, this._price, this._roomType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Room)) {
            return false;
        }

        final Room room = (Room) obj;
        return Objects.equals(this._roomNumber, room._roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._roomNumber);
    }

    // #endregion

    // #region Private Helpers

    private void computeLength() {
        if (this._roomNumber.length() > Room._maxRoomNumLength)
            Room._maxRoomNumLength = this._roomNumber.length();

        Room._maxRoomLength = this.toString().length();
    }

    // #endregion
}
