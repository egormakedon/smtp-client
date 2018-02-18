package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SendMessageCommand implements Command {
    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String toValue = parameters.get(ParameterCriteria.TO);
        String subjectValue = parameters.get(ParameterCriteria.SUBJECT);
        String mailTextValue = parameters.get(ParameterCriteria.MAIL_TEXT);

        if (!Validator.validateMailText(mailTextValue)) {
            throw new InvalidParameterException("Dot '.' can't be the one symbol in sentence");
        }

        if (!Validator.validateToEmail(toValue)) {
            throw new InvalidParameterException("invalid to email\\emails");
        }

        try {
            (new EhloCommand()).execute(parameters);
            (new MailCommand()).execute(parameters);

            for (String rcpt : toValue.split(",")) {
                Map<ParameterCriteria, String> parameter = new HashMap<ParameterCriteria, String>();
                parameter.put(ParameterCriteria.TO, rcpt);
                (new RcptCommand()).execute(parameter);
            }

            (new DataCommand()).execute(null);

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            MailSocket mailSocket = MailSocket.getInstance();
            PrintWriter output = mailSocket.getOutput();
            Scanner input = mailSocket.getInput();

            memoBuffer.appendClient("Subject: " + subjectValue + "\n");
            output.write("Subject: " + subjectValue + "\r\n");
            output.flush();

            for (String sentence : mailTextValue.split("\n")) {
                memoBuffer.appendClient(sentence + "\n");
                output.write(sentence + "\r\n");
                output.flush();
            }

            memoBuffer.appendClient(".\n");
            output.write("\r\n.\r\n");
            output.flush();
            memoBuffer.appendServer(input.nextLine());

            (new QuitCommand()).execute(null);
        } catch (Exception e) {
            throw new CommandException(e);
        } finally {
            MailSocket.getInstance().close();
        }
    }
}
