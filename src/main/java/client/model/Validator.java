package client.model;

import java.util.regex.Pattern;

public class Validator {
    private static final String TO_EMAIL_REGEXP = "([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)" +
            "(,[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+))*)";
    private static final String EMAIL_REGEXP = "^([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+))$";

    public static boolean validateEmail(String emailValue) { return Pattern.matches(EMAIL_REGEXP, emailValue); }
    public static boolean validateToEmail(String to) {
        return Pattern.matches(TO_EMAIL_REGEXP, to);
    }
}