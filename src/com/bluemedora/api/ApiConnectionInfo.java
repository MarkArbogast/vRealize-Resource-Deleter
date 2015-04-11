package com.bluemedora.api;

public class ApiConnectionInfo
{
    private String host;
    private String username;
    private String password;

    public ApiConnectionInfo(String host, String username, String password)
    {
        this.host = host;
        this.username = username;
        this.password = password;
    }


    public String getHost()
    {
        return host;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
