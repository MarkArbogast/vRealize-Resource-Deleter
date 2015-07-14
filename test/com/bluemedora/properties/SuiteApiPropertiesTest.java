package com.bluemedora.properties;

import com.bluemedora.properties.exceptions.FailedToClosePropertiesFileException;
import com.bluemedora.properties.exceptions.FailedToLoadPropertiesFileException;
import com.bluemedora.properties.exceptions.PropertyNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SuiteApiPropertiesTest
{
    private static final String MOCK_VALUE = "mockValue";
    private SuiteApiProperties target;
    private PropertiesFile     mockPropertiesFile;

    @Before
    public void setUp()
    {
        this.mockPropertiesFile = mock(PropertiesFile.class);
        this.target = new SuiteApiProperties(this.mockPropertiesFile);
    }

    @Test
    public void getHost()
    {
        try {
            when(this.mockPropertiesFile.getProperty(SuiteApiProperties.HOST)).thenReturn(MOCK_VALUE);
        } catch (PropertyNotFoundException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToLoadPropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToClosePropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        }

        assertEquals(MOCK_VALUE, this.target.getHostOrEmptyString());
    }

    private void failDueToUnexpectedException(Exception e, String property)
    {
        fail("Unexpected " + e.getClass() + " when getting " + property);
    }

    @Test
    public void getHost_returnsEmptyStringForPropertyNotFoundException()
    {
        try {
            when(this.mockPropertiesFile.getProperty(SuiteApiProperties.HOST)).thenThrow(PropertyNotFoundException.class);
        } catch (PropertyNotFoundException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToLoadPropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToClosePropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        }

        assertEquals("", this.target.getHostOrEmptyString());
    }

    @Test
    public void getHost_returnsEmptyStringForFailedToLoadPropertiesFileException()
    {
        try {
            when(this.mockPropertiesFile.getProperty(SuiteApiProperties.HOST)).thenThrow(FailedToLoadPropertiesFileException.class);
        } catch (PropertyNotFoundException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToLoadPropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToClosePropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        }

        assertEquals("", this.target.getHostOrEmptyString());
    }

    @Test
    public void getHost_returnsEmptyStringForFailedToClosePropertiesFileException()
    {
        try {
            when(this.mockPropertiesFile.getProperty(SuiteApiProperties.HOST)).thenThrow(FailedToClosePropertiesFileException.class);
        } catch (PropertyNotFoundException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToLoadPropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        } catch (FailedToClosePropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.HOST);
        }

        assertEquals("", this.target.getHostOrEmptyString());
    }

    @Test
    public void getUsername()
    {
        try {
            when(this.mockPropertiesFile.getProperty(SuiteApiProperties.USERNAME)).thenReturn(MOCK_VALUE);
        } catch (PropertyNotFoundException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.USERNAME);
        } catch (FailedToLoadPropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.USERNAME);
        } catch (FailedToClosePropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.USERNAME);
        }

        assertEquals(MOCK_VALUE, this.target.getUsernameOrEmptyString());
    }

    @Test
    public void getPassword()
    {
        try {
            when(this.mockPropertiesFile.getProperty(SuiteApiProperties.PASSWORD)).thenReturn(MOCK_VALUE);
        } catch (PropertyNotFoundException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.PASSWORD);
        } catch (FailedToLoadPropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.PASSWORD);
        } catch (FailedToClosePropertiesFileException e) {
            failDueToUnexpectedException(e, SuiteApiProperties.PASSWORD);
        }

        assertEquals(MOCK_VALUE, this.target.getPasswordOrEmptyString());
    }
}

