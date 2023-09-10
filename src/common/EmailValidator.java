package common;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^(.+)@(.+).(.+)$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if (!pattern.matcher(email).matches()) {
            return false;
        }

        return true;
    }
}
