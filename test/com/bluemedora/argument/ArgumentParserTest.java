package com.bluemedora.argument;

import com.bluemedora.api.ApiConnectionInfoArguments;
import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.FlagNotFoundException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ArgumentParserTest
{
    private ArgumentParser target;

    @Before
    public void setTarget()
    {
        this.target = new ArgumentParser(ApiConnectionInfoArguments.API_CONNECTION_INFO_ARGUMENT_MAP);
    }

    @Test
    public void parse_throwsUnknownArgumentExceptionForUnknownArgument()
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
    public void parse_throwsArgumentMissingValueExceptionForArgumentMissingValue()
    {
        for (String argumentMissingValue : ApiConnectionInfoArguments.ARGUMENT_FLAGS) {
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
    public void parse_throwsArgumentMissingValueExceptionForConsecutiveValidArgumentFlags()
    {
        String[] consecutiveValidArgumentFlags = {ApiConnectionInfoArguments.HOST_FLAG, ApiConnectionInfoArguments.USERNAME_FLAG};
        try {
            target.parse(consecutiveValidArgumentFlags);
        } catch (ArgumentMissingValueException e) {
            assert (e.getArgumentMissingValue().equals(ApiConnectionInfoArguments.HOST_FLAG));
            // test passes
            return;
        } catch (UnknownArgumentException e) {
            fail("ArgumentParser threw an UnknownArgumentException instead of an ArgumentMissingValueException when given two consecutive argument flags.");
        }
        fail("ArgumentParser failed to throw an ArgumentMissingValueException when given two consecutive valid argument flags.");
    }

    @Test
    public void parse_parsesNoArguments()
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


        for (String argumentFlag : ApiConnectionInfoArguments.ARGUMENT_FLAGS) {
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
    public void parse_parsesValidArguments()
    {
        String host = "host";
        String username = "username";
        String password = "password";
        String[] validArguments = {ApiConnectionInfoArguments.HOST_FLAG, host, ApiConnectionInfoArguments.USERNAME_FLAG, username, ApiConnectionInfoArguments.PASSWORD_FLAG, password};

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
            assertEquals(host, argumentMap.get(ApiConnectionInfoArguments.HOST_FLAG));
            assertEquals(username, argumentMap.get(ApiConnectionInfoArguments.USERNAME_FLAG));
            assertEquals(password, argumentMap.get(ApiConnectionInfoArguments.PASSWORD_FLAG));
        } catch (FlagNotFoundException e) {
            fail("ArgumentParser failed to parse host, username, or password even though all three were provided.");
        }
    }
}
