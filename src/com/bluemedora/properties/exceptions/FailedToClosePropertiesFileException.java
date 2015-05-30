package com.bluemedora.properties.exceptions;

public class FailedToClosePropertiesFileException extends Exception
{
    private final String file;

    public FailedToClosePropertiesFileException(String file)
    {
        this.file = file;
    }

    public String getFile()
    {
        return this.file;
    }
}
