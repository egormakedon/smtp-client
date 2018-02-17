package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class EhloCommand implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        try {
            String smptServerValue = parameters.get(ParameterCriteria.SMTP_SERVER);
            String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

            MailSocket mailSocket = MailSocket.getInstance();
            if (smptServerValue != null) {
                mailSocket.create(smptServerValue);
            } else {
                mailSocket.create(argumentValue);
            }

            String ehlo;
            try {
                ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                throw new MailSocketException(e);
            }

            Scanner input = mailSocket.getInput();
            PrintWriter output = mailSocket.getOutput();
            MemoBuffer memoBuffer = MemoBuffer.getInstance();

            memoBuffer.appendClient(ehlo);
            output.write(ehlo);
            output.flush();
            memoBuffer.appendServer(input.nextLine());
        } catch (Exception e) {
            MailSocket.getInstance().close();
            throw new CommandException(e);
        }
    }
}
