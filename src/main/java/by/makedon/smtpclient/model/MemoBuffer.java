package by.makedon.smtpclient.model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void appendClient(String s) {
        buffer.append("C: ");
        buffer.append(s);
        buffer.append("\n");
    }

    public void appendServer(String s) {
        buffer.append("S: ");
        buffer.append(s);
        buffer.append("\n");
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