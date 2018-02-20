package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.model.Validator;
import client.socket.SmtpSocket;

import java.util.Map;

public class Rcpt implements Command {
    private static final String RCPT = "RCPT TO:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String toValue = parameters.get(ParameterCriteria.TO);
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        String rcpt;
        if (toValue != null) {
            if (!Validator.validateEmail(toValue)) {
                throw new InvalidParameter("invalid email");
            }
            rcpt = String.format(RCPT, toValue);

        } else {
            if (!Validator.validateEmail(argumentValue)) {
                throw new InvalidParameter("invalid email");
            }
            rcpt = String.format(RCPT, argumentValue);
        }

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(rcpt);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}