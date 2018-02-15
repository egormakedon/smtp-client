package by.makedon.smtpclient.command.factory;

import by.makedon.smtpclient.command.Command;

public enum CommandType {
//    HELLO(),
//    MAIL(),
//    RCPT(),
//    DATA(),
//    RSET(),
//    SEND(),
//    SOML(),
//    SAML(),
//    VRFY(),
//    EXPN(),
//    HELP(),
//    NOOP(),
//    QUIT();
    ;

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
