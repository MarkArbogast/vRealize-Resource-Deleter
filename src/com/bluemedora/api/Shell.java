package com.bluemedora.api;

import com.bluemedora.exceptions.ExitException;
import com.bluemedora.properties.SuiteApiProperties;
import com.vmware.ops.api.model.resource.ResourceDto;
import com.vmware.ops.api.model.resource.ResourceKey;

import java.io.Console;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Shell
{
    public String getHostFromUser() throws ExitException
    {
        return getFieldFromUser(SuiteApiProperties.HOST);
    }

    private String getFieldFromUser(String field) throws ExitException
    {
        String fieldResponse = "";

        while (fieldResponse.isEmpty()) {
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
        return getFieldFromUser("name of the resource to delete");
    }

    public int getIndexFromUser(List<ResourceDto> matchingResources) throws ExitException
    {
        for (int i = 0; i < matchingResources.size(); i++) {
            ResourceKey matchingResourceKey = matchingResources.get(i).getResourceKey();
            System.out.println(i + ":  " +  matchingResourceKey.getName() + " (" + matchingResourceKey.getResourceKindKey() + ")");
        }
        return getIndexFromUser(matchingResources.size() - 1);
    }

    private int getIndexFromUser(int max) throws ExitException
    {
        int index;
        while(true) {
            String indexString = getFieldFromUser("the index (0-" + max + ") of the resource would you like to delete");

            try {
                index = parseInt(indexString);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
                continue;
            }

            if (index < 0 || index > max) {
                System.out.println("");
                continue;
            }

            return index;
        }
    }

    public boolean getDeleteDescendantsFromUser() throws ExitException
    {
        Boolean deleteDescendants = null;

        while (deleteDescendants == null) {
            String response = getConsole().readLine("Delete descendants (y/n)? ");
            if ("y".equals(response)) {
                deleteDescendants = true;
            } else if ("n".equals(response)) {
                deleteDescendants = false;
            }
        }

        return deleteDescendants;
    }
}
