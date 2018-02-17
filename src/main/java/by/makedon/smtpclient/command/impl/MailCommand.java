package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class MailCommand implements Command {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String fromValue = parameters.get(ParameterCriteria.FROM);
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        String mail;
        if (fromValue != null) {
            if (!Validator.validateEmail(fromValue)) {
                throw new InvalidParameterException("invalid from email");
            }
            mail = String.format(MAIL, fromValue);

        } else {
            if (!Validator.validateEmail(argumentValue)) {
                throw new InvalidParameterException("invalid from email");
            }
            mail = String.format(MAIL, argumentValue);
        }

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            Scanner input = mailSocket.getInput();
            PrintWriter output = mailSocket.getOutput();
            MemoBuffer memoBuffer = MemoBuffer.getInstance();

            memoBuffer.appendClient(mail);
            output.write(mail);
            output.flush();
            memoBuffer.appendServer(input.nextLine());
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}