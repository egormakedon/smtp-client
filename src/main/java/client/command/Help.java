package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.util.Map;

public class Help implements Command {
    private static final String HELP_PARAMETER = "HELP %s\r\n";
    private static final String HELP_WITHOUT_PARAMETER = "HELP\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        String help;
        if (argumentValue.isEmpty()) {
            help = HELP_WITHOUT_PARAMETER;
        } else {
            help = String.format(HELP_PARAMETER, argumentValue);
        }

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(help);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
