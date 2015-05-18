package com.bluemedora.argument;

import com.bluemedora.argument.exceptions.ArgumentMissingValueException;
import com.bluemedora.argument.exceptions.UnknownArgumentException;

public class ArgumentParser
{
    private final ArgumentMap argumentMap;

    public ArgumentParser(ArgumentMap argumentMap)
    {
        this.argumentMap = argumentMap;
    }

    public ArgumentMap parse(String[] arguments) throws UnknownArgumentException, ArgumentMissingValueException
    {
        ArgumentMap argumentMap = new ArgumentMap();

        for (int i = 0; i < arguments.length; i += 2) {
            Argument argument = parseValidArgumentAtIndex(arguments, i);
            argumentMap.add(argument);
        }

        return argumentMap;
    }

    private Argument parseValidArgumentAtIndex(String[] arguments, int index) throws UnknownArgumentException, ArgumentMissingValueException
    {
        String argumentFlag = arguments[index];
        validateArgumentFlag(argumentFlag);

        String argumentValue;
        try {
            argumentValue = getArgumentValueAtIndex(arguments, index + 1);
            validateArgumentValue(argumentValue);
        } catch (ArgumentMissingValueException e) {
            throw new ArgumentMissingValueException(argumentFlag);
        }

        return new Argument(argumentFlag, argumentValue);
    }

    private void validateArgumentFlag(String argumentFlag) throws UnknownArgumentException
    {
        if (!argumentMap.hasFlag(argumentFlag)) {
            throw new UnknownArgumentException(argumentFlag);
        }
    }

    private String getArgumentValueAtIndex(String[] arguments, int index) throws ArgumentMissingValueException
    {
        String argumentValue;

        try {
            argumentValue = arguments[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArgumentMissingValueException();
        }

        return argumentValue;
    }

    private void validateArgumentValue(String argumentValue) throws ArgumentMissingValueException
    {
        // flags shouldn't be values
        if (argumentMap.hasFlag(argumentValue)) {
            throw new ArgumentMissingValueException();
        }
    }
}
