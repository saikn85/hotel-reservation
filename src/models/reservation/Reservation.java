package models.reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;

public class Reservation {

    // #region Static Setup

    private static String _reservationFormat = "%" + Customer.getMaxCustomerLenght() + "s | %"
            + Room.getMaxRoomLength() + "s | %s | %s";

    // #endregion

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
        return String.format(Reservation._reservationFormat, this._customer, this._room,
                Reservation.getFormattedDate(this._checkInDate),
                Reservation.getFormattedDate(this._checkOutDate));
    }

    // #endregion

    // #region Pirvate Helpers

    private static String getFormattedDate(final Date date) {
        return new SimpleDateFormat("yyyy-mm-dd").format(date);
    }

    // #endregion
}
