import api.AdminResource;
import common.MainMenuOptions;
import common.PatternPrinter;
import models.customer.Customer;
import models.reservation.Reservation;
import models.room.IRoom;
import models.room.Room;
import models.room.enums.RoomType;

import java.util.Collection;
import java.util.Scanner;

public class AdministrativeMenu {
    private static final AdminResource _adminResource = AdminResource.getAdminResource();
    private boolean _isDataPopulated;

    public AdministrativeMenu() {
        super();
        this._isDataPopulated = false;
    }

    public MainMenuOptions adminMenuOptions() {
        int userOption = 0;
        final Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println();
                System.out.println("Administrative Menu");
                System.out.println();
                System.out.println("1.\tSee all Customers");
                System.out.println("2.\tSee all Rooms");
                System.out.println("3.\tSee all Reservations");
                System.out.println("4.\tAdd a Room");
                System.out.println("5.\tPopulate Data");
                System.out.println("6.\tBack to Main Menu");
                System.out.println("7.\tExit Application");
                System.out.print("Please select an option from the above (example 1): ");
                switch (Integer.parseInt(scanner.next())) {
                    case 1 -> {
                        userOption = 1;
                        this.listAllCustomers();
                    }
                    case 2 -> {
                        userOption = 2;
                        this.listAllRooms();
                    }
                    case 3 -> {
                        userOption = 3;
                        this.listAllReservations();
                    }
                    case 4 -> {
                        userOption = 4;
                        this.addNewRoom(scanner);
                    }
                    case 5 -> {
                        userOption = 5;
                        this.populateData();
                    }
                    case 6 -> userOption = 6;
                    case 7 -> userOption = 7;
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
                System.err.println(e);
                userOption = 7; // Break away from Unknown Exception
            }
        } while (userOption != 6 && userOption != 7);

        if (userOption == 6)
            return MainMenuOptions.TRY_AGAIN;

        return MainMenuOptions.EXIT;
    }

    private void listAllCustomers() {
        Collection<Customer> customers = _adminResource.getAllCustomers();
        System.out.println();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            String format = String.format(Customer.getCustomerFormat(), "First Name", "Last Name", "Email");
            System.out.println(format);
            PatternPrinter.printPattern("_", Customer.getMaxCustomerLength());
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            System.out.println();
            PatternPrinter.printPattern("_",Customer.getMaxCustomerLength());
            System.out.println();
        }
    }

    private void listAllRooms() {
        Collection<IRoom> rooms = _adminResource.getAllRooms();
        System.out.println();
        if (rooms.isEmpty()) {
            System.out.println("No Rooms found.");
        } else {
            String format = String.format(Room.getRoomFormat(), "Room Type", "Room Number", "Cost/Night $", "Beds");
            System.out.println(format);
            PatternPrinter.printPattern("_", Room.getMaxRoomLength());
            for (IRoom room : rooms) {
                System.out.println(room);
            }

            PatternPrinter.printPattern("_", Room.getMaxRoomLength());
            System.out.println();
        }
    }

    private void listAllReservations() {
        Collection<Reservation> reservations = _adminResource.getAllReservations();
        System.out.println();
        if (reservations.isEmpty()) {
            System.out.println("No Reservations found.");
        } else {
            String format = String.format(Reservation.getReservationFormat(), "Customer Details",
                    "Room Details", "Check-In Date", "Check-Out Date");
            System.out.println(format);
            PatternPrinter.printPattern("_", Reservation.getMaxReservationLength());
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }

            PatternPrinter.printPattern("_", Reservation.getMaxReservationLength());
            System.out.println();
        }
    }

    private void addNewRoom(final Scanner scanner) {
        System.out.println();
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

    private double parseRoomPrice(final Scanner scanner) {
        try {
            return Double.parseDouble(scanner.next());
        } catch (NumberFormatException exp) {
            System.out.println("Enter a valid double number. " +
                    "Decimal digits must be separated by point (.)");
            return parseRoomPrice(scanner);
        }
    }

    private RoomType parseRoomType(final Scanner scanner) {
        try {
            return RoomType.getRoomTypeLabel(scanner.next());
        } catch (IllegalArgumentException exp) {
            System.out.print("Invalid room type! Please, choose 1 for single bed or 2 for double bed: ");
            return parseRoomType(scanner);
        }
    }

    private void populateData() {
        if (!this._isDataPopulated) {
            this._isDataPopulated = true;
            _adminResource.populateApplicationData();
            System.out.println();
            System.out.println("Data has been populated.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Data has already been populated!");
            System.out.println();
        }
    }
}