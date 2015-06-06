package com.bluemedora.properties;

import com.bluemedora.properties.exceptions.FailedToFindSuiteApiPropertiesException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileFinderTest
{
    private static String TEST_FILE = "test.txt";

    @Test
    public void findAbsolutePathInCurrentOrAncestorDirectory_findsFileInCurrentDirectory()
    {
        writeTestFile(TEST_FILE);
        try {
            assertEquals(FileFinder.findAbsolutePathInCurrentOrAncestorDirectory(TEST_FILE), getTestFile().getAbsolutePath());
        } catch (FailedToFindSuiteApiPropertiesException e) {
            fail("Failed to find test file in current directory.");
        } finally {
            deleteTestFile(TEST_FILE);
        }
    }

    @Test
    public void findAbsolutePathInCurrentOrAncestorDirectory_findsFileInAncestorDirectory()
    {
        writeTestFile(".." + File.separator + TEST_FILE);
        try {
            assertEquals(getTestFile().getParentFile().getParentFile().getAbsolutePath() + File.separator + TEST_FILE, FileFinder.findAbsolutePathInCurrentOrAncestorDirectory(TEST_FILE));
        } catch (FailedToFindSuiteApiPropertiesException e) {
            fail("Failed to find test file in current directory.");
        } finally {
            deleteTestFile(".." + File.separator + TEST_FILE);
        }
    }

    private void failDueToFileWritingProblem(Exception e)
    {
        fail("Failed to test FileFinder due to file writing problem: " + e.getMessage());
    }

    private void writeTestFile(String testFilePath)
    {
        try {
            PrintWriter writer = new PrintWriter(testFilePath, "UTF-8");
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            failDueToFileWritingProblem(e);
        } catch (UnsupportedEncodingException e) {
            failDueToFileWritingProblem(e);
        }
    }

    private void deleteTestFile(String testFilePath)
    {
        File testFile = new File(testFilePath);
        testFile.delete();
    }

    private File getTestFile()
    {
        return new File(System.getProperty("user.dir") + File.separator + TEST_FILE);
    }
}