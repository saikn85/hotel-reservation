import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import api.HotelResource;
import common.MainMenuOptions;
import common.MenuOptionsHelpers;
import common.PatternPrinter;
import models.reservation.Reservation;
import models.room.IRoom;

public class HotelMenu {

    private static final HotelResource _hotelResource = HotelResource.getHotelResource();
    private final Scanner _scanner;

    public HotelMenu(final Scanner scanner) {
        super();
        this._scanner = scanner;
    }

    public MainMenuOptions hotelMenuOptions() {
        int userOption = 0;
        do {
            try {
                System.out.println();
                System.out.println("1.\tFind and reserve a room");
                System.out.println("2.\tSee my reservations");
                System.out.println("3.\tCreate an Account");
                System.out.println("4.\tBack to Main Menu");
                System.out.println("5.\tExit Application");
                System.out.print(MenuOptionsHelpers.getSelectionOptionHelper());
                switch (Integer.parseInt(this._scanner.next())) {
                    case 1:
                        userOption = 1;
                        this.findRooms();
                        break;
                    case 2:
                        userOption = 2;
                        this.getMyReservations();
                        break;
                    case 3:
                        userOption = 3;
                        this.createUserAccount();
                        break;
                    case 4:
                        userOption = 4;
                        break;
                    case 5:
                        userOption = 5;
                        break;
                    default:
                        System.out.println();
                        PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                        System.out.println();
                        System.out.println("Invalid option specified, try again.");
                        System.out.println();
                        PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                        break;
                }
            } catch (NumberFormatException ex) {
                userOption = 0;
                System.out.println();
                PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                System.out.println();
                System.out.println("Invalid option specified, try again.");
                System.out.println();
                PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
            } catch (Exception e) {
                System.err.println("Exception Occured :: " + e.getMessage() + " :: " + e.getClass());
                userOption = 5; // Break away from Unknown Exception
            }
        } while (userOption != 4 || userOption != 5);

        if (userOption == 4)
            return MainMenuOptions.TRYAGAIN;

        return MainMenuOptions.EXIT;
    }

    // #region findRooms

    private void findRooms() {
        System.out.print("Enter Check-In Date mm/dd/yyyy example 02/01/2020");
        Date checkIn = this.getInputDate(this._scanner);

        System.out.print("Enter Check-Out Date mm/dd/yyyy example 02/21/2020");
        Date checkOut = this.getInputDate(this._scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = _hotelResource.findRoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                Collection<IRoom> alternativeRooms = _hotelResource.findAlternativeRooms(checkIn, checkOut);
                if (alternativeRooms.isEmpty()) {
                    this.searchAheadDays(checkIn, checkOut);
                } else {
                    final Date alternativeCheckIn = _hotelResource.getNextAlternateDate(checkIn);
                    final Date alternativeCheckOut = _hotelResource.getNextAlternateDate(checkOut);
                    System.out.println("We've only found rooms on alternative dates:" +
                            "\nCheck-In Date:" + alternativeCheckIn +
                            "\nCheck-Out Date:" + alternativeCheckOut);

                    this.showRooms(alternativeRooms);
                    this.makeReservation(alternativeCheckIn, alternativeCheckOut, alternativeRooms);
                }
            } else {
                this.showRooms(availableRooms);
                this.makeReservation(checkIn, checkOut, availableRooms);
            }
        }
    }

    // #region makeReservation

    private void makeReservation(final Date checkInDate, final Date checkOutDate,
            final Collection<IRoom> rooms) {
        System.out.println("Would you like to book? y/n");
        final String bookRoom = this._scanner.nextLine();

        if ("y".equals(bookRoom.toLowerCase())) {
            System.out.println("Do you have an account with us? y/n");
            final String haveAccount = this._scanner.nextLine();

            if ("y".equals(haveAccount.toLowerCase())) {
                System.out.println("Enter Email format: name@domain.com");
                final String customerEmail = this._scanner.nextLine();

                if (_hotelResource.getCustomer(customerEmail) == null) {
                    System.out.println("Customer not found.\nYou may need to create a new account.");
                } else {
                    System.out.println("What room number would you like to reserve?");
                    final String roomNumber = this._scanner.nextLine();
                    boolean foundUserSpecifiedRoom = false;
                    for (IRoom iRoom : rooms) {
                        if (iRoom.getRoomNumber() == roomNumber) {
                            foundUserSpecifiedRoom = true;
                            break;
                        }
                    }

                    if (foundUserSpecifiedRoom) {
                        final IRoom room = _hotelResource.getRoom(roomNumber);
                        final Reservation reservation = _hotelResource
                                .bookRoom(customerEmail, room, checkInDate, checkOutDate);

                        System.out.println("Reservation created successfully!");
                        System.out.println(reservation);
                    } else {
                        System.out.println("Error: room number not available.\nStart reservation again.");
                    }
                }
            } else {
                System.out.println("Please, create an account.");
            }
        }
    }

    // #endregion

    // #region showRooms

    private void showRooms(final Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    // #endregion

    // #region searchAheadDays

    private void searchAheadDays(final Date checkIn, final Date checkOut) {

    }

    // #endregion

    // #endregion

    // #region getMyReservations

    private void getMyReservations() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your Email format: name@domain.com");
            final String customerEmail = scanner.nextLine();
            final Collection<Reservation> reservations = _hotelResource.getCustomersReservations(customerEmail);

            if (reservations == null || reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        }
    }

    // #endregion

    // #region createUserAccount

    private void createUserAccount() {
        System.out.println("Enter Email format: name@domain.com");
        final String email = this._scanner.nextLine();

        System.out.println("First Name:");
        final String firstName = this._scanner.nextLine();

        System.out.println("Last Name:");
        final String lastName = this._scanner.nextLine();

        try {
            _hotelResource.newCustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            this.createUserAccount();
        }
    }

    // #endregion

    // #region getInputDate

    private Date getInputDate(final Scanner scanner) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(scanner.next());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date.");
            this.findRooms();
        }

        return null;
    }

    // #endregion
}
