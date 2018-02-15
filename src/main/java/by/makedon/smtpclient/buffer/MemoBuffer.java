package by.makedon.smtpclient.buffer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MemoBuffer {
    private static final Logger LOGGER = LogManager.getLogger(MemoBuffer.class);

    private static boolean instanceCreated;
    private static final MemoBuffer INSTANCE;

    private StringBuilder memoBuffer = new StringBuilder();

    static {
        INSTANCE = new MemoBuffer();
        instanceCreated = true;
    }

    private MemoBuffer() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone singleton object");
            throw new RuntimeException("try to clone singleton object");
        }
    }

    public static MemoBuffer getInstance() {
        return INSTANCE;
    }

    public void append(String s) {
        memoBuffer.append(s);
    }

    @Override
    public String toString() {
        return memoBuffer.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone singleton object");
        }
        return super.clone();
    }
}
