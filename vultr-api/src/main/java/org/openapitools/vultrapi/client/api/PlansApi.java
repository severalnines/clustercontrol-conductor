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


import org.openapitools.vultrapi.client.model.ListMetalPlans200Response;
import org.openapitools.vultrapi.client.model.ListPlans200Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class PlansApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public PlansApi() {
        this(Configuration.getDefaultApiClient());
    }

    public PlansApi(ApiClient apiClient) {
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
     * Build call for listMetalPlans
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
    public okhttp3.Call listMetalPlansCall(String perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/plans-metal";

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
    private okhttp3.Call listMetalPlansValidateBeforeCall(String perPage, String cursor, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listMetalPlansCall(perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Bare Metal Plans
     * Get a list of all Bare Metal plans at Vultr.  The response is an array of JSON &#x60;plan&#x60; objects, with unique &#x60;id&#x60; with sub-fields in the general format of:    &lt;type&gt;-&lt;number of cores&gt;-&lt;memory size&gt;-&lt;optional modifier&gt;  For example: &#x60;vc2-24c-96gb-sc1&#x60;  More about the sub-fields:  * &#x60;&lt;type&gt;&#x60;: The Vultr type code. For example, &#x60;vc2&#x60;, &#x60;vhf&#x60;, &#x60;vdc&#x60;, etc. * &#x60;&lt;number of cores&gt;&#x60;: The number of cores, such as &#x60;4c&#x60; for \&quot;4 cores\&quot;, &#x60;8c&#x60; for \&quot;8 cores\&quot;, etc. * &#x60;&lt;memory size&gt;&#x60;: Size in GB, such as &#x60;32gb&#x60;. * &#x60;&lt;optional modifier&gt;&#x60;: Some plans include a modifier for internal identification purposes, such as CPU type or location surcharges.  &gt; Note: This information about plan id format is for general education. Vultr may change the sub-field format or values at any time. You should not attempt to parse the plan ID sub-fields in your code for any specific purpose. 
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListMetalPlans200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ListMetalPlans200Response listMetalPlans(String perPage, String cursor) throws ApiException {
        ApiResponse<ListMetalPlans200Response> localVarResp = listMetalPlansWithHttpInfo(perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Bare Metal Plans
     * Get a list of all Bare Metal plans at Vultr.  The response is an array of JSON &#x60;plan&#x60; objects, with unique &#x60;id&#x60; with sub-fields in the general format of:    &lt;type&gt;-&lt;number of cores&gt;-&lt;memory size&gt;-&lt;optional modifier&gt;  For example: &#x60;vc2-24c-96gb-sc1&#x60;  More about the sub-fields:  * &#x60;&lt;type&gt;&#x60;: The Vultr type code. For example, &#x60;vc2&#x60;, &#x60;vhf&#x60;, &#x60;vdc&#x60;, etc. * &#x60;&lt;number of cores&gt;&#x60;: The number of cores, such as &#x60;4c&#x60; for \&quot;4 cores\&quot;, &#x60;8c&#x60; for \&quot;8 cores\&quot;, etc. * &#x60;&lt;memory size&gt;&#x60;: Size in GB, such as &#x60;32gb&#x60;. * &#x60;&lt;optional modifier&gt;&#x60;: Some plans include a modifier for internal identification purposes, such as CPU type or location surcharges.  &gt; Note: This information about plan id format is for general education. Vultr may change the sub-field format or values at any time. You should not attempt to parse the plan ID sub-fields in your code for any specific purpose. 
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListMetalPlans200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListMetalPlans200Response> listMetalPlansWithHttpInfo(String perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listMetalPlansValidateBeforeCall(perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListMetalPlans200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Bare Metal Plans (asynchronously)
     * Get a list of all Bare Metal plans at Vultr.  The response is an array of JSON &#x60;plan&#x60; objects, with unique &#x60;id&#x60; with sub-fields in the general format of:    &lt;type&gt;-&lt;number of cores&gt;-&lt;memory size&gt;-&lt;optional modifier&gt;  For example: &#x60;vc2-24c-96gb-sc1&#x60;  More about the sub-fields:  * &#x60;&lt;type&gt;&#x60;: The Vultr type code. For example, &#x60;vc2&#x60;, &#x60;vhf&#x60;, &#x60;vdc&#x60;, etc. * &#x60;&lt;number of cores&gt;&#x60;: The number of cores, such as &#x60;4c&#x60; for \&quot;4 cores\&quot;, &#x60;8c&#x60; for \&quot;8 cores\&quot;, etc. * &#x60;&lt;memory size&gt;&#x60;: Size in GB, such as &#x60;32gb&#x60;. * &#x60;&lt;optional modifier&gt;&#x60;: Some plans include a modifier for internal identification purposes, such as CPU type or location surcharges.  &gt; Note: This information about plan id format is for general education. Vultr may change the sub-field format or values at any time. You should not attempt to parse the plan ID sub-fields in your code for any specific purpose. 
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
    public okhttp3.Call listMetalPlansAsync(String perPage, String cursor, final ApiCallback<ListMetalPlans200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listMetalPlansValidateBeforeCall(perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListMetalPlans200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listPlans
     * @param type Filter the results by type.  | **Type** | **Description** | |----------|-----------------| | all | All available types | | vc2 | Cloud Compute | | vdc | Dedicated Cloud | | vhf | High Frequency Compute | | vhp | High Performance | | voc | All Optimized Cloud types | | voc-g | General Purpose Optimized Cloud | | voc-c | CPU Optimized Cloud | | voc-m | Memory Optimized Cloud | | voc-s | Storage Optimized Cloud | | vcg | Cloud GPU | (optional)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param os Filter the results by operating system.  |   | Type | Description | | - | ------ | ------------- | |   | windows | All available plans that support windows | (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listPlansCall(String type, Integer perPage, String cursor, String os, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/plans";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (type != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
        }

        if (perPage != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("per_page", perPage));
        }

        if (cursor != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cursor", cursor));
        }

        if (os != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("os", os));
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
    private okhttp3.Call listPlansValidateBeforeCall(String type, Integer perPage, String cursor, String os, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listPlansCall(type, perPage, cursor, os, _callback);
        return localVarCall;

    }

    /**
     * List Plans
     * Get a list of all VPS plans at Vultr.  The response is an array of JSON &#x60;plan&#x60; objects, with unique &#x60;id&#x60; with sub-fields in the general format of:    &lt;type&gt;-&lt;number of cores&gt;-&lt;memory size&gt;-&lt;optional modifier&gt;  For example: &#x60;vc2-24c-96gb-sc1&#x60;  More about the sub-fields:  * &#x60;&lt;type&gt;&#x60;: The Vultr type code. For example, &#x60;vc2&#x60;, &#x60;vhf&#x60;, &#x60;vdc&#x60;, etc. * &#x60;&lt;number of cores&gt;&#x60;: The number of cores, such as &#x60;4c&#x60; for \&quot;4 cores\&quot;, &#x60;8c&#x60; for \&quot;8 cores\&quot;, etc. * &#x60;&lt;memory size&gt;&#x60;: Size in GB, such as &#x60;32gb&#x60;. * &#x60;&lt;optional modifier&gt;&#x60;: Some plans include a modifier for internal identification purposes, such as CPU type or location surcharges.  &gt; Note: This information about plan id format is for general education. Vultr may change the sub-field format or values at any time. You should not attempt to parse the plan ID sub-fields in your code for any specific purpose. 
     * @param type Filter the results by type.  | **Type** | **Description** | |----------|-----------------| | all | All available types | | vc2 | Cloud Compute | | vdc | Dedicated Cloud | | vhf | High Frequency Compute | | vhp | High Performance | | voc | All Optimized Cloud types | | voc-g | General Purpose Optimized Cloud | | voc-c | CPU Optimized Cloud | | voc-m | Memory Optimized Cloud | | voc-s | Storage Optimized Cloud | | vcg | Cloud GPU | (optional)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param os Filter the results by operating system.  |   | Type | Description | | - | ------ | ------------- | |   | windows | All available plans that support windows | (optional)
     * @return ListPlans200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ListPlans200Response listPlans(String type, Integer perPage, String cursor, String os) throws ApiException {
        ApiResponse<ListPlans200Response> localVarResp = listPlansWithHttpInfo(type, perPage, cursor, os);
        return localVarResp.getData();
    }

    /**
     * List Plans
     * Get a list of all VPS plans at Vultr.  The response is an array of JSON &#x60;plan&#x60; objects, with unique &#x60;id&#x60; with sub-fields in the general format of:    &lt;type&gt;-&lt;number of cores&gt;-&lt;memory size&gt;-&lt;optional modifier&gt;  For example: &#x60;vc2-24c-96gb-sc1&#x60;  More about the sub-fields:  * &#x60;&lt;type&gt;&#x60;: The Vultr type code. For example, &#x60;vc2&#x60;, &#x60;vhf&#x60;, &#x60;vdc&#x60;, etc. * &#x60;&lt;number of cores&gt;&#x60;: The number of cores, such as &#x60;4c&#x60; for \&quot;4 cores\&quot;, &#x60;8c&#x60; for \&quot;8 cores\&quot;, etc. * &#x60;&lt;memory size&gt;&#x60;: Size in GB, such as &#x60;32gb&#x60;. * &#x60;&lt;optional modifier&gt;&#x60;: Some plans include a modifier for internal identification purposes, such as CPU type or location surcharges.  &gt; Note: This information about plan id format is for general education. Vultr may change the sub-field format or values at any time. You should not attempt to parse the plan ID sub-fields in your code for any specific purpose. 
     * @param type Filter the results by type.  | **Type** | **Description** | |----------|-----------------| | all | All available types | | vc2 | Cloud Compute | | vdc | Dedicated Cloud | | vhf | High Frequency Compute | | vhp | High Performance | | voc | All Optimized Cloud types | | voc-g | General Purpose Optimized Cloud | | voc-c | CPU Optimized Cloud | | voc-m | Memory Optimized Cloud | | voc-s | Storage Optimized Cloud | | vcg | Cloud GPU | (optional)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param os Filter the results by operating system.  |   | Type | Description | | - | ------ | ------------- | |   | windows | All available plans that support windows | (optional)
     * @return ApiResponse&lt;ListPlans200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListPlans200Response> listPlansWithHttpInfo(String type, Integer perPage, String cursor, String os) throws ApiException {
        okhttp3.Call localVarCall = listPlansValidateBeforeCall(type, perPage, cursor, os, null);
        Type localVarReturnType = new TypeToken<ListPlans200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Plans (asynchronously)
     * Get a list of all VPS plans at Vultr.  The response is an array of JSON &#x60;plan&#x60; objects, with unique &#x60;id&#x60; with sub-fields in the general format of:    &lt;type&gt;-&lt;number of cores&gt;-&lt;memory size&gt;-&lt;optional modifier&gt;  For example: &#x60;vc2-24c-96gb-sc1&#x60;  More about the sub-fields:  * &#x60;&lt;type&gt;&#x60;: The Vultr type code. For example, &#x60;vc2&#x60;, &#x60;vhf&#x60;, &#x60;vdc&#x60;, etc. * &#x60;&lt;number of cores&gt;&#x60;: The number of cores, such as &#x60;4c&#x60; for \&quot;4 cores\&quot;, &#x60;8c&#x60; for \&quot;8 cores\&quot;, etc. * &#x60;&lt;memory size&gt;&#x60;: Size in GB, such as &#x60;32gb&#x60;. * &#x60;&lt;optional modifier&gt;&#x60;: Some plans include a modifier for internal identification purposes, such as CPU type or location surcharges.  &gt; Note: This information about plan id format is for general education. Vultr may change the sub-field format or values at any time. You should not attempt to parse the plan ID sub-fields in your code for any specific purpose. 
     * @param type Filter the results by type.  | **Type** | **Description** | |----------|-----------------| | all | All available types | | vc2 | Cloud Compute | | vdc | Dedicated Cloud | | vhf | High Frequency Compute | | vhp | High Performance | | voc | All Optimized Cloud types | | voc-g | General Purpose Optimized Cloud | | voc-c | CPU Optimized Cloud | | voc-m | Memory Optimized Cloud | | voc-s | Storage Optimized Cloud | | vcg | Cloud GPU | (optional)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @param os Filter the results by operating system.  |   | Type | Description | | - | ------ | ------------- | |   | windows | All available plans that support windows | (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listPlansAsync(String type, Integer perPage, String cursor, String os, final ApiCallback<ListPlans200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listPlansValidateBeforeCall(type, perPage, cursor, os, _callback);
        Type localVarReturnType = new TypeToken<ListPlans200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
