package by.makedon.smtpclient.socket;

import by.makedon.smtpclient.buffer.MemoBuffer;
import by.makedon.smtpclient.exception.MailSocketException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MailSocket {
    private static final int PORT = 25;
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    public void create(String address) throws MailSocketException {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);

            if (!inetAddress.isReachable(5000)) {
                throw new MailSocketException("invalid host");
            }

            socket = new Socket(inetAddress, PORT);
            input = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            memoBuffer.append("C: client " + InetAddress.getLocalHost().getHostAddress() + " connected\n");
            memoBuffer.append("S: " + input.nextLine() + "\n");
        } catch (UnknownHostException e) {
            throw new MailSocketException("invalid host", e);
        } catch (IOException e) {
            throw new MailSocketException(e);
        }
    }

    public void close() throws MailSocketException {
        if (input != null) {
            input.close();
        }

        if (output != null) {
            output.close();
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new MailSocketException(e);
            }
        }
    }

    public Scanner getInput() throws MailSocketException {
        if (socket == null || socket.isClosed()) {
            throw new MailSocketException("socket closed");
        }
        return input;
    }

    public PrintWriter getOutput() throws MailSocketException {
        if (socket == null || socket.isClosed()) {
            throw new MailSocketException("socket closed");
        }
        return output;
    }
}