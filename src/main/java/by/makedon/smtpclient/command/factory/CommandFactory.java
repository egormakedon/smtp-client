package by.makedon.smtpclient.command.factory;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.InvalidParameterException;

import java.util.Optional;

public class CommandFactory {
    public static Optional<Command> defineCommand(String commandName) throws InvalidParameterException {
        Optional<Command> current = Optional.empty();

        if (commandName == null) {
            throw new InvalidParameterException("command is null");
        }

        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(commandName + " - command doesn't exist");
        }
    }
}
