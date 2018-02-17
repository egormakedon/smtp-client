package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class MailCommand implements Command {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
//        String fromValue = parameters.get(ParameterCriteria.FROM);
//        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);
//
//        MailSocket mailSocket = MailSocket.getInstance();
//        Scanner input = mailSocket.getInput();
//        PrintWriter output = mailSocket.getOutput();
//        MemoBuffer memoBuffer = MemoBuffer.getInstance();
//
//        if (fromValue != null) {
//            String mail = String.format(MAIL, fromValue);
//
//        } else {
//            String mail = String.format(MAIL, argumentValue);
//        }
    }
}
