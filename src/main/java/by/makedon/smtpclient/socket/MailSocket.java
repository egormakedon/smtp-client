package by.makedon.smtpclient.socket;

import by.makedon.smtpclient.model.MemoBuffer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class MailSocket {
    private static final Logger LOGGER = LogManager.getLogger(MailSocket.class);
    private static boolean instanceCreated;
    private static final MailSocket INSTANCE;
    static {
        INSTANCE = new MailSocket();
        instanceCreated = true;
    }

    private static final int PORT = 25;
    private static boolean socketCreated;

    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private MailSocket() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone singleton object");
            throw new RuntimeException("try to clone singleton object");
        }
    }

    public static MailSocket getInstance() {
        return INSTANCE;
    }

    public void create(String address) throws MailSocketException {
        if (socketCreated) {
            return;
        }

        try {
            InetAddress inetAddress = InetAddress.getByName(address);

            if (!inetAddress.isReachable(5000)) {
                throw new MailSocketException(address + " - invalid host");
            }

            socket = new Socket(inetAddress, PORT);
            input = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            memoBuffer.appendClient("connected to " + address + "\n");
            memoBuffer.appendServer(input.nextLine());

            socketCreated = true;
        } catch (UnknownHostException e) {
            close();
            throw new MailSocketException("invalid host", e);
        } catch (IOException e) {
            close();
            throw new MailSocketException(e);
        }
    }

    public void close() {
        socketCreated = false;

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
                LOGGER.log(Level.WARN, e);
            }
        }
    }

    public Scanner getInput() throws MailSocketException {
        if (!socketCreated) {
            throw new MailSocketException("socket closed");
        }
        return input;
    }

    public PrintWriter getOutput() throws MailSocketException {
        if (!socketCreated) {
            throw new MailSocketException("socket closed");
        }
        return output;
    }
}