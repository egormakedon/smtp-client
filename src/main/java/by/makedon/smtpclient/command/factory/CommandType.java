package by.makedon.smtpclient.command.factory;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.command.impl.*;

public enum CommandType {
    EHLO(new EhloCommand()),
    HELO(new HeloCommand()),
    MAIL(new MailCommand()),
    RCPT(new RcptCommand()),
    DATA(new DataCommand()),
    RSET(new RsetCommand()),
    SEND(new SendCommand()),
    SOML(new SomlCommand()),
    SAML(new SamlCommand()),
    VRFY(new VrfyCommand()),
    EXPN(new ExpnCommand()),
    HELP(new HelpCommand()),
    NOOP(new NoopCommand()),
    QUIT(new QuitCommand()),
    TURN(new TurnCommand()),

    AUTH(new AuthCommand()),
    CONNECT(new ConnectCommand()),
    SUBMIT(new SubmitCommand()),
    SEND_MESSAGE(new SendMessageCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}