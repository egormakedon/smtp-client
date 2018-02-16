package by.makedon.smtpclient.command;

public enum CommandType {
    EHLO("EHLO %s\r"),
    HELO("HELO %s\r"),
    MAIL("MAIL FROM:<%s>\r"),
    RCPT("RCPT TO:<%s>\r"),
    DATA("DATA\r"),
    RSET("RSET"),
    SEND("SEND FROM:<%s>"),
    SOML("SOML FROM:<%s>"),
    SAML("SAML FROM:<%s>"),
    VRFY("VRFY %s"),
    EXPN("EXPN %s"),
    HELP("HELP %s"),
    NOOP("NOOP"),
    QUIT("QUIT\r"),
    TURN("TURN");

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}