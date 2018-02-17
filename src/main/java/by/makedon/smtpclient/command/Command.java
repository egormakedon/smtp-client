package by.makedon.smtpclient.command;

import by.makedon.smtpclient.model.ParameterCriteria;

import java.util.Map;

public interface Command {
    void execute(Map<ParameterCriteria, String> parameters);
}