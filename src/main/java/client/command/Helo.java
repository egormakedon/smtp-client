package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;

import java.net.InetAddress;
import java.util.Map;

public class Helo implements Command {
    private static final String HELO = "HELO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        try {
            String helo = String.format(HELO, InetAddress.getLocalHost().getHostName());
            executeCommand(helo);
        } catch (Exception e) {
            throw new RuntimeError(e);
        }
    }
}
