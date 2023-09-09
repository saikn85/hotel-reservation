package common;

/**
 * Helper for better string formats on Console
 */
public class PatternPrinter {
    private static int count = 20;

    public static void printStars(int times) {
        if (times == 0) {
            System.out.println("*".repeat(count));
        } else {
            System.out.println("*".repeat(times));
        }
    }
}
