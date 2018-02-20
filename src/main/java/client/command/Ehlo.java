package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;

import java.net.InetAddress;
import java.util.Map;

public class Ehlo implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        try {
            String ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            executeCommand(ehlo);
        } catch (Exception e) {
            throw new RuntimeError(e);
        }
    }
}