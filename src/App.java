import java.util.Scanner;
import common.MenOptions;
import common.PatternPrinter;

public class App {
    public static void main(String[] args) throws Exception {
        runApplication();
    }

    /**
     * Will be the strating point in the application
     */
    private static void runApplication() {
        String line = "";
        Scanner scanner = new Scanner(System.in);
        MenOptions userOption = MenOptions.EXIT;

        try {
            PatternPrinter.printStars();
            PatternPrinter.printStars();
            System.out.println("Welcome to the Hotel Reservation Application");
            PatternPrinter.printStars();
            PatternPrinter.printStars();
            do {
                System.out.println("1. Customer Menu");
                System.out.println("2. Administrative Menu");
                System.out.println("3. Exit");
                System.out.print("Please select an option from the above mentioned: ");
            } while (userOption != MenOptions.EXIT);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
