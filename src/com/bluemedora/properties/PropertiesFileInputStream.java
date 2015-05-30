package com.bluemedora.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesFileInputStream extends FileInputStream
{
    private final String path;

    public PropertiesFileInputStream(String path) throws FileNotFoundException
    {
        super(path);
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }
}
