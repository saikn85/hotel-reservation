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

        try {
            PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
            PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
            System.out.println(MenuOptionsHelpers.getMainMenuOptionHelper());
            PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
            PatternPrinter.printStars(MenuOptionsHelpers.getMainMenuOptLength());
            do {
                System.out.println("1.\tCustomer Menu");
                System.out.println("2.\tAdministrative Menu");
                System.out.println("3.\tExit");
                System.out.print(MenuOptionsHelpers.getSelectionOptionHelper());
                String selectedOption = scanner.next();

            } while (userOption != MenOptions.EXIT);
        } catch (Exception e) {
            System.err.println("Exception Occured :: " + e.getMessage() + " :: " + e.getClass());
            scanner.close();
            return;
        }
    }
}
