package services.reservation;

import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;
import services.room.RoomService;

import java.util.*;

public class ReservationService {
    //region Static Setup
    private static final ReservationService _SINGLETON = new ReservationService();
    private final RoomService _roomService = RoomService.getRoomService();
    //endregion

    //region Internal Setup
    private final Set<Reservation> _reservations = new HashSet<>();

    private ReservationService() {
    }

    public static ReservationService getReservationService() {
        return _SINGLETON;
    }
    //endregion

    //region Public Methods
    public void reserveRoom(final Customer customer, final IRoom room, final Date checkInDate,
                            final Date checkOutDate) {
        final Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        _reservations.add(reservation);
    }

    public Collection<Reservation> getAllReservations() {
        return _reservations;
    }

    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
        return getAvailableRooms(checkInDate, checkOutDate);
    }
    //endregion

    //region Private Helpers
    private Collection<IRoom> getAvailableRooms(final Date checkInDate, final Date checkOutDate) {
        Collection<IRoom> notAvailable = new ArrayList<>();
        Collection<IRoom> available = new ArrayList<>();

        for (Reservation reservation : _reservations) {
            if (checkInDate.before(reservation.getCheckOutDate()) &&
                    checkOutDate.after(reservation.getCheckInDate())) {
                notAvailable.add(reservation.getRoom());
            }
        }
        for (IRoom iRoom : _roomService.getAllRooms()) {
            if (!notAvailable.contains(iRoom))
                available.add(iRoom);
        }

        return available;
    }
    //endregion
}