package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class Auth implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Auth.class);

    private static final String MAIL_PROPERTIES_PATH = File.separator + "property" + File.separator + "mail.properties";
    private static final String LOGIN = "mail.login";
    private static final String PASSWORD = "mail.password";

    private static final String AUTH = "AUTH PLAIN %s\r\n";

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError {
        URL url = this.getClass().getResource(MAIL_PROPERTIES_PATH);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(url.toURI())));
        } catch (URISyntaxException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }

        String login = properties.getProperty(LOGIN);
        String password = properties.getProperty(PASSWORD);

        String s = "\000" + login + "\000" + password;
        byte[] encodeBytes = Base64.getEncoder().encode(s.getBytes());

        String auth = String.format(AUTH, new String(encodeBytes));
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(auth);
        } catch (Exception e) {
            smtpSocket.close();
            throw new RuntimeError(e);
        }
    }
}
