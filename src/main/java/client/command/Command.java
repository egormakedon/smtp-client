package client.command;

import client.command.exception.RuntimeError;
import client.command.exception.InvalidParameter;
import client.model.MemoBuffer;
import client.model.ParameterCriteria;
import client.socket.SmtpSocket;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public interface Command {
    void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameter, RuntimeError;

    default void executeCommand(String command) throws Exception {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        Scanner input = smtpSocket.getInput();
        PrintWriter output = smtpSocket.getOutput();
        MemoBuffer memoBuffer = MemoBuffer.getInstance();
        memoBuffer.appendClient(command);
        output.write(command);
        output.flush();
        memoBuffer.appendServer(input);
    }
}