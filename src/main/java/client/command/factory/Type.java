package client.command.factory;

import client.command.*;

public enum Type {
    EHLO(new Ehlo()),
    HELO(new Helo()),
    MAIL(new Mail()),
    RCPT(new Rcpt()),
    DATA(new Data()),
    RSET(new Rset()),
    SEND(new Send()),
    SOML(new Soml()),
    SAML(new Saml()),
    VRFY(new Vrfy()),
    EXPN(new Expn()),
    HELP(new Help()),
    NOOP(new Noop()),
    QUIT(new Quit()),
    TURN(new Turn()),

    AUTH(new Auth()),
    CONNECT(new Connect()),
    SUBMIT(new Submit()),
    SEND_MESSAGE(new SendMessage());

    private Command command;

    Type(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}