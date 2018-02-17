package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class SendCommand implements Command {
    private static final String SEND = "SEND FROM:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
                throw new InvalidParameterException("invalid from email");
        }

        String send = String.format(SEND, argumentValue);

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(send);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
