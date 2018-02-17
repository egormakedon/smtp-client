package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class NoopCommand implements Command {
    private static final String NOOP = "NOOP\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(NOOP);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
