package models.reservation;

import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    //region Static Setup
    private static int _maxReservationLength = 0;
    private static String _reservationFormat = "%" + Customer.getMaxCustomerLength() + "s | %"
            + Room.getMaxRoomLength() + "s | %13s | %14s";
    //endregion

    //region Instance Variables
    private final Customer _customer;
    private final IRoom _room;
    private final Date _checkInDate;
    private final Date _checkOutDate;
    //endregion

    //region Constructor
    public Reservation(final Customer customer, final IRoom room,
                       final Date checkInDate, final Date checkOutDate) {
        super();

        _customer = customer;
        _room = room;
        _checkInDate = checkInDate;
        _checkOutDate = checkOutDate;

        this.computeLength();
    }
    //endregion

    //region Getters - Static Variables
    public static int getMaxReservationLength() {
        return Reservation._maxReservationLength;
    }
    public static String getReservationFormat() {
        return Reservation._reservationFormat;
    }
    private static String getFormattedDate(final Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    //endregion

    //region Getters
    public Customer getCustomer() {
        return _customer;
    }
    public IRoom getRoom() {
        return _room;
    }
    public Date getCheckInDate() {
        return _checkInDate;
    }
    public Date getCheckOutDate() {
        return _checkOutDate;
    }
    //endregion

    //region Overrides
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reservation other)) return false;
        return Objects.equals(_customer, other._customer) &&
                Objects.equals(_room, other._room) &&
                Objects.equals(_checkInDate, other._checkInDate) &&
                Objects.equals(_checkOutDate, other._checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_customer, _room, _checkInDate, _checkOutDate);
    }

    @Override
    public String toString() {
        return String.format(Reservation.getReservationFormat(), this._customer, this._room,
                Reservation.getFormattedDate(this._checkInDate),
                Reservation.getFormattedDate(this._checkOutDate));
    }
    //endregion

    //region Private Helpers
    private void computeLength() {
        Reservation._reservationFormat = "%" + Customer.getMaxCustomerLength() + "s | %"
                + Room.getMaxRoomLength() + "s | %13s | %14s";

        int sum = Customer.getMaxCustomerLength() + Room.getMaxRoomLength() + 27;
        if (sum > Reservation.getMaxReservationLength())
            Reservation._maxReservationLength = sum + 9;
    }
    //endregion
}
