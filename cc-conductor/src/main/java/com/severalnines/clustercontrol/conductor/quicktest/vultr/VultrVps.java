/*
 *     Copyright 2022 Severalnines Inc. @ https://severalnines.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.severalnines.clustercontrol.conductor.quicktest.vultr;

import com.severalnines.clustercontrol.conductor.infraprovider.InfraProviderBase;
import org.openapitools.vultrapi.client.ApiClient;
import org.openapitools.vultrapi.client.ApiException;
import org.openapitools.vultrapi.client.Configuration;
import org.openapitools.vultrapi.client.api.*;
import org.openapitools.vultrapi.client.auth.HttpBearerAuth;
import org.openapitools.vultrapi.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VultrVps extends InfraProviderBase {

    private static final Logger logger
            = LoggerFactory.getLogger(VultrVps.class);

    private ApiClient defaultClient;

    public VultrVps() {
        // Setup the default client
        defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.vultr.com/v2");
        // defaultClient.setApiKey("");

        // Configure HTTP bearer authorization: API Key
        HttpBearerAuth API_Key = (HttpBearerAuth) defaultClient.getAuthentication("API Key");
        // API_Key.setBearerToken("");
        API_Key.setBearerToken(System.getenv("VULTR_API_KEY"));
    }

    private void listFirewallGroups() {
        FirewallApi apiInstance = new FirewallApi(defaultClient);
        Integer perPage = 10; // Integer | Number of items requested per page. Default is 100 and Max is 500.
        String cursor = "cursor_example"; // String | Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination).
        try {
            ListFirewallGroups200Response result = apiInstance.listFirewallGroups(perPage, null);
            logger.info("ListFirewallGroups result: {}", result);
        } catch (ApiException e) {
            logger.warn("Exception in FirewallApi#listFirewallGroups: ", e);
        }
    }

    private void listPlans() {
        PlansApi apiInstance = new PlansApi(defaultClient);
        // Reference: https://www.vultr.com/api/#operation/list-plans
        // String type = "vc2";
        String type = "all";
        Integer perPage = 50;
        String cursor = "cursor_example";
        String os = "ubuntu";
        try {
            ListPlans200Response result = apiInstance.listPlans(type, perPage, null, null);
            logger.info("ListPlans result: {}", result);
        } catch (ApiException e) {
            logger.warn("Exception in PlansApi#listPlans: ", e);
        }
    }

    private void listPrivateNetworks() {
        PrivateNetworksApi apiInstance = new PrivateNetworksApi(defaultClient);
        Integer perPage = 56; // Integer | Number of items requested per page. Default is 100 and Max is 500.
        String cursor = "cursor_example"; // String | Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination).
        try {
            ListNetworks200Response result = apiInstance.listNetworks(perPage, null);
            logger.info("ListPrivateNetworks result: {}", result);
        } catch (ApiException e) {
            logger.warn("Exception in PrivateNetworksApi#listNetworks: ", e);
        }
    }

    private void listSshKeys() {
        SshApi apiInstance = new SshApi(defaultClient);
        Integer perPage = 10; // Integer | Number of items requested per page. Default is 100 and Max is 500.
        String cursor = "cursor_example"; // String | Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination).
        try {
            ListSshKeys200Response result = apiInstance.listSshKeys(perPage, null);
            logger.info("ListSshKeys result: {}", result);
        } catch (ApiException e) {
            logger.warn("Exception in SshApi#listSshKeys: ", e);
        }
    }

    private void listOSs() {
        OsApi apiInstance = new OsApi(defaultClient);
        Integer perPage = 100; // Integer | Number of items requested per page. Default is 100 and Max is 500.
        String cursor = "cursor_example"; // String | Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination).
        try {
            ListOs200Response result = apiInstance.listOs(perPage, null);
            logger.info("ListOSs result: {}", result);
        } catch (ApiException e) {
            logger.warn("Exception in OsApi#listOs: ", e);
        }
    }

    private void listRegions() {
        RegionApi apiInstance = new RegionApi(defaultClient);
        Integer perPage = 300; // Integer | Number of items requested per page. Default is 100 and Max is 500.
        String cursor = "cursor_example"; // String | Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination).
        try {
            ListRegions200Response result = apiInstance.listRegions(perPage, null);
            logger.info("ListRegions result: {}", result);
        } catch (ApiException e) {
            logger.warn("Exception in RegionApi#listRegions: ", e);
        }
    }

    public static void main(String[] args) {
        VultrVps vt = new VultrVps();

        vt.listRegions();
        vt.listPrivateNetworks();
        vt.listSshKeys();
        vt.listFirewallGroups();
        vt.listPlans();
        vt.listOSs();
    }

}
