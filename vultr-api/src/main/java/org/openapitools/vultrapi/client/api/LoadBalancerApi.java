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


import org.openapitools.vultrapi.client.model.CreateLoadBalancer202Response;
import org.openapitools.vultrapi.client.model.CreateLoadBalancerForwardingRulesRequest;
import org.openapitools.vultrapi.client.model.CreateLoadBalancerRequest;
import org.openapitools.vultrapi.client.model.GetLoadBalancerForwardingRule200Response;
import org.openapitools.vultrapi.client.model.ListLoadBalancerForwardingRules200Response;
import org.openapitools.vultrapi.client.model.ListLoadBalancers200Response;
import org.openapitools.vultrapi.client.model.LoadbalancerFirewallRule;
import org.openapitools.vultrapi.client.model.UpdateLoadBalancerRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class LoadBalancerApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public LoadBalancerApi() {
        this(Configuration.getDefaultApiClient());
    }

    public LoadBalancerApi(ApiClient apiClient) {
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
     * Build call for createLoadBalancer
     * @param createLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createLoadBalancerCall(CreateLoadBalancerRequest createLoadBalancerRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createLoadBalancerRequest;

        // create path and map variables
        String localVarPath = "/load-balancers";

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
    private okhttp3.Call createLoadBalancerValidateBeforeCall(CreateLoadBalancerRequest createLoadBalancerRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = createLoadBalancerCall(createLoadBalancerRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Load Balancer
     * Create a new Load Balancer in a particular &#x60;region&#x60;.
     * @param createLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateLoadBalancer202Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateLoadBalancer202Response createLoadBalancer(CreateLoadBalancerRequest createLoadBalancerRequest) throws ApiException {
        ApiResponse<CreateLoadBalancer202Response> localVarResp = createLoadBalancerWithHttpInfo(createLoadBalancerRequest);
        return localVarResp.getData();
    }

    /**
     * Create Load Balancer
     * Create a new Load Balancer in a particular &#x60;region&#x60;.
     * @param createLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateLoadBalancer202Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateLoadBalancer202Response> createLoadBalancerWithHttpInfo(CreateLoadBalancerRequest createLoadBalancerRequest) throws ApiException {
        okhttp3.Call localVarCall = createLoadBalancerValidateBeforeCall(createLoadBalancerRequest, null);
        Type localVarReturnType = new TypeToken<CreateLoadBalancer202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Load Balancer (asynchronously)
     * Create a new Load Balancer in a particular &#x60;region&#x60;.
     * @param createLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createLoadBalancerAsync(CreateLoadBalancerRequest createLoadBalancerRequest, final ApiCallback<CreateLoadBalancer202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createLoadBalancerValidateBeforeCall(createLoadBalancerRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateLoadBalancer202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createLoadBalancerForwardingRules
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param createLoadBalancerForwardingRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createLoadBalancerForwardingRulesCall(String loadBalancerId, CreateLoadBalancerForwardingRulesRequest createLoadBalancerForwardingRulesRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createLoadBalancerForwardingRulesRequest;

        // create path and map variables
        String localVarPath = "/load-balancers/{load-balancer-id}/forwarding-rules"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()));

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
    private okhttp3.Call createLoadBalancerForwardingRulesValidateBeforeCall(String loadBalancerId, CreateLoadBalancerForwardingRulesRequest createLoadBalancerForwardingRulesRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling createLoadBalancerForwardingRules(Async)");
        }
        

        okhttp3.Call localVarCall = createLoadBalancerForwardingRulesCall(loadBalancerId, createLoadBalancerForwardingRulesRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Forwarding Rule
     * Create a new forwarding rule for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param createLoadBalancerForwardingRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void createLoadBalancerForwardingRules(String loadBalancerId, CreateLoadBalancerForwardingRulesRequest createLoadBalancerForwardingRulesRequest) throws ApiException {
        createLoadBalancerForwardingRulesWithHttpInfo(loadBalancerId, createLoadBalancerForwardingRulesRequest);
    }

    /**
     * Create Forwarding Rule
     * Create a new forwarding rule for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param createLoadBalancerForwardingRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> createLoadBalancerForwardingRulesWithHttpInfo(String loadBalancerId, CreateLoadBalancerForwardingRulesRequest createLoadBalancerForwardingRulesRequest) throws ApiException {
        okhttp3.Call localVarCall = createLoadBalancerForwardingRulesValidateBeforeCall(loadBalancerId, createLoadBalancerForwardingRulesRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Create Forwarding Rule (asynchronously)
     * Create a new forwarding rule for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param createLoadBalancerForwardingRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createLoadBalancerForwardingRulesAsync(String loadBalancerId, CreateLoadBalancerForwardingRulesRequest createLoadBalancerForwardingRulesRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = createLoadBalancerForwardingRulesValidateBeforeCall(loadBalancerId, createLoadBalancerForwardingRulesRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteLoadBalancer
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
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
    public okhttp3.Call deleteLoadBalancerCall(String loadBalancerId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{load-balancer-id}"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()));

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
    private okhttp3.Call deleteLoadBalancerValidateBeforeCall(String loadBalancerId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling deleteLoadBalancer(Async)");
        }
        

        okhttp3.Call localVarCall = deleteLoadBalancerCall(loadBalancerId, _callback);
        return localVarCall;

    }

    /**
     * Delete Load Balancer
     * Delete a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
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
    public void deleteLoadBalancer(String loadBalancerId) throws ApiException {
        deleteLoadBalancerWithHttpInfo(loadBalancerId);
    }

    /**
     * Delete Load Balancer
     * Delete a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
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
    public ApiResponse<Void> deleteLoadBalancerWithHttpInfo(String loadBalancerId) throws ApiException {
        okhttp3.Call localVarCall = deleteLoadBalancerValidateBeforeCall(loadBalancerId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Load Balancer (asynchronously)
     * Delete a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
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
    public okhttp3.Call deleteLoadBalancerAsync(String loadBalancerId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteLoadBalancerValidateBeforeCall(loadBalancerId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteLoadBalancerForwardingRule
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
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
    public okhttp3.Call deleteLoadBalancerForwardingRuleCall(String loadBalancerId, String forwardingRuleId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{load-balancer-id}/forwarding-rules/{forwarding-rule-id}"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()))
            .replaceAll("\\{" + "forwarding-rule-id" + "\\}", localVarApiClient.escapeString(forwardingRuleId.toString()));

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
    private okhttp3.Call deleteLoadBalancerForwardingRuleValidateBeforeCall(String loadBalancerId, String forwardingRuleId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling deleteLoadBalancerForwardingRule(Async)");
        }
        
        // verify the required parameter 'forwardingRuleId' is set
        if (forwardingRuleId == null) {
            throw new ApiException("Missing the required parameter 'forwardingRuleId' when calling deleteLoadBalancerForwardingRule(Async)");
        }
        

        okhttp3.Call localVarCall = deleteLoadBalancerForwardingRuleCall(loadBalancerId, forwardingRuleId, _callback);
        return localVarCall;

    }

    /**
     * Delete Forwarding Rule
     * Delete a Forwarding Rule on a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
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
    public void deleteLoadBalancerForwardingRule(String loadBalancerId, String forwardingRuleId) throws ApiException {
        deleteLoadBalancerForwardingRuleWithHttpInfo(loadBalancerId, forwardingRuleId);
    }

    /**
     * Delete Forwarding Rule
     * Delete a Forwarding Rule on a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
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
    public ApiResponse<Void> deleteLoadBalancerForwardingRuleWithHttpInfo(String loadBalancerId, String forwardingRuleId) throws ApiException {
        okhttp3.Call localVarCall = deleteLoadBalancerForwardingRuleValidateBeforeCall(loadBalancerId, forwardingRuleId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Forwarding Rule (asynchronously)
     * Delete a Forwarding Rule on a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
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
    public okhttp3.Call deleteLoadBalancerForwardingRuleAsync(String loadBalancerId, String forwardingRuleId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteLoadBalancerForwardingRuleValidateBeforeCall(loadBalancerId, forwardingRuleId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getLoadBalancer
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
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
    public okhttp3.Call getLoadBalancerCall(String loadBalancerId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{load-balancer-id}"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()));

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
    private okhttp3.Call getLoadBalancerValidateBeforeCall(String loadBalancerId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling getLoadBalancer(Async)");
        }
        

        okhttp3.Call localVarCall = getLoadBalancerCall(loadBalancerId, _callback);
        return localVarCall;

    }

    /**
     * Get Load Balancer
     * Get information for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @return CreateLoadBalancer202Response
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
    public CreateLoadBalancer202Response getLoadBalancer(String loadBalancerId) throws ApiException {
        ApiResponse<CreateLoadBalancer202Response> localVarResp = getLoadBalancerWithHttpInfo(loadBalancerId);
        return localVarResp.getData();
    }

    /**
     * Get Load Balancer
     * Get information for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @return ApiResponse&lt;CreateLoadBalancer202Response&gt;
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
    public ApiResponse<CreateLoadBalancer202Response> getLoadBalancerWithHttpInfo(String loadBalancerId) throws ApiException {
        okhttp3.Call localVarCall = getLoadBalancerValidateBeforeCall(loadBalancerId, null);
        Type localVarReturnType = new TypeToken<CreateLoadBalancer202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Load Balancer (asynchronously)
     * Get information for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
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
    public okhttp3.Call getLoadBalancerAsync(String loadBalancerId, final ApiCallback<CreateLoadBalancer202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getLoadBalancerValidateBeforeCall(loadBalancerId, _callback);
        Type localVarReturnType = new TypeToken<CreateLoadBalancer202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getLoadBalancerForwardingRule
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
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
    public okhttp3.Call getLoadBalancerForwardingRuleCall(String loadBalancerId, String forwardingRuleId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{load-balancer-id}/forwarding-rules/{forwarding-rule-id}"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()))
            .replaceAll("\\{" + "forwarding-rule-id" + "\\}", localVarApiClient.escapeString(forwardingRuleId.toString()));

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
    private okhttp3.Call getLoadBalancerForwardingRuleValidateBeforeCall(String loadBalancerId, String forwardingRuleId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling getLoadBalancerForwardingRule(Async)");
        }
        
        // verify the required parameter 'forwardingRuleId' is set
        if (forwardingRuleId == null) {
            throw new ApiException("Missing the required parameter 'forwardingRuleId' when calling getLoadBalancerForwardingRule(Async)");
        }
        

        okhttp3.Call localVarCall = getLoadBalancerForwardingRuleCall(loadBalancerId, forwardingRuleId, _callback);
        return localVarCall;

    }

    /**
     * Get Forwarding Rule
     * Get information for a Forwarding Rule on a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
     * @return GetLoadBalancerForwardingRule200Response
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
    public GetLoadBalancerForwardingRule200Response getLoadBalancerForwardingRule(String loadBalancerId, String forwardingRuleId) throws ApiException {
        ApiResponse<GetLoadBalancerForwardingRule200Response> localVarResp = getLoadBalancerForwardingRuleWithHttpInfo(loadBalancerId, forwardingRuleId);
        return localVarResp.getData();
    }

    /**
     * Get Forwarding Rule
     * Get information for a Forwarding Rule on a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
     * @return ApiResponse&lt;GetLoadBalancerForwardingRule200Response&gt;
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
    public ApiResponse<GetLoadBalancerForwardingRule200Response> getLoadBalancerForwardingRuleWithHttpInfo(String loadBalancerId, String forwardingRuleId) throws ApiException {
        okhttp3.Call localVarCall = getLoadBalancerForwardingRuleValidateBeforeCall(loadBalancerId, forwardingRuleId, null);
        Type localVarReturnType = new TypeToken<GetLoadBalancerForwardingRule200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Forwarding Rule (asynchronously)
     * Get information for a Forwarding Rule on a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param forwardingRuleId The [Forwarding Rule id](#operation/list-load-balancer-forwarding-rules). (required)
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
    public okhttp3.Call getLoadBalancerForwardingRuleAsync(String loadBalancerId, String forwardingRuleId, final ApiCallback<GetLoadBalancerForwardingRule200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getLoadBalancerForwardingRuleValidateBeforeCall(loadBalancerId, forwardingRuleId, _callback);
        Type localVarReturnType = new TypeToken<GetLoadBalancerForwardingRule200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getLoadbalancerFirewallRule
     * @param loadbalancerId  (required)
     * @param firewallRuleId  (required)
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
    public okhttp3.Call getLoadbalancerFirewallRuleCall(String loadbalancerId, String firewallRuleId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{loadbalancer-id}/firewall-rules/{firewall-rule-id}"
            .replaceAll("\\{" + "loadbalancer-id" + "\\}", localVarApiClient.escapeString(loadbalancerId.toString()))
            .replaceAll("\\{" + "firewall-rule-id" + "\\}", localVarApiClient.escapeString(firewallRuleId.toString()));

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
    private okhttp3.Call getLoadbalancerFirewallRuleValidateBeforeCall(String loadbalancerId, String firewallRuleId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadbalancerId' is set
        if (loadbalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadbalancerId' when calling getLoadbalancerFirewallRule(Async)");
        }
        
        // verify the required parameter 'firewallRuleId' is set
        if (firewallRuleId == null) {
            throw new ApiException("Missing the required parameter 'firewallRuleId' when calling getLoadbalancerFirewallRule(Async)");
        }
        

        okhttp3.Call localVarCall = getLoadbalancerFirewallRuleCall(loadbalancerId, firewallRuleId, _callback);
        return localVarCall;

    }

    /**
     * Get Firewall Rule
     * Get a firewall rule for a Load Balancer.
     * @param loadbalancerId  (required)
     * @param firewallRuleId  (required)
     * @return LoadbalancerFirewallRule
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
    public LoadbalancerFirewallRule getLoadbalancerFirewallRule(String loadbalancerId, String firewallRuleId) throws ApiException {
        ApiResponse<LoadbalancerFirewallRule> localVarResp = getLoadbalancerFirewallRuleWithHttpInfo(loadbalancerId, firewallRuleId);
        return localVarResp.getData();
    }

    /**
     * Get Firewall Rule
     * Get a firewall rule for a Load Balancer.
     * @param loadbalancerId  (required)
     * @param firewallRuleId  (required)
     * @return ApiResponse&lt;LoadbalancerFirewallRule&gt;
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
    public ApiResponse<LoadbalancerFirewallRule> getLoadbalancerFirewallRuleWithHttpInfo(String loadbalancerId, String firewallRuleId) throws ApiException {
        okhttp3.Call localVarCall = getLoadbalancerFirewallRuleValidateBeforeCall(loadbalancerId, firewallRuleId, null);
        Type localVarReturnType = new TypeToken<LoadbalancerFirewallRule>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Firewall Rule (asynchronously)
     * Get a firewall rule for a Load Balancer.
     * @param loadbalancerId  (required)
     * @param firewallRuleId  (required)
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
    public okhttp3.Call getLoadbalancerFirewallRuleAsync(String loadbalancerId, String firewallRuleId, final ApiCallback<LoadbalancerFirewallRule> _callback) throws ApiException {

        okhttp3.Call localVarCall = getLoadbalancerFirewallRuleValidateBeforeCall(loadbalancerId, firewallRuleId, _callback);
        Type localVarReturnType = new TypeToken<LoadbalancerFirewallRule>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listLoadBalancerForwardingRules
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
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
    public okhttp3.Call listLoadBalancerForwardingRulesCall(String loadBalancerId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{load-balancer-id}/forwarding-rules"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (perPage != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("per_page", perPage));
        }

        if (cursor != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cursor", cursor));
        }

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
    private okhttp3.Call listLoadBalancerForwardingRulesValidateBeforeCall(String loadBalancerId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling listLoadBalancerForwardingRules(Async)");
        }
        

        okhttp3.Call localVarCall = listLoadBalancerForwardingRulesCall(loadBalancerId, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Forwarding Rules
     * List the fowarding rules for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListLoadBalancerForwardingRules200Response
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
    public ListLoadBalancerForwardingRules200Response listLoadBalancerForwardingRules(String loadBalancerId, Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListLoadBalancerForwardingRules200Response> localVarResp = listLoadBalancerForwardingRulesWithHttpInfo(loadBalancerId, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Forwarding Rules
     * List the fowarding rules for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListLoadBalancerForwardingRules200Response&gt;
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
    public ApiResponse<ListLoadBalancerForwardingRules200Response> listLoadBalancerForwardingRulesWithHttpInfo(String loadBalancerId, Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listLoadBalancerForwardingRulesValidateBeforeCall(loadBalancerId, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListLoadBalancerForwardingRules200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Forwarding Rules (asynchronously)
     * List the fowarding rules for a Load Balancer.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
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
    public okhttp3.Call listLoadBalancerForwardingRulesAsync(String loadBalancerId, Integer perPage, String cursor, final ApiCallback<ListLoadBalancerForwardingRules200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listLoadBalancerForwardingRulesValidateBeforeCall(loadBalancerId, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListLoadBalancerForwardingRules200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listLoadBalancers
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
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
    public okhttp3.Call listLoadBalancersCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (perPage != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("per_page", perPage));
        }

        if (cursor != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cursor", cursor));
        }

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
    private okhttp3.Call listLoadBalancersValidateBeforeCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listLoadBalancersCall(perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Load Balancers
     * List the Load Balancers in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListLoadBalancers200Response
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
    public ListLoadBalancers200Response listLoadBalancers(Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListLoadBalancers200Response> localVarResp = listLoadBalancersWithHttpInfo(perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Load Balancers
     * List the Load Balancers in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListLoadBalancers200Response&gt;
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
    public ApiResponse<ListLoadBalancers200Response> listLoadBalancersWithHttpInfo(Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listLoadBalancersValidateBeforeCall(perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListLoadBalancers200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Load Balancers (asynchronously)
     * List the Load Balancers in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
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
    public okhttp3.Call listLoadBalancersAsync(Integer perPage, String cursor, final ApiCallback<ListLoadBalancers200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listLoadBalancersValidateBeforeCall(perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListLoadBalancers200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listLoadbalancerFirewallRules
     * @param loadbalancerId  (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
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
    public okhttp3.Call listLoadbalancerFirewallRulesCall(String loadbalancerId, String perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/load-balancers/{loadbalancer-id}/firewall-rules"
            .replaceAll("\\{" + "loadbalancer-id" + "\\}", localVarApiClient.escapeString(loadbalancerId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (perPage != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("per_page", perPage));
        }

        if (cursor != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cursor", cursor));
        }

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
    private okhttp3.Call listLoadbalancerFirewallRulesValidateBeforeCall(String loadbalancerId, String perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadbalancerId' is set
        if (loadbalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadbalancerId' when calling listLoadbalancerFirewallRules(Async)");
        }
        

        okhttp3.Call localVarCall = listLoadbalancerFirewallRulesCall(loadbalancerId, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Firewall Rules
     * List the firewall rules for a Load Balancer.
     * @param loadbalancerId  (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return LoadbalancerFirewallRule
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
    public LoadbalancerFirewallRule listLoadbalancerFirewallRules(String loadbalancerId, String perPage, String cursor) throws ApiException {
        ApiResponse<LoadbalancerFirewallRule> localVarResp = listLoadbalancerFirewallRulesWithHttpInfo(loadbalancerId, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Firewall Rules
     * List the firewall rules for a Load Balancer.
     * @param loadbalancerId  (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;LoadbalancerFirewallRule&gt;
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
    public ApiResponse<LoadbalancerFirewallRule> listLoadbalancerFirewallRulesWithHttpInfo(String loadbalancerId, String perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listLoadbalancerFirewallRulesValidateBeforeCall(loadbalancerId, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<LoadbalancerFirewallRule>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Firewall Rules (asynchronously)
     * List the firewall rules for a Load Balancer.
     * @param loadbalancerId  (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
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
    public okhttp3.Call listLoadbalancerFirewallRulesAsync(String loadbalancerId, String perPage, String cursor, final ApiCallback<LoadbalancerFirewallRule> _callback) throws ApiException {

        okhttp3.Call localVarCall = listLoadbalancerFirewallRulesValidateBeforeCall(loadbalancerId, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<LoadbalancerFirewallRule>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateLoadBalancer
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param updateLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateLoadBalancerCall(String loadBalancerId, UpdateLoadBalancerRequest updateLoadBalancerRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateLoadBalancerRequest;

        // create path and map variables
        String localVarPath = "/load-balancers/{load-balancer-id}"
            .replaceAll("\\{" + "load-balancer-id" + "\\}", localVarApiClient.escapeString(loadBalancerId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateLoadBalancerValidateBeforeCall(String loadBalancerId, UpdateLoadBalancerRequest updateLoadBalancerRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'loadBalancerId' is set
        if (loadBalancerId == null) {
            throw new ApiException("Missing the required parameter 'loadBalancerId' when calling updateLoadBalancer(Async)");
        }
        

        okhttp3.Call localVarCall = updateLoadBalancerCall(loadBalancerId, updateLoadBalancerRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Load Balancer
     * Update information for a Load Balancer. All attributes are optional. If not set, the attributes will retain their original values.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param updateLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void updateLoadBalancer(String loadBalancerId, UpdateLoadBalancerRequest updateLoadBalancerRequest) throws ApiException {
        updateLoadBalancerWithHttpInfo(loadBalancerId, updateLoadBalancerRequest);
    }

    /**
     * Update Load Balancer
     * Update information for a Load Balancer. All attributes are optional. If not set, the attributes will retain their original values.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param updateLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> updateLoadBalancerWithHttpInfo(String loadBalancerId, UpdateLoadBalancerRequest updateLoadBalancerRequest) throws ApiException {
        okhttp3.Call localVarCall = updateLoadBalancerValidateBeforeCall(loadBalancerId, updateLoadBalancerRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update Load Balancer (asynchronously)
     * Update information for a Load Balancer. All attributes are optional. If not set, the attributes will retain their original values.
     * @param loadBalancerId The [Load Balancer id](#operation/list-load-balancers). (required)
     * @param updateLoadBalancerRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateLoadBalancerAsync(String loadBalancerId, UpdateLoadBalancerRequest updateLoadBalancerRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateLoadBalancerValidateBeforeCall(loadBalancerId, updateLoadBalancerRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
