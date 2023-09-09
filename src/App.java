import java.util.Scanner;
import common.MenOptions;
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
        Scanner scanner = new Scanner(System.in);
        MenOptions userOption = MenOptions.EXIT;

        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
        System.out.println(MenuOptionsHelpers.getMainMenuOptionHelper());
        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
        PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());

        do {
            try {
                System.out.println();
                System.out.println("1.\tCustomer Menu");
                System.out.println("2.\tAdministrative Menu");
                System.out.println("3.\tExit");
                System.out.print(MenuOptionsHelpers.getSelectionOptionHelper());
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        System.out.println("Customer Menu");
                        userOption = MenOptions.CUSTOMER;
                        break;
                    case 2:
                        System.out.println("Admin Menu");
                        userOption = MenOptions.ADMIN;
                        break;
                    default:
                        System.out.println("exiting");
                        userOption = MenOptions.EXIT;
                        break;
                }
            } catch (NumberFormatException ex) {
                userOption = MenOptions.TRYAGAIN;
                System.out.println(MenuOptionsHelpers.getSelectionOptLength());
                System.out.println("Invalid Option Specified, try again.");
                System.out.println(MenuOptionsHelpers.getSelectionOptLength());
            } catch (Exception e) {
                System.err.println("Exception Occured :: " + e.getMessage() + " :: " + e.getClass());
                scanner.close();
                return;
            }
        } while (userOption != MenOptions.EXIT);
    }
}
