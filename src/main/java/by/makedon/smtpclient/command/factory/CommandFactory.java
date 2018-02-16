package by.makedon.smtpclient.command.factory;

import by.makedon.smtpclient.command.Command;

import java.util.Optional;

public class CommandFactory {
    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null) {
            return Optional.empty();
        }

        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}