import java.util.Scanner;
import common.MainMenuOptions;
import common.MenuOptionsHelpers;
import common.PatternPrinter;

public class App {
    public static void main(String[] args) throws Exception {
        runApplication();
    }

    /**
     * Will be the strating point in the application
     */
    private static void runApplication() {
        MainMenuOptions userOption = MainMenuOptions.EXIT;

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
                        case 1:
                            userOption = customerMenu.hotelMenuOptions();
                            break;
                        case 2:
                            userOption = adminMenu.adminMenuOptions();
                            break;
                        case 3:
                            scanner.close();
                            userOption = MainMenuOptions.EXIT;
                            System.out.println("Exited");
                            break;
                        default:
                            userOption = MainMenuOptions.TRYAGAIN;
                            System.out.println();
                            PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                            System.out.println();
                            System.out.println("Invalid option specified, try again.");
                            System.out.println();
                            PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                            break;
                    }
                } catch (NumberFormatException ex) {
                    userOption = MainMenuOptions.TRYAGAIN;
                    System.out.println();
                    PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                    System.out.println();
                    System.out.println("Invalid option specified, try again.");
                    System.out.println();
                    PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                } catch (Exception e) {
                    System.err.println("Exception Occured :: " + e.getMessage() + " :: " + e.getClass());
                    return;
                }
            } while (userOption != MainMenuOptions.EXIT);
        }

        return;
    }
}
