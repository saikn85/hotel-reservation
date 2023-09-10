package api;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;
import services.customer.CustomerService;
import services.reservation.ReservationService;

public class HotelResource {
    private static final HotelResource SINGLETON = new HotelResource();

    private final CustomerService _customerService = CustomerService.getCustomerService();
    private final ReservationService _reservationService = ReservationService.getReservationService();

    private HotelResource() {
    }

    public static HotelResource getHotelResource() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return _customerService.getCustomer(email);
    }

    public void newCustomer(String email, String firstName, String lastName) {
        _customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return _reservationService.getARoom(roomNumber);
    }

    public Reservation bookRoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return _reservationService.reserveRoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        final Customer customer = getCustomer(customerEmail);

        if (customer == null) {
            return Collections.emptyList();
        }

        return _reservationService.getAllCustomerReservations(getCustomer(customerEmail));
    }

    public Collection<IRoom> findRoom(final Date checkIn, final Date checkOut) {
        return _reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut) {
        return _reservationService.findAlternateRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut,
            final int extendedSearchDays) {
        return _reservationService.findAlternateRooms(checkIn, checkOut, extendedSearchDays);
    }

    public Date getNextAlternateDate(final Date date) {
        return _reservationService.getNextAlternateDate(date);
    }

    public Date getNextAlternateDate(final Date date, final int extendedSearchDays) {
        return _reservationService.getNextAlternateDate(date, extendedSearchDays);
    }

    public Collection<Reservation> getAllReservations() {
        return _reservationService.getAllReservations();
    }
}
