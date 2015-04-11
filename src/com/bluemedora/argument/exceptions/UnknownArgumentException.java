package com.bluemedora.argument.exceptions;

public class UnknownArgumentException extends Exception
{
    private final String unknownArgument;

    public UnknownArgumentException(String unknownArgument)
    {
        this.unknownArgument = unknownArgument;
    }

    public String getUnknownArgument()
    {
        return this.unknownArgument;
    }
}
