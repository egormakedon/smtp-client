package by.makedon.smtpclient.socket;

import by.makedon.smtpclient.exception.MailSocketException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MailSocket {
    private static final int PORT = 25;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public void create(String address) throws MailSocketException {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            if (!inetAddress.isReachable(2)) {
                throw new MailSocketException("invalid host");
            }
            socket = new Socket(inetAddress, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            throw new MailSocketException("invalid host", e);
        } catch (IOException e) {
            throw new MailSocketException(e);
        }
    }

    public void close() throws MailSocketException {
        if (dataInputStream != null) {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                throw new MailSocketException(e);
            }
        }

        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                throw new MailSocketException(e);
            }
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new MailSocketException(e);
            }
        }
    }

    public DataInputStream getDataInputStream() throws MailSocketException {
        if (socket == null || socket.isClosed()) {
            throw new MailSocketException("socket closed");
        }
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() throws MailSocketException {
        if (socket == null || socket.isClosed()) {
            throw new MailSocketException("socket closed");
        }
        return dataOutputStream;
    }
}
