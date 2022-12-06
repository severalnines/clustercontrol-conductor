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


import org.openapitools.vultrapi.client.model.CreateDnsDomain200Response;
import org.openapitools.vultrapi.client.model.CreateDnsDomainRecord201Response;
import org.openapitools.vultrapi.client.model.CreateDnsDomainRecordRequest;
import org.openapitools.vultrapi.client.model.CreateDnsDomainRequest;
import org.openapitools.vultrapi.client.model.GetDnsDomainDnssec200Response;
import org.openapitools.vultrapi.client.model.GetDnsDomainSoa200Response;
import org.openapitools.vultrapi.client.model.ListDnsDomainRecords200Response;
import org.openapitools.vultrapi.client.model.ListDnsDomains200Response;
import org.openapitools.vultrapi.client.model.UpdateDnsDomainRecordRequest;
import org.openapitools.vultrapi.client.model.UpdateDnsDomainRequest;
import org.openapitools.vultrapi.client.model.UpdateDnsDomainSoaRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class DnsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public DnsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DnsApi(ApiClient apiClient) {
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
     * Build call for createDnsDomain
     * @param createDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDnsDomainCall(CreateDnsDomainRequest createDnsDomainRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDnsDomainRequest;

        // create path and map variables
        String localVarPath = "/domains";

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
    private okhttp3.Call createDnsDomainValidateBeforeCall(CreateDnsDomainRequest createDnsDomainRequest, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = createDnsDomainCall(createDnsDomainRequest, _callback);
        return localVarCall;

    }

    /**
     * Create DNS Domain
     * Create a DNS Domain for &#x60;domain&#x60;. If no &#x60;ip&#x60; address is supplied a domain with no records will be created.
     * @param createDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateDnsDomain200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public CreateDnsDomain200Response createDnsDomain(CreateDnsDomainRequest createDnsDomainRequest) throws ApiException {
        ApiResponse<CreateDnsDomain200Response> localVarResp = createDnsDomainWithHttpInfo(createDnsDomainRequest);
        return localVarResp.getData();
    }

    /**
     * Create DNS Domain
     * Create a DNS Domain for &#x60;domain&#x60;. If no &#x60;ip&#x60; address is supplied a domain with no records will be created.
     * @param createDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateDnsDomain200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDnsDomain200Response> createDnsDomainWithHttpInfo(CreateDnsDomainRequest createDnsDomainRequest) throws ApiException {
        okhttp3.Call localVarCall = createDnsDomainValidateBeforeCall(createDnsDomainRequest, null);
        Type localVarReturnType = new TypeToken<CreateDnsDomain200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create DNS Domain (asynchronously)
     * Create a DNS Domain for &#x60;domain&#x60;. If no &#x60;ip&#x60; address is supplied a domain with no records will be created.
     * @param createDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDnsDomainAsync(CreateDnsDomainRequest createDnsDomainRequest, final ApiCallback<CreateDnsDomain200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDnsDomainValidateBeforeCall(createDnsDomainRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateDnsDomain200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDnsDomainRecord
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param createDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDnsDomainRecordCall(String dnsDomain, CreateDnsDomainRecordRequest createDnsDomainRecordRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDnsDomainRecordRequest;

        // create path and map variables
        String localVarPath = "/domains/{dns-domain}/records"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call createDnsDomainRecordValidateBeforeCall(String dnsDomain, CreateDnsDomainRecordRequest createDnsDomainRecordRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling createDnsDomainRecord(Async)");
        }
        

        okhttp3.Call localVarCall = createDnsDomainRecordCall(dnsDomain, createDnsDomainRecordRequest, _callback);
        return localVarCall;

    }

    /**
     * Create Record
     * Create a DNS record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param createDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return CreateDnsDomainRecord201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public CreateDnsDomainRecord201Response createDnsDomainRecord(String dnsDomain, CreateDnsDomainRecordRequest createDnsDomainRecordRequest) throws ApiException {
        ApiResponse<CreateDnsDomainRecord201Response> localVarResp = createDnsDomainRecordWithHttpInfo(dnsDomain, createDnsDomainRecordRequest);
        return localVarResp.getData();
    }

    /**
     * Create Record
     * Create a DNS record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param createDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;CreateDnsDomainRecord201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDnsDomainRecord201Response> createDnsDomainRecordWithHttpInfo(String dnsDomain, CreateDnsDomainRecordRequest createDnsDomainRecordRequest) throws ApiException {
        okhttp3.Call localVarCall = createDnsDomainRecordValidateBeforeCall(dnsDomain, createDnsDomainRecordRequest, null);
        Type localVarReturnType = new TypeToken<CreateDnsDomainRecord201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Record (asynchronously)
     * Create a DNS record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param createDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDnsDomainRecordAsync(String dnsDomain, CreateDnsDomainRecordRequest createDnsDomainRecordRequest, final ApiCallback<CreateDnsDomainRecord201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDnsDomainRecordValidateBeforeCall(dnsDomain, createDnsDomainRecordRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateDnsDomainRecord201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDnsDomain
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call deleteDnsDomainCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call deleteDnsDomainValidateBeforeCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling deleteDnsDomain(Async)");
        }
        

        okhttp3.Call localVarCall = deleteDnsDomainCall(dnsDomain, _callback);
        return localVarCall;

    }

    /**
     * Delete Domain
     * Delete the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public void deleteDnsDomain(String dnsDomain) throws ApiException {
        deleteDnsDomainWithHttpInfo(dnsDomain);
    }

    /**
     * Delete Domain
     * Delete the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public ApiResponse<Void> deleteDnsDomainWithHttpInfo(String dnsDomain) throws ApiException {
        okhttp3.Call localVarCall = deleteDnsDomainValidateBeforeCall(dnsDomain, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Domain (asynchronously)
     * Delete the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call deleteDnsDomainAsync(String dnsDomain, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDnsDomainValidateBeforeCall(dnsDomain, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDnsDomainRecord
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
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
    public okhttp3.Call deleteDnsDomainRecordCall(String dnsDomain, String recordId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}/records/{record-id}"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()))
            .replaceAll("\\{" + "record-id" + "\\}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call deleteDnsDomainRecordValidateBeforeCall(String dnsDomain, String recordId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling deleteDnsDomainRecord(Async)");
        }
        
        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling deleteDnsDomainRecord(Async)");
        }
        

        okhttp3.Call localVarCall = deleteDnsDomainRecordCall(dnsDomain, recordId, _callback);
        return localVarCall;

    }

    /**
     * Delete Record
     * Delete the DNS record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
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
    public void deleteDnsDomainRecord(String dnsDomain, String recordId) throws ApiException {
        deleteDnsDomainRecordWithHttpInfo(dnsDomain, recordId);
    }

    /**
     * Delete Record
     * Delete the DNS record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
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
    public ApiResponse<Void> deleteDnsDomainRecordWithHttpInfo(String dnsDomain, String recordId) throws ApiException {
        okhttp3.Call localVarCall = deleteDnsDomainRecordValidateBeforeCall(dnsDomain, recordId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Record (asynchronously)
     * Delete the DNS record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
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
    public okhttp3.Call deleteDnsDomainRecordAsync(String dnsDomain, String recordId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDnsDomainRecordValidateBeforeCall(dnsDomain, recordId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDnsDomain
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call getDnsDomainCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call getDnsDomainValidateBeforeCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling getDnsDomain(Async)");
        }
        

        okhttp3.Call localVarCall = getDnsDomainCall(dnsDomain, _callback);
        return localVarCall;

    }

    /**
     * Get DNS Domain
     * Get information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @return CreateDnsDomain200Response
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
    public CreateDnsDomain200Response getDnsDomain(String dnsDomain) throws ApiException {
        ApiResponse<CreateDnsDomain200Response> localVarResp = getDnsDomainWithHttpInfo(dnsDomain);
        return localVarResp.getData();
    }

    /**
     * Get DNS Domain
     * Get information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @return ApiResponse&lt;CreateDnsDomain200Response&gt;
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
    public ApiResponse<CreateDnsDomain200Response> getDnsDomainWithHttpInfo(String dnsDomain) throws ApiException {
        okhttp3.Call localVarCall = getDnsDomainValidateBeforeCall(dnsDomain, null);
        Type localVarReturnType = new TypeToken<CreateDnsDomain200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get DNS Domain (asynchronously)
     * Get information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call getDnsDomainAsync(String dnsDomain, final ApiCallback<CreateDnsDomain200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDnsDomainValidateBeforeCall(dnsDomain, _callback);
        Type localVarReturnType = new TypeToken<CreateDnsDomain200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDnsDomainDnssec
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call getDnsDomainDnssecCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}/dnssec"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call getDnsDomainDnssecValidateBeforeCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling getDnsDomainDnssec(Async)");
        }
        

        okhttp3.Call localVarCall = getDnsDomainDnssecCall(dnsDomain, _callback);
        return localVarCall;

    }

    /**
     * Get DNSSec Info
     * Get the DNSSEC information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @return GetDnsDomainDnssec200Response
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
    public GetDnsDomainDnssec200Response getDnsDomainDnssec(String dnsDomain) throws ApiException {
        ApiResponse<GetDnsDomainDnssec200Response> localVarResp = getDnsDomainDnssecWithHttpInfo(dnsDomain);
        return localVarResp.getData();
    }

    /**
     * Get DNSSec Info
     * Get the DNSSEC information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @return ApiResponse&lt;GetDnsDomainDnssec200Response&gt;
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
    public ApiResponse<GetDnsDomainDnssec200Response> getDnsDomainDnssecWithHttpInfo(String dnsDomain) throws ApiException {
        okhttp3.Call localVarCall = getDnsDomainDnssecValidateBeforeCall(dnsDomain, null);
        Type localVarReturnType = new TypeToken<GetDnsDomainDnssec200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get DNSSec Info (asynchronously)
     * Get the DNSSEC information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call getDnsDomainDnssecAsync(String dnsDomain, final ApiCallback<GetDnsDomainDnssec200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDnsDomainDnssecValidateBeforeCall(dnsDomain, _callback);
        Type localVarReturnType = new TypeToken<GetDnsDomainDnssec200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDnsDomainRecord
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
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
    public okhttp3.Call getDnsDomainRecordCall(String dnsDomain, String recordId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}/records/{record-id}"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()))
            .replaceAll("\\{" + "record-id" + "\\}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call getDnsDomainRecordValidateBeforeCall(String dnsDomain, String recordId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling getDnsDomainRecord(Async)");
        }
        
        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling getDnsDomainRecord(Async)");
        }
        

        okhttp3.Call localVarCall = getDnsDomainRecordCall(dnsDomain, recordId, _callback);
        return localVarCall;

    }

    /**
     * Get Record
     * Get information for a DNS Record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
     * @return CreateDnsDomainRecord201Response
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
    public CreateDnsDomainRecord201Response getDnsDomainRecord(String dnsDomain, String recordId) throws ApiException {
        ApiResponse<CreateDnsDomainRecord201Response> localVarResp = getDnsDomainRecordWithHttpInfo(dnsDomain, recordId);
        return localVarResp.getData();
    }

    /**
     * Get Record
     * Get information for a DNS Record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
     * @return ApiResponse&lt;CreateDnsDomainRecord201Response&gt;
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
    public ApiResponse<CreateDnsDomainRecord201Response> getDnsDomainRecordWithHttpInfo(String dnsDomain, String recordId) throws ApiException {
        okhttp3.Call localVarCall = getDnsDomainRecordValidateBeforeCall(dnsDomain, recordId, null);
        Type localVarReturnType = new TypeToken<CreateDnsDomainRecord201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Record (asynchronously)
     * Get information for a DNS Record.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
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
    public okhttp3.Call getDnsDomainRecordAsync(String dnsDomain, String recordId, final ApiCallback<CreateDnsDomainRecord201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDnsDomainRecordValidateBeforeCall(dnsDomain, recordId, _callback);
        Type localVarReturnType = new TypeToken<CreateDnsDomainRecord201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDnsDomainSoa
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call getDnsDomainSoaCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}/soa"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call getDnsDomainSoaValidateBeforeCall(String dnsDomain, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling getDnsDomainSoa(Async)");
        }
        

        okhttp3.Call localVarCall = getDnsDomainSoaCall(dnsDomain, _callback);
        return localVarCall;

    }

    /**
     * Get SOA information
     * Get SOA information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @return GetDnsDomainSoa200Response
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
    public GetDnsDomainSoa200Response getDnsDomainSoa(String dnsDomain) throws ApiException {
        ApiResponse<GetDnsDomainSoa200Response> localVarResp = getDnsDomainSoaWithHttpInfo(dnsDomain);
        return localVarResp.getData();
    }

    /**
     * Get SOA information
     * Get SOA information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @return ApiResponse&lt;GetDnsDomainSoa200Response&gt;
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
    public ApiResponse<GetDnsDomainSoa200Response> getDnsDomainSoaWithHttpInfo(String dnsDomain) throws ApiException {
        okhttp3.Call localVarCall = getDnsDomainSoaValidateBeforeCall(dnsDomain, null);
        Type localVarReturnType = new TypeToken<GetDnsDomainSoa200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get SOA information (asynchronously)
     * Get SOA information for the DNS Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call getDnsDomainSoaAsync(String dnsDomain, final ApiCallback<GetDnsDomainSoa200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDnsDomainSoaValidateBeforeCall(dnsDomain, _callback);
        Type localVarReturnType = new TypeToken<GetDnsDomainSoa200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listDnsDomainRecords
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call listDnsDomainRecordsCall(String dnsDomain, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains/{dns-domain}/records"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call listDnsDomainRecordsValidateBeforeCall(String dnsDomain, Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling listDnsDomainRecords(Async)");
        }
        

        okhttp3.Call localVarCall = listDnsDomainRecordsCall(dnsDomain, perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List Records
     * Get the DNS records for the Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListDnsDomainRecords200Response
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
    public ListDnsDomainRecords200Response listDnsDomainRecords(String dnsDomain, Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListDnsDomainRecords200Response> localVarResp = listDnsDomainRecordsWithHttpInfo(dnsDomain, perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List Records
     * Get the DNS records for the Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param perPage Number of items requested per page. Default is 100 and Max is 500. (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListDnsDomainRecords200Response&gt;
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
    public ApiResponse<ListDnsDomainRecords200Response> listDnsDomainRecordsWithHttpInfo(String dnsDomain, Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listDnsDomainRecordsValidateBeforeCall(dnsDomain, perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListDnsDomainRecords200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Records (asynchronously)
     * Get the DNS records for the Domain.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
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
    public okhttp3.Call listDnsDomainRecordsAsync(String dnsDomain, Integer perPage, String cursor, final ApiCallback<ListDnsDomainRecords200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listDnsDomainRecordsValidateBeforeCall(dnsDomain, perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListDnsDomainRecords200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listDnsDomains
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
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listDnsDomainsCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/domains";

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
    private okhttp3.Call listDnsDomainsValidateBeforeCall(Integer perPage, String cursor, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = listDnsDomainsCall(perPage, cursor, _callback);
        return localVarCall;

    }

    /**
     * List DNS Domains
     * List all DNS Domains in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ListDnsDomains200Response
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
    public ListDnsDomains200Response listDnsDomains(Integer perPage, String cursor) throws ApiException {
        ApiResponse<ListDnsDomains200Response> localVarResp = listDnsDomainsWithHttpInfo(perPage, cursor);
        return localVarResp.getData();
    }

    /**
     * List DNS Domains
     * List all DNS Domains in your account.
     * @param perPage Number of items requested per page. Default is 100 and Max is 500.  (optional)
     * @param cursor Cursor for paging. See [Meta and Pagination](#section/Introduction/Meta-and-Pagination). (optional)
     * @return ApiResponse&lt;ListDnsDomains200Response&gt;
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
    public ApiResponse<ListDnsDomains200Response> listDnsDomainsWithHttpInfo(Integer perPage, String cursor) throws ApiException {
        okhttp3.Call localVarCall = listDnsDomainsValidateBeforeCall(perPage, cursor, null);
        Type localVarReturnType = new TypeToken<ListDnsDomains200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List DNS Domains (asynchronously)
     * List all DNS Domains in your account.
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
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listDnsDomainsAsync(Integer perPage, String cursor, final ApiCallback<ListDnsDomains200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listDnsDomainsValidateBeforeCall(perPage, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListDnsDomains200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDnsDomain
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateDnsDomainCall(String dnsDomain, UpdateDnsDomainRequest updateDnsDomainRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateDnsDomainRequest;

        // create path and map variables
        String localVarPath = "/domains/{dns-domain}"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call updateDnsDomainValidateBeforeCall(String dnsDomain, UpdateDnsDomainRequest updateDnsDomainRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling updateDnsDomain(Async)");
        }
        

        okhttp3.Call localVarCall = updateDnsDomainCall(dnsDomain, updateDnsDomainRequest, _callback);
        return localVarCall;

    }

    /**
     * Update a DNS Domain
     * Update the DNS Domain. 
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void updateDnsDomain(String dnsDomain, UpdateDnsDomainRequest updateDnsDomainRequest) throws ApiException {
        updateDnsDomainWithHttpInfo(dnsDomain, updateDnsDomainRequest);
    }

    /**
     * Update a DNS Domain
     * Update the DNS Domain. 
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> updateDnsDomainWithHttpInfo(String dnsDomain, UpdateDnsDomainRequest updateDnsDomainRequest) throws ApiException {
        okhttp3.Call localVarCall = updateDnsDomainValidateBeforeCall(dnsDomain, updateDnsDomainRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update a DNS Domain (asynchronously)
     * Update the DNS Domain. 
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateDnsDomainAsync(String dnsDomain, UpdateDnsDomainRequest updateDnsDomainRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDnsDomainValidateBeforeCall(dnsDomain, updateDnsDomainRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDnsDomainRecord
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
     * @param updateDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDnsDomainRecordCall(String dnsDomain, String recordId, UpdateDnsDomainRecordRequest updateDnsDomainRecordRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateDnsDomainRecordRequest;

        // create path and map variables
        String localVarPath = "/domains/{dns-domain}/records/{record-id}"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()))
            .replaceAll("\\{" + "record-id" + "\\}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call updateDnsDomainRecordValidateBeforeCall(String dnsDomain, String recordId, UpdateDnsDomainRecordRequest updateDnsDomainRecordRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling updateDnsDomainRecord(Async)");
        }
        
        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling updateDnsDomainRecord(Async)");
        }
        

        okhttp3.Call localVarCall = updateDnsDomainRecordCall(dnsDomain, recordId, updateDnsDomainRecordRequest, _callback);
        return localVarCall;

    }

    /**
     * Update Record
     * Update the information for a DNS record. All attributes are optional. If not set, the attributes will retain their original values.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
     * @param updateDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
     </table>
     */
    public void updateDnsDomainRecord(String dnsDomain, String recordId, UpdateDnsDomainRecordRequest updateDnsDomainRecordRequest) throws ApiException {
        updateDnsDomainRecordWithHttpInfo(dnsDomain, recordId, updateDnsDomainRecordRequest);
    }

    /**
     * Update Record
     * Update the information for a DNS record. All attributes are optional. If not set, the attributes will retain their original values.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
     * @param updateDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> updateDnsDomainRecordWithHttpInfo(String dnsDomain, String recordId, UpdateDnsDomainRecordRequest updateDnsDomainRecordRequest) throws ApiException {
        okhttp3.Call localVarCall = updateDnsDomainRecordValidateBeforeCall(dnsDomain, recordId, updateDnsDomainRecordRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update Record (asynchronously)
     * Update the information for a DNS record. All attributes are optional. If not set, the attributes will retain their original values.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param recordId The [DNS Record id](#operation/list-dns-domain-records). (required)
     * @param updateDnsDomainRecordRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> No Content </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDnsDomainRecordAsync(String dnsDomain, String recordId, UpdateDnsDomainRecordRequest updateDnsDomainRecordRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDnsDomainRecordValidateBeforeCall(dnsDomain, recordId, updateDnsDomainRecordRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDnsDomainSoa
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainSoaRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateDnsDomainSoaCall(String dnsDomain, UpdateDnsDomainSoaRequest updateDnsDomainSoaRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateDnsDomainSoaRequest;

        // create path and map variables
        String localVarPath = "/domains/{dns-domain}/soa"
            .replaceAll("\\{" + "dns-domain" + "\\}", localVarApiClient.escapeString(dnsDomain.toString()));

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
    private okhttp3.Call updateDnsDomainSoaValidateBeforeCall(String dnsDomain, UpdateDnsDomainSoaRequest updateDnsDomainSoaRequest, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'dnsDomain' is set
        if (dnsDomain == null) {
            throw new ApiException("Missing the required parameter 'dnsDomain' when calling updateDnsDomainSoa(Async)");
        }
        

        okhttp3.Call localVarCall = updateDnsDomainSoaCall(dnsDomain, updateDnsDomainSoaRequest, _callback);
        return localVarCall;

    }

    /**
     * Update SOA information
     * Update the SOA information for the DNS Domain. All attributes are optional. If not set, the attributes will retain their original values.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainSoaRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public void updateDnsDomainSoa(String dnsDomain, UpdateDnsDomainSoaRequest updateDnsDomainSoaRequest) throws ApiException {
        updateDnsDomainSoaWithHttpInfo(dnsDomain, updateDnsDomainSoaRequest);
    }

    /**
     * Update SOA information
     * Update the SOA information for the DNS Domain. All attributes are optional. If not set, the attributes will retain their original values.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainSoaRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public ApiResponse<Void> updateDnsDomainSoaWithHttpInfo(String dnsDomain, UpdateDnsDomainSoaRequest updateDnsDomainSoaRequest) throws ApiException {
        okhttp3.Call localVarCall = updateDnsDomainSoaValidateBeforeCall(dnsDomain, updateDnsDomainSoaRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Update SOA information (asynchronously)
     * Update the SOA information for the DNS Domain. All attributes are optional. If not set, the attributes will retain their original values.
     * @param dnsDomain The [DNS Domain](#operation/list-dns-domains). (required)
     * @param updateDnsDomainSoaRequest Include a JSON object in the request body with a content type of **application/json**. (optional)
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
    public okhttp3.Call updateDnsDomainSoaAsync(String dnsDomain, UpdateDnsDomainSoaRequest updateDnsDomainSoaRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDnsDomainSoaValidateBeforeCall(dnsDomain, updateDnsDomainSoaRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
