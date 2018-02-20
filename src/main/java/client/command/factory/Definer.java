package client.command.factory;

import client.command.Command;

import java.util.Optional;

public class Definer {
    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null) {
            return Optional.empty();
        }

        try {
            Type type = Type.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}