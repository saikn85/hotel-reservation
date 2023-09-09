package common;

public class MenuOptionsHelpers {
    private static String mainMenuOptionHelper = "Welcome to the Hotel Reservation Application";

    public static String getMainMenuOptionHelper() {
        return mainMenuOptionHelper;
    }

    private static int mainMenuOptLength = mainMenuOptionHelper.length();

    public static int getMainMenuOptLength() {
        return mainMenuOptLength;
    }

    private static String selectionOptionHelper = "Please select an option from the above mentioned: ";

    public static String getSelectionOptionHelper() {
        return selectionOptionHelper;
    }

    private static int selectionOptLength = selectionOptionHelper.length();

    public static int getSelectionOptLength() {
        return selectionOptLength;
    }

}
