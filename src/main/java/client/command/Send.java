package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.model.Validator;
import client.socket.SmtpSocket;

import java.util.Map;

public class Send implements Command {
    private static final String SEND = "SEND FROM:<%s>\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
                throw new InvalidParameter("invalid from email");
        }

        String send = String.format(SEND, argumentValue);

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(send);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
