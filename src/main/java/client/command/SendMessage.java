package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.MessageId;
import client.model.ParameterCriteria;
import client.model.Validator;
import client.socket.SmtpSocket;

import java.util.*;

public class SendMessage implements Command {
    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String toValue = parameters.get(ParameterCriteria.TO);
        String subjectValue = parameters.get(ParameterCriteria.SUBJECT);
        String mailTextValue = parameters.get(ParameterCriteria.MAIL_TEXT);

        if (!Validator.validateToEmail(toValue)) {
            throw new InvalidParameter("invalid to email\\emails");
        }

        try {
            Map<ParameterCriteria, String> parameter = new HashMap<ParameterCriteria, String>();
            parameter.put(ParameterCriteria.ARGUMENT, "");

            (new Connect()).execute(null);
            (new Ehlo()).execute(null);
            (new Auth()).execute(null);

            parameter.replace(ParameterCriteria.ARGUMENT, "emakedonw@gmail.com");
            (new Mail()).execute(parameter);

            for (String rcpt : toValue.split(",")) {
                parameter.replace(ParameterCriteria.ARGUMENT, rcpt);
                (new Rcpt()).execute(parameter);
            }

            (new Data()).execute(null);

            parameter.replace(ParameterCriteria.ARGUMENT, "Subject: " + subjectValue);
            (new Submit()).execute(parameter);

            long messageId = MessageId.getMessageId();
            parameter.replace(ParameterCriteria.ARGUMENT, "Message-ID: " + messageId);
            (new Submit()).execute(parameter);

            parameter.replace(ParameterCriteria.ARGUMENT, "");
            (new Submit()).execute(parameter);

            for (String sentence : mailTextValue.split("\n")) {
                parameter.replace(ParameterCriteria.ARGUMENT, sentence);
                (new Submit()).execute(parameter);
            }

            parameter.replace(ParameterCriteria.ARGUMENT, "\r\n.");
            (new Submit()).execute(parameter);

            (new Quit()).execute(null);
        } catch (Exception e) {
            throw new RuntimeError(e);
        } finally {
            SmtpSocket.getInstance().close();
        }
    }
}
