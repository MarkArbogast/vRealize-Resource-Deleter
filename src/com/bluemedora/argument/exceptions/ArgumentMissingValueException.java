package com.bluemedora.argument.exceptions;

public class ArgumentMissingValueException extends Exception
{
    private final String argumentMissingValue;

    public ArgumentMissingValueException(String argumentMissingValue)
    {
        this.argumentMissingValue = argumentMissingValue;
    }

    public ArgumentMissingValueException()
    {
        this.argumentMissingValue = "";
    }

    public String getArgumentMissingValue()
    {
        return this.argumentMissingValue;
    }
}
