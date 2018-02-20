package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.SmtpSocketException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.net.InetAddress;
import java.util.Map;

public class EhloCommand implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        try {
            String ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            executeCommand(ehlo);
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }
}