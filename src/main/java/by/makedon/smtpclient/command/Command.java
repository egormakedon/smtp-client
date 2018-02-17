package by.makedon.smtpclient.command;

import by.makedon.smtpclient.exception.CommandException;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.model.ParameterCriteria;

import java.util.Map;

public interface Command {
    void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, CommandException;
}