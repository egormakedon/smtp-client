package by.makedon.smtpclient.command.factory;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.command.impl.DataCommand;
import by.makedon.smtpclient.command.impl.EhloCommand;
import by.makedon.smtpclient.command.impl.ExpnCommand;
import by.makedon.smtpclient.command.impl.HeloCommand;
import by.makedon.smtpclient.command.impl.HelpCommand;
import by.makedon.smtpclient.command.impl.MailCommand;
import by.makedon.smtpclient.command.impl.NoopCommand;
import by.makedon.smtpclient.command.impl.QuitCommand;
import by.makedon.smtpclient.command.impl.RcptCommand;
import by.makedon.smtpclient.command.impl.RsetCommand;
import by.makedon.smtpclient.command.impl.SamlCommand;
import by.makedon.smtpclient.command.impl.SendCommand;
import by.makedon.smtpclient.command.impl.SendMessageCommand;
import by.makedon.smtpclient.command.impl.SomlCommand;
import by.makedon.smtpclient.command.impl.TurnCommand;
import by.makedon.smtpclient.command.impl.VrfyCommand;

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

    SEND_MESSAGE(new SendMessageCommand());

//    EHLO("EHLO %s\r"),
//    HELO("HELO %s\r"),
//    MAIL("MAIL FROM:<%s>\r"),
//    RCPT("RCPT TO:<%s>\r"),
//    DATA("DATA\r"),
//    RSET("RSET"),
//    SEND("SEND FROM:<%s>"),
//    SOML("SOML FROM:<%s>"),
//    SAML("SAML FROM:<%s>"),
//    VRFY("VRFY %s"),
//    EXPN("EXPN %s"),
//    HELP("HELP %s"),
//    NOOP("NOOP"),
//    QUIT("QUIT\r"),
//    TURN("TURN");

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}