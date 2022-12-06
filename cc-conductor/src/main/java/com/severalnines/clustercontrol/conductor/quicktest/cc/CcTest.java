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
package com.severalnines.clustercontrol.conductor.quicktest.cc;

import org.openapitools.ccapi.client.ApiClient;
import org.openapitools.ccapi.client.ApiException;
import org.openapitools.ccapi.client.ApiResponse;
import org.openapitools.ccapi.client.Configuration;
import org.openapitools.ccapi.client.api.AuthApi;
import org.openapitools.ccapi.client.api.DiscoveryApi;
import org.openapitools.ccapi.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcTest {

    private static final Logger logger
            = LoggerFactory.getLogger(CcTest.class);

    public CcTest() {
    }

    public void authenticateWithCmon() {
        String URL = System.getenv("CC_URL");
        String API_USER = System.getenv("CC_API_USER");
        String API_USER_PW = System.getenv("CC_API_USER_PW");
        logger.info("CC_URL: {}", URL);
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // defaultClient.setDebugging(true);
        defaultClient.setBasePath(URL);
        defaultClient.setVerifyingSsl(false);

        AuthApi authApiInstance = new AuthApi(defaultClient);
        Authenticate authenticate = new Authenticate(); // Authenticate | Authentication parameters
        authenticate.operation(Authenticate.OperationEnum.AUTHENTICATEWITHPASSWORD);
        authenticate.userName(API_USER);
        authenticate.password(API_USER_PW);
        try {
            // authApiInstance.authPost(authenticate);
            logger.debug("Auth request: {}", authenticate.toString());
            ApiResponse<Void> resp = authApiInstance.authPostWithHttpInfo(authenticate);
            logger.info("Auth response: {}", resp.getData());
        } catch (ApiException e) {
            logger.warn("Exception in AuthPost: ", e);
        }
    }

    private void discoveryTest() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        DiscoveryApi discovApiInstance = new DiscoveryApi(defaultClient);
        Discovery discovery = new Discovery(); // Discovery | All things related to Clusters and cluster Hosts
        discovery.setOperation(Discovery.OperationEnum.GETSUPPORTEDCLUSTERTYPES);
        try {
            // discovApiInstance.discoveryPost(discovery);
            logger.debug("Discovery request: {}", discovery.toString());
            ApiResponse<Void> resp = discovApiInstance.discoveryPostWithHttpInfo(discovery);
            logger.info("Discovery response: {}", resp.getData());
        } catch (ApiException e) {
            logger.warn("Exception in DiscoverPost: ", e);
        }
    }

    public static void main(String[] args) {
        CcTest cct = new CcTest();
        cct.authenticateWithCmon();
        cct.discoveryTest();
    }
}
