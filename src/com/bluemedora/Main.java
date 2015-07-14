package com.bluemedora;

import com.bluemedora.api.ApiConnectionInfo;
import com.bluemedora.api.ApiConnectionInfoGatherer;
import com.bluemedora.api.Shell;
import com.bluemedora.api.SuiteApi;
import com.bluemedora.argument.ArgumentMap;
import com.bluemedora.argument.ArgumentParser;
import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;
import com.bluemedora.exceptions.ExitException;
import com.bluemedora.properties.FileFinder;
import com.bluemedora.properties.PropertiesFile;
import com.bluemedora.properties.SuiteApiProperties;
import com.bluemedora.properties.exceptions.FailedToFindSuiteApiPropertiesException;

import static com.bluemedora.api.ApiConnectionInfoArguments.API_CONNECTION_INFO_ARGUMENT_MAP;

public class Main
{
    private static final String SUITE_API_FILE = ".suiteapi";
    private static final Shell  shell          = new Shell();

    public static void main(String[] arguments)
    {
        ApiConnectionInfo apiConnectionInfo;
        try {
            apiConnectionInfo = gatherApiConnectionInfo(arguments);
            System.out.println("Testing vRealize Suite API connection...");
            SuiteApi.testApiConnectionInfo(apiConnectionInfo);
            String resourceToDelete = getResourceToDelete();
        } catch (ExitException e) {
            System.err.println(e.getExitMessage());
            System.err.println("Exiting.");
            System.exit(0);
        }

        System.out.println("\nConnection successful.\n");
    }

    private static String getResourceToDelete() throws ExitException
    {
        return shell.getResourceToDeleteFromUser();
    }

    private static ApiConnectionInfo gatherApiConnectionInfo(String[] arguments) throws ExitException
    {
        ArgumentMap argumentMap = API_CONNECTION_INFO_ARGUMENT_MAP;
        ArgumentParser argumentParser = new ArgumentParser(argumentMap);
        ApiConnectionInfoGatherer apiConnectionInfoGatherer = new ApiConnectionInfoGatherer(argumentParser);

        ApiConnectionInfo apiConnectionInfo = getApiConnectionInfoFromArguments(apiConnectionInfoGatherer, arguments);
        apiConnectionInfo.mergeNewValues(getApiConnectionInfoFromSuiteApiPropertiesFile(apiConnectionInfoGatherer));
        apiConnectionInfo.mergeNewValues(apiConnectionInfoGatherer.getMissingApiConnectionInfoFromUser(apiConnectionInfo, shell));

        return apiConnectionInfo;
    }

    private static ApiConnectionInfo getApiConnectionInfoFromArguments(ApiConnectionInfoGatherer apiConnectionInfoGatherer, String[] arguments) throws ExitException
    {
        ApiConnectionInfo apiConnectionInfo;

        try {
            apiConnectionInfo = apiConnectionInfoGatherer.getApiConnectionInfoFromArguments(arguments);
        } catch (UnknownArgumentException e) {
            throw new ExitException("Unknown argument: " + e.getUnknownArgument());
        } catch (ArgumentMissingValueException e) {
            throw new ExitException("Argument missing value: " + e.getArgumentMissingValue());
        }

        return apiConnectionInfo;
    }

    private static ApiConnectionInfo getApiConnectionInfoFromSuiteApiPropertiesFile(ApiConnectionInfoGatherer apiConnectionInfoGatherer)
    {
        ApiConnectionInfo apiConnectionInfo = new ApiConnectionInfo();

        try {
            String suiteApiPropertiesFilePath = FileFinder.findAbsolutePathInCurrentOrAncestorDirectory(SUITE_API_FILE);
            PropertiesFile propertiesFile = new PropertiesFile(suiteApiPropertiesFilePath);
            SuiteApiProperties suiteApiProperties = new SuiteApiProperties(propertiesFile);
            apiConnectionInfo = apiConnectionInfoGatherer.getApiConnectionInfoFromSuiteApiProperties(suiteApiProperties);
        } catch (FailedToFindSuiteApiPropertiesException e) {
            // no problem
        }

        return apiConnectionInfo;
    }
}
