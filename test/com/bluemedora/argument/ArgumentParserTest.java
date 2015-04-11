package com.bluemedora.argument;

import com.bluemedora.api.ApiConnectionInfoGatherer;
import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.FlagNotFoundException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.fail;

public class ArgumentParserTest
{
    private ArgumentParser target;

    @Before
    public void setTarget()
    {
        this.target = new ArgumentParser(ApiConnectionInfoGatherer.API_CONNECTION_INFO_ARGUMENT_MAP);
    }

    @Test
    public void throwsUnknownArgumentExceptionForUnknownArgument()
    {
        String unknownArgument = "unknownArgument";
        String[] unknownArguments = {unknownArgument};
        try {
            target.parse(unknownArguments);
        } catch (UnknownArgumentException e) {
            assert (e.getUnknownArgument().equals(unknownArgument));
            // test passes
            return;
        } catch (ArgumentMissingValueException e) {
            fail("ArgumentParser threw an ArgumentMissingValueException instead of an UnknownArgumentException when given an unknown argument.");
        }
        fail("ArgumentParser failed to throw an UnknownArgumentException when given an unknown argument.");
    }

    @Test
    public void throwsArgumentMissingValueExceptionForArgumentMissingValue()
    {
        for (String argumentMissingValue : ApiConnectionInfoGatherer.ARGUMENT_FLAGS) {
            String[] argumentsMissingValue = {argumentMissingValue};
            try {
                target.parse(argumentsMissingValue);
            } catch (ArgumentMissingValueException e) {
                assert (e.getArgumentMissingValue().equals(argumentMissingValue));
                // test passes
                continue;
            } catch (UnknownArgumentException e) {
                fail("ArgumentParser threw an UnknownArgumentException instead of an ArgumentMissingValueException when given an argument without a value.");
            }
            fail("ArgumentParser failed to throw an ArgumentMissingValueException when given an argument without a value.");
        }
    }

    @Test
    public void throwsArgumentMissingValueExceptionForConsecutiveValidArgumentFlags()
    {
        String[] consecutiveValidArgumentFlags = {ApiConnectionInfoGatherer.HOST_FLAG, ApiConnectionInfoGatherer.USERNAME_FLAG};
        try {
            target.parse(consecutiveValidArgumentFlags);
        } catch (ArgumentMissingValueException e) {
            assert (e.getArgumentMissingValue().equals(ApiConnectionInfoGatherer.HOST_FLAG));
            // test passes
            return;
        } catch (UnknownArgumentException e) {
            fail("ArgumentParser threw an UnknownArgumentException instead of an ArgumentMissingValueException when given two consecutive argument flags.");
        }
        fail("ArgumentParser failed to throw an ArgumentMissingValueException when given two consecutive valid argument flags.");
    }

    @Test
    public void parsesNoArguments()
    {
        String[] validArguments = {};

        ArgumentMap argumentMap;

        try {
            argumentMap = target.parse(validArguments);
        } catch (ArgumentMissingValueException e) {
            fail("ArgumentParser threw an unexpected ArgumentMissingValueException while parsing valid arguments.");
            return;
        } catch (UnknownArgumentException e) {
            fail("ArgumentParser threw an unexpected UnknownArgumentException while parsing valid arguments.");
            return;
        }


        for (String argumentFlag : ApiConnectionInfoGatherer.ARGUMENT_FLAGS) {
            try {
                argumentMap.get(argumentFlag);
            } catch (FlagNotFoundException e) {
                // test passes
                continue;
            }
            fail("ArgumentParser returned a value for <" + argumentFlag + "> even though no arguments were provided.");
        }
    }

    @Test
    public void parsesValidArguments()
    {
        String host = "host";
        String username = "username";
        String password = "password";
        String[] validArguments = {ApiConnectionInfoGatherer.HOST_FLAG, host, ApiConnectionInfoGatherer.USERNAME_FLAG, username, ApiConnectionInfoGatherer.PASSWORD_FLAG, password};

        ArgumentMap argumentMap;

        try {
            argumentMap = target.parse(validArguments);
        } catch (ArgumentMissingValueException e) {
            fail("ArgumentParser threw an unexpected ArgumentMissingValueException while parsing valid arguments.");
            return;
        } catch (UnknownArgumentException e) {
            fail("ArgumentParser threw an unexpected UnknownArgumentException while parsing valid arguments.");
            return;
        }

        try {
            assertEquals(host, argumentMap.get(ApiConnectionInfoGatherer.HOST_FLAG));
            assertEquals(username, argumentMap.get(ApiConnectionInfoGatherer.USERNAME_FLAG));
            assertEquals(password, argumentMap.get(ApiConnectionInfoGatherer.PASSWORD_FLAG));
        } catch (FlagNotFoundException e) {
            fail("ArgumentParser failed to parse host, username, or password even though all three were provided.");
        }
    }
}
