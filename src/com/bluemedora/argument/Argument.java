package com.bluemedora.argument;

public class Argument
{
    private final String flag;
    private final String value;

    public Argument(String flag, String value)
    {
        this.flag = flag;
        this.value = value;
    }

    public String getFlag()
    {
        return flag;
    }

    public String getValue()
    {
        return value;
    }
}
