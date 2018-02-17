package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.net.InetAddress;
import java.util.Map;

public class EhloCommand implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String smptServerValue = parameters.get(ParameterCriteria.SMTP_SERVER);
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            if (smptServerValue != null) {
                mailSocket.create(smptServerValue);
            } else {
                mailSocket.create(argumentValue);
            }
        } catch (MailSocketException e) {
            mailSocket.close();
            throw new CommandException(e);
        }

        try {
            String ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            executeCommand(ehlo);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}