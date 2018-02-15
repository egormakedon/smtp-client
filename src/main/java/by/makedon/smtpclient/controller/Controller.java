package by.makedon.smtpclient.controller;

import by.makedon.smtpclient.criteria.MailFormCriteria;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.socket.MailSocket;
import by.makedon.smtpclient.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public final class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private static boolean instanceCreated;
    private static final Controller INSTANCE;

    static {
        INSTANCE = new Controller();
        instanceCreated = true;
    }

    private Controller() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone singleton object");
            throw new RuntimeException("try to clone singleton object");
        }
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void sendMessage(Map<MailFormCriteria, String> parameters) throws InvalidParameterException, MailSocketException {
        String smtpServerValue = parameters.get(MailFormCriteria.SMTP_SERVER);
        String fromValue = parameters.get(MailFormCriteria.FROM);
        String toValue = parameters.get(MailFormCriteria.TO);
        String subjectValue = parameters.get(MailFormCriteria.SUBJECT);
        String mailTextValue = parameters.get(MailFormCriteria.MAIL_TEXT);

        if (!Validator.validateOnNullEmpty(smtpServerValue, fromValue, toValue, subjectValue, mailTextValue)) {
            throw new InvalidParameterException("parameters are empty or null");
        }

        if (!Validator.validateEmail(fromValue)) {
            throw new InvalidParameterException("invalid from email");
        }

        if (!Validator.validateTo(toValue)) {
            throw new InvalidParameterException("invalid to emails");
        }

        if (!Validator.validateMailText(mailTextValue)) {
            throw new InvalidParameterException("Dot '.' can't be the one symbol in sentence");
        }

        MailSocket mailSocket = new MailSocket();
        try {

        } finally {
            mailSocket.close();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone singleton object");
        }
        return super.clone();
    }
}
