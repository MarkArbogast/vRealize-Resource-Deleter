package com.bluemedora.properties;

import com.bluemedora.properties.exceptions.FailedToClosePropertiesFileException;
import com.bluemedora.properties.exceptions.FailedToLoadPropertiesFileException;
import com.bluemedora.properties.exceptions.PropertyNotFoundException;

import java.io.IOException;
import java.util.Properties;

public class PropertiesFile
{
    private final String path;

    public PropertiesFile(String path)
    {
        this.path = path;
    }

    public String getProperty(String property) throws PropertyNotFoundException, FailedToLoadPropertiesFileException, FailedToClosePropertiesFileException
    {
        PropertiesFileInputStream propertiesFileInputStream = getPropertiesFileInputStream(this.path);

        try {
            return getPropertyFromFileInputStream(propertiesFileInputStream, property);
        } finally {
            closeFileInputStream(propertiesFileInputStream);
        }
    }

    private PropertiesFileInputStream getPropertiesFileInputStream(String path) throws FailedToLoadPropertiesFileException
    {
        try {
            return new PropertiesFileInputStream(path);
        } catch (IOException e) {
            throw new FailedToLoadPropertiesFileException(path);
        }
    }

    private String getPropertyFromFileInputStream(PropertiesFileInputStream propertiesFileInputStream, String property) throws FailedToClosePropertiesFileException, FailedToLoadPropertiesFileException, PropertyNotFoundException
    {
        Properties properties = getPropertiesFromFileInputStream(propertiesFileInputStream);

        String propertyValue = properties.getProperty(property);

        if (propertyValue == null) {
            throw new PropertyNotFoundException(property);
        }

        return propertyValue;

    }

    private Properties getPropertiesFromFileInputStream(PropertiesFileInputStream fileInputStream) throws FailedToLoadPropertiesFileException, FailedToClosePropertiesFileException
    {
        Properties properties = new Properties();

        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new FailedToLoadPropertiesFileException(fileInputStream.getPath());
        } finally {
            closeFileInputStream(fileInputStream);
        }

        return properties;
    }

    private void closeFileInputStream(PropertiesFileInputStream fileInputStream) throws FailedToClosePropertiesFileException
    {
        try {
            fileInputStream.close();
        } catch (IOException e) {
            throw new FailedToClosePropertiesFileException(fileInputStream.getPath());
        }
    }
}
