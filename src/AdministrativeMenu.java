import java.util.Collection;
import java.util.Scanner;

import api.AdminResource;
import common.MainMenuOptions;
import common.MenuOptionsHelpers;
import common.PatternPrinter;
import models.customer.Customer;
import models.room.IRoom;
import models.room.Room;
import models.room.enums.RoomType;

public class AdministrativeMenu {
    private static final AdminResource _adminResource = AdminResource.getAdminResource();
    private final Scanner _scanner;

    public AdministrativeMenu(final Scanner scanner) {
        super();
        this._scanner = scanner;
    }

    public MainMenuOptions adminMenuOptions() {
        int userOption = 0;
        do {
            try {
                PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
                System.out.println();
                System.out.println("Administravtive Menu");
                System.out.println();
                PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
                System.out.println();
                System.out.println("1.\tSee all Customers");
                System.out.println("2.\tSee all Rooms");
                System.out.println("3.\tSee all Reservations");
                System.out.println("4.\tAdd a Room");
                System.out.println("5.\tPopulate Data");
                System.out.println("6.\tBack to Main Menu");
                System.out.println("7.\tExit Application");
                System.out.print(MenuOptionsHelpers.getSelectionOptionHelper());
                switch (Integer.parseInt(_scanner.next())) {
                    case 1:
                        userOption = 1;
                        this.listAllCustomers();
                        break;
                    case 2:
                        userOption = 2;
                        this.listAllRooms();
                        break;
                    case 3:
                        userOption = 3;
                        this.listAllReservations();
                        break;
                    case 4:
                        userOption = 4;
                        this.addNewRoom();
                        break;
                    case 5:
                        userOption = 5;
                        this.populateData();
                        break;
                    case 6:
                        userOption = 6;
                        break;
                    case 7:
                        userOption = 7;
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
                userOption = 7; // Break away from Unknown Exception
            }
        } while (userOption != 6 || userOption != 7);

        if (userOption == 6)
            return MainMenuOptions.TRYAGAIN;

        return MainMenuOptions.EXIT;
    }

    // #region listAllCustomers

    private void listAllCustomers() {
        Collection<Customer> customers = _adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println();
            String format = "%" + Customer.getMaxFirstNameLength() + "s | %" + Customer.getMaxLastNameLength()
                    + "s | %s";
            System.out.println(String.format(format, "First Name", "Last Name", "Email"));
            PatternPrinter.printStars(Customer.getMaxCustomerLenght());
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            System.out.println();
            PatternPrinter.printStars(Customer.getMaxCustomerLenght());
            System.out.println();
        }
    }

    // #endregion

    // #region listAllRooms

    private void listAllRooms() {
        Collection<IRoom> rooms = _adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No Rooms found.");
        } else {
            System.out.println();
            String format = "%" + Room.getmaxRoomNumLength() + "s | %6s | %s";
            System.out.println(String.format(format, "Room Type => Room Number", "Cost/Night $", "Beds"));
            PatternPrinter.printStars(Customer.getMaxCustomerLenght());
            for (IRoom room : rooms) {
                System.out.println(room);
            }

            System.out.println();
            PatternPrinter.printStars(Room.getmaxRoomNumLength());
            System.out.println();
        }
    }

    // #endregion

    // #region listAllReservations

    private void listAllReservations() {
        _adminResource.displayAllReservations();
    }

    // #endregion

    // #region addNewRoom

    private void addNewRoom() {
        System.out.println("Enter room number:");
        final String roomNumber = this._scanner.nextLine();

        System.out.println("Enter price per night:");
        final double roomPrice = this.parseRoomPrice();

        System.out.println("Enter room type: 1 for single bed, 2 for double bed:");
        final RoomType roomType = this.parseRoomType();

        final Room room = new Room(roomNumber, roomPrice, roomType);
        _adminResource.addRoom(room);
        System.out.println("Room added successfully!");
    }

    // #region parseRoomPrice

    private double parseRoomPrice() {
        try {
            return Double.parseDouble(this._scanner.nextLine());
        } catch (NumberFormatException exp) {
            System.out.println("Enter a valid double number. " +
                    "Decimal digits must be separated by point (.)");
            return parseRoomPrice();
        }
    }

    // #endregion

    // #region parseRoomType

    private RoomType parseRoomType() {
        try {
            return RoomType.valueOfLabel(this._scanner.nextLine());
        } catch (IllegalArgumentException exp) {
            System.out.println("Invalid room type! Please, choose 1 for single bed or 2 for double bed:");
            return parseRoomType();
        }
    }

    // #endregion

    // #endregion

    // #region populateData

    private void populateData() {
        _adminResource.populateApplicationData();
    }

    // #endregion
}
