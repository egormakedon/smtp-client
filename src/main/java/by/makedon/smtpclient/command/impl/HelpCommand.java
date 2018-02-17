package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.util.Map;

public class HelpCommand implements Command {
    private static final String HELP_PARAMETER = "HELP %s\r\n";
    private static final String HELP_WITHOUT_PARAMETER = "HELP\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String argumentValue = parameters.get(ParameterCriteria.ARGUMENT);

        String help;
        if (argumentValue.isEmpty()) {
            help = HELP_WITHOUT_PARAMETER;
        } else {
            help = String.format(HELP_PARAMETER, argumentValue);
        }

        MailSocket mailSocket = MailSocket.getInstance();
        try {
            executeCommand(help);
        } catch (Exception e) {
            mailSocket.close();
            throw new CommandException(e);
        }
    }
}
