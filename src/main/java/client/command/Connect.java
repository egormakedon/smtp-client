package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.socket.SmtpSocketException;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Connect implements Command {
    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        try {
            SmtpSocket.getInstance().create();
        } catch (SmtpSocketException e) {
            throw new RuntimeError(e);
        }
    }
}
