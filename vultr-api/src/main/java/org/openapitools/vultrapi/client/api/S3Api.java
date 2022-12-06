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


import org.openapitools.vultrapi.client.model.CreateObjectStorage202Response;
import org.openapitools.vultrapi.client.model.CreateObjectStorageRequest;
import org.openapitools.vultrapi.client.model.ListObjectStorageClusters200Response;
import org.openapitools.vultrapi.client.model.ListObjectStorages200Response;
import org.openapitools.vultrapi.client.model.RegenerateObjectStorageKeys201Response;
import org.openapitools.vultrapi.client.model.UpdateObjectStorageRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class S3Api {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public S3Api() {
        this(Configuration.getDefaultApiClient());
    }

    public S3Api(ApiClient apiClient) {
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
     * Build call for createObjectStorage
     * @param createObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     </table>
     */
    public okhttp3.Call createObjectStorageCall(CreateObjectStorageRequest createObjectStorageRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createObjectStorageRequest;

        // create path and map variables
        String localVarPath = "/object-storage";

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
    private okhttp3.Call createObjectStorageValidateBeforeCall(CreateObjectStorageRequest createObjectStorageRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = createObjectStorageCall(createObjectStorageRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Object Storage
     * Create new Object Storage. The &#x60;cluster_id&#x60; attribute is required.
     * @param createObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateObjectStorage202Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
     </table>
     */
    public CreateObjectStorage202Response createObjectStorage(CreateObjectStorageRequest createObjectStorageRequest) throws ApiException {
        ApiResponse<CreateObjectStorage202Response> localVarResp = createObjectStorageWithHttpInfo(createObjectStorageRequest);
        return localVarResp.getData();
    }

    /**
     * Create Object Storage
     * Create new Object Storage. The &#x60;cluster_id&#x60; attribute is required.
     * @param createObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateObjectStorage202Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 202 </td><td> Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateObjectStorage202Response> createObjectStorageWithHttpInfo(CreateObjectStorageRequest createObjectStorageRequest) throws ApiException {
        okhttp3.Call localVarCall = createObjectStorageValidateBeforeCall(createObjectStorageRequest, null);
        Type localVarReturnType = new TypeToken<CreateObjectStorage202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Object Storage (asynchronously)
     * Create new Object Storage. The &#x60;cluster_id&#x60; attribute is required.
     * @param createObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
     </table>
     */
    public okhttp3.Call createObjectStorageAsync(CreateObjectStorageRequest createObjectStorageRequest, final ApiCallback<CreateObjectStorage202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createObjectStorageValidateBeforeCall(createObjectStorageRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateObjectStorage202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteObjectStorage
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public okhttp3.Call deleteObjectStorageCall(String objectStorageId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/object-storage/{object-storage-id}"
            .replaceAll("\\{" + "object-storage-id" + "\\}", localVarApiClient.escapeString(objectStorageId.toString()));

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
    private okhttp3.Call deleteObjectStorageValidateBeforeCall(String objectStorageId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'objectStorageId' is set
        if (objectStorageId == null) {
            throw new ApiException("Missing the required parameter 'objectStorageId' when calling deleteObjectStorage(Async)");
        }
        

        okhttp3.Call localVarCall = deleteObjectStorageCall(objectStorageId, _callback);
        return localVarCall;

    }

    /**
     * Delete Object Storage
     * Delete an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public void deleteObjectStorage(String objectStorageId) throws ApiException {
        deleteObjectStorageWithHttpInfo(objectStorageId);
    }

    /**
     * Delete Object Storage
     * Delete an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public ApiResponse<Void> deleteObjectStorageWithHttpInfo(String objectStorageId) throws ApiException {
        okhttp3.Call localVarCall = deleteObjectStorageValidateBeforeCall(objectStorageId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Object Storage (asynchronously)
     * Delete an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public okhttp3.Call deleteObjectStorageAsync(String objectStorageId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteObjectStorageValidateBeforeCall(objectStorageId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getObjectStorage
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public okhttp3.Call getObjectStorageCall(String objectStorageId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/object-storage/{object-storage-id}"
            .replaceAll("\\{" + "object-storage-id" + "\\}", localVarApiClient.escapeString(objectStorageId.toString()));

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
    private okhttp3.Call getObjectStorageValidateBeforeCall(String objectStorageId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'objectStorageId' is set
        if (objectStorageId == null) {
            throw new ApiException("Missing the required parameter 'objectStorageId' when calling getObjectStorage(Async)");
        }
        

        okhttp3.Call localVarCall = getObjectStorageCall(objectStorageId, _callback);
        return localVarCall;

    }

    /**
     * Get Object Storage
     * Get information about an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @return CreateObjectStorage202Response
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
    public CreateObjectStorage202Response getObjectStorage(String objectStorageId) throws ApiException {
        ApiResponse<CreateObjectStorage202Response> localVarResp = getObjectStorageWithHttpInfo(objectStorageId);
        return localVarResp.getData();
    }

    /**
     * Get Object Storage
     * Get information about an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @return ApiResponse&lt;CreateObjectStorage202Response&gt;
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
    public ApiResponse<CreateObjectStorage202Response> getObjectStorageWithHttpInfo(String objectStorageId) throws ApiException {
        okhttp3.Call localVarCall = getObjectStorageValidateBeforeCall(objectStorageId, null);
        Type localVarReturnType = new TypeToken<CreateObjectStorage202Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Object Storage (asynchronously)
     * Get information about an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public okhttp3.Call getObjectStorageAsync(String objectStorageId, final ApiCallback<CreateObjectStorage202Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getObjectStorageValidateBeforeCall(objectStorageId, _callback);
        Type localVarReturnType = new TypeToken<CreateObjectStorage202Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listObjectStorageClusters
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listObjectStorageClustersCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/object-storage/clusters";

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

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listObjectStorageClustersValidateBeforeCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listObjectStorageClustersCall(perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * Get All Clusters
     * Get a list of all Object Storage Clusters.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListObjectStorageClusters200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ListObjectStorageClusters200Response listObjectStorageClusters(Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListObjectStorageClusters200Response> localVarResp = listObjectStorageClustersWithHttpInfo(perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * Get All Clusters
     * Get a list of all Object Storage Clusters.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListObjectStorageClusters200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListObjectStorageClusters200Response> listObjectStorageClustersWithHttpInfo(Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listObjectStorageClustersValidateBeforeCall(perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListObjectStorageClusters200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get All Clusters (asynchronously)
     * Get a list of all Object Storage Clusters.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listObjectStorageClustersAsync(Integer perPage, String cursor, final ApiCallback<ListObjectStorageClusters200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listObjectStorageClustersValidateBeforeCall(perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListObjectStorageClusters200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listObjectStorages
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
     </table>
     */
    public okhttp3.Call listObjectStoragesCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/object-storage";

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
    private okhttp3.Call listObjectStoragesValidateBeforeCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listObjectStoragesCall(perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Object Storages
     * Get a list of all Object Storage in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListObjectStorages200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
     </table>
     */
    public ListObjectStorages200Response listObjectStorages(Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListObjectStorages200Response> localVarResp = listObjectStoragesWithHttpInfo(perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Object Storages
     * Get a list of all Object Storage in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListObjectStorages200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListObjectStorages200Response> listObjectStoragesWithHttpInfo(Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listObjectStoragesValidateBeforeCall(perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListObjectStorages200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Object Storages (asynchronously)
     * Get a list of all Object Storage in your account.
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
     </table>
     */
    public okhttp3.Call listObjectStoragesAsync(Integer perPage, String cursor, final ApiCallback<ListObjectStorages200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listObjectStoragesValidateBeforeCall(perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListObjectStorages200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for regenerateObjectStorageKeys
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public okhttp3.Call regenerateObjectStorageKeysCall(String objectStorageId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/object-storage/{object-storage-id}/regenerate-keys"
            .replaceAll("\\{" + "object-storage-id" + "\\}", localVarApiClient.escapeString(objectStorageId.toString()));

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
    private okhttp3.Call regenerateObjectStorageKeysValidateBeforeCall(String objectStorageId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'objectStorageId' is set
        if (objectStorageId == null) {
            throw new ApiException("Missing the required parameter 'objectStorageId' when calling regenerateObjectStorageKeys(Async)");
        }
        

        okhttp3.Call localVarCall = regenerateObjectStorageKeysCall(objectStorageId, _callback);
        return localVarCall;

    }

    /**
     * Regenerate Object Storage Keys
     * Regenerate the keys for an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @return RegenerateObjectStorageKeys201Response
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
    public RegenerateObjectStorageKeys201Response regenerateObjectStorageKeys(String objectStorageId) throws ApiException {
        ApiResponse<RegenerateObjectStorageKeys201Response> localVarResp = regenerateObjectStorageKeysWithHttpInfo(objectStorageId);
        return localVarResp.getData();
    }

    /**
     * Regenerate Object Storage Keys
     * Regenerate the keys for an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @return ApiResponse&lt;RegenerateObjectStorageKeys201Response&gt;
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
    public ApiResponse<RegenerateObjectStorageKeys201Response> regenerateObjectStorageKeysWithHttpInfo(String objectStorageId) throws ApiException {
        okhttp3.Call localVarCall = regenerateObjectStorageKeysValidateBeforeCall(objectStorageId, null);
        Type localVarReturnType = new TypeToken<RegenerateObjectStorageKeys201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Regenerate Object Storage Keys (asynchronously)
     * Regenerate the keys for an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
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
    public okhttp3.Call regenerateObjectStorageKeysAsync(String objectStorageId, final ApiCallback<RegenerateObjectStorageKeys201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = regenerateObjectStorageKeysValidateBeforeCall(objectStorageId, _callback);
        Type localVarReturnType = new TypeToken<RegenerateObjectStorageKeys201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateObjectStorage
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @param updateObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateObjectStorageCall(String objectStorageId, UpdateObjectStorageRequest updateObjectStorageRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateObjectStorageRequest;

        // create path and map variables
        String localVarPath = "/object-storage/{object-storage-id}"
            .replaceAll("\\{" + "object-storage-id" + "\\}", localVarApiClient.escapeString(objectStorageId.toString()));

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
    private okhttp3.Call updateObjectStorageValidateBeforeCall(String objectStorageId, UpdateObjectStorageRequest updateObjectStorageRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'objectStorageId' is set
        if (objectStorageId == null) {
            throw new ApiException("Missing the required parameter 'objectStorageId' when calling updateObjectStorage(Async)");
        }
        

        okhttp3.Call localVarCall = updateObjectStorageCall(objectStorageId, updateObjectStorageRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Object Storage
     * Update the label for an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @param updateObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void updateObjectStorage(String objectStorageId, UpdateObjectStorageRequest updateObjectStorageRequest) throws ApiException {
        updateObjectStorageWithHttpInfo(objectStorageId, updateObjectStorageRequest);
    }

    /**
     * Update Object Storage
     * Update the label for an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @param updateObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> updateObjectStorageWithHttpInfo(String objectStorageId, UpdateObjectStorageRequest updateObjectStorageRequest) throws ApiException {
        okhttp3.Call localVarCall = updateObjectStorageValidateBeforeCall(objectStorageId, updateObjectStorageRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update Object Storage (asynchronously)
     * Update the label for an Object Storage.
     * @param objectStorageId The [Object Storage id](#operation/list-object-storages). (required)
     * @param updateObjectStorageRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateObjectStorageAsync(String objectStorageId, UpdateObjectStorageRequest updateObjectStorageRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateObjectStorageValidateBeforeCall(objectStorageId, updateObjectStorageRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
