package client;

import client.command.Command;
import client.command.factory.Definer;
import client.command.exception.RuntimeError;
import client.model.ParameterCriteria;
import client.command.exception.InvalidParameter;
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

    public void processRequest(String commandName, Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        Optional<Command> optionalCommand = Definer.defineCommand(commandName);
        if (!optionalCommand.isPresent()) {
            throw new InvalidParameter(commandName + " - invalid command");
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
