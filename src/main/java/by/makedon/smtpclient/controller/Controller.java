package by.makedon.smtpclient.controller;

import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.command.factory.CommandType;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.socket.MailSocket;
import by.makedon.smtpclient.model.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;
import java.util.Scanner;

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

    public void processRequest(String commandName, Map<ParameterCriteria, String> parameters) throws InvalidParameterException, MailSocketException {
        System.out.println(commandName);
        System.out.println(parameters.get(ParameterCriteria.ARGUMENT));
    }

    public void sendMessage(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, MailSocketException {
//        String smtpServerValue = parameters.get(ParameterCriteria.SMTP_SERVER);
//        String fromValue = parameters.get(ParameterCriteria.FROM);
//        String toValue = parameters.get(ParameterCriteria.TO);
//        String subjectValue = parameters.get(ParameterCriteria.SUBJECT);
//        String mailTextValue = parameters.get(ParameterCriteria.MAIL_TEXT);
//
//        if (!Validator.validateOnNullEmpty(smtpServerValue, fromValue, toValue, subjectValue, mailTextValue)) {
//            throw new InvalidParameterException("parameters are empty or null");
//        }
//
//        if (!Validator.validateEmail(fromValue)) {
//            throw new InvalidParameterException("invalid from email");
//        }
//
//        if (!Validator.validateTo(toValue)) {
//            throw new InvalidParameterException("invalid to emails");
//        }
//
//        if (!Validator.validateMailText(mailTextValue)) {
//            throw new InvalidParameterException("Dot '.' can't be the one symbol in sentence");
//        }
//
//        MailSocket mailSocket = new MailSocket();
//        MemoBuffer memoBuffer = MemoBuffer.getInstance();
//        try {
//            mailSocket.create(smtpServerValue);
//            Scanner input = mailSocket.getInput();
//            PrintWriter output = mailSocket.getOutput();
//
//            String heloCommand = CommandType.EHLO.getCommand();
//            String helo = String.format(heloCommand, InetAddress.getLocalHost().getHostAddress());
//            //memoBuffer.append("C: " + helo + "\n");
//            output.write(helo);
//            output.flush();
//            //memoBuffer.append("S: " + input.nextLine() + "\n");
//
//            String mailCommand = CommandType.MAIL.getCommand();
//            String mail = String.format(mailCommand, fromValue);
//            //memoBuffer.append("C: " + mail + "\n");
//            output.write(mail);
//            output.flush();
//            //memoBuffer.append("S: " + input.nextLine() + "\n");
//
//            String rcptCommand = CommandType.RCPT.getCommand();
//            for (String s : toValue.split(",")) {
//                String rcpt = String.format(rcptCommand, s);
//                //memoBuffer.append("C: " + rcpt + "\n");
//                output.write(rcpt);
//                output.flush();
//                //memoBuffer.append("S: " + input.nextLine() + "\n");
//            }
//
//            String dataCommand = CommandType.DATA.getCommand();
//            //memoBuffer.append("C: " + dataCommand + "\n");
//            output.write(dataCommand);
//            output.flush();
//            //memoBuffer.append("S: " + input.nextLine() + "\n");
//
//            //memoBuffer.append("C: Subject: " + subjectValue + "\n");
//            output.write("Subject: " + subjectValue);
//            output.flush();
//            //memoBuffer.append("S: " + input.nextLine() + "\n");
//
//            for (String s : mailTextValue.split("\n")) {
//                //memoBuffer.append("C: " + s + "\n");
//                output.write(s);
//                output.flush();
//            }
//            //memoBuffer.append("C: .\n");
//            output.write(".");
//            output.flush();
//            //memoBuffer.append("S: " + input.nextLine() + "\n");
//
//            String quitCommand = CommandType.QUIT.getCommand();
//            //memoBuffer.append("C: " + quitCommand + "\n");
//            output.write(quitCommand);
//            output.flush();
//            //memoBuffer.append("S: " + input.nextLine() + "\n");
//        } catch (IOException e) {
//            throw new MailSocketException(e);
//        } finally {
//            mailSocket.close();
//        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone singleton object");
        }
        return super.clone();
    }
}
