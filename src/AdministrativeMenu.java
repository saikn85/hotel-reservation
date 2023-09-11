import java.util.Collection;
import java.util.Scanner;

import api.AdminResource;
import common.MainMenuOptions;
import common.MenuOptionsHelpers;
import common.PatternPrinter;
import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;
import models.room.Room;
import models.room.enums.RoomType;

public class AdministrativeMenu {
    private static final AdminResource _adminResource = AdminResource.getAdminResource();

    public AdministrativeMenu() {
        super();

    }

    public MainMenuOptions adminMenuOptions() {
        int userOption = 0;
        try (Scanner scanner = new Scanner(System.in)) {
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
                    switch (Integer.parseInt(scanner.next())) {
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
                    System.err.println(e.getLocalizedMessage());
                    userOption = 7; // Break away from Unknown Exception
                }
            } while (userOption != 6 && userOption != 7);
        }

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
            System.out.println(String.format(Customer.getCustomerFormat(), "First Name", "Last Name", "Email"));
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
            System.out.println(String.format(Room.getRoomFormat(), "Room Type => Room Number", "Cost/Night $", "Beds"));
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

    // #region listAllReservations

    private void listAllReservations() {
        Collection<Reservation> reservations = _adminResource.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No Reservations found.");
        } else {
            System.out.println();
            System.out.println(String.format(Reservation.getReservationFormat(), "Customer Details",
                    "Room Details", "Check-In Date", "Check-Out Date"));
            PatternPrinter.printStars(Reservation.getMaxReservationLenght());
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }

            System.out.println();
            PatternPrinter.printStars(Room.getMaxRoomLength());
            System.out.println();
        }
    }

    // #endregion

    // #region addNewRoom

    private void addNewRoom() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter room number: ");
            final String roomNumber = scanner.next();

            System.out.print("\nEnter price per night: ");
            final double roomPrice = this.parseRoomPrice(scanner);

            System.out.print("\nEnter room type: 1 for single bed, 2 for double bed: ");
            final RoomType roomType = this.parseRoomType(scanner);

            final Room room = new Room(roomNumber, roomPrice, roomType);
            _adminResource.addRoom(room);
            System.out.println();
            System.out.println("Room added successfully!");
        }
    }

    // #region parseRoomPrice

    private double parseRoomPrice(final Scanner scanner) {
        try {
            return Double.parseDouble(scanner.next());
        } catch (NumberFormatException exp) {
            System.out.println("Enter a valid double number. " +
                    "Decimal digits must be separated by point (.)");
            return parseRoomPrice(scanner);
        }
    }

    // #endregion

    // #region parseRoomType

    private RoomType parseRoomType(final Scanner scanner) {
        try {
            return RoomType.valueOfLabel(scanner.next());
        } catch (IllegalArgumentException exp) {
            System.out.println("Invalid room type! Please, choose 1 for single bed or 2 for double bed:");
            return parseRoomType(scanner);
        }
    }

    // #endregion

    // #endregion

    // #region populateData

    private void populateData() {
        _adminResource.populateApplicationData();
        System.out.println();
        PatternPrinter.printStars(Customer.getMaxCustomerLenght());
        System.out.println();
        System.out.println("Data has been populated");
        System.out.println();
        PatternPrinter.printStars(Customer.getMaxCustomerLenght());
        System.out.println();
    }

    // #endregion
}
