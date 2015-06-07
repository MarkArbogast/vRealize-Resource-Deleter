package com.bluemedora.api;

import com.bluemedora.exceptions.ExitException;
import com.vmware.ops.api.client.Client;
import com.vmware.ops.api.client.exceptions.AuthException;
import com.vmware.ops.api.model.common.PageInfo;
import com.vmware.ops.api.model.resource.ResourceQuery;


public class SuiteApi
{
    public static void testApiConnectionInfo(ApiConnectionInfo apiConnectionInfo) throws ExitException
    {
        Client client = getClient(apiConnectionInfo.getHost(), apiConnectionInfo.getUsername(), apiConnectionInfo.getPassword());

        try {
            client.resourcesClient().getResources(new ResourceQuery(), new PageInfo());
        } catch (AuthException e) {
            throw new ExitException("Invalid username or password.");
        } catch (Exception e) {
            throw new ExitException("Invalid hostname.");
        }
    }

    private static Client getClient(String vRealizeHostname, String vRealizeAdminUser, String vRealizePassword)
    {
        return Client.ClientConfig.builder().basicAuth(vRealizeAdminUser, vRealizePassword).useJson()
                .serverUrl("https://" + vRealizeHostname + "/suite-api/")
                .verify("false")
                .ignoreHostName(true)
                .build()
                .newClient();
    }
}

