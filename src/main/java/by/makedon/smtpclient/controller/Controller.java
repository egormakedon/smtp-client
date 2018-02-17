package by.makedon.smtpclient.controller;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.command.factory.CommandFactory;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public final class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static boolean instanceCreated;
    private static final Controller INSTANCE;
    static {
        INSTANCE = new Controller();
        instanceCreated = true;
    }

    private Controller() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone singleton object");
            throw new RuntimeException("try to clone singleton object");
        }
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void processRequest(String commandName, Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        Optional<Command> optionalCommand = CommandFactory.defineCommand(commandName);
        if (!optionalCommand.isPresent()) {
            throw new InvalidParameterException(commandName + " - invalid command");
        }

        Command command = optionalCommand.get();
        command.execute(parameters);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone singleton object");
        }
        return super.clone();
    }
}
