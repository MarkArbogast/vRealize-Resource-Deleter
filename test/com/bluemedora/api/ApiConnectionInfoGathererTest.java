package com.bluemedora.api;

import com.bluemedora.argument.ArgumentMap;
import com.bluemedora.argument.ArgumentParser;
import org.junit.Before;

public class ApiConnectionInfoGathererTest
{
    private ApiConnectionInfoGatherer target;

    @Before
    public void setTarget()
    {
        ArgumentMap argumentMap = new ArgumentMap();
        ArgumentParser argumentParser = new ArgumentParser(argumentMap);

        this.target = new ApiConnectionInfoGatherer(argumentParser);
    }
}
