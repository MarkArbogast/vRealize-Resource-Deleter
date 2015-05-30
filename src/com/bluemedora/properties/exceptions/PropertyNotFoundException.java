package com.bluemedora.properties.exceptions;

public class PropertyNotFoundException extends Exception
{
    private final String property;

    public PropertyNotFoundException(String property)
    {
        this.property = property;
    }

    public String getProperty()
    {
        return property;
    }
}
