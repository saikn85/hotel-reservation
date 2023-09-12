import java.util.Scanner;
import common.MainMenuOptions;
import common.PatternPrinter;

public class App {
    public static void main(String[] args) {
        runApplication();
    }

    /**
     * Will be the starting point in the application
     */
    private static void runApplication() {
        MainMenuOptions userOption;

        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
        System.out.println(MenuOptionsHelpers.getMainMenuOptionHelper());
        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());

        HotelMenu customerMenu = new HotelMenu();
        AdministrativeMenu adminMenu = new AdministrativeMenu();

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                try {
                    System.out.println();
                    System.out.println("1.\tCustomer Menu");
                    System.out.println("2.\tAdministrative Menu");
                    System.out.println("3.\tExit");
                    System.out.print(MenuOptionsHelpers.getSelectionOptionHelper());
                    switch (Integer.parseInt(scanner.next())) {
                        case 1 -> userOption = customerMenu.hotelMenuOptions();
                        case 2 -> userOption = adminMenu.adminMenuOptions();
                        case 3 -> {
                            scanner.close();
                            userOption = MainMenuOptions.EXIT;
                            System.out.println("Exited");
                        }
                        default -> {
                            userOption = MainMenuOptions.TRY_AGAIN;
                            System.out.println();
                            PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                            System.out.println();
                            System.out.println("Invalid option specified, try again.");
                            System.out.println();
                            PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                        }
                    }
                } catch (NumberFormatException ex) {
                    userOption = MainMenuOptions.TRY_AGAIN;
                    System.out.println();
                    PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                    System.out.println();
                    System.out.println("Invalid option specified, try again.");
                    System.out.println();
                    PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                } catch (Exception e) {
                    System.err.println("Exception Occurred :: " + e.getMessage() + " :: " + e.getClass());
                    return;
                }
            } while (userOption != MainMenuOptions.EXIT);
        }
    }
}