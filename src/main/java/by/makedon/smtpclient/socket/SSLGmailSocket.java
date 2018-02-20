package by.makedon.smtpclient.socket;

import by.makedon.smtpclient.exception.SmtpSocketException;
import by.makedon.smtpclient.model.MemoBuffer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

public class SSLGmailSocket {
    private static final Logger LOGGER = LogManager.getLogger(SSLGmailSocket.class);

    private static final String SMTP_PROPERTIES_PATH = File.separator + "property" + File.separator + "smtp.properties";

    private static final String HOST = "smtp.host";
    private static final String PORT = "smtp.port";

    private SSLSocket socket;
    private Scanner input;
    private PrintWriter output;

    public void create() throws SmtpSocketException {
        URL url = this.getClass().getResource(SMTP_PROPERTIES_PATH);
        if (url == null) {
            LOGGER.log(Level.FATAL, "smtp property file hasn't found");
            throw new RuntimeException("smtp property file hasn't found");
        }

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

        try {
            String host = properties.getProperty(HOST);
            int port = Integer.parseInt(properties.getProperty(PORT));

            SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) ssf.createSocket(host, port);
            input = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            memoBuffer.appendClient("connected to " + host + "\n");
            memoBuffer.appendServer(input);
        } catch (UnknownHostException e) {
            close();
            throw new SmtpSocketException("invalid host", e);
        } catch (IOException e) {
            close();
            throw new SmtpSocketException(e);
        }
    }

    public void close() {
        if (input != null) {
            input.close();
            input = null;
        }

        if (output != null) {
            output.close();
            output = null;
        }

        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                LOGGER.log(Level.FATAL, e);
                throw new RuntimeException(e);
            }
        }
    }

    public Scanner getInput() throws SmtpSocketException {
        if (input == null) {
            throw new SmtpSocketException("socket closed");
        }
        return input;
    }

    public PrintWriter getOutput() throws SmtpSocketException {
        if (output == null) {
            throw new SmtpSocketException("socket closed");
        }
        return output;
    }
}
