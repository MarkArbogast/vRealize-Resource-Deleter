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

    public ApiConnectionInfo()
    {
        this.host = "";
        this.username = "";
        this.password = "";
    }

    public boolean hasAllInfo()
    {
        return hasHost() && hasUsername() && hasPassword();
    }

    public boolean hasHost()
    {
        return !nullOrEmpty(this.host);
    }

    public boolean hasUsername()
    {
        return !nullOrEmpty(this.username);
    }

    public boolean hasPassword()
    {
        return !nullOrEmpty(this.password);
    }

    private boolean nullOrEmpty(String info)
    {
        return info == null || info.isEmpty();
    }

    // Prioritize existing values.
    public void mergeNewValues(ApiConnectionInfo apiConnectionInfo)
    {
        if (nullOrEmpty(this.host)) {
            this.host = apiConnectionInfo.getHost();
        }

        if (nullOrEmpty(this.username)) {
            this.username = apiConnectionInfo.getUsername();
        }

        if (nullOrEmpty(this.password)) {
            this.password = apiConnectionInfo.getPassword();
        }
    }

    public String getHost()
    {
        return this.host;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    @Override
    public String toString()
    {
        return "host: <" + this.host + ">\nusername: <" + this.username + ">\npassword: <" + this.password + ">";
    }

}
