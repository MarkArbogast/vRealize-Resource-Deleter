package com.bluemedora.api;

import com.bluemedora.exceptions.ExitException;
import com.bluemedora.properties.SuiteApiProperties;

import java.io.Console;

public class Shell
{
    public String getHostFromUser() throws ExitException
    {
        return getFieldFromUser(SuiteApiProperties.HOST);
    }

    public String getUsernameFromUser() throws ExitException
    {
        return getFieldFromUser(SuiteApiProperties.USERNAME);
    }

    public String getPasswordFromUser() throws ExitException
    {
        return String.valueOf(getConsole().readPassword("Enter password: "));
    }

    public String getResourceToDeleteFromUser() throws ExitException
    {
        return getFieldFromUser("name or ID of resource to delete");
    }

    private String getFieldFromUser(String field) throws ExitException
    {
        String fieldResponse = "";

        while(fieldResponse.isEmpty()) {
            fieldResponse = getConsole().readLine("Enter " + field + ": ");
        }

        return fieldResponse;
    }

    private Console getConsole() throws ExitException
    {
        Console console = System.console();

        if (console == null) {
            throw new ExitException("Couldn't get Console instance.");
        }

        return console;
    }

}
