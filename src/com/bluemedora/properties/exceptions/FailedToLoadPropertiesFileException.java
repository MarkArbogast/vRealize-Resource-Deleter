package com.bluemedora.properties.exceptions;

public class FailedToLoadPropertiesFileException extends Exception
{
    private final String file;

    public FailedToLoadPropertiesFileException(String file)
    {
        this.file = file;
    }

    public String getFile()
    {
        return file;
    }
}
