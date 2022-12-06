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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for InstancesApi
 */
@Disabled
public class InstancesApiTest {

    private final InstancesApi api = new InstancesApi();

    /**
     * Attach ISO to Instance
     *
     * Attach an ISO to an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachInstanceIsoTest() throws ApiException {
        String instanceId = null;
        AttachInstanceIsoRequest attachInstanceIsoRequest = null;
        AttachInstanceIso202Response response = api.attachInstanceIso(instanceId, attachInstanceIsoRequest);
        // TODO: test validations
    }

    /**
     * Attach Private Network to Instance
     *
     * Attach Private Network to an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Attach VPC to Instance](#operation/attach-instance-vpc) instead.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachInstanceNetworkTest() throws ApiException {
        String instanceId = null;
        AttachInstanceNetworkRequest attachInstanceNetworkRequest = null;
        api.attachInstanceNetwork(instanceId, attachInstanceNetworkRequest);
        // TODO: test validations
    }

    /**
     * Attach VPC to Instance
     *
     * Attach a VPC to an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachInstanceVpcTest() throws ApiException {
        String instanceId = null;
        AttachInstanceVpcRequest attachInstanceVpcRequest = null;
        api.attachInstanceVpc(instanceId, attachInstanceVpcRequest);
        // TODO: test validations
    }

    /**
     * Create Instance
     *
     * Create a new VPS Instance in a &#x60;region&#x60; with the desired &#x60;plan&#x60;. Choose one of the following to deploy the instance:  * &#x60;os_id&#x60; * &#x60;iso_id&#x60; * &#x60;snapshot_id&#x60; * &#x60;app_id&#x60; * &#x60;image_id&#x60;  Supply other attributes as desired.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createInstanceTest() throws ApiException {
        CreateInstanceRequest createInstanceRequest = null;
        CreateInstance202Response response = api.createInstance(createInstanceRequest);
        // TODO: test validations
    }

    /**
     * Set Instance Backup Schedule
     *
     * Set the backup schedule for an Instance in UTC. The &#x60;type&#x60; is required.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createInstanceBackupScheduleTest() throws ApiException {
        String instanceId = null;
        CreateInstanceBackupScheduleRequest createInstanceBackupScheduleRequest = null;
        api.createInstanceBackupSchedule(instanceId, createInstanceBackupScheduleRequest);
        // TODO: test validations
    }

    /**
     * Create IPv4
     *
     * Create an IPv4 address for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createInstanceIpv4Test() throws ApiException {
        String instanceId = null;
        CreateInstanceIpv4Request createInstanceIpv4Request = null;
        Object response = api.createInstanceIpv4(instanceId, createInstanceIpv4Request);
        // TODO: test validations
    }

    /**
     * Create Instance Reverse IPv4
     *
     * Create a reverse IPv4 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. 
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createInstanceReverseIpv4Test() throws ApiException {
        String instanceId = null;
        CreateInstanceReverseIpv4Request createInstanceReverseIpv4Request = null;
        api.createInstanceReverseIpv4(instanceId, createInstanceReverseIpv4Request);
        // TODO: test validations
    }

    /**
     * Create Instance Reverse IPv6
     *
     * Create a reverse IPv6 entry for an Instance. The &#x60;ip&#x60; and &#x60;reverse&#x60; attributes are required. IP address must be in full, expanded format.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createInstanceReverseIpv6Test() throws ApiException {
        String instanceId = null;
        CreateInstanceReverseIpv6Request createInstanceReverseIpv6Request = null;
        api.createInstanceReverseIpv6(instanceId, createInstanceReverseIpv6Request);
        // TODO: test validations
    }

    /**
     * Delete Instance
     *
     * Delete an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteInstanceTest() throws ApiException {
        String instanceId = null;
        api.deleteInstance(instanceId);
        // TODO: test validations
    }

    /**
     * Delete IPv4 Address
     *
     * Delete an IPv4 address from an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteInstanceIpv4Test() throws ApiException {
        String instanceId = null;
        String ipv4 = null;
        api.deleteInstanceIpv4(instanceId, ipv4);
        // TODO: test validations
    }

    /**
     * Delete Instance Reverse IPv6
     *
     * Delete the reverse IPv6 for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteInstanceReverseIpv6Test() throws ApiException {
        String instanceId = null;
        String ipv6 = null;
        api.deleteInstanceReverseIpv6(instanceId, ipv6);
        // TODO: test validations
    }

    /**
     * Detach ISO from instance
     *
     * Detach the ISO from an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void detachInstanceIsoTest() throws ApiException {
        String instanceId = null;
        DetachInstanceIso202Response response = api.detachInstanceIso(instanceId);
        // TODO: test validations
    }

    /**
     * Detach Private Network from Instance.
     *
     * Detach Private Network from an Instance.&lt;br&gt;&lt;br&gt;**Deprecated**: use [Detach VPC from Instance](#operation/detach-instance-vpc) instead.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void detachInstanceNetworkTest() throws ApiException {
        String instanceId = null;
        DetachInstanceNetworkRequest detachInstanceNetworkRequest = null;
        api.detachInstanceNetwork(instanceId, detachInstanceNetworkRequest);
        // TODO: test validations
    }

    /**
     * Detach VPC from Instance
     *
     * Detach a VPC from an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void detachInstanceVpcTest() throws ApiException {
        String instanceId = null;
        DetachInstanceVpcRequest detachInstanceVpcRequest = null;
        api.detachInstanceVpc(instanceId, detachInstanceVpcRequest);
        // TODO: test validations
    }

    /**
     * Get Instance
     *
     * Get information about an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceTest() throws ApiException {
        String instanceId = null;
        CreateInstance202Response response = api.getInstance(instanceId);
        // TODO: test validations
    }

    /**
     * Get Instance Backup Schedule
     *
     * Get the backup schedule for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceBackupScheduleTest() throws ApiException {
        String instanceId = null;
        GetInstanceBackupSchedule200Response response = api.getInstanceBackupSchedule(instanceId);
        // TODO: test validations
    }

    /**
     * Instance Bandwidth
     *
     * Get bandwidth information about an Instance.&lt;br&gt;&lt;br&gt;The &#x60;bandwidth&#x60; object in a successful response contains objects representing a day in the month. The date is denoted by the nested object keys. Days begin and end in the UTC timezone. The bandwidth utilization data contained within the date object is refreshed periodically. We do not recommend using this endpoint to gather real-time metrics.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceBandwidthTest() throws ApiException {
        String instanceId = null;
        GetBandwidthBaremetal200Response response = api.getInstanceBandwidth(instanceId);
        // TODO: test validations
    }

    /**
     * List Instance IPv4 Information
     *
     * List the IPv4 information for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceIpv4Test() throws ApiException {
        String instanceId = null;
        Boolean publicNetwork = null;
        Integer perPage = null;
        String cursor = null;
        GetIpv4Baremetal200Response response = api.getInstanceIpv4(instanceId, publicNetwork, perPage, cursor);
        // TODO: test validations
    }

    /**
     * Get Instance IPv6 Information
     *
     * Get the IPv6 information for an VPS Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceIpv6Test() throws ApiException {
        String instanceId = null;
        GetIpv6Baremetal200Response response = api.getInstanceIpv6(instanceId);
        // TODO: test validations
    }

    /**
     * Get Instance ISO Status
     *
     * Get the ISO status for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceIsoStatusTest() throws ApiException {
        String instanceId = null;
        GetInstanceIsoStatus200Response response = api.getInstanceIsoStatus(instanceId);
        // TODO: test validations
    }

    /**
     * Get Instance neighbors
     *
     * Get a list of other instances in the same location as this Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceNeighborsTest() throws ApiException {
        String instanceId = null;
        GetInstanceNeighbors200Response response = api.getInstanceNeighbors(instanceId);
        // TODO: test validations
    }

    /**
     * Get Available Instance Upgrades
     *
     * Get available upgrades for an Instance
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceUpgradesTest() throws ApiException {
        String instanceId = null;
        String type = null;
        GetInstanceUpgrades200Response response = api.getInstanceUpgrades(instanceId, type);
        // TODO: test validations
    }

    /**
     * Get Instance User Data
     *
     * Get the user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getInstanceUserdataTest() throws ApiException {
        String instanceId = null;
        GetInstanceUserdata200Response response = api.getInstanceUserdata(instanceId);
        // TODO: test validations
    }

    /**
     * Halt Instance
     *
     * Halt an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void haltInstanceTest() throws ApiException {
        String instanceId = null;
        api.haltInstance(instanceId);
        // TODO: test validations
    }

    /**
     * Halt Instances
     *
     * Halt Instances.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void haltInstancesTest() throws ApiException {
        HaltInstancesRequest haltInstancesRequest = null;
        api.haltInstances(haltInstancesRequest);
        // TODO: test validations
    }

    /**
     * List Instance IPv6 Reverse
     *
     * List the reverse IPv6 information for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listInstanceIpv6ReverseTest() throws ApiException {
        String instanceId = null;
        ListInstanceIpv6Reverse200Response response = api.listInstanceIpv6Reverse(instanceId);
        // TODO: test validations
    }

    /**
     * List instance Private Networks
     *
     * **Deprecated**: use [List Instance VPCs](#operation/list-instance-vpcs) instead.&lt;br&gt;&lt;br&gt;List the private networks for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listInstancePrivateNetworksTest() throws ApiException {
        String instanceId = null;
        Integer perPage = null;
        String cursor = null;
        ListInstancePrivateNetworks200Response response = api.listInstancePrivateNetworks(instanceId, perPage, cursor);
        // TODO: test validations
    }

    /**
     * List instance VPCs
     *
     * List the VPCs for an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listInstanceVpcsTest() throws ApiException {
        String instanceId = null;
        Integer perPage = null;
        String cursor = null;
        ListInstanceVpcs200Response response = api.listInstanceVpcs(instanceId, perPage, cursor);
        // TODO: test validations
    }

    /**
     * List Instances
     *
     * List all VPS instances in your account.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listInstancesTest() throws ApiException {
        Integer perPage = null;
        String cursor = null;
        String tag = null;
        String label = null;
        String mainIp = null;
        String region = null;
        ListInstances200Response response = api.listInstances(perPage, cursor, tag, label, mainIp, region);
        // TODO: test validations
    }

    /**
     * Set Default Reverse DNS Entry
     *
     * Set a reverse DNS entry for an IPv4 address
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postInstancesInstanceIdIpv4ReverseDefaultTest() throws ApiException {
        String instanceId = null;
        PostInstancesInstanceIdIpv4ReverseDefaultRequest postInstancesInstanceIdIpv4ReverseDefaultRequest = null;
        api.postInstancesInstanceIdIpv4ReverseDefault(instanceId, postInstancesInstanceIdIpv4ReverseDefaultRequest);
        // TODO: test validations
    }

    /**
     * Reboot Instance
     *
     * Reboot an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rebootInstanceTest() throws ApiException {
        String instanceId = null;
        api.rebootInstance(instanceId);
        // TODO: test validations
    }

    /**
     * Reboot instances
     *
     * Reboot Instances.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rebootInstancesTest() throws ApiException {
        RebootInstancesRequest rebootInstancesRequest = null;
        api.rebootInstances(rebootInstancesRequest);
        // TODO: test validations
    }

    /**
     * Reinstall Instance
     *
     * Reinstall an Instance using an optional &#x60;hostname&#x60;.  **Note:** This action may take a few extra seconds to complete.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void reinstallInstanceTest() throws ApiException {
        String instanceId = null;
        ReinstallInstanceRequest reinstallInstanceRequest = null;
        CreateInstance202Response response = api.reinstallInstance(instanceId, reinstallInstanceRequest);
        // TODO: test validations
    }

    /**
     * Restore Instance
     *
     * Restore an Instance from either &#x60;backup_id&#x60; or &#x60;snapshot_id&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void restoreInstanceTest() throws ApiException {
        String instanceId = null;
        RestoreInstanceRequest restoreInstanceRequest = null;
        RestoreInstance202Response response = api.restoreInstance(instanceId, restoreInstanceRequest);
        // TODO: test validations
    }

    /**
     * Start instance
     *
     * Start an Instance.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void startInstanceTest() throws ApiException {
        String instanceId = null;
        api.startInstance(instanceId);
        // TODO: test validations
    }

    /**
     * Start instances
     *
     * Start Instances.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void startInstancesTest() throws ApiException {
        StartInstancesRequest startInstancesRequest = null;
        api.startInstances(startInstancesRequest);
        // TODO: test validations
    }

    /**
     * Update Instance
     *
     * Update information for an Instance. All attributes are optional. If not set, the attributes will retain their original values.  **Note:** Changing &#x60;os_id&#x60;, &#x60;app_id&#x60; or &#x60;image_id&#x60; may take a few extra seconds to complete.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateInstanceTest() throws ApiException {
        String instanceId = null;
        UpdateInstanceRequest updateInstanceRequest = null;
        CreateInstance202Response response = api.updateInstance(instanceId, updateInstanceRequest);
        // TODO: test validations
    }

}
