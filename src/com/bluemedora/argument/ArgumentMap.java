package com.bluemedora.argument;

import com.bluemedora.argument.exceptions.FlagNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ArgumentMap
{
    private Map<String, String> argumentValueMap = new HashMap<String, String>();

    public void add(Argument argument)
    {
        String flag = emptyIfNull(argument.getFlag());
        String value = emptyIfNull(argument.getValue());
        this.argumentValueMap.put(flag, value);
    }

    private String emptyIfNull(String string)
    {
        return string == null ? "" : string;
    }

    public String get(String flag) throws FlagNotFoundException
    {
        if (this.argumentValueMap.containsKey(flag)) {
            return this.argumentValueMap.get(flag);
        }
        throw new FlagNotFoundException(flag);
    }

    public boolean hasFlag(String flag)
    {
        return argumentValueMap.containsKey(flag);
    }
}
