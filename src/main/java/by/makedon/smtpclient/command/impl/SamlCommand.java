package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class SamlCommand implements Command {
    private static final String SAML = "SAML FROM:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
            throw new InvalidParameterException("invalid from email");
        }

        String saml = String.format(SAML, argumentValue);

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(saml);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
