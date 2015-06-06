package com.bluemedora;

import com.bluemedora.api.ApiConnectionInfo;
import com.bluemedora.api.ApiConnectionInfoGatherer;
import com.bluemedora.argument.ArgumentMap;
import com.bluemedora.argument.ArgumentParser;
import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;
import com.bluemedora.properties.PropertiesFile;
import com.bluemedora.properties.SuiteApiProperties;
import com.bluemedora.properties.FileFinder;
import com.bluemedora.properties.exceptions.FailedToFindSuiteApiPropertiesException;

import static com.bluemedora.api.ApiConnectionInfoArguments.API_CONNECTION_INFO_ARGUMENT_MAP;

public class Main
{
    private static final String SUITE_API_FILE = ".suiteapi";

    public static void main(String[] arguments)
    {
        ArgumentMap argumentMap = API_CONNECTION_INFO_ARGUMENT_MAP;
        ArgumentParser argumentParser = new ArgumentParser(argumentMap);
        ApiConnectionInfoGatherer apiConnectionInfoGatherer = new ApiConnectionInfoGatherer(argumentParser);

        ApiConnectionInfo apiConnectionInfo;
        try {
            apiConnectionInfo = apiConnectionInfoGatherer.getApiConnectionInfoFromArguments(arguments);
        } catch (UnknownArgumentException e) {
            System.err.println("Unknown argument: " + e.getUnknownArgument());
            return;
        } catch (ArgumentMissingValueException e) {
            System.err.println("Argument missing value: " + e.getArgumentMissingValue());
            return;
        }

        if (!apiConnectionInfo.hasAllInfo()) {
            try {
                String suiteApiPropertiesFilePath = FileFinder.findAbsolutePathInCurrentOrAncestorDirectory(SUITE_API_FILE);
                PropertiesFile propertiesFile = new PropertiesFile(suiteApiPropertiesFilePath);
                SuiteApiProperties suiteApiProperties = new SuiteApiProperties(propertiesFile);
                apiConnectionInfo.merge(apiConnectionInfoGatherer.getApiConnectionInfoFromSuiteApiProperties(suiteApiProperties));
            } catch (FailedToFindSuiteApiPropertiesException e) {
                // no problem
            }
        }

        if (!apiConnectionInfo.hasAllInfo()) {

        }

        System.out.println(apiConnectionInfo);
    }
}
