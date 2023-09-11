package models.reservation;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;

public class Reservation {

    // #region Static Setup

    private static int _maxReservationLenght = 0;
    private static String _reservationFormat = "%" + Customer.getMaxCustomerLenght() + "s | %"
            + Room.getMaxRoomLength() + "s | %13s | %14s";

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

    public static int getMaxReservationLenght() {
        return Reservation._maxReservationLenght;
    }

    public static String getReservationFormat() {
        _reservationFormat = "%" + Customer.getMaxCustomerLenght() + "s | %"
                + Room.getMaxRoomLength() + "s | %13s | %14s";
        return Reservation._reservationFormat;
    }

    // #endregion

    // #region Override(s)

    @Override
    public String toString() {
        String customer = this._customer.toString();
        String room = this._room.toString();
        return String.format(Reservation._reservationFormat, customer, room,
                Reservation.getFormattedDate(this._checkInDate),
                Reservation.getFormattedDate(this._checkOutDate));
    }

    // #endregion

    // #region Pirvate Helpers

    private void computeLength() {
        int sum = Customer.getMaxCustomerLenght() + Room.getMaxRoomLength() + 27;
        if (sum > Reservation._maxReservationLenght)
            Reservation._maxReservationLenght = sum + 9;
    }

    private static String getFormattedDate(final Date date) {
        return new SimpleDateFormat("yyyy-mm-dd").format(date);
    }

    // #endregion
}
