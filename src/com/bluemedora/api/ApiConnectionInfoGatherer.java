package com.bluemedora.api;

import com.bluemedora.argument.ArgumentMap;
import com.bluemedora.argument.ArgumentParser;
import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;
import com.bluemedora.exceptions.ExitException;
import com.bluemedora.properties.SuiteApiProperties;

import static com.bluemedora.api.ApiConnectionInfoArguments.*;

public class ApiConnectionInfoGatherer
{
    private final ArgumentParser argumentParser;

    public ApiConnectionInfoGatherer(ArgumentParser argumentParser)
    {
        this.argumentParser = argumentParser;
    }

    public ApiConnectionInfo getApiConnectionInfoFromArguments(String[] arguments) throws UnknownArgumentException, ArgumentMissingValueException
    {
        ArgumentMap parsedArgumentMap = this.argumentParser.parse(arguments);

        String host = parsedArgumentMap.getValueOrEmptyString(HOST_FLAG);
        String username = parsedArgumentMap.getValueOrEmptyString(USERNAME_FLAG);
        String password = parsedArgumentMap.getValueOrEmptyString(PASSWORD_FLAG);

        return new ApiConnectionInfo(host, username, password);
    }

    public ApiConnectionInfo getApiConnectionInfoFromSuiteApiProperties(SuiteApiProperties suiteApiProperties)
    {
        String host = suiteApiProperties.getHostOrEmptyString();
        String username = suiteApiProperties.getUsernameOrEmptyString();
        String password = suiteApiProperties.getPasswordOrEmptyString();

        return new ApiConnectionInfo(host, username, password);
    }

    public ApiConnectionInfo getMissingApiConnectionInfoFromUser(ApiConnectionInfo apiConnectionInfo, Shell shell) throws ExitException
    {
        ApiConnectionInfo newApiConnectionInfo = new ApiConnectionInfo();

        if (!apiConnectionInfo.hasHost()) {
            newApiConnectionInfo.setHost(shell.getHostFromUser());
        }

        if (!apiConnectionInfo.hasUsername()) {
            newApiConnectionInfo.setUsername(shell.getUsernameFromUser());
        }

        if (!apiConnectionInfo.hasPassword()) {
            newApiConnectionInfo.setPassword(shell.getPasswordFromUser());
        }

        return newApiConnectionInfo;
    }

}
