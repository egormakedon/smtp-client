package by.makedon.smtpclient.validator;

import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$";

    public static boolean validateEmail(String emailValue) {
        return Pattern.matches(EMAIL_REGEXP, emailValue);
    }
}
