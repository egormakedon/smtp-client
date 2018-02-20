package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.model.Validator;
import client.socket.SmtpSocket;

import java.util.Map;

public class Mail implements Command {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
            throw new InvalidParameter("invalid from email");
        }

        String mail = String.format(MAIL, argumentValue);

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(mail);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}