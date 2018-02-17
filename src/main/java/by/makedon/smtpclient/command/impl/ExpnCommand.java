package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class ExpnCommand implements Command {
    private static final String EXPN = "EXPN %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (argumentValue.isEmpty()) {
            throw new InvalidParameterException("invalid address");
        }

        String expn = String.format(EXPN, argumentValue);

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(expn);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
