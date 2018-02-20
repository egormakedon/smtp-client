package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.SmtpSocketException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.MessageId;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.model.Validator;
import by.makedon.smtpclient.socket.MailSocket;
import by.makedon.smtpclient.socket.SSLGmailSocket;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class SendMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SendMessageCommand.class);

    private static final String MAIL_PROPERTIES_PATH = File.separator + "property" + File.separator + "mail.properties";
    private static final String LOGIN = "mail.login";
    private static final String PASSWORD = "mail.password";

    private String login;

    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException {
        String toValue = parameters.get(ParameterCriteria.TO);
        String subjectValue = parameters.get(ParameterCriteria.SUBJECT);
        String mailTextValue = parameters.get(ParameterCriteria.MAIL_TEXT);

        if (!Validator.validateToEmail(toValue)) {
            throw new InvalidParameterException("invalid to email\\emails");
        }

        SSLGmailSocket socket = null;
        try {
            Map<ParameterCriteria, String> parameter;

            socket = connect();

            MailSocket.getInstance().setInput(socket.getInput());
            MailSocket.getInstance().setOutput(socket.getOutput());

            (new EhloCommand()).execute(null);

            auth(socket);

            parameter = new HashMap<ParameterCriteria, String>();
            parameter.put(ParameterCriteria.ARGUMENT, login);
            (new MailCommand()).execute(parameter);

            for (String rcpt : toValue.split(",")) {
                parameter = new HashMap<ParameterCriteria, String>();
                parameter.put(ParameterCriteria.TO, rcpt);
                (new RcptCommand()).execute(parameter);
            }

            (new DataCommand()).execute(null);

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            PrintWriter output = socket.getOutput();
            Scanner input = socket.getInput();

            memoBuffer.appendClient("Subject: " + subjectValue + "\n");
            output.write("Subject: " + subjectValue + "\r\n");
            output.flush();

            long messageId = MessageId.getMessageId();
            memoBuffer.appendClient("Message-ID: " + messageId + "\n");
            output.write("Message-ID: " + messageId + "\r\n");
            output.flush();

            memoBuffer.appendClient("\n");
            output.write("\r\n");
            output.flush();

            for (String sentence : mailTextValue.split("\n")) {
                memoBuffer.appendClient(sentence + "\n");
                output.write(sentence + "\r\n");
                output.flush();
            }

            memoBuffer.appendClient(".\n");
            output.write("\r\n.\r\n");
            output.flush();
            memoBuffer.appendServer(input);

            (new QuitCommand()).execute(null);
        } catch (Exception e) {
            throw new CommandException(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private SSLGmailSocket connect() throws SmtpSocketException {
        SSLGmailSocket socket = new SSLGmailSocket();
        socket.create();
        return socket;
    }

    private void auth(SSLGmailSocket socket) throws SmtpSocketException {
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

        login = properties.getProperty(LOGIN);
        String password = properties.getProperty(PASSWORD);

        String s = "\000" + login + "\000" + password;
        byte[] encodeBytes = Base64.getEncoder().encode(s.getBytes());

        MemoBuffer memoBuffer = MemoBuffer.getInstance();
        PrintWriter output = socket.getOutput();
        Scanner input = socket.getInput();

        memoBuffer.appendClient("AUTH PLAIN " + new String(encodeBytes) + "\n");
        output.write("AUTH PLAIN " + new String(encodeBytes) + "\r\n");
        output.flush();
        memoBuffer.appendServer(input);
    }
}
