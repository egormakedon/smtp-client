package by.makedon.smtpclient.controller;

import by.makedon.smtpclient.buffer.MemoBuffer;
import by.makedon.smtpclient.command.CommandType;
import by.makedon.smtpclient.criteria.MailFormCriteria;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.socket.MailSocket;
import by.makedon.smtpclient.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

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

    public void sendMessage(Map<MailFormCriteria, String> parameters) throws InvalidParameterException, MailSocketException {
        String smtpServerValue = parameters.get(MailFormCriteria.SMTP_SERVER);
        String fromValue = parameters.get(MailFormCriteria.FROM);
        String toValue = parameters.get(MailFormCriteria.TO);
        String subjectValue = parameters.get(MailFormCriteria.SUBJECT);
        String mailTextValue = parameters.get(MailFormCriteria.MAIL_TEXT);

        if (!Validator.validateOnNullEmpty(smtpServerValue, fromValue, toValue, subjectValue, mailTextValue)) {
            throw new InvalidParameterException("parameters are empty or null");
        }

        if (!Validator.validateEmail(fromValue)) {
            throw new InvalidParameterException("invalid from email");
        }

        if (!Validator.validateTo(toValue)) {
            throw new InvalidParameterException("invalid to emails");
        }

        if (!Validator.validateMailText(mailTextValue)) {
            throw new InvalidParameterException("Dot '.' can't be the one symbol in sentence");
        }

        MailSocket mailSocket = new MailSocket();
        MemoBuffer memoBuffer = MemoBuffer.getInstance();
        try {
            mailSocket.create(smtpServerValue);
            DataInputStream dataInputStream = mailSocket.getDataInputStream();
            DataOutputStream dataOutputStream = mailSocket.getDataOutputStream();

            String heloCommand = CommandType.HELO.getCommand();
            String helo = String.format(heloCommand, InetAddress.getLocalHost().getHostAddress());
            memoBuffer.append("C: " + helo + "\n");
            dataOutputStream.writeUTF(helo);
            dataOutputStream.flush();
            memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");

            String mailCommand = CommandType.MAIL.getCommand();
            String mail = String.format(mailCommand, fromValue);
            memoBuffer.append("C: " + mail + "\n");
            dataOutputStream.writeUTF(mail);
            dataOutputStream.flush();
            memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");

            String rcptCommand = CommandType.RCPT.getCommand();
            for (String s : toValue.split(",")) {
                String rcpt = String.format(rcptCommand, s);
                memoBuffer.append("C: " + rcpt + "\n");
                dataOutputStream.writeUTF(rcpt);
                dataOutputStream.flush();
                memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");
            }

            String dataCommand = CommandType.DATA.getCommand();
            memoBuffer.append("C: " + dataCommand + "\n");
            dataOutputStream.writeUTF(dataCommand);
            dataOutputStream.flush();
            memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");

            memoBuffer.append("C: Subject: " + subjectValue + "\n");
            dataOutputStream.writeUTF("Subject: " + subjectValue);
            dataOutputStream.flush();
            memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");

            for (String s : mailTextValue.split("\n")) {
                memoBuffer.append("C: " + s + "\n");
                dataOutputStream.writeUTF(s);
                dataOutputStream.flush();
            }
            memoBuffer.append("C: .\n");
            dataOutputStream.writeUTF(".");
            dataOutputStream.flush();
            memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");

            String quitCommand = CommandType.QUIT.getCommand();
            memoBuffer.append("C: " + quitCommand + "\n");
            dataOutputStream.writeUTF(quitCommand);
            dataOutputStream.flush();
            memoBuffer.append("S: " + dataInputStream.readUTF() + "\n");
        } catch (IOException e) {
            throw new MailSocketException(e);
        } finally {
            mailSocket.close();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone singleton object");
        }
        return super.clone();
    }
}
