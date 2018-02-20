package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Expn implements Command {
    private static final String EXPN = "EXPN %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        if (argumentValue.isEmpty()) {
            throw new InvalidParameter("invalid address");
        }

        String expn = String.format(EXPN, argumentValue);

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(expn);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
