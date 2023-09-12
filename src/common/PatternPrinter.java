package common;

/**
 * Helper for better string formats on Console
 */
public class PatternPrinter {
    public static void printStars(int times) {
            System.out.println("*".repeat(times));
    }

    public static void printStars(String pattern, int times) {
        System.out.println(String.format("%s", pattern).repeat(times));
    }
}
