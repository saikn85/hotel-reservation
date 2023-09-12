package api;

import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;
import services.customer.CustomerService;
import services.reservation.ReservationService;
import services.room.RoomService;

import java.util.*;

public class HotelResource {
    // region Static Setup
    private static final int _SEARCH_AHEAD_DAYS = 7;
    private static final HotelResource SINGLETON = new HotelResource();
    // endregion

    // region Services
    private final CustomerService _customerService = CustomerService.getCustomerService();
    private final RoomService _roomService = RoomService.getRoomService();
    private final ReservationService _reservationService = ReservationService.getReservationService();
    // endregion

    // region Constructor
    private HotelResource() {
    }

    public static HotelResource getHotelResource() {
        return SINGLETON;
    }
    // endregion

    // region Public Methods
    public Customer getCustomer(String email) {
        return _customerService.getCustomer(email);
    }

    public void newCustomer(String email, String firstName, String lastName) {
        _customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return _roomService.getARoom(roomNumber);
    }

    public void bookRoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        _reservationService.reserveRoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        final Customer customer = getCustomer(customerEmail);
        if (customer == null) {
            return Collections.emptyList();
        }

        Collection<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : _reservationService.getAllReservations()) {
            if (reservation.getCustomer().equals(customer))
                customerReservations.add(reservation);
        }

        return customerReservations;
    }

    public Collection<IRoom> findRooms(final Date checkIn, final Date checkOut) {
        return _reservationService.findRooms(checkIn, checkOut);
    }

    public Date getNextAlternateDate(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, _SEARCH_AHEAD_DAYS);

        return calendar.getTime();
    }

    public Date getNextAlternateDate(final Date date, final int extendedSearchDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, extendedSearchDays);

        return calendar.getTime();
    }
    // endregion
}
