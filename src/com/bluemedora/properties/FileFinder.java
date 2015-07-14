package com.bluemedora.properties;

import com.bluemedora.properties.exceptions.FailedToFindSuiteApiPropertiesException;

import java.io.File;

public class FileFinder
{
    private FileFinder()
    {
    }

    public static String findAbsolutePathInCurrentOrAncestorDirectory(String filename) throws FailedToFindSuiteApiPropertiesException
    {
        String workingDirectoryPath = System.getProperty("user.dir");
        File workingDirectory = new File(workingDirectoryPath);
        return findAbsolutePathInCurrentOrAncestorDirectory(workingDirectory, filename);
    }

    private static String findAbsolutePathInCurrentOrAncestorDirectory(File directory, String filename) throws FailedToFindSuiteApiPropertiesException
    {
        File potentialSuiteApiFile = new File(directory.getAbsolutePath() + File.separator + filename);

        if (potentialSuiteApiFile.isFile()) {
            return potentialSuiteApiFile.getAbsoluteFile().getAbsolutePath();
        }

        if (reachedRootDirectory(directory)) {
            throw new FailedToFindSuiteApiPropertiesException();
        }

        return findAbsolutePathInCurrentOrAncestorDirectory(directory.getParentFile(), filename);
    }

    private static boolean reachedRootDirectory(File directory)
    {
        return directory == null || directory.getAbsolutePath().equals(File.separator);
    }
}
