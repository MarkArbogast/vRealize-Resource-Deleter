package com.bluemedora.api;

import com.bluemedora.exceptions.ExitException;
import com.vmware.ops.api.client.Client;
import com.vmware.ops.api.client.exceptions.AuthException;
import com.vmware.ops.api.model.common.PageInfo;
import com.vmware.ops.api.model.common.types.RelationshipType;
import com.vmware.ops.api.model.resource.ResourceDto;
import com.vmware.ops.api.model.resource.ResourceQuery;

import java.util.ArrayList;
import java.util.List;


public class SuiteApi
{
    public static void testApiConnectionInfo(ApiConnectionInfo apiConnectionInfo) throws ExitException
    {
        Client client = getClient(apiConnectionInfo.getHost(), apiConnectionInfo.getUsername(), apiConnectionInfo.getPassword());

        System.out.println("Testing vRealize Suite API connection...");

        try {
            client.resourcesClient().getResources(new ResourceQuery(), new PageInfo());
        } catch (AuthException e) {
            throw new ExitException("Invalid username or password.");
        } catch (Exception e) {
            throw new ExitException("Invalid hostname.");
        }

        System.out.println("\nConnection successful.\n");
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

    public static void deleteResource(ApiConnectionInfo apiConnectionInfo, ResourceDto resourceToDelete) throws ExitException
    {
        Client client = getClient(apiConnectionInfo.getHost(), apiConnectionInfo.getUsername(), apiConnectionInfo.getPassword());
        String resourceName = resourceToDelete.getResourceKey().getName();
        try {
            System.out.println("Attempting to trigger resource deletion for <" + resourceName + ">...");
            client.resourcesClient().deleteResource(resourceToDelete.getIdentifier());
            System.out.println("...Successfully triggered deletion of resource <" + resourceName + ">");
        } catch (AuthException e) {
            throw new ExitException("Invalid username or password.");
        } catch (Exception e) {
            throw new ExitException("Invalid resource.");
        }
    }

    public static List<ResourceDto> getMatchingResources(ApiConnectionInfo apiConnectionInfo, String resourceToDelete) throws ExitException
    {
        Client client = getClient(apiConnectionInfo.getHost(), apiConnectionInfo.getUsername(), apiConnectionInfo.getPassword());
        ResourceQuery nameQuery = new ResourceQuery();
        nameQuery.setName(new String[]{resourceToDelete});

        List<ResourceDto> matchingResources = client.resourcesClient().getResources(nameQuery, new PageInfo()).getResourceList();
        if (matchingResources == null || matchingResources.size() < 1) {
            throw new ExitException("No resource found with name <" + resourceToDelete + ">");
        }

        return matchingResources;
    }

    public static List<ResourceDto> getChildren(ApiConnectionInfo apiConnectionInfo, ResourceDto resourceDtoToDelete)
    {
        Client client = getClient(apiConnectionInfo.getHost(), apiConnectionInfo.getUsername(), apiConnectionInfo.getPassword());

        PageInfo pageInfo = new PageInfo(0, 5000, 5000);
        ResourceDto.ResourceRelationDto resourceDtoToDeleteDescendants;
        List<ResourceDto> children = new ArrayList<ResourceDto>();
        do {
            resourceDtoToDeleteDescendants = client.resourcesClient().getRelationships(resourceDtoToDelete.getIdentifier(), RelationshipType.DESCENDANT, new PageInfo());
            children.addAll(resourceDtoToDeleteDescendants.getResourceList());
            pageInfo.setPage(pageInfo.getPage() + 1);
        } while (resourceDtoToDeleteDescendants.getResourceList().size() == 5000);

        return children;
    }
}

