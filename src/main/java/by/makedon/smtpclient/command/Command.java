package by.makedon.smtpclient.command;

import by.makedon.smtpclient.exception.MethodNotSupportedException;

public interface Command {
    void execute() throws MethodNotSupportedException;
    void execute(String parameter) throws MethodNotSupportedException;
}