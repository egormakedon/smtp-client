package by.makedon.smtpclient.command;

import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.MemoBuffer;
import by.makedon.smtpclient.model.ParameterCriteria;
import by.makedon.smtpclient.socket.MailSocket;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public interface Command {
    void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException;

    default void executeCommand(String command) throws Exception {
        MailSocket mailSocket = MailSocket.getInstance();
        Scanner input = mailSocket.getInput();
        PrintWriter output = mailSocket.getOutput();
        MemoBuffer memoBuffer = MemoBuffer.getInstance();
        memoBuffer.appendClient(command);
        output.write(command);
        output.flush();
        memoBuffer.appendServer(input.nextLine());
    }
}