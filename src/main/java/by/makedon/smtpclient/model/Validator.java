package by.makedon.smtpclient.model;

import java.util.regex.Pattern;

public class Validator {
    private static final String TO_EMAIL_REGEXP = "([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)" +
            "(,[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+))*)";
    private static final String EMAIL_REGEXP = "^([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+))$";

    public static boolean validateOnNullEmpty(String ... parameters) {
        for (String parameter : parameters) {
            if (parameter == null || parameter.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    public static boolean validateEmail(String emailValue) { return Pattern.matches(EMAIL_REGEXP, emailValue); }
    public static boolean validateToEmail(String to) {
        return Pattern.matches(TO_EMAIL_REGEXP, to);
    }
    public static boolean validateMailText(String mailText) {
        for (String s : mailText.split("\n")) {
            if (s.equals(".")) {
                return false;
            }
        }
        return true;
    }
}