package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Rset implements Command {
    private static final String RSET = "RSET\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(RSET);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}