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

    private final CustomerService customerService = CustomerService.getCustomerService();
    private final ReservationService reservationService = ReservationService.getReservationService();

    private HotelResource() {
    }

    public static HotelResource getHotelResource() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void newCustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookRoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveRoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        final Customer customer = getCustomer(customerEmail);

        if (customer == null) {
            return Collections.emptyList();
        }

        return reservationService.getAllCustomerReservations(getCustomer(customerEmail));
    }

    public Collection<IRoom> findRoom(final Date checkIn, final Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut) {
        return reservationService.findAlternateRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut,
            final int extendedSearchDays) {
        return reservationService.findAlternateRooms(checkIn, checkOut, extendedSearchDays);
    }

    // public Date addDefaultPlusDays(final Date date) {
    // return reservationService.(date);
    // }
}
