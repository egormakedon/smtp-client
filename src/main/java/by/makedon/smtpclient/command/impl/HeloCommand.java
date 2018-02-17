package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;
import java.util.Scanner;

public class HeloCommand implements Command {
    private static final String HELO = "HELO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        MailSocket mailSocket = MailSocket.getInstance();
        try {
            String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

            mailSocket.create(argumentValue);

            String helo = String.format(HELO, InetAddress.getLocalHost().getHostName());

            Scanner input = mailSocket.getInput();
            PrintWriter output = mailSocket.getOutput();
            MemoBuffer memoBuffer = MemoBuffer.getInstance();

            memoBuffer.appendClient(helo);
            output.write(helo);
            output.flush();
            memoBuffer.appendServer(input.nextLine());
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
