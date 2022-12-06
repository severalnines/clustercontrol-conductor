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
package com.severalnines.clustercontrol.conductor.infraprovider.vultr;

import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import com.severalnines.clustercontrol.conductor.infraprovider.*;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraInput;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraOutput;
import org.openapitools.vultrapi.client.ApiClient;
import org.openapitools.vultrapi.client.ApiException;
import org.openapitools.vultrapi.client.Configuration;
import org.openapitools.vultrapi.client.api.*;
import org.openapitools.vultrapi.client.auth.*;
import org.openapitools.vultrapi.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

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

    @Override
    public String createInstance(String jsonStr) throws Exception {
        CreateInfraInput in = JsonSerializeDeserialize.jsonToObject(jsonStr, CreateInfraInput.class);
        List<String> tags = new LinkedList<>();
        // tags.add(instLabel);
        tags.add(in.getClusterName());

        // Instantiate the API client
        InstancesApi instApi = new InstancesApi(defaultClient);

        // CreateInstanceRequest | Include a JSON object in the request body with a content type of **application/json**.
        CreateInstanceRequest createInstanceRequest = new CreateInstanceRequest();
        // createInstanceRequest.setImageId("");
        List<String> ssh = new LinkedList<>();
        ssh.add("5bcc28f5-809e-4a34-b41e-47902363b9b8");
        createInstanceRequest.setSshkeyId(ssh);
        createInstanceRequest.setEnableIpv6(false);
        createInstanceRequest.setRegion("ewr");
        List<String> privNet = new LinkedList<>();
        privNet.add("c758325d-cb2c-4638-9b8c-3f274c105e65");
        createInstanceRequest.setAttachPrivateNetwork(privNet);
        createInstanceRequest.setEnablePrivateNetwork(true);
        // createInstanceRequest.setHostname(instLabel);
        // createInstanceRequest.setLabel(instLabel);
        createInstanceRequest.setHostname(in.getClusterName());
        createInstanceRequest.setLabel(in.getClusterName());
        createInstanceRequest.setFirewallGroupId("5fd09b1e-8e98-4e4f-9160-392c56395a2b");
        createInstanceRequest.setPlan("vc2-1c-1gb");
        createInstanceRequest.setTags(tags);
        createInstanceRequest.setOsId(387);

        // Create the VM instance
        CreateInstance202Response result = instApi.createInstance(createInstanceRequest);

        CreateInfraOutput out = new CreateInfraOutput();
        out.setPrivateIp(result.getInstance().getInternalIp());
        out.setPublicIp(result.getInstance().getMainIp());
        out.setHostname(result.getInstance().getMainIp());
        logger.info("VM-ID: {}", result.getInstance().getId());
        out.setInternalVmId(result.getInstance().getId());
        logger.debug("Result: {}", result);

        return (JsonSerializeDeserialize.objectToJson(out));
    }

    @Override
    public String listInstance(String jsonStr) throws Exception {
        CreateInfraInput in = JsonSerializeDeserialize.jsonToObject(jsonStr, CreateInfraInput.class);

        /*
         * NOTE: Only one filter is allowed.
         */
        InstancesApi instApi = new InstancesApi(defaultClient);
        Integer perPage = 50; // Integer | Number of items requested per page. Default is 100 and Max is 500.
        // String cursor = "cursor_example"; // String | Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination).
        // String tag = "tag_example"; // String | Filter by specific tag.
        // String label = "openapi-java-client-test"; // String | Filter by label.

        String label = in.getClusterName(); // String | Filter by label.
        // String label = in.getInternalVmId();

        // String mainIp = "mainIp_example"; // String | Filter by main ip address.
        // String region = "ewr"; // String | Filter by [Region id](#operation/list-regions).
        String ret = null;

        try {
            int MAX_TIME = 1000*60*15;
            int SLEEP_TIME = 1000*5;
            while (MAX_TIME > 0) {
                // while (true) {
                ListInstances200Response result = instApi.listInstances(perPage, null, null, label, null, null);
                Instance i = result.getInstances().get(0);
                // assert i == null;
                // i.getMainIp();
                if (i != null && i.getStatus().compareToIgnoreCase("active")==0 &&
                        i.getServerStatus().compareToIgnoreCase("ok")==0) {
                    logger.debug("Result: {}", result);
                    logger.info("--------------------------");
                    logger.info("publicIP: {}", i.getMainIp());
                    logger.info("privateIP: {}", i.getInternalIp());
                    logger.info("--------------------------");
                    CreateInfraOutput out = new CreateInfraOutput();
                    out.setPrivateIp(i.getInternalIp());
                    out.setPublicIp(i.getMainIp());
                    out.setHostname(i.getMainIp());
                    out.setInternalVmId(in.getInternalVmId());
                    ret = JsonSerializeDeserialize.objectToJson(out);
                    break;
                } else {
                    logger.debug("VM Status: {}", i.getStatus());
                    logger.debug("VM Server Status: {}", i.getServerStatus());
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (Exception ex) {
                    logger.warn("Exception while polling for instande ready with IP: ", ex);
                } finally {
                    // System.out.println(MAX_TIME);
                    MAX_TIME -= SLEEP_TIME;
                    // System.out.println(MAX_TIME);
                }
            }
        } catch (ApiException e) {
            logger.warn("Exception polling for InstancesApi#listInstances: ", e);
        }

        return (ret);
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

        // ApiClient defaultClient = Configuration.getDefaultApiClient();
        // defaultClient.setBasePath("https://api.vultr.com/v2");
        // defaultClient.setApiKey("");

        // Configure HTTP bearer authorization: API Key
        // HttpBearerAuth API_Key = (HttpBearerAuth) defaultClient.getAuthentication("API Key");
        // API_Key.setBearerToken("");

        String instLabel = "openapi-java-client-test";
        // vt.createInstance(instLabel);
        // vt.listInstance(instLabel);
        vt.listFirewallGroups();
        vt.listPlans();
        vt.listRegions();
        vt.listOSs();
        vt.listPrivateNetworks();
        vt.listSshKeys();
    }

}
