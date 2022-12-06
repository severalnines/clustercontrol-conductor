/*
 * Vultr API
 * # Introduction  The Vultr API v2 is a set of HTTP endpoints that adhere to RESTful design principles and CRUD actions with predictable URIs. It uses standard HTTP response codes, authentication, and verbs. The API has consistent and well-formed JSON requests and responses with cursor-based pagination to simplify list handling. Error messages are descriptive and easy to understand. All functions of the Vultr customer portal are accessible via the API, enabling you to script complex unattended scenarios with any tool fluent in HTTP.  ## Requests  Communicate with the API by making an HTTP request at the correct endpoint. The chosen method determines the action taken.  | Method | Usage | | ------ | ------------- | | DELETE | Use the DELETE method to destroy a resource in your account. If it is not found, the operation will return a 4xx error and an appropriate message. | | GET | To retrieve information about a resource, use the GET method. The data is returned as a JSON object. GET methods are read-only and do not affect any resources. | | PATCH | Some resources support partial modification with PATCH, which modifies specific attributes without updating the entire object representation. | | POST | Issue a POST method to create a new object. Include all needed attributes in the request body encoded as JSON. | | PUT | Use the PUT method to update information about a resource. PUT will set new values on the item without regard to their current values. |  **Rate Limit:** Vultr safeguards the API against bursts of incoming traffic based on the request's IP address to ensure stability for all users. If your application sends more than 30 requests per second, the API may return HTTP status code 503.  ## Response Codes  We use standard HTTP response codes to show the success or failure of requests. Response codes in the 2xx range indicate success, while codes in the 4xx range indicate an error, such as an authorization failure or a malformed request. All 4xx errors will return a JSON response object with an `error` attribute explaining the error. Codes in the 5xx range indicate a server-side problem preventing Vultr from fulfilling your request.  | Response | Description | | ------ | ------------- | | 200 OK | The response contains your requested information. | | 201 Created | Your request was accepted. The resource was created. | | 202 Accepted | Your request was accepted. The resource was created or updated. | | 204 No Content | Your request succeeded, there is no additional information returned. | | 400 Bad Request | Your request was malformed. | | 401 Unauthorized | You did not supply valid authentication credentials. | | 403 Forbidden | You are not allowed to perform that action. | | 404 Not Found | No results were found for your request. | | 429 Too Many Requests | Your request exceeded the API rate limit. | | 500 Internal Server Error | We were unable to perform the request due to server-side problems. |  ## Meta and Pagination  Many API calls will return a `meta` object with paging information.  ### Definitions  | Term | Description | | ------ | ------------- | | List | All items available from your request. | | Page | A subset of a List. Choose the size of a Page with the `per_page` parameter. | | Total | The `total` attribute indicates the number of items in the full List.| | Cursor | Use the `cursor` query parameter to select a next or previous Page. | | Next & Prev | Use the `next` and `prev` attributes of the `links` meta object as `cursor` values. |  ### How to use Paging  You can request paging by setting the `per_page` query parameter.  ### Paging Example  > These examples have abbreviated attributes and sample values. Your actual `cursor` values will be encoded alphanumeric strings.  To return a Page with the first two Plans in the List:      curl \"https://api.vultr.com/v2/plans?per_page=2\" \\       -X GET \\       -H \"Authorization: Bearer ${VULTR_API_KEY}\"  The API returns an object similar to this:      {         \"plans\": [             {                 \"id\": \"vc2-1c-2gb\",                 \"vcpu_count\": 1,                 \"ram\": 2048,                 \"locations\": []             },             {                 \"id\": \"vc2-24c-97gb\",                 \"vcpu_count\": 24,                 \"ram\": 98304,                 \"locations\": []             }         ],         \"meta\": {             \"total\": 19,             \"links\": {                 \"next\": \"WxYzExampleNext\",                 \"prev\": \"WxYzExamplePrev\"             }         }     }  The object contains two plans. The `total` attribute indicates that 19 plans are available in the List. To navigate forward in the List, use the `next` value (**WxYzExampleNext** in this example) as your `cursor` query parameter.      curl \"https://api.vultr.com/v2/plans?per_page=2&cursor=WxYzExampleNext\" \\       -X GET       -H \"Authorization: Bearer ${VULTR_API_KEY}\"  Likewise, you can use the example `prev` value **WxYzExamplePrev** to navigate backward.  ## Parameters  You can pass information to the API with three different types of parameters.  ### Path parameters  Some API calls require variable parameters as part of the endpoint path. For example, to retrieve information about a user, supply the `user-id` in the endpoint.      curl \"https://api.vultr.com/v2/users/{user-id}\" \\       -X GET \\       -H \"Authorization: Bearer ${VULTR_API_KEY}\"  ### Query parameters  Some API calls allow filtering with query parameters. For example, the `/v2/plans` endpoint supports a `type` query parameter. Setting `type=vhf` instructs the API only to return High Frequency Compute plans in the list. You'll find more specifics about valid filters in the endpoint documentation below.      curl \"https://api.vultr.com/v2/plans?type=vhf\" \\       -X GET \\       -H \"Authorization: Bearer ${VULTR_API_KEY}\"  You can also combine filtering with paging. Use the `per_page` parameter to retreive a subset of vhf plans.      curl \"https://api.vultr.com/v2/plans?type=vhf&per_page=2\" \\       -X GET \\       -H \"Authorization: Bearer ${VULTR_API_KEY}\"  ### Request Body  PUT, POST, and PATCH methods may include an object in the request body with a content type of **application/json**. The documentation for each endpoint below has more information about the expected object.  ## API Example Conventions  The examples in this documentation use `curl`, a command-line HTTP client, to demonstrate useage. Linux and macOS computers usually have curl installed by default, and it's [available for download](https://curl.se/download.html) on all popular platforms including Windows.  Each example is split across multiple lines with the `\\` character, which is compatible with a `bash` terminal. A typical example looks like this:      curl \"https://api.vultr.com/v2/domains\" \\       -X POST \\       -H \"Authorization: Bearer ${VULTR_API_KEY}\" \\       -H \"Content-Type: application/json\" \\       --data '{         \"domain\" : \"example.com\",         \"ip\" : \"192.0.2.123\"       }'  * The `-X` parameter sets the request method. For consistency, we show the method on all examples, even though it's not explicitly required for GET methods. * The `-H` lines set required HTTP headers. These examples are formatted to expand the VULTR\\_API\\_KEY environment variable for your convenience. * Examples that require a JSON object in the request body pass the required data via the `--data` parameter.  All values in this guide are examples. Do not rely on the OS or Plan IDs listed in this guide; use the appropriate endpoint to retreive values before creating resources.  # Authentication  <!-- ReDoc-Inject: <security-definitions> -->
 *
 * The version of the OpenAPI document: 2.0
 * Contact: opensource@vultr.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.vultrapi.client.api;

import org.openapitools.vultrapi.client.ApiException;
import org.openapitools.vultrapi.client.model.CreateKubernetesCluster201Response;
import org.openapitools.vultrapi.client.model.CreateKubernetesClusterRequest;
import org.openapitools.vultrapi.client.model.CreateNodepools201Response;
import org.openapitools.vultrapi.client.model.CreateNodepoolsRequest;
import org.openapitools.vultrapi.client.model.GetKubernetesAvailableUpgrades200Response;
import org.openapitools.vultrapi.client.model.GetKubernetesClustersConfig200Response;
import org.openapitools.vultrapi.client.model.GetKubernetesResources200Response;
import org.openapitools.vultrapi.client.model.GetKubernetesVersions200Response;
import org.openapitools.vultrapi.client.model.GetNodepools200Response;
import org.openapitools.vultrapi.client.model.ListKubernetesClusters200Response;
import org.openapitools.vultrapi.client.model.StartKubernetesClusterUpgradeRequest;
import org.openapitools.vultrapi.client.model.UpdateKubernetesClusterRequest;
import org.openapitools.vultrapi.client.model.UpdateNodepoolRequest;
import org.openapitools.vultrapi.client.model.UpdateNodepoolRequest1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for KubernetesApi
 */
