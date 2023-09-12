package services.reservation;

import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;

import java.util.*;

public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();
    private static final int SEARCH_AHEAD_DAYS = 7;
    private final Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {
    }

    public static ReservationService getReservationService() {
        return SINGLETON;
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

    public Collection<Reservation> getAllCustomerReservations(final Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<Reservation> getAllReservations() {
        final Collection<Reservation> allReservations = new ArrayList<>();
        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }

        return allReservations;
    }
//
//    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
//        return findAllAvailableRooms(checkInDate, checkOutDate);
//    }
//
//    public Collection<IRoom> findAlternateRooms(final Date checkInDate, final Date checkOutDate) {
//        return findAllAvailableRooms(getNextAlternateDate(checkInDate), getNextAlternateDate(checkOutDate));
//    }
//
//    public Collection<IRoom> findAlternateRooms(final Date checkInDate, final Date checkOutDate,
//                                                final int extendedSearchDays) {
//        return findAllAvailableRooms(getNextAlternateDate(checkInDate, extendedSearchDays),
//                getNextAlternateDate(checkOutDate, extendedSearchDays));
//    }
//
//
//
//    public Date getNextAlternateDate(final Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, SEARCH_AHEAD_DAYS);
//
//        return calendar.getTime();
//    }
//
//    public Date getNextAlternateDate(final Date date, final int extendedSearchDays) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, extendedSearchDays);
//
//        return calendar.getTime();
//    }
//

//
//    // #region Private Helpers
//
//    private Collection<IRoom> findAllAvailableRooms(final Date checkInDate, final Date checkOutDate) {
//        final Collection<Reservation> allReservations = getAllReservations();
//        final Collection<IRoom> roomsNotAvailable = new ArrayList<>();
//        final Collection<IRoom> roomsAvailable = new ArrayList<>();
//
//        for (Reservation reservation : allReservations) {
//            if (checkForReservationOverlaps(reservation, checkInDate, checkOutDate)) {
//                roomsNotAvailable.add(reservation.getRoom());
//            }
//        }
//
//        for (IRoom iRoom : rooms.values()) {
//            if (roomsNotAvailable.contains(iRoom)) {
//                continue;
//            }
//            roomsAvailable.add(iRoom);
//        }
//
//        return roomsAvailable;
//    }
//
//    private boolean checkForReservationOverlaps(final Reservation reservation, final Date checkInDate,
//                                                final Date checkOutDate) {
//        return checkInDate.before(reservation.getCheckOutDate())
//                && checkOutDate.after(reservation.getCheckInDate());
//    }
}