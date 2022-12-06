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

import org.openapitools.vultrapi.client.ApiCallback;
import org.openapitools.vultrapi.client.ApiClient;
import org.openapitools.vultrapi.client.ApiException;
import org.openapitools.vultrapi.client.ApiResponse;
import org.openapitools.vultrapi.client.Configuration;
import org.openapitools.vultrapi.client.Pair;
import org.openapitools.vultrapi.client.ProgressRequestBody;
import org.openapitools.vultrapi.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class KubernetesApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public KubernetesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public KubernetesApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for createKubernetesCluster
     * @param createKubernetesClusterRequest Request Body (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createKubernetesClusterCall(CreateKubernetesClusterRequest createKubernetesClusterRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = createKubernetesClusterRequest;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createKubernetesClusterValidateBeforeCall(CreateKubernetesClusterRequest createKubernetesClusterRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = createKubernetesClusterCall(createKubernetesClusterRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Kubernetes Cluster
     * Create Kubernetes Cluster
     * @param createKubernetesClusterRequest Request Body (optional)
     * @return CreateKubernetesCluster201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateKubernetesCluster201Response createKubernetesCluster(CreateKubernetesClusterRequest createKubernetesClusterRequest) throws ApiException {
        ApiResponse<CreateKubernetesCluster201Response> localVarResp = createKubernetesClusterWithHttpInfo(createKubernetesClusterRequest);
        return localVarResp.getData();
    }

    /**
     * Create Kubernetes Cluster
     * Create Kubernetes Cluster
     * @param createKubernetesClusterRequest Request Body (optional)
     * @return ApiResponse&lt;CreateKubernetesCluster201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateKubernetesCluster201Response> createKubernetesClusterWithHttpInfo(CreateKubernetesClusterRequest createKubernetesClusterRequest) throws ApiException {
        okhttp3.Call localVarCall = createKubernetesClusterValidateBeforeCall(createKubernetesClusterRequest, null);
        Type localVarReturnType = new TypeToken<CreateKubernetesCluster201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Kubernetes Cluster (asynchronously)
     * Create Kubernetes Cluster
     * @param createKubernetesClusterRequest Request Body (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createKubernetesClusterAsync(CreateKubernetesClusterRequest createKubernetesClusterRequest, final ApiCallback<CreateKubernetesCluster201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createKubernetesClusterValidateBeforeCall(createKubernetesClusterRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateKubernetesCluster201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createNodepools
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param createNodepoolsRequest Request Body (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createNodepoolsCall(String vkeId, CreateNodepoolsRequest createNodepoolsRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = createNodepoolsRequest;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createNodepoolsValidateBeforeCall(String vkeId, CreateNodepoolsRequest createNodepoolsRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling createNodepools(Async)");
        }
        

        okhttp3.Call localVarCall = createNodepoolsCall(vkeId, createNodepoolsRequest, _callback);
        return localVarCall;

    }

    /**
     * Create NodePool
     * Create NodePool for a Existing Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param createNodepoolsRequest Request Body (optional)
     * @return CreateNodepools201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateNodepools201Response createNodepools(String vkeId, CreateNodepoolsRequest createNodepoolsRequest) throws ApiException {
        ApiResponse<CreateNodepools201Response> localVarResp = createNodepoolsWithHttpInfo(vkeId, createNodepoolsRequest);
        return localVarResp.getData();
    }

    /**
     * Create NodePool
     * Create NodePool for a Existing Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param createNodepoolsRequest Request Body (optional)
     * @return ApiResponse&lt;CreateNodepools201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateNodepools201Response> createNodepoolsWithHttpInfo(String vkeId, CreateNodepoolsRequest createNodepoolsRequest) throws ApiException {
        okhttp3.Call localVarCall = createNodepoolsValidateBeforeCall(vkeId, createNodepoolsRequest, null);
        Type localVarReturnType = new TypeToken<CreateNodepools201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create NodePool (asynchronously)
     * Create NodePool for a Existing Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param createNodepoolsRequest Request Body (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createNodepoolsAsync(String vkeId, CreateNodepoolsRequest createNodepoolsRequest, final ApiCallback<CreateNodepools201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createNodepoolsValidateBeforeCall(vkeId, createNodepoolsRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateNodepools201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteKubernetesCluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteKubernetesClusterCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteKubernetesClusterValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling deleteKubernetesCluster(Async)");
        }
        

        okhttp3.Call localVarCall = deleteKubernetesClusterCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * Delete Kubernetes Cluster
     * Delete Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void deleteKubernetesCluster(String vkeId) throws ApiException {
        deleteKubernetesClusterWithHttpInfo(vkeId);
    }

    /**
     * Delete Kubernetes Cluster
     * Delete Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteKubernetesClusterWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = deleteKubernetesClusterValidateBeforeCall(vkeId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Kubernetes Cluster (asynchronously)
     * Delete Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteKubernetesClusterAsync(String vkeId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteKubernetesClusterValidateBeforeCall(vkeId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteKubernetesClusterVkeIdDeleteWithLinkedResources
     * @param vkeId  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/delete-with-linked-resources"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling deleteKubernetesClusterVkeIdDeleteWithLinkedResources(Async)");
        }
        

        okhttp3.Call localVarCall = deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * Delete VKE Cluster and All Related Resources
     * Delete Kubernetes Cluster and all related resources. 
     * @param vkeId  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void deleteKubernetesClusterVkeIdDeleteWithLinkedResources(String vkeId) throws ApiException {
        deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesWithHttpInfo(vkeId);
    }

    /**
     * Delete VKE Cluster and All Related Resources
     * Delete Kubernetes Cluster and all related resources. 
     * @param vkeId  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesValidateBeforeCall(vkeId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete VKE Cluster and All Related Resources (asynchronously)
     * Delete Kubernetes Cluster and all related resources. 
     * @param vkeId  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesAsync(String vkeId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteKubernetesClusterVkeIdDeleteWithLinkedResourcesValidateBeforeCall(vkeId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteNodepool
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteNodepoolCall(String vkeId, String nodepoolId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools/{nodepool-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()))
            .replaceAll("\\{" + "nodepool-id" + "\\}", localVarApiClient.escapeString(nodepoolId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteNodepoolValidateBeforeCall(String vkeId, String nodepoolId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling deleteNodepool(Async)");
        }
        
        // verify the required parameter 'nodepoolId' is set
        if (nodepoolId == null) {
            throw new ApiException("Missing the required parameter 'nodepoolId' when calling deleteNodepool(Async)");
        }
        

        okhttp3.Call localVarCall = deleteNodepoolCall(vkeId, nodepoolId, _callback);
        return localVarCall;

    }

    /**
     * Delete Nodepool
     * Delete a NodePool from a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void deleteNodepool(String vkeId, String nodepoolId) throws ApiException {
        deleteNodepoolWithHttpInfo(vkeId, nodepoolId);
    }

    /**
     * Delete Nodepool
     * Delete a NodePool from a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteNodepoolWithHttpInfo(String vkeId, String nodepoolId) throws ApiException {
        okhttp3.Call localVarCall = deleteNodepoolValidateBeforeCall(vkeId, nodepoolId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Nodepool (asynchronously)
     * Delete a NodePool from a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteNodepoolAsync(String vkeId, String nodepoolId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteNodepoolValidateBeforeCall(vkeId, nodepoolId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteNodepoolInstance
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId The [Instance ID](#operation/list-instances). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteNodepoolInstanceCall(String vkeId, String nodepoolId, String nodeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools/{nodepool-id}/nodes/{node-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()))
            .replaceAll("\\{" + "nodepool-id" + "\\}", localVarApiClient.escapeString(nodepoolId.toString()))
            .replaceAll("\\{" + "node-id" + "\\}", localVarApiClient.escapeString(nodeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteNodepoolInstanceValidateBeforeCall(String vkeId, String nodepoolId, String nodeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling deleteNodepoolInstance(Async)");
        }
        
        // verify the required parameter 'nodepoolId' is set
        if (nodepoolId == null) {
            throw new ApiException("Missing the required parameter 'nodepoolId' when calling deleteNodepoolInstance(Async)");
        }
        
        // verify the required parameter 'nodeId' is set
        if (nodeId == null) {
            throw new ApiException("Missing the required parameter 'nodeId' when calling deleteNodepoolInstance(Async)");
        }
        

        okhttp3.Call localVarCall = deleteNodepoolInstanceCall(vkeId, nodepoolId, nodeId, _callback);
        return localVarCall;

    }

    /**
     * Delete NodePool Instance
     * Delete a single nodepool instance from a given Nodepool
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId The [Instance ID](#operation/list-instances). (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void deleteNodepoolInstance(String vkeId, String nodepoolId, String nodeId) throws ApiException {
        deleteNodepoolInstanceWithHttpInfo(vkeId, nodepoolId, nodeId);
    }

    /**
     * Delete NodePool Instance
     * Delete a single nodepool instance from a given Nodepool
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteNodepoolInstanceWithHttpInfo(String vkeId, String nodepoolId, String nodeId) throws ApiException {
        okhttp3.Call localVarCall = deleteNodepoolInstanceValidateBeforeCall(vkeId, nodepoolId, nodeId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete NodePool Instance (asynchronously)
     * Delete a single nodepool instance from a given Nodepool
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId The [Instance ID](#operation/list-instances). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteNodepoolInstanceAsync(String vkeId, String nodepoolId, String nodeId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteNodepoolInstanceValidateBeforeCall(vkeId, nodepoolId, nodeId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getKubernetesAvailableUpgrades
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesAvailableUpgradesCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/available-upgrades"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getKubernetesAvailableUpgradesValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling getKubernetesAvailableUpgrades(Async)");
        }
        

        okhttp3.Call localVarCall = getKubernetesAvailableUpgradesCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * Get Kubernetes Available Upgrades
     * Get the available upgrades for the specified Kubernetes cluster.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return GetKubernetesAvailableUpgrades200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public GetKubernetesAvailableUpgrades200Response getKubernetesAvailableUpgrades(String vkeId) throws ApiException {
        ApiResponse<GetKubernetesAvailableUpgrades200Response> localVarResp = getKubernetesAvailableUpgradesWithHttpInfo(vkeId);
        return localVarResp.getData();
    }

    /**
     * Get Kubernetes Available Upgrades
     * Get the available upgrades for the specified Kubernetes cluster.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return ApiResponse&lt;GetKubernetesAvailableUpgrades200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetKubernetesAvailableUpgrades200Response> getKubernetesAvailableUpgradesWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = getKubernetesAvailableUpgradesValidateBeforeCall(vkeId, null);
        Type localVarReturnType = new TypeToken<GetKubernetesAvailableUpgrades200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Kubernetes Available Upgrades (asynchronously)
     * Get the available upgrades for the specified Kubernetes cluster.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesAvailableUpgradesAsync(String vkeId, final ApiCallback<GetKubernetesAvailableUpgrades200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getKubernetesAvailableUpgradesValidateBeforeCall(vkeId, _callback);
        Type localVarReturnType = new TypeToken<GetKubernetesAvailableUpgrades200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getKubernetesClusters
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesClustersCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getKubernetesClustersValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling getKubernetesClusters(Async)");
        }
        

        okhttp3.Call localVarCall = getKubernetesClustersCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * Get Kubernetes Cluster
     * Get Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return CreateKubernetesCluster201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateKubernetesCluster201Response getKubernetesClusters(String vkeId) throws ApiException {
        ApiResponse<CreateKubernetesCluster201Response> localVarResp = getKubernetesClustersWithHttpInfo(vkeId);
        return localVarResp.getData();
    }

    /**
     * Get Kubernetes Cluster
     * Get Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return ApiResponse&lt;CreateKubernetesCluster201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateKubernetesCluster201Response> getKubernetesClustersWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = getKubernetesClustersValidateBeforeCall(vkeId, null);
        Type localVarReturnType = new TypeToken<CreateKubernetesCluster201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Kubernetes Cluster (asynchronously)
     * Get Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesClustersAsync(String vkeId, final ApiCallback<CreateKubernetesCluster201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getKubernetesClustersValidateBeforeCall(vkeId, _callback);
        Type localVarReturnType = new TypeToken<CreateKubernetesCluster201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getKubernetesClustersConfig
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesClustersConfigCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/config"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getKubernetesClustersConfigValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling getKubernetesClustersConfig(Async)");
        }
        

        okhttp3.Call localVarCall = getKubernetesClustersConfigCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * Get Kubernetes Cluster Kubeconfig
     * Get Kubernetes Cluster Kubeconfig
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return GetKubernetesClustersConfig200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public GetKubernetesClustersConfig200Response getKubernetesClustersConfig(String vkeId) throws ApiException {
        ApiResponse<GetKubernetesClustersConfig200Response> localVarResp = getKubernetesClustersConfigWithHttpInfo(vkeId);
        return localVarResp.getData();
    }

    /**
     * Get Kubernetes Cluster Kubeconfig
     * Get Kubernetes Cluster Kubeconfig
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return ApiResponse&lt;GetKubernetesClustersConfig200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetKubernetesClustersConfig200Response> getKubernetesClustersConfigWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = getKubernetesClustersConfigValidateBeforeCall(vkeId, null);
        Type localVarReturnType = new TypeToken<GetKubernetesClustersConfig200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Kubernetes Cluster Kubeconfig (asynchronously)
     * Get Kubernetes Cluster Kubeconfig
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesClustersConfigAsync(String vkeId, final ApiCallback<GetKubernetesClustersConfig200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getKubernetesClustersConfigValidateBeforeCall(vkeId, _callback);
        Type localVarReturnType = new TypeToken<GetKubernetesClustersConfig200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getKubernetesResources
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesResourcesCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/resources"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getKubernetesResourcesValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling getKubernetesResources(Async)");
        }
        

        okhttp3.Call localVarCall = getKubernetesResourcesCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * Get Kubernetes Resources
     * Get the block storage volumes and load balancers deployed by the specified Kubernetes cluster.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return GetKubernetesResources200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public GetKubernetesResources200Response getKubernetesResources(String vkeId) throws ApiException {
        ApiResponse<GetKubernetesResources200Response> localVarResp = getKubernetesResourcesWithHttpInfo(vkeId);
        return localVarResp.getData();
    }

    /**
     * Get Kubernetes Resources
     * Get the block storage volumes and load balancers deployed by the specified Kubernetes cluster.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return ApiResponse&lt;GetKubernetesResources200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetKubernetesResources200Response> getKubernetesResourcesWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = getKubernetesResourcesValidateBeforeCall(vkeId, null);
        Type localVarReturnType = new TypeToken<GetKubernetesResources200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Kubernetes Resources (asynchronously)
     * Get the block storage volumes and load balancers deployed by the specified Kubernetes cluster.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesResourcesAsync(String vkeId, final ApiCallback<GetKubernetesResources200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getKubernetesResourcesValidateBeforeCall(vkeId, _callback);
        Type localVarReturnType = new TypeToken<GetKubernetesResources200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getKubernetesVersions
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesVersionsCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/versions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getKubernetesVersionsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = getKubernetesVersionsCall(_callback);
        return localVarCall;

    }

    /**
     * Get Kubernetes Versions
     * Get a list of supported Kubernetes versions
     * @return GetKubernetesVersions200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public GetKubernetesVersions200Response getKubernetesVersions() throws ApiException {
        ApiResponse<GetKubernetesVersions200Response> localVarResp = getKubernetesVersionsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Get Kubernetes Versions
     * Get a list of supported Kubernetes versions
     * @return ApiResponse&lt;GetKubernetesVersions200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetKubernetesVersions200Response> getKubernetesVersionsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getKubernetesVersionsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetKubernetesVersions200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Kubernetes Versions (asynchronously)
     * Get a list of supported Kubernetes versions
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getKubernetesVersionsAsync(final ApiCallback<GetKubernetesVersions200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getKubernetesVersionsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetKubernetesVersions200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getNodepool
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getNodepoolCall(String vkeId, String nodepoolId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools/{nodepool-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()))
            .replaceAll("\\{" + "nodepool-id" + "\\}", localVarApiClient.escapeString(nodepoolId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getNodepoolValidateBeforeCall(String vkeId, String nodepoolId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling getNodepool(Async)");
        }
        
        // verify the required parameter 'nodepoolId' is set
        if (nodepoolId == null) {
            throw new ApiException("Missing the required parameter 'nodepoolId' when calling getNodepool(Async)");
        }
        

        okhttp3.Call localVarCall = getNodepoolCall(vkeId, nodepoolId, _callback);
        return localVarCall;

    }

    /**
     * Get NodePool
     * Get Nodepool from a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @return CreateNodepools201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateNodepools201Response getNodepool(String vkeId, String nodepoolId) throws ApiException {
        ApiResponse<CreateNodepools201Response> localVarResp = getNodepoolWithHttpInfo(vkeId, nodepoolId);
        return localVarResp.getData();
    }

    /**
     * Get NodePool
     * Get Nodepool from a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @return ApiResponse&lt;CreateNodepools201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateNodepools201Response> getNodepoolWithHttpInfo(String vkeId, String nodepoolId) throws ApiException {
        okhttp3.Call localVarCall = getNodepoolValidateBeforeCall(vkeId, nodepoolId, null);
        Type localVarReturnType = new TypeToken<CreateNodepools201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get NodePool (asynchronously)
     * Get Nodepool from a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getNodepoolAsync(String vkeId, String nodepoolId, final ApiCallback<CreateNodepools201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getNodepoolValidateBeforeCall(vkeId, nodepoolId, _callback);
        Type localVarReturnType = new TypeToken<CreateNodepools201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getNodepools
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getNodepoolsCall(String vkeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getNodepoolsValidateBeforeCall(String vkeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling getNodepools(Async)");
        }
        

        okhttp3.Call localVarCall = getNodepoolsCall(vkeId, _callback);
        return localVarCall;

    }

    /**
     * List NodePools
     * List all available NodePools on a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return GetNodepools200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public GetNodepools200Response getNodepools(String vkeId) throws ApiException {
        ApiResponse<GetNodepools200Response> localVarResp = getNodepoolsWithHttpInfo(vkeId);
        return localVarResp.getData();
    }

    /**
     * List NodePools
     * List all available NodePools on a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @return ApiResponse&lt;GetNodepools200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetNodepools200Response> getNodepoolsWithHttpInfo(String vkeId) throws ApiException {
        okhttp3.Call localVarCall = getNodepoolsValidateBeforeCall(vkeId, null);
        Type localVarReturnType = new TypeToken<GetNodepools200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List NodePools (asynchronously)
     * List all available NodePools on a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getNodepoolsAsync(String vkeId, final ApiCallback<GetNodepools200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getNodepoolsValidateBeforeCall(vkeId, _callback);
        Type localVarReturnType = new TypeToken<GetNodepools200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listKubernetesClusters
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listKubernetesClustersCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listKubernetesClustersValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listKubernetesClustersCall(_callback);
        return localVarCall;

    }

    /**
     * List all Kubernetes Clusters
     * List all Kubernetes clusters currently deployed
     * @return ListKubernetesClusters200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ListKubernetesClusters200Response listKubernetesClusters() throws ApiException {
        ApiResponse<ListKubernetesClusters200Response> localVarResp = listKubernetesClustersWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * List all Kubernetes Clusters
     * List all Kubernetes clusters currently deployed
     * @return ApiResponse&lt;ListKubernetesClusters200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListKubernetesClusters200Response> listKubernetesClustersWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = listKubernetesClustersValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<ListKubernetesClusters200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List all Kubernetes Clusters (asynchronously)
     * List all Kubernetes clusters currently deployed
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listKubernetesClustersAsync(final ApiCallback<ListKubernetesClusters200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listKubernetesClustersValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<ListKubernetesClusters200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for recycleNodepoolInstance
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId Node ID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call recycleNodepoolInstanceCall(String vkeId, String nodepoolId, String nodeId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools/{nodepool-id}/nodes/{node-id}/recycle"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()))
            .replaceAll("\\{" + "nodepool-id" + "\\}", localVarApiClient.escapeString(nodepoolId.toString()))
            .replaceAll("\\{" + "node-id" + "\\}", localVarApiClient.escapeString(nodeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call recycleNodepoolInstanceValidateBeforeCall(String vkeId, String nodepoolId, String nodeId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling recycleNodepoolInstance(Async)");
        }
        
        // verify the required parameter 'nodepoolId' is set
        if (nodepoolId == null) {
            throw new ApiException("Missing the required parameter 'nodepoolId' when calling recycleNodepoolInstance(Async)");
        }
        
        // verify the required parameter 'nodeId' is set
        if (nodeId == null) {
            throw new ApiException("Missing the required parameter 'nodeId' when calling recycleNodepoolInstance(Async)");
        }
        

        okhttp3.Call localVarCall = recycleNodepoolInstanceCall(vkeId, nodepoolId, nodeId, _callback);
        return localVarCall;

    }

    /**
     * Recycle a NodePool Instance
     * Recycle a specific NodePool Instance
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId Node ID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void recycleNodepoolInstance(String vkeId, String nodepoolId, String nodeId) throws ApiException {
        recycleNodepoolInstanceWithHttpInfo(vkeId, nodepoolId, nodeId);
    }

    /**
     * Recycle a NodePool Instance
     * Recycle a specific NodePool Instance
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId Node ID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> recycleNodepoolInstanceWithHttpInfo(String vkeId, String nodepoolId, String nodeId) throws ApiException {
        okhttp3.Call localVarCall = recycleNodepoolInstanceValidateBeforeCall(vkeId, nodepoolId, nodeId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Recycle a NodePool Instance (asynchronously)
     * Recycle a specific NodePool Instance
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param nodeId Node ID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call recycleNodepoolInstanceAsync(String vkeId, String nodepoolId, String nodeId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = recycleNodepoolInstanceValidateBeforeCall(vkeId, nodepoolId, nodeId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for startKubernetesClusterUpgrade
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param startKubernetesClusterUpgradeRequest Request Body (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call startKubernetesClusterUpgradeCall(String vkeId, StartKubernetesClusterUpgradeRequest startKubernetesClusterUpgradeRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = startKubernetesClusterUpgradeRequest;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/upgrades"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call startKubernetesClusterUpgradeValidateBeforeCall(String vkeId, StartKubernetesClusterUpgradeRequest startKubernetesClusterUpgradeRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling startKubernetesClusterUpgrade(Async)");
        }
        

        okhttp3.Call localVarCall = startKubernetesClusterUpgradeCall(vkeId, startKubernetesClusterUpgradeRequest, _callback);
        return localVarCall;

    }

    /**
     * Start Kubernetes Cluster Upgrade
     * Start a Kubernetes cluster upgrade.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param startKubernetesClusterUpgradeRequest Request Body (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void startKubernetesClusterUpgrade(String vkeId, StartKubernetesClusterUpgradeRequest startKubernetesClusterUpgradeRequest) throws ApiException {
        startKubernetesClusterUpgradeWithHttpInfo(vkeId, startKubernetesClusterUpgradeRequest);
    }

    /**
     * Start Kubernetes Cluster Upgrade
     * Start a Kubernetes cluster upgrade.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param startKubernetesClusterUpgradeRequest Request Body (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> startKubernetesClusterUpgradeWithHttpInfo(String vkeId, StartKubernetesClusterUpgradeRequest startKubernetesClusterUpgradeRequest) throws ApiException {
        okhttp3.Call localVarCall = startKubernetesClusterUpgradeValidateBeforeCall(vkeId, startKubernetesClusterUpgradeRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Start Kubernetes Cluster Upgrade (asynchronously)
     * Start a Kubernetes cluster upgrade.
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param startKubernetesClusterUpgradeRequest Request Body (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call startKubernetesClusterUpgradeAsync(String vkeId, StartKubernetesClusterUpgradeRequest startKubernetesClusterUpgradeRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = startKubernetesClusterUpgradeValidateBeforeCall(vkeId, startKubernetesClusterUpgradeRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateKubernetesCluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param updateKubernetesClusterRequest Request Body (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateKubernetesClusterCall(String vkeId, UpdateKubernetesClusterRequest updateKubernetesClusterRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = updateKubernetesClusterRequest;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateKubernetesClusterValidateBeforeCall(String vkeId, UpdateKubernetesClusterRequest updateKubernetesClusterRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling updateKubernetesCluster(Async)");
        }
        

        okhttp3.Call localVarCall = updateKubernetesClusterCall(vkeId, updateKubernetesClusterRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Kubernetes Cluster
     * Update Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param updateKubernetesClusterRequest Request Body (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void updateKubernetesCluster(String vkeId, UpdateKubernetesClusterRequest updateKubernetesClusterRequest) throws ApiException {
        updateKubernetesClusterWithHttpInfo(vkeId, updateKubernetesClusterRequest);
    }

    /**
     * Update Kubernetes Cluster
     * Update Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param updateKubernetesClusterRequest Request Body (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> updateKubernetesClusterWithHttpInfo(String vkeId, UpdateKubernetesClusterRequest updateKubernetesClusterRequest) throws ApiException {
        okhttp3.Call localVarCall = updateKubernetesClusterValidateBeforeCall(vkeId, updateKubernetesClusterRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update Kubernetes Cluster (asynchronously)
     * Update Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param updateKubernetesClusterRequest Request Body (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateKubernetesClusterAsync(String vkeId, UpdateKubernetesClusterRequest updateKubernetesClusterRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateKubernetesClusterValidateBeforeCall(vkeId, updateKubernetesClusterRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateNodepool
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param updateNodepoolRequest Request Body (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateNodepoolCall(String vkeId, String nodepoolId, UpdateNodepoolRequest updateNodepoolRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = updateNodepoolRequest;

        // create path and map variables
        String localVarPath = "/kubernetes/clusters/{vke-id}/node-pools/{nodepool-id}"
            .replaceAll("\\{" + "vke-id" + "\\}", localVarApiClient.escapeString(vkeId.toString()))
            .replaceAll("\\{" + "nodepool-id" + "\\}", localVarApiClient.escapeString(nodepoolId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json", "application/xml"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "API Key" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateNodepoolValidateBeforeCall(String vkeId, String nodepoolId, UpdateNodepoolRequest updateNodepoolRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'vkeId' is set
        if (vkeId == null) {
            throw new ApiException("Missing the required parameter 'vkeId' when calling updateNodepool(Async)");
        }
        
        // verify the required parameter 'nodepoolId' is set
        if (nodepoolId == null) {
            throw new ApiException("Missing the required parameter 'nodepoolId' when calling updateNodepool(Async)");
        }
        

        okhttp3.Call localVarCall = updateNodepoolCall(vkeId, nodepoolId, updateNodepoolRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Nodepool
     * Update a Nodepool on a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param updateNodepoolRequest Request Body (optional)
     * @return CreateNodepools201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateNodepools201Response updateNodepool(String vkeId, String nodepoolId, UpdateNodepoolRequest updateNodepoolRequest) throws ApiException {
        ApiResponse<CreateNodepools201Response> localVarResp = updateNodepoolWithHttpInfo(vkeId, nodepoolId, updateNodepoolRequest);
        return localVarResp.getData();
    }

    /**
     * Update Nodepool
     * Update a Nodepool on a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param updateNodepoolRequest Request Body (optional)
     * @return ApiResponse&lt;CreateNodepools201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateNodepools201Response> updateNodepoolWithHttpInfo(String vkeId, String nodepoolId, UpdateNodepoolRequest updateNodepoolRequest) throws ApiException {
        okhttp3.Call localVarCall = updateNodepoolValidateBeforeCall(vkeId, nodepoolId, updateNodepoolRequest, null);
        Type localVarReturnType = new TypeToken<CreateNodepools201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update Nodepool (asynchronously)
     * Update a Nodepool on a Kubernetes Cluster
     * @param vkeId The [VKE ID](#operation/list-kubernetes-clusters). (required)
     * @param nodepoolId The [NodePool ID](#operation/get-nodepools). (required)
     * @param updateNodepoolRequest Request Body (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateNodepoolAsync(String vkeId, String nodepoolId, UpdateNodepoolRequest updateNodepoolRequest, final ApiCallback<CreateNodepools201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateNodepoolValidateBeforeCall(vkeId, nodepoolId, updateNodepoolRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateNodepools201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
