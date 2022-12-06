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
import org.openapitools.vultrapi.client.model.CreateBaremetal202Response;
import org.openapitools.vultrapi.client.model.CreateBaremetalRequest;
import org.openapitools.vultrapi.client.model.GetBandwidthBaremetal200Response;
import org.openapitools.vultrapi.client.model.GetBareMetalUserdata200Response;
import org.openapitools.vultrapi.client.model.GetBareMetalVnc200Response;
import org.openapitools.vultrapi.client.model.GetBareMetalsUpgrades200Response;
import org.openapitools.vultrapi.client.model.GetBaremetal200Response;
import org.openapitools.vultrapi.client.model.GetIpv4Baremetal200Response;
import org.openapitools.vultrapi.client.model.GetIpv6Baremetal200Response;
import org.openapitools.vultrapi.client.model.HaltBaremetalsRequest;
import org.openapitools.vultrapi.client.model.ListBaremetals200Response;
import org.openapitools.vultrapi.client.model.UpdateBaremetalRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for BaremetalApi
 */
@Disabled
public class BaremetalApiTest {

    private final BaremetalApi api = new BaremetalApi();

    /**
     * Create Bare Metal Instance
     *
     * Create a new Bare Metal instance in a &#x60;region&#x60; with the desired &#x60;plan&#x60;. Choose one of the following to deploy the instance:  * &#x60;os_id&#x60; * &#x60;snapshot_id&#x60; * &#x60;app_id&#x60; * &#x60;image_id&#x60;  Supply other attributes as desired.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createBaremetalTest() throws ApiException {
        CreateBaremetalRequest createBaremetalRequest = null;
        CreateBaremetal202Response response = api.createBaremetal(createBaremetalRequest);
        // TODO: test validations
    }

    /**
     * Delete Bare Metal
     *
     * Delete a Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteBaremetalTest() throws ApiException {
        String baremetalId = null;
        api.deleteBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Bare Metal Bandwidth
     *
     * Get bandwidth information for the Bare Metal instance.&lt;br&gt;&lt;br&gt;The &#x60;bandwidth&#x60; object in a successful response contains objects representing a day in the month. The date is denoted by the nested object keys. Days begin and end in the UTC timezone. Bandwidth utilization data contained within the date object is refreshed periodically. We do not recommend using this endpoint to gather real-time metrics.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getBandwidthBaremetalTest() throws ApiException {
        String baremetalId = null;
        GetBandwidthBaremetal200Response response = api.getBandwidthBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Get Bare Metal User Data
     *
     * Get the user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for a Bare Metal.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getBareMetalUserdataTest() throws ApiException {
        String baremetalId = null;
        GetBareMetalUserdata200Response response = api.getBareMetalUserdata(baremetalId);
        // TODO: test validations
    }

    /**
     * Get VNC URL for a Bare Metal
     *
     * Get the VNC URL for a Bare Metal
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getBareMetalVncTest() throws ApiException {
        String baremetalId = null;
        GetBareMetalVnc200Response response = api.getBareMetalVnc(baremetalId);
        // TODO: test validations
    }

    /**
     * Get Available Bare Metal Upgrades
     *
     * Get available upgrades for a Bare Metal
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getBareMetalsUpgradesTest() throws ApiException {
        String baremetalId = null;
        String type = null;
        GetBareMetalsUpgrades200Response response = api.getBareMetalsUpgrades(baremetalId, type);
        // TODO: test validations
    }

    /**
     * Get Bare Metal
     *
     * Get information for a Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getBaremetalTest() throws ApiException {
        String baremetalId = null;
        GetBaremetal200Response response = api.getBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Bare Metal IPv4 Addresses
     *
     * Get the IPv4 information for the Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getIpv4BaremetalTest() throws ApiException {
        String baremetalId = null;
        GetIpv4Baremetal200Response response = api.getIpv4Baremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Bare Metal IPv6 Addresses
     *
     * Get the IPv6 information for the Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getIpv6BaremetalTest() throws ApiException {
        String baremetalId = null;
        GetIpv6Baremetal200Response response = api.getIpv6Baremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Halt Bare Metal
     *
     * Halt the Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void haltBaremetalTest() throws ApiException {
        String baremetalId = null;
        api.haltBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Halt Bare Metals
     *
     * Halt Bare Metals.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void haltBaremetalsTest() throws ApiException {
        HaltBaremetalsRequest haltBaremetalsRequest = null;
        api.haltBaremetals(haltBaremetalsRequest);
        // TODO: test validations
    }

    /**
     * List Bare Metal Instances
     *
     * List all Bare Metal instances in your account.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listBaremetalsTest() throws ApiException {
        Integer perPage = null;
        String cursor = null;
        ListBaremetals200Response response = api.listBaremetals(perPage, cursor);
        // TODO: test validations
    }

    /**
     * Reboot Bare Metals
     *
     * Reboot Bare Metals.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rebootBareMetalsTest() throws ApiException {
        HaltBaremetalsRequest haltBaremetalsRequest = null;
        api.rebootBareMetals(haltBaremetalsRequest);
        // TODO: test validations
    }

    /**
     * Reboot Bare Metal
     *
     * Reboot the Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rebootBaremetalTest() throws ApiException {
        String baremetalId = null;
        api.rebootBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Reinstall Bare Metal
     *
     * Reinstall the Bare Metal instance.  **Note:** This action may take a few extra seconds to complete.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void reinstallBaremetalTest() throws ApiException {
        String baremetalId = null;
        GetBaremetal200Response response = api.reinstallBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Start Bare Metals
     *
     * Start Bare Metals.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void startBareMetalsTest() throws ApiException {
        HaltBaremetalsRequest haltBaremetalsRequest = null;
        api.startBareMetals(haltBaremetalsRequest);
        // TODO: test validations
    }

    /**
     * Start Bare Metal
     *
     * Start the Bare Metal instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void startBaremetalTest() throws ApiException {
        String baremetalId = null;
        api.startBaremetal(baremetalId);
        // TODO: test validations
    }

    /**
     * Update Bare Metal
     *
     * Update a Bare Metal instance. All attributes are optional. If not set, the attributes will retain their original values.  **Note:** Changing &#x60;os_id&#x60;, &#x60;app_id&#x60; or &#x60;image_id&#x60; may take a few extra seconds to complete.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateBaremetalTest() throws ApiException {
        String baremetalId = null;
        UpdateBaremetalRequest updateBaremetalRequest = null;
        GetBaremetal200Response response = api.updateBaremetal(baremetalId, updateBaremetalRequest);
        // TODO: test validations
    }

}
