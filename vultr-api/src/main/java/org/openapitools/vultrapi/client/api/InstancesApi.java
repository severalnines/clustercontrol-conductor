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


import org.openapitools.vultrapi.client.model.AttachInstanceIso202Response;
import org.openapitools.vultrapi.client.model.AttachInstanceIsoRequest;
import org.openapitools.vultrapi.client.model.AttachInstanceNetworkRequest;
import org.openapitools.vultrapi.client.model.AttachInstanceVpcRequest;
import org.openapitools.vultrapi.client.model.CreateInstance202Response;
import org.openapitools.vultrapi.client.model.CreateInstanceBackupScheduleRequest;
import org.openapitools.vultrapi.client.model.CreateInstanceIpv4Request;
import org.openapitools.vultrapi.client.model.CreateInstanceRequest;
import org.openapitools.vultrapi.client.model.CreateInstanceReverseIpv4Request;
import org.openapitools.vultrapi.client.model.CreateInstanceReverseIpv6Request;
import org.openapitools.vultrapi.client.model.DetachInstanceIso202Response;
import org.openapitools.vultrapi.client.model.DetachInstanceNetworkRequest;
import org.openapitools.vultrapi.client.model.DetachInstanceVpcRequest;
import org.openapitools.vultrapi.client.model.GetBandwidthBaremetal200Response;
import org.openapitools.vultrapi.client.model.GetInstanceBackupSchedule200Response;
import org.openapitools.vultrapi.client.model.GetInstanceIsoStatus200Response;
import org.openapitools.vultrapi.client.model.GetInstanceNeighbors200Response;
import org.openapitools.vultrapi.client.model.GetInstanceUpgrades200Response;
import org.openapitools.vultrapi.client.model.GetInstanceUserdata200Response;
import org.openapitools.vultrapi.client.model.GetIpv4Baremetal200Response;
import org.openapitools.vultrapi.client.model.GetIpv6Baremetal200Response;
import org.openapitools.vultrapi.client.model.HaltInstancesRequest;
import org.openapitools.vultrapi.client.model.ListInstanceIpv6Reverse200Response;
import org.openapitools.vultrapi.client.model.ListInstancePrivateNetworks200Response;
import org.openapitools.vultrapi.client.model.ListInstanceVpcs200Response;
import org.openapitools.vultrapi.client.model.ListInstances200Response;
import org.openapitools.vultrapi.client.model.PostInstancesInstanceIdIpv4ReverseDefaultRequest;
import org.openapitools.vultrapi.client.model.RebootInstancesRequest;
import org.openapitools.vultrapi.client.model.ReinstallInstanceRequest;
import org.openapitools.vultrapi.client.model.RestoreInstance202Response;
import org.openapitools.vultrapi.client.model.RestoreInstanceRequest;
import org.openapitools.vultrapi.client.model.StartInstancesRequest;
import org.openapitools.vultrapi.client.model.UpdateInstanceRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class InstancesApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public InstancesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public InstancesApi(ApiClient apiClient) {
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
     * Build call for attachInstanceIso
     * @param instanceId  (required)
     * @param attachInstanceIsoRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call attachInstanceIsoCall(String instanceId, AttachInstanceIsoRequest attachInstanceIsoRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = attachInstanceIsoRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/iso/attach"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call attachInstanceIsoValidateBeforeCall(String instanceId, AttachInstanceIsoRequest attachInstanceIsoRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling attachInstanceIso(Async)");
        }
        

        okhttp3.Call localVarCall = attachInstanceIsoCall(instanceId, attachInstanceIsoRequest, _callback);
        return localVarCall;

    }

    /**
     * Attach ISO to Instance
     * Attach an ISO to an Instance.
     * @param instanceId  (required)
     * @param attachInstanceIsoRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return AttachInstanceIso202Response
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
    public AttachInstanceIso202Response attachInstanceIso(String instanceId, AttachInstanceIsoRequest attachInstanceIsoRequest) throws ApiException {
        ApiResponse<AttachInstanceIso202Response> localVarResp = attachInstanceIsoWithHttpInfo(instanceId, attachInstanceIsoRequest);
        return localVarResp.getData();
    }

    /**
     * Attach ISO to Instance
     * Attach an ISO to an Instance.
     * @param instanceId  (required)
     * @param attachInstanceIsoRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;AttachInstanceIso202Response&gt;
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
    public ApiResponse<AttachInstanceIso202Response> attachInstanceIsoWithHttpInfo(String instanceId, AttachInstanceIsoRequest attachInstanceIsoRequest) throws ApiException {
        okhttp3.Call localVarCall = attachInstanceIsoValidateBeforeCall(instanceId, attachInstanceIsoRequest, null);
        Type localVarReturnType = new TypeToken<AttachInstanceIso202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Attach ISO to Instance (asynchronously)
     * Attach an ISO to an Instance.
     * @param instanceId  (required)
     * @param attachInstanceIsoRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call attachInstanceIsoAsync(String instanceId, AttachInstanceIsoRequest attachInstanceIsoRequest, final ApiCallback<AttachInstanceIso202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = attachInstanceIsoValidateBeforeCall(instanceId, attachInstanceIsoRequest, _callback);
        Type localVarReturnType = new TypeToken<AttachInstanceIso202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for attachInstanceNetwork
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call attachInstanceNetworkCall(String instanceId, AttachInstanceNetworkRequest attachInstanceNetworkRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = attachInstanceNetworkRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/private-networks/attach"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call attachInstanceNetworkValidateBeforeCall(String instanceId, AttachInstanceNetworkRequest attachInstanceNetworkRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling attachInstanceNetwork(Async)");
        }
        

        okhttp3.Call localVarCall = attachInstanceNetworkCall(instanceId, attachInstanceNetworkRequest, _callback);
        return localVarCall;

    }

    /**
     * Attach Private Network to Instance
     * Attach Private Network to an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Attach VPC to Instance](#operation/attach-instance-vpc) instead.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public void attachInstanceNetwork(String instanceId, AttachInstanceNetworkRequest attachInstanceNetworkRequest) throws ApiException {
        attachInstanceNetworkWithHttpInfo(instanceId, attachInstanceNetworkRequest);
    }

    /**
     * Attach Private Network to Instance
     * Attach Private Network to an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Attach VPC to Instance](#operation/attach-instance-vpc) instead.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     * @deprecated
     */
    @Deprecated
    public ApiResponse<Void> attachInstanceNetworkWithHttpInfo(String instanceId, AttachInstanceNetworkRequest attachInstanceNetworkRequest) throws ApiException {
        okhttp3.Call localVarCall = attachInstanceNetworkValidateBeforeCall(instanceId, attachInstanceNetworkRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Attach Private Network to Instance (asynchronously)
     * Attach Private Network to an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Attach VPC to Instance](#operation/attach-instance-vpc) instead.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call attachInstanceNetworkAsync(String instanceId, AttachInstanceNetworkRequest attachInstanceNetworkRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = attachInstanceNetworkValidateBeforeCall(instanceId, attachInstanceNetworkRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for attachInstanceVpc
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call attachInstanceVpcCall(String instanceId, AttachInstanceVpcRequest attachInstanceVpcRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = attachInstanceVpcRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/vpcs/attach"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call attachInstanceVpcValidateBeforeCall(String instanceId, AttachInstanceVpcRequest attachInstanceVpcRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling attachInstanceVpc(Async)");
        }
        

        okhttp3.Call localVarCall = attachInstanceVpcCall(instanceId, attachInstanceVpcRequest, _callback);
        return localVarCall;

    }

    /**
     * Attach VPC to Instance
     * Attach a VPC to an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void attachInstanceVpc(String instanceId, AttachInstanceVpcRequest attachInstanceVpcRequest) throws ApiException {
        attachInstanceVpcWithHttpInfo(instanceId, attachInstanceVpcRequest);
    }

    /**
     * Attach VPC to Instance
     * Attach a VPC to an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> attachInstanceVpcWithHttpInfo(String instanceId, AttachInstanceVpcRequest attachInstanceVpcRequest) throws ApiException {
        okhttp3.Call localVarCall = attachInstanceVpcValidateBeforeCall(instanceId, attachInstanceVpcRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Attach VPC to Instance (asynchronously)
     * Attach a VPC to an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param attachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call attachInstanceVpcAsync(String instanceId, AttachInstanceVpcRequest attachInstanceVpcRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = attachInstanceVpcValidateBeforeCall(instanceId, attachInstanceVpcRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for createInstance
     * @param createInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createInstanceCall(CreateInstanceRequest createInstanceRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createInstanceRequest;

        // create path and map variables
        String localVarPath = "/instances";

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
    private okhttp3.Call createInstanceValidateBeforeCall(CreateInstanceRequest createInstanceRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = createInstanceCall(createInstanceRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Instance
     * Create a new VPS Instance in a &#x60;region&#x60; with the desired &#x60;plan&#x60;. Choose one of the following to deploy the instance:  * &#x60;os_id&#x60; * &#x60;iso_id&#x60; * &#x60;snapshot_id&#x60; * &#x60;app_id&#x60; * &#x60;image_id&#x60;  Supply other attributes as desired.
     * @param createInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateInstance202Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateInstance202Response createInstance(CreateInstanceRequest createInstanceRequest) throws ApiException {
        ApiResponse<CreateInstance202Response> localVarResp = createInstanceWithHttpInfo(createInstanceRequest);
        return localVarResp.getData();
    }

    /**
     * Create Instance
     * Create a new VPS Instance in a &#x60;region&#x60; with the desired &#x60;plan&#x60;. Choose one of the following to deploy the instance:  * &#x60;os_id&#x60; * &#x60;iso_id&#x60; * &#x60;snapshot_id&#x60; * &#x60;app_id&#x60; * &#x60;image_id&#x60;  Supply other attributes as desired.
     * @param createInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateInstance202Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateInstance202Response> createInstanceWithHttpInfo(CreateInstanceRequest createInstanceRequest) throws ApiException {
        okhttp3.Call localVarCall = createInstanceValidateBeforeCall(createInstanceRequest, null);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Instance (asynchronously)
     * Create a new VPS Instance in a &#x60;region&#x60; with the desired &#x60;plan&#x60;. Choose one of the following to deploy the instance:  * &#x60;os_id&#x60; * &#x60;iso_id&#x60; * &#x60;snapshot_id&#x60; * &#x60;app_id&#x60; * &#x60;image_id&#x60;  Supply other attributes as desired.
     * @param createInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createInstanceAsync(CreateInstanceRequest createInstanceRequest, final ApiCallback<CreateInstance202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createInstanceValidateBeforeCall(createInstanceRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createInstanceBackupSchedule
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceBackupScheduleRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createInstanceBackupScheduleCall(String instanceId, CreateInstanceBackupScheduleRequest createInstanceBackupScheduleRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createInstanceBackupScheduleRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/backup-schedule"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call createInstanceBackupScheduleValidateBeforeCall(String instanceId, CreateInstanceBackupScheduleRequest createInstanceBackupScheduleRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling createInstanceBackupSchedule(Async)");
        }
        

        okhttp3.Call localVarCall = createInstanceBackupScheduleCall(instanceId, createInstanceBackupScheduleRequest, _callback);
        return localVarCall;

    }

    /**
     * Set Instance Backup Schedule
     * Set the backup schedule for an Instance in UTC. The &#x60;type&#x60; is required.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceBackupScheduleRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public void createInstanceBackupSchedule(String instanceId, CreateInstanceBackupScheduleRequest createInstanceBackupScheduleRequest) throws ApiException {
        createInstanceBackupScheduleWithHttpInfo(instanceId, createInstanceBackupScheduleRequest);
    }

    /**
     * Set Instance Backup Schedule
     * Set the backup schedule for an Instance in UTC. The &#x60;type&#x60; is required.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceBackupScheduleRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> createInstanceBackupScheduleWithHttpInfo(String instanceId, CreateInstanceBackupScheduleRequest createInstanceBackupScheduleRequest) throws ApiException {
        okhttp3.Call localVarCall = createInstanceBackupScheduleValidateBeforeCall(instanceId, createInstanceBackupScheduleRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Set Instance Backup Schedule (asynchronously)
     * Set the backup schedule for an Instance in UTC. The &#x60;type&#x60; is required.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceBackupScheduleRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createInstanceBackupScheduleAsync(String instanceId, CreateInstanceBackupScheduleRequest createInstanceBackupScheduleRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = createInstanceBackupScheduleValidateBeforeCall(instanceId, createInstanceBackupScheduleRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for createInstanceIpv4
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createInstanceIpv4Call(String instanceId, CreateInstanceIpv4Request createInstanceIpv4Request, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createInstanceIpv4Request;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/ipv4"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call createInstanceIpv4ValidateBeforeCall(String instanceId, CreateInstanceIpv4Request createInstanceIpv4Request, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling createInstanceIpv4(Async)");
        }
        

        okhttp3.Call localVarCall = createInstanceIpv4Call(instanceId, createInstanceIpv4Request, _callback);
        return localVarCall;

    }

    /**
     * Create IPv4
     * Create an IPv4 address for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return Object
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
    public Object createInstanceIpv4(String instanceId, CreateInstanceIpv4Request createInstanceIpv4Request) throws ApiException {
        ApiResponse<Object> localVarResp = createInstanceIpv4WithHttpInfo(instanceId, createInstanceIpv4Request);
        return localVarResp.getData();
    }

    /**
     * Create IPv4
     * Create an IPv4 address for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;Object&gt;
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
    public ApiResponse<Object> createInstanceIpv4WithHttpInfo(String instanceId, CreateInstanceIpv4Request createInstanceIpv4Request) throws ApiException {
        okhttp3.Call localVarCall = createInstanceIpv4ValidateBeforeCall(instanceId, createInstanceIpv4Request, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create IPv4 (asynchronously)
     * Create an IPv4 address for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createInstanceIpv4Async(String instanceId, CreateInstanceIpv4Request createInstanceIpv4Request, final ApiCallback<Object> _callback) throws ApiException {

        okhttp3.Call localVarCall = createInstanceIpv4ValidateBeforeCall(instanceId, createInstanceIpv4Request, _callback);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createInstanceReverseIpv4
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createInstanceReverseIpv4Call(String instanceId, CreateInstanceReverseIpv4Request createInstanceReverseIpv4Request, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createInstanceReverseIpv4Request;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/ipv4/reverse"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call createInstanceReverseIpv4ValidateBeforeCall(String instanceId, CreateInstanceReverseIpv4Request createInstanceReverseIpv4Request, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling createInstanceReverseIpv4(Async)");
        }
        

        okhttp3.Call localVarCall = createInstanceReverseIpv4Call(instanceId, createInstanceReverseIpv4Request, _callback);
        return localVarCall;

    }

    /**
     * Create Instance Reverse IPv4
     * Create a reverse IPv4 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. 
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void createInstanceReverseIpv4(String instanceId, CreateInstanceReverseIpv4Request createInstanceReverseIpv4Request) throws ApiException {
        createInstanceReverseIpv4WithHttpInfo(instanceId, createInstanceReverseIpv4Request);
    }

    /**
     * Create Instance Reverse IPv4
     * Create a reverse IPv4 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. 
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> createInstanceReverseIpv4WithHttpInfo(String instanceId, CreateInstanceReverseIpv4Request createInstanceReverseIpv4Request) throws ApiException {
        okhttp3.Call localVarCall = createInstanceReverseIpv4ValidateBeforeCall(instanceId, createInstanceReverseIpv4Request, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Create Instance Reverse IPv4 (asynchronously)
     * Create a reverse IPv4 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. 
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv4Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createInstanceReverseIpv4Async(String instanceId, CreateInstanceReverseIpv4Request createInstanceReverseIpv4Request, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = createInstanceReverseIpv4ValidateBeforeCall(instanceId, createInstanceReverseIpv4Request, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for createInstanceReverseIpv6
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv6Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createInstanceReverseIpv6Call(String instanceId, CreateInstanceReverseIpv6Request createInstanceReverseIpv6Request, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createInstanceReverseIpv6Request;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/ipv6/reverse"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call createInstanceReverseIpv6ValidateBeforeCall(String instanceId, CreateInstanceReverseIpv6Request createInstanceReverseIpv6Request, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling createInstanceReverseIpv6(Async)");
        }
        

        okhttp3.Call localVarCall = createInstanceReverseIpv6Call(instanceId, createInstanceReverseIpv6Request, _callback);
        return localVarCall;

    }

    /**
     * Create Instance Reverse IPv6
     * Create a reverse IPv6 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. IP address must be in full, expanded format.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv6Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void createInstanceReverseIpv6(String instanceId, CreateInstanceReverseIpv6Request createInstanceReverseIpv6Request) throws ApiException {
        createInstanceReverseIpv6WithHttpInfo(instanceId, createInstanceReverseIpv6Request);
    }

    /**
     * Create Instance Reverse IPv6
     * Create a reverse IPv6 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. IP address must be in full, expanded format.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv6Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> createInstanceReverseIpv6WithHttpInfo(String instanceId, CreateInstanceReverseIpv6Request createInstanceReverseIpv6Request) throws ApiException {
        okhttp3.Call localVarCall = createInstanceReverseIpv6ValidateBeforeCall(instanceId, createInstanceReverseIpv6Request, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Create Instance Reverse IPv6 (asynchronously)
     * Create a reverse IPv6 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. IP address must be in full, expanded format.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param createInstanceReverseIpv6Request Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call createInstanceReverseIpv6Async(String instanceId, CreateInstanceReverseIpv6Request createInstanceReverseIpv6Request, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = createInstanceReverseIpv6ValidateBeforeCall(instanceId, createInstanceReverseIpv6Request, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call deleteInstanceCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call deleteInstanceValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling deleteInstance(Async)");
        }
        

        okhttp3.Call localVarCall = deleteInstanceCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Delete Instance
     * Delete an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public void deleteInstance(String instanceId) throws ApiException {
        deleteInstanceWithHttpInfo(instanceId);
    }

    /**
     * Delete Instance
     * Delete an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public ApiResponse<Void> deleteInstanceWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = deleteInstanceValidateBeforeCall(instanceId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Instance (asynchronously)
     * Delete an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call deleteInstanceAsync(String instanceId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteInstanceValidateBeforeCall(instanceId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteInstanceIpv4
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv4 The IPv4 address. (required)
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
     </table>
     */
    public okhttp3.Call deleteInstanceIpv4Call(String instanceId, String ipv4, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/ipv4/{ipv4}"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()))
            .replaceAll("\\{" + "ipv4" + "\\}", localVarApiClient.escapeString(ipv4.toString()));

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
    private okhttp3.Call deleteInstanceIpv4ValidateBeforeCall(String instanceId, String ipv4, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling deleteInstanceIpv4(Async)");
        }
        
        // verify the required parameter 'ipv4' is set
        if (ipv4 == null) {
            throw new ApiException("Missing the required parameter 'ipv4' when calling deleteInstanceIpv4(Async)");
        }
        

        okhttp3.Call localVarCall = deleteInstanceIpv4Call(instanceId, ipv4, _callback);
        return localVarCall;

    }

    /**
     * Delete IPv4 Address
     * Delete an IPv4 address from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv4 The IPv4 address. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
     </table>
     */
    public void deleteInstanceIpv4(String instanceId, String ipv4) throws ApiException {
        deleteInstanceIpv4WithHttpInfo(instanceId, ipv4);
    }

    /**
     * Delete IPv4 Address
     * Delete an IPv4 address from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv4 The IPv4 address. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteInstanceIpv4WithHttpInfo(String instanceId, String ipv4) throws ApiException {
        okhttp3.Call localVarCall = deleteInstanceIpv4ValidateBeforeCall(instanceId, ipv4, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete IPv4 Address (asynchronously)
     * Delete an IPv4 address from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv4 The IPv4 address. (required)
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
     </table>
     */
    public okhttp3.Call deleteInstanceIpv4Async(String instanceId, String ipv4, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteInstanceIpv4ValidateBeforeCall(instanceId, ipv4, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteInstanceReverseIpv6
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv6 The IPv6 address. (required)
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
     </table>
     */
    public okhttp3.Call deleteInstanceReverseIpv6Call(String instanceId, String ipv6, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/ipv6/reverse/{ipv6}"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()))
            .replaceAll("\\{" + "ipv6" + "\\}", localVarApiClient.escapeString(ipv6.toString()));

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
    private okhttp3.Call deleteInstanceReverseIpv6ValidateBeforeCall(String instanceId, String ipv6, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling deleteInstanceReverseIpv6(Async)");
        }
        
        // verify the required parameter 'ipv6' is set
        if (ipv6 == null) {
            throw new ApiException("Missing the required parameter 'ipv6' when calling deleteInstanceReverseIpv6(Async)");
        }
        

        okhttp3.Call localVarCall = deleteInstanceReverseIpv6Call(instanceId, ipv6, _callback);
        return localVarCall;

    }

    /**
     * Delete Instance Reverse IPv6
     * Delete the reverse IPv6 for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv6 The IPv6 address. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
     </table>
     */
    public void deleteInstanceReverseIpv6(String instanceId, String ipv6) throws ApiException {
        deleteInstanceReverseIpv6WithHttpInfo(instanceId, ipv6);
    }

    /**
     * Delete Instance Reverse IPv6
     * Delete the reverse IPv6 for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv6 The IPv6 address. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteInstanceReverseIpv6WithHttpInfo(String instanceId, String ipv6) throws ApiException {
        okhttp3.Call localVarCall = deleteInstanceReverseIpv6ValidateBeforeCall(instanceId, ipv6, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Instance Reverse IPv6 (asynchronously)
     * Delete the reverse IPv6 for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param ipv6 The IPv6 address. (required)
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
     </table>
     */
    public okhttp3.Call deleteInstanceReverseIpv6Async(String instanceId, String ipv6, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteInstanceReverseIpv6ValidateBeforeCall(instanceId, ipv6, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for detachInstanceIso
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call detachInstanceIsoCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/iso/detach"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call detachInstanceIsoValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling detachInstanceIso(Async)");
        }
        

        okhttp3.Call localVarCall = detachInstanceIsoCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Detach ISO from instance
     * Detach the ISO from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return DetachInstanceIso202Response
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
    public DetachInstanceIso202Response detachInstanceIso(String instanceId) throws ApiException {
        ApiResponse<DetachInstanceIso202Response> localVarResp = detachInstanceIsoWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Detach ISO from instance
     * Detach the ISO from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;DetachInstanceIso202Response&gt;
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
    public ApiResponse<DetachInstanceIso202Response> detachInstanceIsoWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = detachInstanceIsoValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<DetachInstanceIso202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Detach ISO from instance (asynchronously)
     * Detach the ISO from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call detachInstanceIsoAsync(String instanceId, final ApiCallback<DetachInstanceIso202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = detachInstanceIsoValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<DetachInstanceIso202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for detachInstanceNetwork
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call detachInstanceNetworkCall(String instanceId, DetachInstanceNetworkRequest detachInstanceNetworkRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = detachInstanceNetworkRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/private-networks/detach"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call detachInstanceNetworkValidateBeforeCall(String instanceId, DetachInstanceNetworkRequest detachInstanceNetworkRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling detachInstanceNetwork(Async)");
        }
        

        okhttp3.Call localVarCall = detachInstanceNetworkCall(instanceId, detachInstanceNetworkRequest, _callback);
        return localVarCall;

    }

    /**
     * Detach Private Network from Instance.
     * Detach Private Network from an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Detach VPC from Instance](#operation/detach-instance-vpc) instead.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public void detachInstanceNetwork(String instanceId, DetachInstanceNetworkRequest detachInstanceNetworkRequest) throws ApiException {
        detachInstanceNetworkWithHttpInfo(instanceId, detachInstanceNetworkRequest);
    }

    /**
     * Detach Private Network from Instance.
     * Detach Private Network from an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Detach VPC from Instance](#operation/detach-instance-vpc) instead.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     * @deprecated
     */
    @Deprecated
    public ApiResponse<Void> detachInstanceNetworkWithHttpInfo(String instanceId, DetachInstanceNetworkRequest detachInstanceNetworkRequest) throws ApiException {
        okhttp3.Call localVarCall = detachInstanceNetworkValidateBeforeCall(instanceId, detachInstanceNetworkRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Detach Private Network from Instance. (asynchronously)
     * Detach Private Network from an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Detach VPC from Instance](#operation/detach-instance-vpc) instead.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceNetworkRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call detachInstanceNetworkAsync(String instanceId, DetachInstanceNetworkRequest detachInstanceNetworkRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = detachInstanceNetworkValidateBeforeCall(instanceId, detachInstanceNetworkRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for detachInstanceVpc
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call detachInstanceVpcCall(String instanceId, DetachInstanceVpcRequest detachInstanceVpcRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = detachInstanceVpcRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/vpcs/detach"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call detachInstanceVpcValidateBeforeCall(String instanceId, DetachInstanceVpcRequest detachInstanceVpcRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling detachInstanceVpc(Async)");
        }
        

        okhttp3.Call localVarCall = detachInstanceVpcCall(instanceId, detachInstanceVpcRequest, _callback);
        return localVarCall;

    }

    /**
     * Detach VPC from Instance
     * Detach a VPC from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void detachInstanceVpc(String instanceId, DetachInstanceVpcRequest detachInstanceVpcRequest) throws ApiException {
        detachInstanceVpcWithHttpInfo(instanceId, detachInstanceVpcRequest);
    }

    /**
     * Detach VPC from Instance
     * Detach a VPC from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> detachInstanceVpcWithHttpInfo(String instanceId, DetachInstanceVpcRequest detachInstanceVpcRequest) throws ApiException {
        okhttp3.Call localVarCall = detachInstanceVpcValidateBeforeCall(instanceId, detachInstanceVpcRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Detach VPC from Instance (asynchronously)
     * Detach a VPC from an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param detachInstanceVpcRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call detachInstanceVpcAsync(String instanceId, DetachInstanceVpcRequest detachInstanceVpcRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = detachInstanceVpcValidateBeforeCall(instanceId, detachInstanceVpcRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstance(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Get Instance
     * Get information about an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return CreateInstance202Response
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
    public CreateInstance202Response getInstance(String instanceId) throws ApiException {
        ApiResponse<CreateInstance202Response> localVarResp = getInstanceWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Get Instance
     * Get information about an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;CreateInstance202Response&gt;
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
    public ApiResponse<CreateInstance202Response> getInstanceWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Instance (asynchronously)
     * Get information about an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceAsync(String instanceId, final ApiCallback<CreateInstance202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceBackupSchedule
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceBackupScheduleCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/backup-schedule"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceBackupScheduleValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceBackupSchedule(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceBackupScheduleCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Get Instance Backup Schedule
     * Get the backup schedule for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return GetInstanceBackupSchedule200Response
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
    public GetInstanceBackupSchedule200Response getInstanceBackupSchedule(String instanceId) throws ApiException {
        ApiResponse<GetInstanceBackupSchedule200Response> localVarResp = getInstanceBackupScheduleWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Get Instance Backup Schedule
     * Get the backup schedule for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;GetInstanceBackupSchedule200Response&gt;
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
    public ApiResponse<GetInstanceBackupSchedule200Response> getInstanceBackupScheduleWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceBackupScheduleValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<GetInstanceBackupSchedule200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Instance Backup Schedule (asynchronously)
     * Get the backup schedule for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceBackupScheduleAsync(String instanceId, final ApiCallback<GetInstanceBackupSchedule200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceBackupScheduleValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<GetInstanceBackupSchedule200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceBandwidth
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceBandwidthCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/bandwidth"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceBandwidthValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceBandwidth(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceBandwidthCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Instance Bandwidth
     * Get bandwidth information about an Instance.&lt;br&gt;&lt;br&gt;The &#x60;bandwidth&#x60; object in a successful response contains objects representing a day in the month. The date is denoted by the nested object keys. Days begin and end in the UTC timezone. The bandwidth utilization data contained within the date object is refreshed periodically. We do not recommend using this endpoint to gather real-time metrics.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return GetBandwidthBaremetal200Response
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
    public GetBandwidthBaremetal200Response getInstanceBandwidth(String instanceId) throws ApiException {
        ApiResponse<GetBandwidthBaremetal200Response> localVarResp = getInstanceBandwidthWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Instance Bandwidth
     * Get bandwidth information about an Instance.&lt;br&gt;&lt;br&gt;The &#x60;bandwidth&#x60; object in a successful response contains objects representing a day in the month. The date is denoted by the nested object keys. Days begin and end in the UTC timezone. The bandwidth utilization data contained within the date object is refreshed periodically. We do not recommend using this endpoint to gather real-time metrics.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;GetBandwidthBaremetal200Response&gt;
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
    public ApiResponse<GetBandwidthBaremetal200Response> getInstanceBandwidthWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceBandwidthValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<GetBandwidthBaremetal200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Instance Bandwidth (asynchronously)
     * Get bandwidth information about an Instance.&lt;br&gt;&lt;br&gt;The &#x60;bandwidth&#x60; object in a successful response contains objects representing a day in the month. The date is denoted by the nested object keys. Days begin and end in the UTC timezone. The bandwidth utilization data contained within the date object is refreshed periodically. We do not recommend using this endpoint to gather real-time metrics.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceBandwidthAsync(String instanceId, final ApiCallback<GetBandwidthBaremetal200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceBandwidthValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<GetBandwidthBaremetal200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceIpv4
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param publicNetwork If &#x60;true&#x60;, includes information about the public network adapter (such as MAC address) with the &#x60;main_ip&#x60; entry. (optional)
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
    public okhttp3.Call getInstanceIpv4Call(String instanceId, Boolean publicNetwork, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/ipv4"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (publicNetwork != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("public_network", publicNetwork));
        }

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
    private okhttp3.Call getInstanceIpv4ValidateBeforeCall(String instanceId, Boolean publicNetwork, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceIpv4(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceIpv4Call(instanceId, publicNetwork, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Instance IPv4 Information
     * List the IPv4 information for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param publicNetwork If &#x60;true&#x60;, includes information about the public network adapter (such as MAC address) with the &#x60;main_ip&#x60; entry. (optional)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return GetIpv4Baremetal200Response
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
    public GetIpv4Baremetal200Response getInstanceIpv4(String instanceId, Boolean publicNetwork, Integer perPage, String cursor) throws ApiException {
        ApiResponse<GetIpv4Baremetal200Response> localVarResp = getInstanceIpv4WithHttpInfo(instanceId, publicNetwork, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Instance IPv4 Information
     * List the IPv4 information for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param publicNetwork If &#x60;true&#x60;, includes information about the public network adapter (such as MAC address) with the &#x60;main_ip&#x60; entry. (optional)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;GetIpv4Baremetal200Response&gt;
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
    public ApiResponse<GetIpv4Baremetal200Response> getInstanceIpv4WithHttpInfo(String instanceId, Boolean publicNetwork, Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = getInstanceIpv4ValidateBeforeCall(instanceId, publicNetwork, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<GetIpv4Baremetal200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Instance IPv4 Information (asynchronously)
     * List the IPv4 information for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param publicNetwork If &#x60;true&#x60;, includes information about the public network adapter (such as MAC address) with the &#x60;main_ip&#x60; entry. (optional)
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
    public okhttp3.Call getInstanceIpv4Async(String instanceId, Boolean publicNetwork, Integer perPage, String cursor, final ApiCallback<GetIpv4Baremetal200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceIpv4ValidateBeforeCall(instanceId, publicNetwork, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<GetIpv4Baremetal200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceIpv6
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceIpv6Call(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/ipv6"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceIpv6ValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceIpv6(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceIpv6Call(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Get Instance IPv6 Information
     * Get the IPv6 information for an VPS Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return GetIpv6Baremetal200Response
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
    public GetIpv6Baremetal200Response getInstanceIpv6(String instanceId) throws ApiException {
        ApiResponse<GetIpv6Baremetal200Response> localVarResp = getInstanceIpv6WithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Get Instance IPv6 Information
     * Get the IPv6 information for an VPS Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;GetIpv6Baremetal200Response&gt;
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
    public ApiResponse<GetIpv6Baremetal200Response> getInstanceIpv6WithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceIpv6ValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<GetIpv6Baremetal200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Instance IPv6 Information (asynchronously)
     * Get the IPv6 information for an VPS Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceIpv6Async(String instanceId, final ApiCallback<GetIpv6Baremetal200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceIpv6ValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<GetIpv6Baremetal200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceIsoStatus
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceIsoStatusCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/iso"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceIsoStatusValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceIsoStatus(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceIsoStatusCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Get Instance ISO Status
     * Get the ISO status for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return GetInstanceIsoStatus200Response
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
    public GetInstanceIsoStatus200Response getInstanceIsoStatus(String instanceId) throws ApiException {
        ApiResponse<GetInstanceIsoStatus200Response> localVarResp = getInstanceIsoStatusWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Get Instance ISO Status
     * Get the ISO status for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;GetInstanceIsoStatus200Response&gt;
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
    public ApiResponse<GetInstanceIsoStatus200Response> getInstanceIsoStatusWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceIsoStatusValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<GetInstanceIsoStatus200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Instance ISO Status (asynchronously)
     * Get the ISO status for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceIsoStatusAsync(String instanceId, final ApiCallback<GetInstanceIsoStatus200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceIsoStatusValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<GetInstanceIsoStatus200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceNeighbors
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceNeighborsCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/neighbors"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceNeighborsValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceNeighbors(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceNeighborsCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Get Instance neighbors
     * Get a list of other instances in the same location as this Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return GetInstanceNeighbors200Response
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
    public GetInstanceNeighbors200Response getInstanceNeighbors(String instanceId) throws ApiException {
        ApiResponse<GetInstanceNeighbors200Response> localVarResp = getInstanceNeighborsWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Get Instance neighbors
     * Get a list of other instances in the same location as this Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;GetInstanceNeighbors200Response&gt;
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
    public ApiResponse<GetInstanceNeighbors200Response> getInstanceNeighborsWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceNeighborsValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<GetInstanceNeighbors200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Instance neighbors (asynchronously)
     * Get a list of other instances in the same location as this Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call getInstanceNeighborsAsync(String instanceId, final ApiCallback<GetInstanceNeighbors200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceNeighborsValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<GetInstanceNeighbors200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceUpgrades
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param type Filter upgrade by type:  - all (applications, os, plans) - applications - os - plans (optional)
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
    public okhttp3.Call getInstanceUpgradesCall(String instanceId, String type, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/upgrades"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (type != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
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
    private okhttp3.Call getInstanceUpgradesValidateBeforeCall(String instanceId, String type, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceUpgrades(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceUpgradesCall(instanceId, type, _callback);
        return localVarCall;

    }

    /**
     * Get Available Instance Upgrades
     * Get available upgrades for an Instance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param type Filter upgrade by type:  - all (applications, os, plans) - applications - os - plans (optional)
     * @return GetInstanceUpgrades200Response
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
    public GetInstanceUpgrades200Response getInstanceUpgrades(String instanceId, String type) throws ApiException {
        ApiResponse<GetInstanceUpgrades200Response> localVarResp = getInstanceUpgradesWithHttpInfo(instanceId, type);
        return localVarResp.getData();
    }

    /**
     * Get Available Instance Upgrades
     * Get available upgrades for an Instance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param type Filter upgrade by type:  - all (applications, os, plans) - applications - os - plans (optional)
     * @return ApiResponse&lt;GetInstanceUpgrades200Response&gt;
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
    public ApiResponse<GetInstanceUpgrades200Response> getInstanceUpgradesWithHttpInfo(String instanceId, String type) throws ApiException {
        okhttp3.Call localVarCall = getInstanceUpgradesValidateBeforeCall(instanceId, type, null);
        Type localVarReturnType = new TypeToken<GetInstanceUpgrades200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Available Instance Upgrades (asynchronously)
     * Get available upgrades for an Instance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param type Filter upgrade by type:  - all (applications, os, plans) - applications - os - plans (optional)
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
    public okhttp3.Call getInstanceUpgradesAsync(String instanceId, String type, final ApiCallback<GetInstanceUpgrades200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceUpgradesValidateBeforeCall(instanceId, type, _callback);
        Type localVarReturnType = new TypeToken<GetInstanceUpgrades200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getInstanceUserdata
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getInstanceUserdataCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/user-data"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getInstanceUserdataValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getInstanceUserdata(Async)");
        }
        

        okhttp3.Call localVarCall = getInstanceUserdataCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Get Instance User Data
     * Get the user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return GetInstanceUserdata200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public GetInstanceUserdata200Response getInstanceUserdata(String instanceId) throws ApiException {
        ApiResponse<GetInstanceUserdata200Response> localVarResp = getInstanceUserdataWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * Get Instance User Data
     * Get the user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;GetInstanceUserdata200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetInstanceUserdata200Response> getInstanceUserdataWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = getInstanceUserdataValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<GetInstanceUserdata200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Instance User Data (asynchronously)
     * Get the user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getInstanceUserdataAsync(String instanceId, final ApiCallback<GetInstanceUserdata200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getInstanceUserdataValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<GetInstanceUserdata200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for haltInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call haltInstanceCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/halt"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call haltInstanceValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling haltInstance(Async)");
        }
        

        okhttp3.Call localVarCall = haltInstanceCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Halt Instance
     * Halt an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public void haltInstance(String instanceId) throws ApiException {
        haltInstanceWithHttpInfo(instanceId);
    }

    /**
     * Halt Instance
     * Halt an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public ApiResponse<Void> haltInstanceWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = haltInstanceValidateBeforeCall(instanceId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Halt Instance (asynchronously)
     * Halt an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call haltInstanceAsync(String instanceId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = haltInstanceValidateBeforeCall(instanceId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for haltInstances
     * @param haltInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call haltInstancesCall(HaltInstancesRequest haltInstancesRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = haltInstancesRequest;

        // create path and map variables
        String localVarPath = "/instances/halt";

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
    private okhttp3.Call haltInstancesValidateBeforeCall(HaltInstancesRequest haltInstancesRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = haltInstancesCall(haltInstancesRequest, _callback);
        return localVarCall;

    }

    /**
     * Halt Instances
     * Halt Instances.
     * @param haltInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void haltInstances(HaltInstancesRequest haltInstancesRequest) throws ApiException {
        haltInstancesWithHttpInfo(haltInstancesRequest);
    }

    /**
     * Halt Instances
     * Halt Instances.
     * @param haltInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> haltInstancesWithHttpInfo(HaltInstancesRequest haltInstancesRequest) throws ApiException {
        okhttp3.Call localVarCall = haltInstancesValidateBeforeCall(haltInstancesRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Halt Instances (asynchronously)
     * Halt Instances.
     * @param haltInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call haltInstancesAsync(HaltInstancesRequest haltInstancesRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = haltInstancesValidateBeforeCall(haltInstancesRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for listInstanceIpv6Reverse
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listInstanceIpv6ReverseCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/ipv6/reverse"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call listInstanceIpv6ReverseValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling listInstanceIpv6Reverse(Async)");
        }
        

        okhttp3.Call localVarCall = listInstanceIpv6ReverseCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * List Instance IPv6 Reverse
     * List the reverse IPv6 information for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ListInstanceIpv6Reverse200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ListInstanceIpv6Reverse200Response listInstanceIpv6Reverse(String instanceId) throws ApiException {
        ApiResponse<ListInstanceIpv6Reverse200Response> localVarResp = listInstanceIpv6ReverseWithHttpInfo(instanceId);
        return localVarResp.getData();
    }

    /**
     * List Instance IPv6 Reverse
     * List the reverse IPv6 information for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @return ApiResponse&lt;ListInstanceIpv6Reverse200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListInstanceIpv6Reverse200Response> listInstanceIpv6ReverseWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = listInstanceIpv6ReverseValidateBeforeCall(instanceId, null);
        Type localVarReturnType = new TypeToken<ListInstanceIpv6Reverse200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Instance IPv6 Reverse (asynchronously)
     * List the reverse IPv6 information for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listInstanceIpv6ReverseAsync(String instanceId, final ApiCallback<ListInstanceIpv6Reverse200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listInstanceIpv6ReverseValidateBeforeCall(instanceId, _callback);
        Type localVarReturnType = new TypeToken<ListInstanceIpv6Reverse200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listInstancePrivateNetworks
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call listInstancePrivateNetworksCall(String instanceId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/private-networks"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call listInstancePrivateNetworksValidateBeforeCall(String instanceId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling listInstancePrivateNetworks(Async)");
        }
        

        okhttp3.Call localVarCall = listInstancePrivateNetworksCall(instanceId, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List instance Private Networks
     * **Deprecated**: use [List Instance VPCs](#operation/list-instance-vpcs) instead.&lt;br&gt;&lt;br&gt;List the private networks for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListInstancePrivateNetworks200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ListInstancePrivateNetworks200Response listInstancePrivateNetworks(String instanceId, Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListInstancePrivateNetworks200Response> localVarResp = listInstancePrivateNetworksWithHttpInfo(instanceId, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List instance Private Networks
     * **Deprecated**: use [List Instance VPCs](#operation/list-instance-vpcs) instead.&lt;br&gt;&lt;br&gt;List the private networks for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListInstancePrivateNetworks200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ApiResponse<ListInstancePrivateNetworks200Response> listInstancePrivateNetworksWithHttpInfo(String instanceId, Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listInstancePrivateNetworksValidateBeforeCall(instanceId, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListInstancePrivateNetworks200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List instance Private Networks (asynchronously)
     * **Deprecated**: use [List Instance VPCs](#operation/list-instance-vpcs) instead.&lt;br&gt;&lt;br&gt;List the private networks for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call listInstancePrivateNetworksAsync(String instanceId, Integer perPage, String cursor, final ApiCallback<ListInstancePrivateNetworks200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listInstancePrivateNetworksValidateBeforeCall(instanceId, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListInstancePrivateNetworks200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listInstanceVpcs
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call listInstanceVpcsCall(String instanceId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/vpcs"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call listInstanceVpcsValidateBeforeCall(String instanceId, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling listInstanceVpcs(Async)");
        }
        

        okhttp3.Call localVarCall = listInstanceVpcsCall(instanceId, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List instance VPCs
     * List the VPCs for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListInstanceVpcs200Response
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
    public ListInstanceVpcs200Response listInstanceVpcs(String instanceId, Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListInstanceVpcs200Response> localVarResp = listInstanceVpcsWithHttpInfo(instanceId, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List instance VPCs
     * List the VPCs for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListInstanceVpcs200Response&gt;
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
    public ApiResponse<ListInstanceVpcs200Response> listInstanceVpcsWithHttpInfo(String instanceId, Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listInstanceVpcsValidateBeforeCall(instanceId, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListInstanceVpcs200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List instance VPCs (asynchronously)
     * List the VPCs for an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call listInstanceVpcsAsync(String instanceId, Integer perPage, String cursor, final ApiCallback<ListInstanceVpcs200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listInstanceVpcsValidateBeforeCall(instanceId, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListInstanceVpcs200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listInstances
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param tag Filter by specific tag. (optional)
     * @param label Filter by label. (optional)
     * @param mainIp Filter by main ip address. (optional)
     * @param region Filter by [Region id](#operation/list-regions). (optional)
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
    public okhttp3.Call listInstancesCall(Integer perPage, String cursor, String tag, String label, String mainIp, String region, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances";

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

        if (tag != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tag", tag));
        }

        if (label != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("label", label));
        }

        if (mainIp != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("main_ip", mainIp));
        }

        if (region != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("region", region));
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
    private okhttp3.Call listInstancesValidateBeforeCall(Integer perPage, String cursor, String tag, String label, String mainIp, String region, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listInstancesCall(perPage, cursor, tag, label, mainIp, region, _callback);
        return localVarCall;

    }

    /**
     * List Instances
     * List all VPS instances in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param tag Filter by specific tag. (optional)
     * @param label Filter by label. (optional)
     * @param mainIp Filter by main ip address. (optional)
     * @param region Filter by [Region id](#operation/list-regions). (optional)
     * @return ListInstances200Response
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
    public ListInstances200Response listInstances(Integer perPage, String cursor, String tag, String label, String mainIp, String region) throws ApiException {
        ApiResponse<ListInstances200Response> localVarResp = listInstancesWithHttpInfo(perPage, cursor, tag, label, mainIp, region);
        return localVarResp.getData();
    }

    /**
     * List Instances
     * List all VPS instances in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param tag Filter by specific tag. (optional)
     * @param label Filter by label. (optional)
     * @param mainIp Filter by main ip address. (optional)
     * @param region Filter by [Region id](#operation/list-regions). (optional)
     * @return ApiResponse&lt;ListInstances200Response&gt;
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
    public ApiResponse<ListInstances200Response> listInstancesWithHttpInfo(Integer perPage, String cursor, String tag, String label, String mainIp, String region) throws ApiException {
        okhttp3.Call localVarCall = listInstancesValidateBeforeCall(perPage, cursor, tag, label, mainIp, region, null);
        Type localVarReturnType = new TypeToken<ListInstances200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Instances (asynchronously)
     * List all VPS instances in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param tag Filter by specific tag. (optional)
     * @param label Filter by label. (optional)
     * @param mainIp Filter by main ip address. (optional)
     * @param region Filter by [Region id](#operation/list-regions). (optional)
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
    public okhttp3.Call listInstancesAsync(Integer perPage, String cursor, String tag, String label, String mainIp, String region, final ApiCallback<ListInstances200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listInstancesValidateBeforeCall(perPage, cursor, tag, label, mainIp, region, _callback);
        Type localVarReturnType = new TypeToken<ListInstances200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for postInstancesInstanceIdIpv4ReverseDefault
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param postInstancesInstanceIdIpv4ReverseDefaultRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call postInstancesInstanceIdIpv4ReverseDefaultCall(String instanceId, PostInstancesInstanceIdIpv4ReverseDefaultRequest postInstancesInstanceIdIpv4ReverseDefaultRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postInstancesInstanceIdIpv4ReverseDefaultRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/ipv4/reverse/default"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call postInstancesInstanceIdIpv4ReverseDefaultValidateBeforeCall(String instanceId, PostInstancesInstanceIdIpv4ReverseDefaultRequest postInstancesInstanceIdIpv4ReverseDefaultRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling postInstancesInstanceIdIpv4ReverseDefault(Async)");
        }
        

        okhttp3.Call localVarCall = postInstancesInstanceIdIpv4ReverseDefaultCall(instanceId, postInstancesInstanceIdIpv4ReverseDefaultRequest, _callback);
        return localVarCall;

    }

    /**
     * Set Default Reverse DNS Entry
     * Set a reverse DNS entry for an IPv4 address
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param postInstancesInstanceIdIpv4ReverseDefaultRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void postInstancesInstanceIdIpv4ReverseDefault(String instanceId, PostInstancesInstanceIdIpv4ReverseDefaultRequest postInstancesInstanceIdIpv4ReverseDefaultRequest) throws ApiException {
        postInstancesInstanceIdIpv4ReverseDefaultWithHttpInfo(instanceId, postInstancesInstanceIdIpv4ReverseDefaultRequest);
    }

    /**
     * Set Default Reverse DNS Entry
     * Set a reverse DNS entry for an IPv4 address
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param postInstancesInstanceIdIpv4ReverseDefaultRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> postInstancesInstanceIdIpv4ReverseDefaultWithHttpInfo(String instanceId, PostInstancesInstanceIdIpv4ReverseDefaultRequest postInstancesInstanceIdIpv4ReverseDefaultRequest) throws ApiException {
        okhttp3.Call localVarCall = postInstancesInstanceIdIpv4ReverseDefaultValidateBeforeCall(instanceId, postInstancesInstanceIdIpv4ReverseDefaultRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Set Default Reverse DNS Entry (asynchronously)
     * Set a reverse DNS entry for an IPv4 address
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param postInstancesInstanceIdIpv4ReverseDefaultRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call postInstancesInstanceIdIpv4ReverseDefaultAsync(String instanceId, PostInstancesInstanceIdIpv4ReverseDefaultRequest postInstancesInstanceIdIpv4ReverseDefaultRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = postInstancesInstanceIdIpv4ReverseDefaultValidateBeforeCall(instanceId, postInstancesInstanceIdIpv4ReverseDefaultRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for rebootInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call rebootInstanceCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/reboot"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call rebootInstanceValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling rebootInstance(Async)");
        }
        

        okhttp3.Call localVarCall = rebootInstanceCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Reboot Instance
     * Reboot an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public void rebootInstance(String instanceId) throws ApiException {
        rebootInstanceWithHttpInfo(instanceId);
    }

    /**
     * Reboot Instance
     * Reboot an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public ApiResponse<Void> rebootInstanceWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = rebootInstanceValidateBeforeCall(instanceId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Reboot Instance (asynchronously)
     * Reboot an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call rebootInstanceAsync(String instanceId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = rebootInstanceValidateBeforeCall(instanceId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for rebootInstances
     * @param rebootInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call rebootInstancesCall(RebootInstancesRequest rebootInstancesRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = rebootInstancesRequest;

        // create path and map variables
        String localVarPath = "/instances/reboot";

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
    private okhttp3.Call rebootInstancesValidateBeforeCall(RebootInstancesRequest rebootInstancesRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = rebootInstancesCall(rebootInstancesRequest, _callback);
        return localVarCall;

    }

    /**
     * Reboot instances
     * Reboot Instances.
     * @param rebootInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void rebootInstances(RebootInstancesRequest rebootInstancesRequest) throws ApiException {
        rebootInstancesWithHttpInfo(rebootInstancesRequest);
    }

    /**
     * Reboot instances
     * Reboot Instances.
     * @param rebootInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> rebootInstancesWithHttpInfo(RebootInstancesRequest rebootInstancesRequest) throws ApiException {
        okhttp3.Call localVarCall = rebootInstancesValidateBeforeCall(rebootInstancesRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Reboot instances (asynchronously)
     * Reboot Instances.
     * @param rebootInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call rebootInstancesAsync(RebootInstancesRequest rebootInstancesRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = rebootInstancesValidateBeforeCall(rebootInstancesRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for reinstallInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param reinstallInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call reinstallInstanceCall(String instanceId, ReinstallInstanceRequest reinstallInstanceRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = reinstallInstanceRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/reinstall"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call reinstallInstanceValidateBeforeCall(String instanceId, ReinstallInstanceRequest reinstallInstanceRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling reinstallInstance(Async)");
        }
        

        okhttp3.Call localVarCall = reinstallInstanceCall(instanceId, reinstallInstanceRequest, _callback);
        return localVarCall;

    }

    /**
     * Reinstall Instance
     * Reinstall an Instance using an optional &#x60;hostname&#x60;.  **Note:** This action may take a few extra seconds to complete.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param reinstallInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateInstance202Response
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
    public CreateInstance202Response reinstallInstance(String instanceId, ReinstallInstanceRequest reinstallInstanceRequest) throws ApiException {
        ApiResponse<CreateInstance202Response> localVarResp = reinstallInstanceWithHttpInfo(instanceId, reinstallInstanceRequest);
        return localVarResp.getData();
    }

    /**
     * Reinstall Instance
     * Reinstall an Instance using an optional &#x60;hostname&#x60;.  **Note:** This action may take a few extra seconds to complete.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param reinstallInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateInstance202Response&gt;
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
    public ApiResponse<CreateInstance202Response> reinstallInstanceWithHttpInfo(String instanceId, ReinstallInstanceRequest reinstallInstanceRequest) throws ApiException {
        okhttp3.Call localVarCall = reinstallInstanceValidateBeforeCall(instanceId, reinstallInstanceRequest, null);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Reinstall Instance (asynchronously)
     * Reinstall an Instance using an optional &#x60;hostname&#x60;.  **Note:** This action may take a few extra seconds to complete.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param reinstallInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call reinstallInstanceAsync(String instanceId, ReinstallInstanceRequest reinstallInstanceRequest, final ApiCallback<CreateInstance202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = reinstallInstanceValidateBeforeCall(instanceId, reinstallInstanceRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for restoreInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param restoreInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call restoreInstanceCall(String instanceId, RestoreInstanceRequest restoreInstanceRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = restoreInstanceRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}/restore"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call restoreInstanceValidateBeforeCall(String instanceId, RestoreInstanceRequest restoreInstanceRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling restoreInstance(Async)");
        }
        

        okhttp3.Call localVarCall = restoreInstanceCall(instanceId, restoreInstanceRequest, _callback);
        return localVarCall;

    }

    /**
     * Restore Instance
     * Restore an Instance from either &#x60;backup_id&#x60; or &#x60;snapshot_id&#x60;.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param restoreInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return RestoreInstance202Response
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
    public RestoreInstance202Response restoreInstance(String instanceId, RestoreInstanceRequest restoreInstanceRequest) throws ApiException {
        ApiResponse<RestoreInstance202Response> localVarResp = restoreInstanceWithHttpInfo(instanceId, restoreInstanceRequest);
        return localVarResp.getData();
    }

    /**
     * Restore Instance
     * Restore an Instance from either &#x60;backup_id&#x60; or &#x60;snapshot_id&#x60;.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param restoreInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;RestoreInstance202Response&gt;
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
    public ApiResponse<RestoreInstance202Response> restoreInstanceWithHttpInfo(String instanceId, RestoreInstanceRequest restoreInstanceRequest) throws ApiException {
        okhttp3.Call localVarCall = restoreInstanceValidateBeforeCall(instanceId, restoreInstanceRequest, null);
        Type localVarReturnType = new TypeToken<RestoreInstance202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Restore Instance (asynchronously)
     * Restore an Instance from either &#x60;backup_id&#x60; or &#x60;snapshot_id&#x60;.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param restoreInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call restoreInstanceAsync(String instanceId, RestoreInstanceRequest restoreInstanceRequest, final ApiCallback<RestoreInstance202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = restoreInstanceValidateBeforeCall(instanceId, restoreInstanceRequest, _callback);
        Type localVarReturnType = new TypeToken<RestoreInstance202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for startInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call startInstanceCall(String instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/instances/{instance-id}/start"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call startInstanceValidateBeforeCall(String instanceId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling startInstance(Async)");
        }
        

        okhttp3.Call localVarCall = startInstanceCall(instanceId, _callback);
        return localVarCall;

    }

    /**
     * Start instance
     * Start an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public void startInstance(String instanceId) throws ApiException {
        startInstanceWithHttpInfo(instanceId);
    }

    /**
     * Start instance
     * Start an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public ApiResponse<Void> startInstanceWithHttpInfo(String instanceId) throws ApiException {
        okhttp3.Call localVarCall = startInstanceValidateBeforeCall(instanceId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Start instance (asynchronously)
     * Start an Instance.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
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
    public okhttp3.Call startInstanceAsync(String instanceId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = startInstanceValidateBeforeCall(instanceId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for startInstances
     * @param startInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call startInstancesCall(StartInstancesRequest startInstancesRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = startInstancesRequest;

        // create path and map variables
        String localVarPath = "/instances/start";

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
    private okhttp3.Call startInstancesValidateBeforeCall(StartInstancesRequest startInstancesRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = startInstancesCall(startInstancesRequest, _callback);
        return localVarCall;

    }

    /**
     * Start instances
     * Start Instances.
     * @param startInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void startInstances(StartInstancesRequest startInstancesRequest) throws ApiException {
        startInstancesWithHttpInfo(startInstancesRequest);
    }

    /**
     * Start instances
     * Start Instances.
     * @param startInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> startInstancesWithHttpInfo(StartInstancesRequest startInstancesRequest) throws ApiException {
        okhttp3.Call localVarCall = startInstancesValidateBeforeCall(startInstancesRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Start instances (asynchronously)
     * Start Instances.
     * @param startInstancesRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call startInstancesAsync(StartInstancesRequest startInstancesRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = startInstancesValidateBeforeCall(startInstancesRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateInstance
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param updateInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateInstanceCall(String instanceId, UpdateInstanceRequest updateInstanceRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateInstanceRequest;

        // create path and map variables
        String localVarPath = "/instances/{instance-id}"
            .replaceAll("\\{" + "instance-id" + "\\}", localVarApiClient.escapeString(instanceId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateInstanceValidateBeforeCall(String instanceId, UpdateInstanceRequest updateInstanceRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling updateInstance(Async)");
        }
        

        okhttp3.Call localVarCall = updateInstanceCall(instanceId, updateInstanceRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Instance
     * Update information for an Instance. All attributes are optional. If not set, the attributes will retain their original values.  **Note:** Changing &#x60;os_id&#x60;, &#x60;app_id&#x60; or &#x60;image_id&#x60; may take a few extra seconds to complete.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param updateInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateInstance202Response
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
    public CreateInstance202Response updateInstance(String instanceId, UpdateInstanceRequest updateInstanceRequest) throws ApiException {
        ApiResponse<CreateInstance202Response> localVarResp = updateInstanceWithHttpInfo(instanceId, updateInstanceRequest);
        return localVarResp.getData();
    }

    /**
     * Update Instance
     * Update information for an Instance. All attributes are optional. If not set, the attributes will retain their original values.  **Note:** Changing &#x60;os_id&#x60;, &#x60;app_id&#x60; or &#x60;image_id&#x60; may take a few extra seconds to complete.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param updateInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateInstance202Response&gt;
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
    public ApiResponse<CreateInstance202Response> updateInstanceWithHttpInfo(String instanceId, UpdateInstanceRequest updateInstanceRequest) throws ApiException {
        okhttp3.Call localVarCall = updateInstanceValidateBeforeCall(instanceId, updateInstanceRequest, null);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update Instance (asynchronously)
     * Update information for an Instance. All attributes are optional. If not set, the attributes will retain their original values.  **Note:** Changing &#x60;os_id&#x60;, &#x60;app_id&#x60; or &#x60;image_id&#x60; may take a few extra seconds to complete.
     * @param instanceId The [Instance ID](#operation/list-instances). (required)
     * @param updateInstanceRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateInstanceAsync(String instanceId, UpdateInstanceRequest updateInstanceRequest, final ApiCallback<CreateInstance202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateInstanceValidateBeforeCall(instanceId, updateInstanceRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateInstance202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
