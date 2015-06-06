package com.bluemedora.api;

import com.bluemedora.argument.ArgumentMap;
import com.bluemedora.argument.ArgumentParser;
import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;
import com.bluemedora.exceptions.ExitException;
import org.junit.Before;
import org.junit.Test;

import static com.bluemedora.api.ApiConnectionInfoArguments.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ApiConnectionInfoGathererTest
{
    private final static String   MOCK_HOST_VALUE     = "MOCK_HOST_VALUE";
    private final static String   MOCK_USERNAME_VALUE = "MOCK_USERNAME_VALUE";
    private final static String   MOCK_PASSWORD_VALUE = "MOCK_PASSWORD_VALUE";
    private final static String[] MOCK_ARGUMENTS      = {};

    private ApiConnectionInfoGatherer target;
    private ApiConnectionInfo         apiConnectionInfo;
    private ArgumentParser            mockArgumentParser;
    private ArgumentMap               mockArgumentMap;
    private Shell                     mockShell;

    private UnknownArgumentException      mockUnknownArgumentException;
    private ArgumentMissingValueException mockArgumentMissingValueException;

    @Before
    public void setUp()
    {
        this.mockArgumentMap = mock(ArgumentMap.class);
        this.mockArgumentParser = mock(ArgumentParser.class);
        this.mockShell = mock(Shell.class);
        this.apiConnectionInfo = mock(ApiConnectionInfo.class);
        this.mockUnknownArgumentException = mock(UnknownArgumentException.class);
        this.mockArgumentMissingValueException = mock(ArgumentMissingValueException.class);
        this.target = new ApiConnectionInfoGatherer(this.mockArgumentParser);

    }

    @Test
    public void getApiConnectionInfoFromArguments_handlesValuesForAllArguments()
    {
        tellMockArgumentParserToReturnMockArgumentMap();
        tellMockMapToReturnMockValueForFlag(HOST_FLAG, MOCK_HOST_VALUE);
        tellMockMapToReturnMockValueForFlag(USERNAME_FLAG, MOCK_USERNAME_VALUE);
        tellMockMapToReturnMockValueForFlag(PASSWORD_FLAG, MOCK_PASSWORD_VALUE);

        ApiConnectionInfo result = getApiConnectionInfoFromArgumentsWithoutException();

        assertEquals(MOCK_HOST_VALUE, result.getHost());
        assertEquals(MOCK_USERNAME_VALUE, result.getUsername());
        assertEquals(MOCK_PASSWORD_VALUE, result.getPassword());
    }


    @Test
    public void getApiConnectionInfoFromArguments_handlesValuesForOneArgument()
    {
        tellMockArgumentParserToReturnMockArgumentMap();
        tellMockMapToReturnMockValueForFlag(HOST_FLAG, MOCK_HOST_VALUE);
        tellMockMapToReturnMockValueForFlag(USERNAME_FLAG, "");
        tellMockMapToReturnMockValueForFlag(PASSWORD_FLAG, "");

        ApiConnectionInfo result = getApiConnectionInfoFromArgumentsWithoutException();

        assertEquals(MOCK_HOST_VALUE, result.getHost());
        assertEquals("", result.getUsername());
        assertEquals("", result.getPassword());
    }


    @Test
    public void getApiConnectionInfoFromArguments_handlesValuesForTwoArguments()
    {
        tellMockArgumentParserToReturnMockArgumentMap();
        tellMockMapToReturnMockValueForFlag(HOST_FLAG, MOCK_HOST_VALUE);
        tellMockMapToReturnMockValueForFlag(USERNAME_FLAG, MOCK_USERNAME_VALUE);
        tellMockMapToReturnMockValueForFlag(PASSWORD_FLAG, "");

        ApiConnectionInfo result = getApiConnectionInfoFromArgumentsWithoutException();

        assertEquals(MOCK_HOST_VALUE, result.getHost());
        assertEquals(MOCK_USERNAME_VALUE, result.getUsername());
        assertEquals("", result.getPassword());
    }

    private ApiConnectionInfo getApiConnectionInfoFromArgumentsWithoutException()
    {
        try {
            return this.target.getApiConnectionInfoFromArguments(MOCK_ARGUMENTS);
        } catch (UnknownArgumentException e) {
            fail("ApiConnectionInfoGatherer threw an ArgumentMissingValueException when it should not have.");
        } catch (ArgumentMissingValueException e) {
            fail("ApiConnectionInfoGatherer threw an ArgumentMissingValueException when it should not have.");
        }

        // shouldn't get here
        return null;
    }


    @Test
    public void getApiConnectionInfoFromArguments_handlesUnknownArgumentException()
    {
        tellMockArgumentParserToThrowMockUnknownArgumentException();
        tellMockMapToReturnMockValueForFlag(HOST_FLAG, MOCK_HOST_VALUE);
        tellMockMapToReturnMockValueForFlag(USERNAME_FLAG, MOCK_USERNAME_VALUE);
        tellMockMapToReturnMockValueForFlag(PASSWORD_FLAG, "");

        try {
            this.target.getApiConnectionInfoFromArguments(MOCK_ARGUMENTS);
        } catch (UnknownArgumentException e) {
            // test passes
            return;
        } catch (ArgumentMissingValueException e) {
            fail("ApiConnectionInfoGatherer threw an ArgumentMissingValueException instead of an UnknownArgumentException.");
        }

        fail("ApiConnectionInfoGatherer did not throw an UnknownArgumentException when it should have.");

    }

    @Test
    public void getApiConnectionInfoFromArguments_handlesArgumentMissingValueException()
    {
        tellMockArgumentParserToThrowMockArgumentMissingValueException();
        tellMockMapToReturnMockValueForFlag(HOST_FLAG, MOCK_HOST_VALUE);
        tellMockMapToReturnMockValueForFlag(USERNAME_FLAG, MOCK_USERNAME_VALUE);
        tellMockMapToReturnMockValueForFlag(PASSWORD_FLAG, "");

        try {
            this.target.getApiConnectionInfoFromArguments(MOCK_ARGUMENTS);
        } catch (UnknownArgumentException e) {
            fail("ApiConnectionInfoGatherer threw an UnknownArgumentException instead of an ArgumentMissingValueException.");
        } catch (ArgumentMissingValueException e) {
            // test passes
            return;
        }

        fail("ApiConnectionInfoGatherer did not throw an UnknownArgumentException when it should have.");

    }

    @Test
    public void getMissingApiConnectionInfoFromUser()
    {
        when(this.apiConnectionInfo.hasHost()).thenReturn(false);
        when(this.apiConnectionInfo.hasUsername()).thenReturn(false);
        when(this.apiConnectionInfo.hasPassword()).thenReturn(false);

        ApiConnectionInfo result;
        try {
            when(this.mockShell.getHostFromUser()).thenReturn(MOCK_HOST_VALUE);
            when(this.mockShell.getUsernameFromUser()).thenReturn(MOCK_USERNAME_VALUE);
            when(this.mockShell.getPasswordFromUser()).thenReturn(MOCK_PASSWORD_VALUE);
            result = this.target.getMissingApiConnectionInfoFromUser(this.apiConnectionInfo, this.mockShell);
        } catch (ExitException e) {
            fail("Unexpected ExitException when getting missing API connection info from user: " + e.getExitMessage());
            return;
        }

        assertEquals(MOCK_HOST_VALUE, result.getHost());
        assertEquals(MOCK_USERNAME_VALUE, result.getUsername());
        assertEquals(MOCK_PASSWORD_VALUE, result.getPassword());
    }

    private void tellMockArgumentParserToThrowMockUnknownArgumentException()
    {
        tellMockArgumentParserToThrowMockException(this.mockUnknownArgumentException);
}

    private void tellMockArgumentParserToThrowMockArgumentMissingValueException()
    {
        tellMockArgumentParserToThrowMockException(this.mockArgumentMissingValueException);
    }

    private void tellMockArgumentParserToThrowMockException(Exception mockException)
    {
        try {
            when(this.mockArgumentParser.parse(MOCK_ARGUMENTS)).thenThrow(mockException);
        } catch (UnknownArgumentException e) {
            failDueToUnexpectedExceptionWhileSettingUpMockBehavior(e);
        } catch (ArgumentMissingValueException e) {
            failDueToUnexpectedExceptionWhileSettingUpMockBehavior(e);
        }
    }

    private void tellMockArgumentParserToReturnMockArgumentMap()
    {
        try {
            when(this.mockArgumentParser.parse(MOCK_ARGUMENTS)).thenReturn(this.mockArgumentMap);
        } catch (UnknownArgumentException e) {
            failDueToUnexpectedExceptionWhileSettingUpMockBehavior(e);
        } catch (ArgumentMissingValueException e) {
            failDueToUnexpectedExceptionWhileSettingUpMockBehavior(e);
        }
    }

    private void tellMockMapToReturnMockValueForFlag(String flag, String value)
    {
        when(this.mockArgumentMap.getValueOrEmptyString(flag)).thenReturn(value);
    }

    private void failDueToUnexpectedExceptionWhileSettingUpMockBehavior(Exception e)
    {
        fail("Unexpected " + e.getClass() + " while setting up mock behavior.");
    }
}
