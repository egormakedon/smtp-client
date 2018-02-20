package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Noop implements Command {
    private static final String NOOP = "NOOP\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(NOOP);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
