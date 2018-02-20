package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.MessageId;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.*;

public class SendMessageCommand implements Command {
    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String toValue = parameters.get(ParameterCriteria.TO);
        String subjectValue = parameters.get(ParameterCriteria.SUBJECT);
        String mailTextValue = parameters.get(ParameterCriteria.MAIL_TEXT);

        if (!Validator.validateToEmail(toValue)) {
            throw new InvalidParameterException("invalid to email\\emails");
        }

        try {
            Map<ParameterCriteria, String> parameter = new HashMap<ParameterCriteria, String>();
            parameter.put(ParameterCriteria.ARGUMENT, "");

            (new ConnectCommand()).execute(null);
            (new EhloCommand()).execute(null);
            (new AuthCommand()).execute(null);

            parameter.replace(ParameterCriteria.ARGUMENT, "emakedonw@gmail.com");
            (new MailCommand()).execute(parameter);

            for (String rcpt : toValue.split(",")) {
                parameter.replace(ParameterCriteria.ARGUMENT, rcpt);
                (new RcptCommand()).execute(parameter);
            }

            (new DataCommand()).execute(null);

            parameter.replace(ParameterCriteria.ARGUMENT, "Subject: " + subjectValue);
            (new SubmitCommand()).execute(parameter);

            long messageId = MessageId.getMessageId();
            parameter.replace(ParameterCriteria.ARGUMENT, "Message-ID: " + messageId);
            (new SubmitCommand()).execute(parameter);

            parameter.replace(ParameterCriteria.ARGUMENT, "");
            (new SubmitCommand()).execute(parameter);

            for (String sentence : mailTextValue.split("\n")) {
                parameter.replace(ParameterCriteria.ARGUMENT, sentence);
                (new SubmitCommand()).execute(parameter);
            }

            parameter.replace(ParameterCriteria.ARGUMENT, "\r\n.");
            (new SubmitCommand()).execute(parameter);

            (new QuitCommand()).execute(null);
        } catch (Exception e) {
            throw new CommandException(e);
        } finally {
            MailSocket.getInstance().close();
        }
    }
}
