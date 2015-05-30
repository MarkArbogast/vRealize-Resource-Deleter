package com.bluemedora.properties;

import com.bluemedora.properties.exceptions.FailedToClosePropertiesFileException;
import com.bluemedora.properties.exceptions.FailedToLoadPropertiesFileException;
import com.bluemedora.properties.exceptions.PropertyNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class PropertiesFileTest
{
    private static final String TEST_FILES_PATH         = "test/files/";
    private static final String TEST_PROPERTIES         = TEST_FILES_PATH + "test.properties";
    private static final String NON_EXISTENT_PROPERTIES = TEST_FILES_PATH + "fake.properties";

    private static final String TEST_KEY         = "testKey";
    private static final String NON_EXISTENT_KEY = "fakeKey";

    private static final String TEST_VALUE = "testValue";

    private PropertiesFile target;

    @Before
    public void setUp()
    {
        this.target = new PropertiesFile(TEST_PROPERTIES);
    }

    @Test
    public void getProperty()
    {
        this.target = new PropertiesFile(TEST_PROPERTIES);
        String value;
        try {
            value = this.target.getProperty(TEST_KEY);
            assertEquals(TEST_VALUE, value);
        } catch (PropertyNotFoundException wrongException) {
            receivedUnexpectedException(wrongException);
        } catch (FailedToLoadPropertiesFileException wrongException) {
            receivedUnexpectedException(wrongException);
        } catch (FailedToClosePropertiesFileException wrongException) {
            receivedUnexpectedException(wrongException);
        }
    }

    private void receivedUnexpectedException(Exception unexpectedException)
    {
        fail("Unexpected exception while retrieving property: " + unexpectedException);
    }

    @Test
    public void getProperty_throwsFailedToLoadPropertiesFileException()
    {
        this.target = new PropertiesFile(NON_EXISTENT_PROPERTIES);
        try {
            this.target.getProperty(TEST_KEY);
        } catch (PropertyNotFoundException wrongException) {
            receivedWrongException(FailedToLoadPropertiesFileException.class, wrongException);
        } catch (FailedToLoadPropertiesFileException expectedException) {
            // success
            return;
        } catch (FailedToClosePropertiesFileException wrongException) {
            receivedWrongException(FailedToLoadPropertiesFileException.class, wrongException);
        }
    }

    @Test
    public void getProperty_throwsPropertyNotFoundException()
    {
        try {
            this.target.getProperty(NON_EXISTENT_KEY);
        } catch (PropertyNotFoundException expectedException) {
            // success
            return;
        } catch (FailedToLoadPropertiesFileException wrongException) {
            receivedWrongException(PropertyNotFoundException.class, wrongException);
        } catch (FailedToClosePropertiesFileException wrongException) {
            receivedWrongException(PropertyNotFoundException.class, wrongException);
        }
    }

    private void receivedWrongException(Class expectedException, Exception wrongException)
    {
        fail("Expected " + expectedException.toString() + ", received " + wrongException.getClass() + ".");
    }
}
