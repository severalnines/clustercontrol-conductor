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


import org.openapitools.vultrapi.client.model.CreateFirewallGroup201Response;
import org.openapitools.vultrapi.client.model.CreateFirewallGroupRequest;
import org.openapitools.vultrapi.client.model.ListFirewallGroupRules200Response;
import org.openapitools.vultrapi.client.model.ListFirewallGroups200Response;
import org.openapitools.vultrapi.client.model.PostFirewallsFirewallGroupIdRules201Response;
import org.openapitools.vultrapi.client.model.PostFirewallsFirewallGroupIdRulesRequest;
import org.openapitools.vultrapi.client.model.UpdateFirewallGroupRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class FirewallApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public FirewallApi() {
        this(Configuration.getDefaultApiClient());
    }

    public FirewallApi(ApiClient apiClient) {
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
     * Build call for createFirewallGroup
     * @param createFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createFirewallGroupCall(CreateFirewallGroupRequest createFirewallGroupRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createFirewallGroupRequest;

        // create path and map variables
        String localVarPath = "/firewalls";

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
    private okhttp3.Call createFirewallGroupValidateBeforeCall(CreateFirewallGroupRequest createFirewallGroupRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = createFirewallGroupCall(createFirewallGroupRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Firewall Group
     * Create a new Firewall Group.
     * @param createFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateFirewallGroup201Response
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
    public CreateFirewallGroup201Response createFirewallGroup(CreateFirewallGroupRequest createFirewallGroupRequest) throws ApiException {
        ApiResponse<CreateFirewallGroup201Response> localVarResp = createFirewallGroupWithHttpInfo(createFirewallGroupRequest);
        return localVarResp.getData();
    }

    /**
     * Create Firewall Group
     * Create a new Firewall Group.
     * @param createFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateFirewallGroup201Response&gt;
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
    public ApiResponse<CreateFirewallGroup201Response> createFirewallGroupWithHttpInfo(CreateFirewallGroupRequest createFirewallGroupRequest) throws ApiException {
        okhttp3.Call localVarCall = createFirewallGroupValidateBeforeCall(createFirewallGroupRequest, null);
        Type localVarReturnType = new TypeToken<CreateFirewallGroup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Firewall Group (asynchronously)
     * Create a new Firewall Group.
     * @param createFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createFirewallGroupAsync(CreateFirewallGroupRequest createFirewallGroupRequest, final ApiCallback<CreateFirewallGroup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createFirewallGroupValidateBeforeCall(createFirewallGroupRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateFirewallGroup201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteFirewallGroup
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
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
    public okhttp3.Call deleteFirewallGroupCall(String firewallGroupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/firewalls/{firewall-group-id}"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()));

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
    private okhttp3.Call deleteFirewallGroupValidateBeforeCall(String firewallGroupId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling deleteFirewallGroup(Async)");
        }
        

        okhttp3.Call localVarCall = deleteFirewallGroupCall(firewallGroupId, _callback);
        return localVarCall;

    }

    /**
     * Delete Firewall Group
     * Delete a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
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
    public void deleteFirewallGroup(String firewallGroupId) throws ApiException {
        deleteFirewallGroupWithHttpInfo(firewallGroupId);
    }

    /**
     * Delete Firewall Group
     * Delete a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
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
    public ApiResponse<Void> deleteFirewallGroupWithHttpInfo(String firewallGroupId) throws ApiException {
        okhttp3.Call localVarCall = deleteFirewallGroupValidateBeforeCall(firewallGroupId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Firewall Group (asynchronously)
     * Delete a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
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
    public okhttp3.Call deleteFirewallGroupAsync(String firewallGroupId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteFirewallGroupValidateBeforeCall(firewallGroupId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteFirewallGroupRule
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
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
    public okhttp3.Call deleteFirewallGroupRuleCall(String firewallGroupId, String firewallRuleId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/firewalls/{firewall-group-id}/rules/{firewall-rule-id}"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()))
            .replaceAll("\\{" + "firewall-rule-id" + "\\}", localVarApiClient.escapeString(firewallRuleId.toString()));

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
    private okhttp3.Call deleteFirewallGroupRuleValidateBeforeCall(String firewallGroupId, String firewallRuleId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling deleteFirewallGroupRule(Async)");
        }
        
        // verify the required parameter 'firewallRuleId' is set
        if (firewallRuleId == null) {
            throw new ApiException("Missing the required parameter 'firewallRuleId' when calling deleteFirewallGroupRule(Async)");
        }
        

        okhttp3.Call localVarCall = deleteFirewallGroupRuleCall(firewallGroupId, firewallRuleId, _callback);
        return localVarCall;

    }

    /**
     * Delete Firewall Rule
     * Delete a Firewall Rule.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
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
    public void deleteFirewallGroupRule(String firewallGroupId, String firewallRuleId) throws ApiException {
        deleteFirewallGroupRuleWithHttpInfo(firewallGroupId, firewallRuleId);
    }

    /**
     * Delete Firewall Rule
     * Delete a Firewall Rule.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
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
    public ApiResponse<Void> deleteFirewallGroupRuleWithHttpInfo(String firewallGroupId, String firewallRuleId) throws ApiException {
        okhttp3.Call localVarCall = deleteFirewallGroupRuleValidateBeforeCall(firewallGroupId, firewallRuleId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Firewall Rule (asynchronously)
     * Delete a Firewall Rule.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
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
    public okhttp3.Call deleteFirewallGroupRuleAsync(String firewallGroupId, String firewallRuleId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteFirewallGroupRuleValidateBeforeCall(firewallGroupId, firewallRuleId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFirewallGroup
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFirewallGroupCall(String firewallGroupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/firewalls/{firewall-group-id}"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()));

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
    private okhttp3.Call getFirewallGroupValidateBeforeCall(String firewallGroupId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling getFirewallGroup(Async)");
        }
        

        okhttp3.Call localVarCall = getFirewallGroupCall(firewallGroupId, _callback);
        return localVarCall;

    }

    /**
     * Get Firewall Group
     * Get information for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @return CreateFirewallGroup201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateFirewallGroup201Response getFirewallGroup(String firewallGroupId) throws ApiException {
        ApiResponse<CreateFirewallGroup201Response> localVarResp = getFirewallGroupWithHttpInfo(firewallGroupId);
        return localVarResp.getData();
    }

    /**
     * Get Firewall Group
     * Get information for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @return ApiResponse&lt;CreateFirewallGroup201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateFirewallGroup201Response> getFirewallGroupWithHttpInfo(String firewallGroupId) throws ApiException {
        okhttp3.Call localVarCall = getFirewallGroupValidateBeforeCall(firewallGroupId, null);
        Type localVarReturnType = new TypeToken<CreateFirewallGroup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Firewall Group (asynchronously)
     * Get information for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFirewallGroupAsync(String firewallGroupId, final ApiCallback<CreateFirewallGroup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFirewallGroupValidateBeforeCall(firewallGroupId, _callback);
        Type localVarReturnType = new TypeToken<CreateFirewallGroup201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getFirewallGroupRule
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
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
    public okhttp3.Call getFirewallGroupRuleCall(String firewallGroupId, String firewallRuleId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/firewalls/{firewall-group-id}/rules/{firewall-rule-id}"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()))
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
    private okhttp3.Call getFirewallGroupRuleValidateBeforeCall(String firewallGroupId, String firewallRuleId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling getFirewallGroupRule(Async)");
        }
        
        // verify the required parameter 'firewallRuleId' is set
        if (firewallRuleId == null) {
            throw new ApiException("Missing the required parameter 'firewallRuleId' when calling getFirewallGroupRule(Async)");
        }
        

        okhttp3.Call localVarCall = getFirewallGroupRuleCall(firewallGroupId, firewallRuleId, _callback);
        return localVarCall;

    }

    /**
     * Get Firewall Rule
     * Get a Firewall Rule.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
     * @return PostFirewallsFirewallGroupIdRules201Response
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
    public PostFirewallsFirewallGroupIdRules201Response getFirewallGroupRule(String firewallGroupId, String firewallRuleId) throws ApiException {
        ApiResponse<PostFirewallsFirewallGroupIdRules201Response> localVarResp = getFirewallGroupRuleWithHttpInfo(firewallGroupId, firewallRuleId);
        return localVarResp.getData();
    }

    /**
     * Get Firewall Rule
     * Get a Firewall Rule.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
     * @return ApiResponse&lt;PostFirewallsFirewallGroupIdRules201Response&gt;
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
    public ApiResponse<PostFirewallsFirewallGroupIdRules201Response> getFirewallGroupRuleWithHttpInfo(String firewallGroupId, String firewallRuleId) throws ApiException {
        okhttp3.Call localVarCall = getFirewallGroupRuleValidateBeforeCall(firewallGroupId, firewallRuleId, null);
        Type localVarReturnType = new TypeToken<PostFirewallsFirewallGroupIdRules201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Firewall Rule (asynchronously)
     * Get a Firewall Rule.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param firewallRuleId The [Firewall Rule id](#operation/list-firewall-group-rules). (required)
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
    public okhttp3.Call getFirewallGroupRuleAsync(String firewallGroupId, String firewallRuleId, final ApiCallback<PostFirewallsFirewallGroupIdRules201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFirewallGroupRuleValidateBeforeCall(firewallGroupId, firewallRuleId, _callback);
        Type localVarReturnType = new TypeToken<PostFirewallsFirewallGroupIdRules201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listFirewallGroupRules
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
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
    public okhttp3.Call listFirewallGroupRulesCall(String firewallGroupId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/firewalls/{firewall-group-id}/rules"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()));

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
    private okhttp3.Call listFirewallGroupRulesValidateBeforeCall(String firewallGroupId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling listFirewallGroupRules(Async)");
        }
        

        okhttp3.Call localVarCall = listFirewallGroupRulesCall(firewallGroupId, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Firewall Rules
     * Get the Firewall Rules for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListFirewallGroupRules200Response
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
    public ListFirewallGroupRules200Response listFirewallGroupRules(String firewallGroupId, Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListFirewallGroupRules200Response> localVarResp = listFirewallGroupRulesWithHttpInfo(firewallGroupId, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Firewall Rules
     * Get the Firewall Rules for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListFirewallGroupRules200Response&gt;
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
    public ApiResponse<ListFirewallGroupRules200Response> listFirewallGroupRulesWithHttpInfo(String firewallGroupId, Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listFirewallGroupRulesValidateBeforeCall(firewallGroupId, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListFirewallGroupRules200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Firewall Rules (asynchronously)
     * Get the Firewall Rules for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
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
    public okhttp3.Call listFirewallGroupRulesAsync(String firewallGroupId, Integer perPage, String cursor, final ApiCallback<ListFirewallGroupRules200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listFirewallGroupRulesValidateBeforeCall(firewallGroupId, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListFirewallGroupRules200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listFirewallGroups
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listFirewallGroupsCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/firewalls";

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
    private okhttp3.Call listFirewallGroupsValidateBeforeCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listFirewallGroupsCall(perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Firewall Groups
     * Get a list of all Firewall Groups.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListFirewallGroups200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
     </table>
     */
    public ListFirewallGroups200Response listFirewallGroups(Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListFirewallGroups200Response> localVarResp = listFirewallGroupsWithHttpInfo(perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Firewall Groups
     * Get a list of all Firewall Groups.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListFirewallGroups200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListFirewallGroups200Response> listFirewallGroupsWithHttpInfo(Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listFirewallGroupsValidateBeforeCall(perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListFirewallGroups200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Firewall Groups (asynchronously)
     * Get a list of all Firewall Groups.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listFirewallGroupsAsync(Integer perPage, String cursor, final ApiCallback<ListFirewallGroups200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listFirewallGroupsValidateBeforeCall(perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListFirewallGroups200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postFirewallsFirewallGroupIdRules
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param postFirewallsFirewallGroupIdRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call postFirewallsFirewallGroupIdRulesCall(String firewallGroupId, PostFirewallsFirewallGroupIdRulesRequest postFirewallsFirewallGroupIdRulesRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postFirewallsFirewallGroupIdRulesRequest;

        // create path and map variables
        String localVarPath = "/firewalls/{firewall-group-id}/rules"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()));

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
    private okhttp3.Call postFirewallsFirewallGroupIdRulesValidateBeforeCall(String firewallGroupId, PostFirewallsFirewallGroupIdRulesRequest postFirewallsFirewallGroupIdRulesRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling postFirewallsFirewallGroupIdRules(Async)");
        }
        

        okhttp3.Call localVarCall = postFirewallsFirewallGroupIdRulesCall(firewallGroupId, postFirewallsFirewallGroupIdRulesRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Firewall Rules
     * Create a Firewall Rule for a Firewall Group. The attributes &#x60;ip_type&#x60;, &#x60;protocol&#x60;, &#x60;subnet&#x60;, and &#x60;subnet_size&#x60; are required.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param postFirewallsFirewallGroupIdRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return PostFirewallsFirewallGroupIdRules201Response
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
    public PostFirewallsFirewallGroupIdRules201Response postFirewallsFirewallGroupIdRules(String firewallGroupId, PostFirewallsFirewallGroupIdRulesRequest postFirewallsFirewallGroupIdRulesRequest) throws ApiException {
        ApiResponse<PostFirewallsFirewallGroupIdRules201Response> localVarResp = postFirewallsFirewallGroupIdRulesWithHttpInfo(firewallGroupId, postFirewallsFirewallGroupIdRulesRequest);
        return localVarResp.getData();
    }

    /**
     * Create Firewall Rules
     * Create a Firewall Rule for a Firewall Group. The attributes &#x60;ip_type&#x60;, &#x60;protocol&#x60;, &#x60;subnet&#x60;, and &#x60;subnet_size&#x60; are required.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param postFirewallsFirewallGroupIdRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;PostFirewallsFirewallGroupIdRules201Response&gt;
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
    public ApiResponse<PostFirewallsFirewallGroupIdRules201Response> postFirewallsFirewallGroupIdRulesWithHttpInfo(String firewallGroupId, PostFirewallsFirewallGroupIdRulesRequest postFirewallsFirewallGroupIdRulesRequest) throws ApiException {
        okhttp3.Call localVarCall = postFirewallsFirewallGroupIdRulesValidateBeforeCall(firewallGroupId, postFirewallsFirewallGroupIdRulesRequest, null);
        Type localVarReturnType = new TypeToken<PostFirewallsFirewallGroupIdRules201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Firewall Rules (asynchronously)
     * Create a Firewall Rule for a Firewall Group. The attributes &#x60;ip_type&#x60;, &#x60;protocol&#x60;, &#x60;subnet&#x60;, and &#x60;subnet_size&#x60; are required.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param postFirewallsFirewallGroupIdRulesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call postFirewallsFirewallGroupIdRulesAsync(String firewallGroupId, PostFirewallsFirewallGroupIdRulesRequest postFirewallsFirewallGroupIdRulesRequest, final ApiCallback<PostFirewallsFirewallGroupIdRules201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = postFirewallsFirewallGroupIdRulesValidateBeforeCall(firewallGroupId, postFirewallsFirewallGroupIdRulesRequest, _callback);
        Type localVarReturnType = new TypeToken<PostFirewallsFirewallGroupIdRules201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateFirewallGroup
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param updateFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateFirewallGroupCall(String firewallGroupId, UpdateFirewallGroupRequest updateFirewallGroupRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateFirewallGroupRequest;

        // create path and map variables
        String localVarPath = "/firewalls/{firewall-group-id}"
            .replaceAll("\\{" + "firewall-group-id" + "\\}", localVarApiClient.escapeString(firewallGroupId.toString()));

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
    private okhttp3.Call updateFirewallGroupValidateBeforeCall(String firewallGroupId, UpdateFirewallGroupRequest updateFirewallGroupRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'firewallGroupId' is set
        if (firewallGroupId == null) {
            throw new ApiException("Missing the required parameter 'firewallGroupId' when calling updateFirewallGroup(Async)");
        }
        

        okhttp3.Call localVarCall = updateFirewallGroupCall(firewallGroupId, updateFirewallGroupRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Firewall Group
     * Update information for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param updateFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void updateFirewallGroup(String firewallGroupId, UpdateFirewallGroupRequest updateFirewallGroupRequest) throws ApiException {
        updateFirewallGroupWithHttpInfo(firewallGroupId, updateFirewallGroupRequest);
    }

    /**
     * Update Firewall Group
     * Update information for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param updateFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> updateFirewallGroupWithHttpInfo(String firewallGroupId, UpdateFirewallGroupRequest updateFirewallGroupRequest) throws ApiException {
        okhttp3.Call localVarCall = updateFirewallGroupValidateBeforeCall(firewallGroupId, updateFirewallGroupRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update Firewall Group (asynchronously)
     * Update information for a Firewall Group.
     * @param firewallGroupId The [Firewall Group id](#operation/list-firewall-groups). (required)
     * @param updateFirewallGroupRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateFirewallGroupAsync(String firewallGroupId, UpdateFirewallGroupRequest updateFirewallGroupRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateFirewallGroupValidateBeforeCall(firewallGroupId, updateFirewallGroupRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
