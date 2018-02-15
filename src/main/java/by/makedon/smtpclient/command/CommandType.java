package by.makedon.smtpclient.command;

public enum CommandType {
    HELO("HELO %s"),
    MAIL("MAIL FROM:%s"),
    RCPT("RCPT TO:%s"),
    DATA("DATA"),
    RSET("RSET"),
    SEND("SEND FROM:%s"),
    SOML("SOML FROM:%s"),
    SAML("SAML FROM:%s"),
    VRFY("VRFY %s"),
    EXPN("EXPN %s"),
    HELP("HELP %s"),
    NOOP("NOOP"),
    QUIT("QUIT"),
    TURN("TURN");

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}