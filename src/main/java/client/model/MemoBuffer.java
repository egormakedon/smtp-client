package client.model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class MemoBuffer {
    private static final Logger LOGGER = LogManager.getLogger(MemoBuffer.class);
    private static boolean instanceCreated;
    private static final MemoBuffer INSTANCE;
    static {
        INSTANCE = new MemoBuffer();
        instanceCreated = true;
    }

    private StringBuilder buffer = new StringBuilder();

    private MemoBuffer() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone singleton object");
            throw new RuntimeException("try to clone singleton object");
        }
    }
    public static MemoBuffer getInstance() {
        return INSTANCE;
    }

    private class AppendServerThread extends Thread {
        private Scanner input;

        AppendServerThread(Scanner input) {
            this.input = input;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String s = input.nextLine();
                    buffer.append("S: ");
                    buffer.append(s);
                    buffer.append("\n");
                }
            } catch (Exception e) {
                //
            }
        }
    }

    public void appendClient(String s) {
        buffer.append("C: ");
        buffer.append(s);
    }

    public void appendServer(Scanner input) {
        AppendServerThread thread = new AppendServerThread(input);
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, e);
        } finally {
            thread.interrupt();
        }
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone singleton object");
        }
        return super.clone();
    }
}