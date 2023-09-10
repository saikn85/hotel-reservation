import java.util.Scanner;
import common.MainMenOptions;
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
        MainMenOptions userOption = MainMenOptions.EXIT;

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

                        userOption = MainMenOptions.CUSTOMER;
                        break;
                    case 2:
                        userOption = MainMenOptions.ADMIN;
                        break;
                    default:
                        scanner.close();
                        userOption = MainMenOptions.EXIT;
                        System.out.println("Exited");
                        break;
                }
            } catch (NumberFormatException ex) {
                userOption = MainMenOptions.TRYAGAIN;
                System.out.println();
                PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
                System.out.println();
                System.out.println("Invalid option specified, try again.");
                System.out.println();
                PatternPrinter.printStars(MenuOptionsHelpers.getSelectionOptLength());
            } catch (Exception e) {
                System.err.println("Exception Occured :: " + e.getMessage() + " :: " + e.getClass());
                scanner.close();
                return;
            }
        } while (userOption != MainMenOptions.EXIT);

        return;
    }
}
