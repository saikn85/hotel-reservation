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
import models.room.Room;

public class HotelMenu {
    private static final HotelResource _hotelResource = HotelResource.getHotelResource();

    public HotelMenu() {
        super();
    }

    public MainMenuOptions hotelMenuOptions() {
        int userOption = 0;
        final Scanner scanner = new Scanner(System.in);
        do {
            try {
                PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
                System.out.println();
                System.out.println("Hotel Services Menu");
                System.out.println();
                PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
                System.out.println();
                System.out.println("1.\tFind and reserve a room");
                System.out.println("2.\tSee my reservations");
                System.out.println("3.\tCreate an Account");
                System.out.println("4.\tBack to Main Menu");
                System.out.println("5.\tExit Application");
                System.out.println();
                System.out.print(MenuOptionsHelpers.getSelectionOptionHelper());
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        userOption = 1;
                        this.findRooms(scanner);
                        break;
                    case 2:
                        userOption = 2;
                        this.getMyReservations(scanner);
                        break;
                    case 3:
                        userOption = 3;
                        this.createUserAccount(scanner);
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
        } while (userOption != 4 && userOption != 5);

        if (userOption == 4)
            return MainMenuOptions.TRYAGAIN;

        return MainMenuOptions.EXIT;
    }

    // #region findRooms

    private void findRooms(final Scanner scanner) {
        System.out.println();

        System.out.print("Enter Check-In Date mm/dd/yyyy example 02/01/2020: ");
        Date checkIn = this.getInputDate(scanner);

        System.out.print("\nEnter Check-Out Date mm/dd/yyyy example 02/21/2020: ");
        Date checkOut = this.getInputDate(scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = _hotelResource.findRoom(checkIn, checkOut);
            if (availableRooms.isEmpty()) {
                System.out.println();
                System.out.println("Searching for Alternative rooms... ");
                Collection<IRoom> alternativeRooms = _hotelResource.findAlternativeRooms(checkIn, checkOut);
                if (alternativeRooms.isEmpty()) {
                    this.searchAheadDays(scanner, checkIn, checkOut);
                } else {
                    final Date alternativeCheckIn = _hotelResource.getNextAlternateDate(checkIn);
                    final Date alternativeCheckOut = _hotelResource.getNextAlternateDate(checkOut);
                    System.out.println();
                    System.out.println("We've only found rooms on alternative dates:" +
                            "\nCheck-In Date:" + alternativeCheckIn +
                            "\nCheck-Out Date:" + alternativeCheckOut);

                    this.showRooms(alternativeRooms);
                    this.makeReservation(scanner, alternativeCheckIn, alternativeCheckOut, alternativeRooms);
                }
            } else {
                this.showRooms(availableRooms);
                this.makeReservation(scanner, checkIn, checkOut, availableRooms);
            }
        }
    }

    // #region searchAheadDays

    private void searchAheadDays(final Scanner scanner, final Date checkIn, final Date checkOut) {
        System.out.println();
        System.out.println("Alternate rooms were not found...");
        System.out.println();
        System.out.print("Would you like to extend the serach? y/n: ");
        final String search = scanner.next();

        if ("y".equals(search.toLowerCase())) {
            System.out.print("\nEnter the number of look ahead days: ");
            final String searchDays = scanner.next();
            try {
                int days = Integer.parseInt(searchDays);
                Collection<IRoom> alternativeRooms = _hotelResource.findAlternativeRooms(checkIn, checkOut, days);

                if (alternativeRooms.isEmpty()) {
                    System.out.println();
                    System.out.println("No rooms were found.");
                } else {
                    final Date alternativeCheckIn = _hotelResource.getNextAlternateDate(checkIn, days);
                    final Date alternativeCheckOut = _hotelResource.getNextAlternateDate(checkOut, days);
                    System.out.println();
                    System.out.println("We've only found rooms on alternative dates:" +
                            "\nCheck-In Date:" + alternativeCheckIn +
                            "\nCheck-Out Date:" + alternativeCheckOut);

                    this.showRooms(alternativeRooms);
                    this.makeReservation(scanner, alternativeCheckIn, alternativeCheckOut, alternativeRooms);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid entry, try again.");
                this.searchAheadDays(scanner, checkIn, checkOut);
            } catch (Exception ex) {
                System.out.println("Oops! something went wrong.");
                return;
            }
        }

        return;
    }

    // #endregion

    // #region makeReservation

    private void makeReservation(final Scanner scanner, final Date checkInDate, final Date checkOutDate,
            final Collection<IRoom> rooms) {
        System.out.println();
        System.out.print("Would you like to book a Room? y/n: ");
        final String bookRoom = scanner.next();

        if ("y".equals(bookRoom.toLowerCase())) {
            System.out.print("\nDo you have an account with us? y/n: ");
            final String haveAccount = scanner.next();

            if ("y".equals(haveAccount.toLowerCase())) {
                System.out.print("\nEnter Email format: name@domain.com: ");
                final String customerEmail = scanner.next();

                if (_hotelResource.getCustomer(customerEmail) == null) {
                    System.out.println();
                    System.out.println("Customer not found. You may need to create a new account.");
                } else {
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
                        _hotelResource.bookRoom(customerEmail, room, checkInDate, checkOutDate);

                        System.out.println();
                        System.out.println("Reservation created successfully!");
                    } else {
                        System.out.println("Error: room number not available. Start reservation again.");
                    }
                }
            } else {
                System.out.println("Please, create an account.");
            }
        } else if ("n".equals(bookRoom.toLowerCase())) {
            return;
        } else {
            this.makeReservation(scanner, checkInDate, checkOutDate, rooms);
        }
    }

    // #endregion

    // #region showRooms

    private void showRooms(final Collection<IRoom> rooms) {
        System.out.println();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println();
            System.out.println(String.format(Room.getRoomFormat(), "Room Type", "Room Number", "Cost/Night $", "Beds"));
            PatternPrinter.printStars(Room.getMaxRoomLength());
            for (IRoom room : rooms) {
                System.out.println(room);
            }

            System.out.println();
            PatternPrinter.printStars(Room.getMaxRoomLength());
            System.out.println();
        }
    }

    // #endregion

    // #endregion

    // #region getMyReservations

    private void getMyReservations(final Scanner scanner) {
        System.out.println();
        System.out.print("Enter your Email format: name@domain.com: ");
        final String customerEmail = scanner.next();
        final Collection<Reservation> reservations = _hotelResource.getCustomersReservations(customerEmail);

        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    // #endregion

    // #region createUserAccount

    private void createUserAccount(Scanner scanner) {
        System.out.println();
        System.out.print("Enter Email format: name@domain.com: ");
        final String email = scanner.next();

        System.out.println("First Name:");
        final String firstName = scanner.nextLine();

        System.out.println("Last Name:");
        final String lastName = scanner.nextLine();

        try {
            _hotelResource.newCustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            this.createUserAccount(scanner);
        }
    }

    // #endregion

    // #region getInputDate

    private Date getInputDate(final Scanner scanner) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(scanner.next());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date.");
            this.findRooms(scanner);
        }

        return null;
    }

    // #endregion
}
