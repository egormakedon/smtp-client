package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class VrfyCommand implements Command {
    private static final String VRFY = "VRFY %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (argumentValue.isEmpty()) {
            throw new InvalidParameterException("invalid address");
        }

        String vrfy = String.format(VRFY, argumentValue);

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(vrfy);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
