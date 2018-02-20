package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Submit implements Command {
    private static final String SUBMIT = "%s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        String submit = String.format(SUBMIT, argumentValue);

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(submit);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
