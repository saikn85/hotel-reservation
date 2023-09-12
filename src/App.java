import common.MainMenuOptions;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        runApplication();
    }

    private static void runApplication() {
        MainMenuOptions userOption;
        HotelMenu customerMenu = new HotelMenu();
        AdministrativeMenu adminMenu = new AdministrativeMenu();

        System.out.println("Welcome to Hotel Reservation Application");
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                try {
                    System.out.println();
                    System.out.println("1.\tCustomer Menu");
                    System.out.println("2.\tAdministrative Menu");
                    System.out.println("3.\tExit");
                    System.out.print("Please select an option from the above (example 1): ");
                    switch (Integer.parseInt(scanner.next())) {
                        case 1 -> userOption = customerMenu.hotelMenuOptions();
                        case 2 -> userOption = adminMenu.adminMenuOptions();
                        case 3 -> userOption = MainMenuOptions.EXIT;
                        default -> {
                            userOption = MainMenuOptions.TRY_AGAIN;
                            System.out.println();
                            System.out.println("Invalid option specified, try again.");
                            System.out.println();
                        }
                    }
                } catch (NumberFormatException ex) {
                    userOption = MainMenuOptions.TRY_AGAIN;
                    System.out.println();
                    System.out.println("Invalid option specified, try again.");
                    System.out.println();
                } catch (Exception e) {
                    System.err.println("Exception Occurred :: " + e.getMessage() + " :: " + e.getClass());
                    return;
                }
            } while (userOption != MainMenuOptions.EXIT);
        }
    }
}