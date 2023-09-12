package models.reservation;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;

public class Reservation {

    private static int _maxReservationLength = 0;
    private static String _reservationFormat = "%" + Customer.getMaxCustomerLength() + "s | %"
            + Room.getMaxRoomLength() + "s | %13s | %14s";

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

    public IRoom getRoom() {
        return this._room;
    }

    public Date getCheckInDate() {
        return this._checkInDate;
    }

    public Date getCheckOutDate() {
        return this._checkOutDate;
    }

    public static int getMaxReservationLength() {
        return Reservation._maxReservationLength;
    }

    public static String getReservationFormat() {
        return Reservation._reservationFormat;
    }

    @Override
    public String toString() {
        return String.format(Reservation._reservationFormat, this._customer, this._room,
                Reservation.getFormattedDate(this._checkInDate),
                Reservation.getFormattedDate(this._checkOutDate));
    }

    private void computeLength() {
        Reservation._reservationFormat = "%" + Customer.getMaxCustomerLength() + "s | %"
                + Room.getMaxRoomLength() + "s | %13s | %14s";

        int sum = Customer.getMaxCustomerLength() + Room.getMaxRoomLength() + 27;
        if (sum > Reservation.getMaxReservationLength())
            Reservation._maxReservationLength = sum + 9;
    }

    private static String getFormattedDate(final Date date) {
        return new SimpleDateFormat("yyyy-mm-dd").format(date);
    }
}