@Disabled
public class KubernetesApiTest {

    private final KubernetesApi api = new KubernetesApi();

    /**
     * Create Kubernetes Cluster
     *
     * Create Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createKubernetesClusterTest() throws ApiException {
        CreateKubernetesClusterRequest createKubernetesClusterRequest = null;
        CreateKubernetesCluster201Response response = api.createKubernetesCluster(createKubernetesClusterRequest);
        // TODO: test validations
    }

    /**
     * Create NodePool
     *
     * Create NodePool for a Existing Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createNodepoolsTest() throws ApiException {
        String vkeId = null;
        CreateNodepoolsRequest createNodepoolsRequest = null;
        CreateNodepools201Response response = api.createNodepools(vkeId, createNodepoolsRequest);
        // TODO: test validations
    }

    /**
     * Delete Kubernetes Cluster
     *
     * Delete Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteKubernetesClusterTest() throws ApiException {
        String vkeId = null;
        api.deleteKubernetesCluster(vkeId);
        // TODO: test validations
    }

    /**
     * Delete VKE Cluster and All Related Resources
     *
     * Delete Kubernetes Cluster and all related resources. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesTest() throws ApiException {
        String vkeId = null;
        api.deleteKubernetesClusterVkeIdDeleteWithLinkedResources(vkeId);
        // TODO: test validations
    }

    /**
     * Delete Nodepool
     *
     * Delete a NodePool from a Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteNodepoolTest() throws ApiException {
        String vkeId = null;
        String nodepoolId = null;
        api.deleteNodepool(vkeId, nodepoolId);
        // TODO: test validations
    }

    /**
     * Delete NodePool Instance
     *
     * Delete a single nodepool instance from a given Nodepool
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteNodepoolInstanceTest() throws ApiException {
        String vkeId = null;
        String nodepoolId = null;
        String nodeId = null;
        api.deleteNodepoolInstance(vkeId, nodepoolId, nodeId);
        // TODO: test validations
    }

    /**
     * Get Kubernetes Available Upgrades
     *
     * Get the available upgrades for the specified Kubernetes cluster.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKubernetesAvailableUpgradesTest() throws ApiException {
        String vkeId = null;
        GetKubernetesAvailableUpgrades200Response response = api.getKubernetesAvailableUpgrades(vkeId);
        // TODO: test validations
    }

    /**
     * Get Kubernetes Cluster
     *
     * Get Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKubernetesClustersTest() throws ApiException {
        String vkeId = null;
        CreateKubernetesCluster201Response response = api.getKubernetesClusters(vkeId);
        // TODO: test validations
    }

    /**
     * Get Kubernetes Cluster Kubeconfig
     *
     * Get Kubernetes Cluster Kubeconfig
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKubernetesClustersConfigTest() throws ApiException {
        String vkeId = null;
        GetKubernetesClustersConfig200Response response = api.getKubernetesClustersConfig(vkeId);
        // TODO: test validations
    }

    /**
     * Get Kubernetes Resources
     *
     * Get the block storage volumes and load balancers deployed by the specified Kubernetes cluster.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKubernetesResourcesTest() throws ApiException {
        String vkeId = null;
        GetKubernetesResources200Response response = api.getKubernetesResources(vkeId);
        // TODO: test validations
    }

    /**
     * Get Kubernetes Versions
     *
     * Get a list of supported Kubernetes versions
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKubernetesVersionsTest() throws ApiException {
        GetKubernetesVersions200Response response = api.getKubernetesVersions();
        // TODO: test validations
    }

    /**
     * Get NodePool
     *
     * Get Nodepool from a Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getNodepoolTest() throws ApiException {
        String vkeId = null;
        String nodepoolId = null;
        CreateNodepools201Response response = api.getNodepool(vkeId, nodepoolId);
        // TODO: test validations
    }

    /**
     * List NodePools
     *
     * List all available NodePools on a Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getNodepoolsTest() throws ApiException {
        String vkeId = null;
        GetNodepools200Response response = api.getNodepools(vkeId);
        // TODO: test validations
    }

    /**
     * List all Kubernetes Clusters
     *
     * List all Kubernetes clusters currently deployed
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listKubernetesClustersTest() throws ApiException {
        ListKubernetesClusters200Response response = api.listKubernetesClusters();
        // TODO: test validations
    }

    /**
     * Recycle a NodePool Instance
     *
     * Recycle a specific NodePool Instance
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void recycleNodepoolInstanceTest() throws ApiException {
        String vkeId = null;
        String nodepoolId = null;
        String nodeId = null;
        api.recycleNodepoolInstance(vkeId, nodepoolId, nodeId);
        // TODO: test validations
    }

    /**
     * Start Kubernetes Cluster Upgrade
     *
     * Start a Kubernetes cluster upgrade.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void startKubernetesClusterUpgradeTest() throws ApiException {
        String vkeId = null;
        StartKubernetesClusterUpgradeRequest startKubernetesClusterUpgradeRequest = null;
        api.startKubernetesClusterUpgrade(vkeId, startKubernetesClusterUpgradeRequest);
        // TODO: test validations
    }

    /**
     * Update Kubernetes Cluster
     *
     * Update Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateKubernetesClusterTest() throws ApiException {
        String vkeId = null;
        UpdateKubernetesClusterRequest updateKubernetesClusterRequest = null;
        api.updateKubernetesCluster(vkeId, updateKubernetesClusterRequest);
        // TODO: test validations
    }

    /**
     * Update Nodepool
     *
     * Update a Nodepool on a Kubernetes Cluster
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateNodepoolTest() throws ApiException {
        String vkeId = null;
        String nodepoolId = null;
        UpdateNodepoolRequest updateNodepoolRequest = null;
        CreateNodepools201Response response = api.updateNodepool(vkeId, nodepoolId, updateNodepoolRequest);
        // TODO: test validations
    }

}
