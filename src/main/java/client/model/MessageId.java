package client.model;

public class MessageId {
    private static long messageId = -1;
    public static long getMessageId() {
        return ++messageId;
    }
}