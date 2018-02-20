package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Quit implements Command {
    private static final String QUIT = "QUIT\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(QUIT);
            SmtpSocket.getInstance().close();
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
