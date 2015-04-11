package com.bluemedora.argument.exceptions;

public class FlagNotFoundException extends Exception
{
    private final String flag;

    public FlagNotFoundException(String flag)
    {
        this.flag = flag;
    }
}
