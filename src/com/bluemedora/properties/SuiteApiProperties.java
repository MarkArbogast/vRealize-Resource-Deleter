package com.bluemedora.properties;

import com.bluemedora.properties.exceptions.FailedToClosePropertiesFileException;
import com.bluemedora.properties.exceptions.FailedToLoadPropertiesFileException;
import com.bluemedora.properties.exceptions.PropertyNotFoundException;

public class SuiteApiProperties
{
    public static final String HOST     = "host";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private final PropertiesFile propertiesFile;

    public SuiteApiProperties(PropertiesFile propertiesFile)
    {
        this.propertiesFile = propertiesFile;
    }

    public String getHostOrEmptyString()
    {
        try {
            return this.propertiesFile.getProperty(HOST);
        } catch (PropertyNotFoundException e) {
            return "";
        } catch (FailedToLoadPropertiesFileException e) {
            return "";
        } catch (FailedToClosePropertiesFileException e) {
            return "";
        }
    }

    public String getUsernameOrEmptyString()
    {
        try {
            return this.propertiesFile.getProperty(USERNAME);
        } catch (PropertyNotFoundException e) {
            return "";
        } catch (FailedToLoadPropertiesFileException e) {
            return "";
        } catch (FailedToClosePropertiesFileException e) {
            return "";
        }
    }

    public String getPasswordOrEmptyString()
    {
        try {
            return this.propertiesFile.getProperty(PASSWORD);
        } catch (PropertyNotFoundException e) {
            return "";
        } catch (FailedToLoadPropertiesFileException e) {
            return "";
        } catch (FailedToClosePropertiesFileException e) {
            return "";
        }
    }
}
