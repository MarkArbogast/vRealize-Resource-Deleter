package com.bluemedora.api;

import com.bluemedora.argument.Argument;
import com.bluemedora.argument.ArgumentMap;

import java.util.Arrays;
import java.util.List;

public class ApiConnectionInfoGatherer
{
    public static final String HOST_FLAG     = "-h";
    public static final String USERNAME_FLAG = "-u";
    public static final String PASSWORD_FLAG = "-p";

    public static final List<String> ARGUMENT_FLAGS = Arrays.asList(HOST_FLAG, USERNAME_FLAG, PASSWORD_FLAG);

    public static final ArgumentMap API_CONNECTION_INFO_ARGUMENT_MAP = initializeApiConnectionInfoArgumentMap();

    private static ArgumentMap initializeApiConnectionInfoArgumentMap()
    {
        ArgumentMap argumentMap = new ArgumentMap();

        for (String argumentFlag : ARGUMENT_FLAGS) {
            Argument argument = new Argument(argumentFlag, "");
            argumentMap.add(argument);
        }

        return argumentMap;
    }

}
