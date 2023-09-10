package models.reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;

public class Reservation {

    private static int _maxCustomerLenght = 0;
    private static int _maxRoomLength = 0;

    private final Customer _customer;
    private final IRoom _room;
    private final Date _checkInDate;
    private final Date _checkOutDate;

    public Reservation(final Customer customer, final IRoom room,
            final Date checkInDate, final Date checkOutDate) {
        super();

        this._customer = customer;
        this._room = room;
        this._checkInDate = checkInDate;
        this._checkOutDate = checkOutDate;

        this.computeLength();
    }

    // #region Getters

    public IRoom getRoom() {
        return this._room;
    }

    public Date getCheckInDate() {
        return this._checkInDate;
    }

    public Date getCheckOutDate() {
        return this._checkOutDate;
    }

    // #endregion

    // #region Override(s)

    @Override
    public String toString() {
        String format = "%" + Reservation._maxCustomerLenght + "s | %" + Reservation._maxRoomLength + "s | %s | %s";
        return String.format(format, this._customer, this._room, Reservation.getFormattedDate(this._checkInDate),
                Reservation.getFormattedDate(this._checkOutDate));
    }

    // #endregion

    // #region Pirvate Helpers

    private void computeLength() {
        if (Customer.getMaxCustomerLenght() > _maxCustomerLenght)
            Reservation._maxCustomerLenght = Customer.getMaxCustomerLenght();

        if (Room.getMaxRoomLength() > _maxRoomLength)
            Reservation._maxRoomLength = Room.getMaxRoomLength();
    }

    private static String getFormattedDate(final Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return df.format(date);
    }

    // #endregion
}
