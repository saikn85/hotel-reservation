import api.HotelResource;
import common.MainMenuOptions;
import common.PatternPrinter;
import models.reservation.Reservation;
import models.room.IRoom;
import models.room.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class HotelMenu {
    //region Base Setup
    private static final HotelResource _hotelResource = HotelResource.getHotelResource();

    public HotelMenu() {
        super();
    }

    public MainMenuOptions hotelMenuOptions() {
        int userOption = 0;
        final Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println();
                System.out.println("Hotel Services Menu");
                System.out.println();
                System.out.println("1.\tFind and reserve a room");
                System.out.println("2.\tSee my reservations");
                System.out.println("3.\tCreate an Account");
                System.out.println("4.\tBack to Main Menu");
                System.out.println("5.\tExit Application");
                System.out.println();
                System.out.print("Please select an option from the above (example 1): ");
                switch (Integer.parseInt(scanner.next())) {
                    case 1 -> {
                        userOption = 1;
                        this.findRooms(scanner);
                    }
                    case 2 -> {
                        userOption = 2;
                        this.getMyReservations(scanner);
                    }
                    case 3 -> {
                        userOption = 3;
                        this.createUserAccount(scanner);
                    }
                    case 4 -> userOption = 4;
                    case 5 -> userOption = 5;
                    default -> {
                        System.out.println();
                        System.out.println("Invalid option specified, try again.");
                        System.out.println();
                    }
                }
            } catch (NumberFormatException ex) {
                userOption = 0;
                System.out.println();
                System.out.println("Invalid option specified, try again.");
                System.out.println();
            } catch (Exception e) {
                System.err.println("Exception Occurred :: " + e.getMessage() + " :: " + e.getClass());
                userOption = 5; // Break away from Unknown Exception
            }
        } while (userOption != 4 && userOption != 5);

        if (userOption == 4)
            return MainMenuOptions.TRY_AGAIN;

        return MainMenuOptions.EXIT;
    }
    //endregion

    //region Find Rooms
    private void findRooms(final Scanner scanner) {
        System.out.println();
        System.out.print("Do you have an account with us (y/n)? ");
        final String accResp = scanner.next();

        // When the user has account
        if (accResp.equalsIgnoreCase("y")) {
            System.out.println();
            System.out.print("Enter Email format: name@domain.com: ");
            final String customerEmail = scanner.next();

            System.out.println();
            System.out.print("Enter Check-In Date mm/dd/yyyy example 02/01/2020: ");
            final Date checkIn = this.getInputDate(scanner);

            System.out.println();
            System.out.print("Enter Check-Out Date mm/dd/yyyy example 02/21/2020: ");
            final Date checkOut = this.getInputDate(scanner);

            if (checkIn != null && checkOut != null) {
                Collection<IRoom> availableRooms = _hotelResource.findRoom(checkIn, checkOut);
                if (availableRooms.isEmpty()) {
                    processAlternateLogic(scanner, customerEmail, checkIn, checkOut);
                } else {
                    processReservationLogic(scanner, checkIn, checkOut, customerEmail,availableRooms);
                }
            } else {
                System.out.println();
                System.out.println("Failed to parse dates, retry!");
                findRooms(scanner);
            }
        }

        // When they don't have an account
        if (accResp.equalsIgnoreCase("n")) {
            System.out.println();
            System.out.print("Do you wish to create one (y/n)? ");
            final String accCreateResp = scanner.next();

            if (accCreateResp.equalsIgnoreCase("y"))
                createUserAccount(scanner);

            return;
        }

        System.out.println();
        System.out.print("Invalid Option, retry!");
        findRooms(scanner);
    }

    private void processAlternateLogic(final Scanner scanner, final String customerEmail, final Date checkIn,
                                       final Date checkOut) {
        Collection<IRoom> alternativeRooms;
        System.out.println();
        System.out.print("Do you wish to change the default extended search? (y/n)");
        final String extendSearchResp = scanner.next();

        if (extendSearchResp.equalsIgnoreCase("n")) {
            final int days = getInputDays(scanner);
            final Date newCheckIn = _hotelResource.getNextAlternateDate(checkIn, days);
            final Date newCheckOut = _hotelResource.getNextAlternateDate(checkOut, days);
            alternativeRooms = _hotelResource.findRoom(newCheckIn, newCheckOut);
            processReservationLogic(scanner, newCheckIn, newCheckOut, customerEmail, alternativeRooms);
        }

        final Date newCheckIn = _hotelResource.getNextAlternateDate(checkIn);
        final Date newCheckOut = _hotelResource.getNextAlternateDate(checkOut);
        alternativeRooms = _hotelResource.findRoom(newCheckIn, newCheckOut);
        processReservationLogic(scanner, newCheckIn, newCheckOut, customerEmail, alternativeRooms);
    }

    private void processReservationLogic(final Scanner scanner, final Date checkIn, final Date checkOut,
                                         final String customerEmail, final Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println();
            String msg = String.format("No Rooms are available between %s & %s", checkIn, checkOut);
            System.out.println(msg);
            return;
        }

        System.out.println();
        String msg = String.format("Rooms are available between %s & %s", checkIn, checkOut);
        System.out.println(msg);

        System.out.println();
        String roomFmt = String.format(Room.getRoomFormat(), "Room Type", "Room Number", "Cost/Night $", "Beds");
        System.out.println(roomFmt);
        PatternPrinter.printPattern(Room.getMaxRoomLength());
        for (IRoom room : rooms) {
            System.out.println(room);
        }

        System.out.println();
        PatternPrinter.printPattern(Room.getMaxRoomLength());
        System.out.println();

        System.out.print("Would you like to book a Room? y/n: ");
        final String bookRoom = scanner.next();

        if (bookRoom.equalsIgnoreCase("n"))
            return;

        System.out.println();
        System.out.print("\nEnter Room Number that you wish to book: ");
        final String userInRoomNumber = scanner.next();

        boolean foundUserSpecifiedRoom = false;
        for (IRoom iRoom : rooms) {
            if (iRoom.getRoomNumber().equals(userInRoomNumber)) {
                foundUserSpecifiedRoom = true;
                break;
            }
        }

        if (foundUserSpecifiedRoom) {
            final IRoom room = _hotelResource.getRoom(userInRoomNumber);
            _hotelResource.bookRoom(customerEmail, room, checkIn, checkOut);

            System.out.println();
            System.out.println("Reservation created successfully!");
        }
    }
    //endregion

    //region Get User Reservation
    private void getMyReservations(final Scanner scanner) {
        System.out.println();
        System.out.print("Enter your Email format: name@domain.com: ");
        final String customerEmail = scanner.next();
        final Collection<Reservation> reservations = _hotelResource.getCustomersReservations(customerEmail);

        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println();
            System.out.printf((Reservation.getReservationFormat()) + "%n", "Customer Details", "Room Details",
                    "Check-In Date", "Check-Out Date");
            PatternPrinter.printPattern(Reservation.getMaxReservationLength());
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }

            System.out.println();
            PatternPrinter.printPattern(Reservation.getMaxReservationLength());
            System.out.println();
        }
    }
    //endregion

    //region Create User Account
    private void createUserAccount(Scanner scanner) {
        System.out.println();
        System.out.print("Enter Email format: name@domain.com: ");
        final String email = scanner.next();

        System.out.print("First Name: ");
        final String firstName = scanner.next();

        System.out.print("Last Name: ");
        final String lastName = scanner.next();

        try {
            _hotelResource.newCustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            this.createUserAccount(scanner);
        }
    }
    //endregion

    //region Private Helper
    private Date getInputDate(final Scanner scanner) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(scanner.next());
        } catch (ParseException ex) {
            return null;
        }
    }

    private int getInputDays(final Scanner scanner) {
        int days = 0;
        System.out.println();
        System.out.print("Enter the days to search ahead (must be > 0) (example: 5): ");
        final String strDays = scanner.next();
        try {
            days = Integer.parseInt(strDays);
            if (days == 0)
                getInputDays(scanner);
        } catch (NumberFormatException ex) {
            System.out.println();
            System.out.print("Invalid number for days, retry!");
            getInputDays(scanner);
        }

        return days;
    }
    //endregion
}