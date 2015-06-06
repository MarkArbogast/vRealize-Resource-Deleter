package com.bluemedora.exceptions;

public class ExitException extends Exception
{
    private final String exitMessage;

    public ExitException(String exitMessage)
    {
        this.exitMessage = exitMessage;
    }

    public String getExitMessage()
    {
        return exitMessage;
    }
}
