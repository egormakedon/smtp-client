package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class RcptCommand implements Command {
    private static final String RCPT = "RCPT TO:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String toValue = parameters.get(ParameterCriteria.TO);
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        String rcpt;
        if (toValue != null) {
            if (!Validator.validateEmail(toValue)) {
                throw new InvalidParameterException("invalid email");
            }
            rcpt = String.format(RCPT, toValue);

        } else {
            if (!Validator.validateEmail(argumentValue)) {
                throw new InvalidParameterException("invalid email");
            }
            rcpt = String.format(RCPT, argumentValue);
        }

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(rcpt);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}