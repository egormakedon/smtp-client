package by.makedon.smtpclient.command.impl;

import by.makedon.smtpclient.command.Command;
import by.makedon.smtpclient.exception.InvalidParameterException;
import by.makedon.smtpclient.exception.MailSocketException;
import by.makedon.smtpclient.model.ParameterCriteria;

import java.util.Map;

public class DataCommand implements Command {
    @Override
    public void execute(Map<ParameterCriteria, String> parameters) throws InvalidParameterException, MailSocketException {

    }
}
