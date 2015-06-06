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

    public boolean hasAllInfo()
    {
        return !nullOrEmpty(this.host) && !nullOrEmpty(this.username) && !nullOrEmpty(this.password);
    }

    public void merge(ApiConnectionInfo apiConnectionInfo)
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

    private boolean nullOrEmpty(String info)
    {
        return info != null && !info.isEmpty();
    }

    @Override
    public String toString()
    {
        return "host: <" + this.host + ">\nusername: <" + this.username + ">\npassword: <" + this.password + ">";
    }

}
