package services.reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;

public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();
    private static final int SEARCH_AHEAD_DAYS = 7;

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService() {
    }

    public static ReservationService getReservationService() {
        return SINGLETON;
    }

    public void addRoom(final IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(final String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveRoom(final Customer customer, final IRoom room, final Date checkInDate,
            final Date checkOutDate) {
        final Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> customerReservations = getAllCustomerReservations(customer);

        if (customerReservations == null) {
            customerReservations = new ArrayList<>();
        }

        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);

        return reservation;
    }

    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
        return findAllAvailableRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom> findAlternateRooms(final Date checkInDate, final Date checkOutDate) {
        return findAllAvailableRooms(addDefaultPlusDays(checkInDate), addDefaultPlusDays(checkOutDate));
    }

    public Collection<IRoom> findAlternateRooms(final Date checkInDate, final Date checkOutDate,
            final int extendedSearchDays) {
        return findAllAvailableRooms(addExtendedSearchDays(checkInDate, extendedSearchDays),
                addExtendedSearchDays(checkOutDate, extendedSearchDays));
    }

    public Collection<Reservation> getAllCustomerReservations(final Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        final Collection<Reservation> reservations = getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    // #region Private Helpers

    private Collection<IRoom> findAllAvailableRooms(final Date checkInDate, final Date checkOutDate) {
        final Collection<Reservation> allReservations = getAllReservations();
        final Collection<IRoom> roomsNotAvailable = new ArrayList<>();
        final Collection<IRoom> roomsAvailable = new ArrayList<>();

        for (Reservation reservation : allReservations) {
            if (checkForReservationOverlaps(reservation, checkInDate, checkOutDate)) {
                roomsNotAvailable.add(reservation.getRoom());
            }
        }

        for (IRoom iRoom : rooms.values()) {
            if (roomsNotAvailable.contains(iRoom)) {
                continue;
            }
            roomsAvailable.add(iRoom);
        }

        return roomsAvailable;
    }

    private Date addDefaultPlusDays(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, SEARCH_AHEAD_DAYS);

        return calendar.getTime();
    }

    private Date addExtendedSearchDays(final Date date, final int extendedSearchDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, extendedSearchDays);

        return calendar.getTime();
    }

    private boolean checkForReservationOverlaps(final Reservation reservation, final Date checkInDate,
            final Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate())
                && checkOutDate.after(reservation.getCheckInDate());
    }

    private Collection<Reservation> getAllReservations() {
        final Collection<Reservation> allReservations = new ArrayList<>();

        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }

        return allReservations;
    }

    // #endregion
}
