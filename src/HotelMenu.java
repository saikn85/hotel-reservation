import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import api.HotelResource;
import common.MainMenuOptions;
import common.MenuOptionsHelpers;
import common.PatternPrinter;
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
                switch (Integer.parseInt(_scanner.next())) {
                    case 1:
                        this.findRooms();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                    case 5:
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

    // #region Private Helpers

    // #region findRooms

    private void findRooms() {
        System.out.print("Enter Check-In Date mm/dd/yyyy example 02/01/2020");
        Date checkIn = this.getInputDate(_scanner);

        System.out.print("Enter Check-Out Date mm/dd/yyyy example 02/21/2020");
        Date checkOut = this.getInputDate(_scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = _hotelResource.findRoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                Collection<IRoom> alternativeRooms = _hotelResource.findAlternativeRooms(checkIn, checkOut);
                if (alternativeRooms.isEmpty()) {
                    this.searchAheadDays();
                } else {
                    final Date alternativeCheckIn = _hotelResource.getNextAlternateDate(checkIn);
                    final Date alternativeCheckOut = _hotelResource.getNextAlternateDate(checkOut);
                    System.out.println("We've only found rooms on alternative dates:" +
                            "\nCheck-In Date:" + alternativeCheckIn +
                            "\nCheck-Out Date:" + alternativeCheckOut);

                    showRooms(alternativeRooms);
                    reserveRoom(scanner, alternativeCheckIn, alternativeCheckOut, alternativeRooms);
                }
            } else {
                showRooms(availableRooms);
                reserveRoom(scanner, checkIn, checkOut, availableRooms);
            }
        }
    }

    // #endregion

    // #region searchAheadDays

    private void searchAheadDays() {

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

    // #region showRooms

    // #endregion

    // #endregion
}
