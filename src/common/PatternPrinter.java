package common;

/**
 * Helper for better string formats on Console
 */
public class PatternPrinter {
    public static void printPattern(int times) {
            System.out.println("*".repeat(times));
    }

    public static void printPattern(String pattern, int times) {
        System.out.println(String.format("%s", pattern).repeat(times));
    }
}
