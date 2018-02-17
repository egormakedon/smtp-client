package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.net.InetAddress;
import java.util.Map;

public class HeloCommand implements Command {
    private static final String HELO = "HELO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            mailSocket.create(argumentValue);
        } catch (MailSocketException e) {
            mailSocket.close();
            throw new CommandException(e);
        }

        try {
            String helo = String.format(HELO, InetAddress.getLocalHost().getHostName());
            executeCommand(helo);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
