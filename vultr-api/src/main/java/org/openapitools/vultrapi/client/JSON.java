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


package org.openapitools.vultrapi.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

import okio.ByteString;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/*
 * A JSON utility class
 *
 * NOTE: in the future, this class may be converted to static, which may break
 *       backward-compatibility
 */
public class JSON {
    private static Gson gson;
    private static boolean isLenientOnJson = false;
    private static DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private static SqlDateTypeAdapter sqlDateTypeAdapter = new SqlDateTypeAdapter();
    private static OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new OffsetDateTimeTypeAdapter();
    private static LocalDateTypeAdapter localDateTypeAdapter = new LocalDateTypeAdapter();
    private static ByteArrayAdapter byteArrayAdapter = new ByteArrayAdapter();

    @SuppressWarnings("unchecked")
    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder()
        ;
        GsonBuilder builder = fireBuilder.createGsonBuilder();
        return builder;
    }

    private static String getDiscriminatorValue(JsonElement readElement, String discriminatorField) {
        JsonElement element = readElement.getAsJsonObject().get(discriminatorField);
        if (null == element) {
            throw new IllegalArgumentException("missing discriminator field: <" + discriminatorField + ">");
        }
        return element.getAsString();
    }

    /**
     * Returns the Java class that implements the OpenAPI schema for the specified discriminator value.
     *
     * @param classByDiscriminatorValue The map of discriminator values to Java classes.
     * @param discriminatorValue The value of the OpenAPI discriminator in the input data.
     * @return The Java class that implements the OpenAPI schema
     */
    private static Class getClassByDiscriminator(Map classByDiscriminatorValue, String discriminatorValue) {
        Class clazz = (Class) classByDiscriminatorValue.get(discriminatorValue);
        if (null == clazz) {
            throw new IllegalArgumentException("cannot determine model class of name: <" + discriminatorValue + ">");
        }
        return clazz;
    }

    {
        gson = createGson()
            .registerTypeAdapter(Date.class, dateTypeAdapter)
            .registerTypeAdapter(java.sql.Date.class, sqlDateTypeAdapter)
            .registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter)
            .registerTypeAdapter(LocalDate.class, localDateTypeAdapter)
            .registerTypeAdapter(byte[].class, byteArrayAdapter)
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Account.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Application.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachBlockRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachInstanceIso202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachInstanceIso202ResponseIsoStatus.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachInstanceIsoRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachInstanceNetworkRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachInstanceVpcRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.AttachReservedIpRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Backup.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.BackupSchedule.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Bandwidth.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Baremetal.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.BaremetalIpv4.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.BaremetalIpv6.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Billing.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Blockstorage.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Clusters.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ConvertReservedIpRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateBaremetal202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateBaremetalRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateBlock202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateBlockRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateDnsDomain200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateDnsDomainRecord201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateDnsDomainRecordRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateDnsDomainRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateFirewallGroup201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateFirewallGroupRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateInstance202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateInstanceBackupScheduleRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateInstanceIpv4Request.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateInstanceRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateInstanceReverseIpv4Request.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateInstanceReverseIpv6Request.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateIso201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateIsoRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateKubernetesCluster201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateKubernetesClusterRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateKubernetesClusterRequestNodePoolsInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancer202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerForwardingRulesRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerRequestFirewallRulesInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerRequestForwardingRulesInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerRequestHealthCheck.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerRequestSsl.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateLoadBalancerRequestStickySession.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateNetworkRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateNodepools201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateNodepoolsRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateObjectStorage202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateObjectStorageRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateReservedIpRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateSnapshotCreateFromUrlRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateSnapshotRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateSshKeyRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateStartupScriptRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateUserRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.CreateVpcRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DetachBlockRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DetachInstanceIso202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DetachInstanceIso202ResponseIsoStatus.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DetachInstanceNetworkRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DetachInstanceVpcRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DnsRecord.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.DnsSoa.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Domain.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.FirewallGroup.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.FirewallRule.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ForwardingRule.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetAccount200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBackup200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBandwidthBaremetal200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBandwidthBaremetal200ResponseBandwidth.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBareMetalUserdata200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBareMetalUserdata200ResponseUserData.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBareMetalVnc200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBareMetalVnc200ResponseVnc.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBareMetalsUpgrades200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBareMetalsUpgrades200ResponseUpgrades.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetBaremetal200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetDnsDomainDnssec200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetDnsDomainSoa200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceBackupSchedule200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceIsoStatus200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceIsoStatus200ResponseIsoStatus.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceNeighbors200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceUpgrades200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceUpgrades200ResponseUpgrades.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceUserdata200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInstanceUserdata200ResponseUserData.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInvoice200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInvoiceItems200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInvoiceItems200ResponseInvoiceItemsInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInvoiceItems200ResponseMeta.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetInvoiceItems200ResponseMetaLinks.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetIpv4Baremetal200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetIpv6Baremetal200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesAvailableUpgrades200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesClustersConfig200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesResources200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesResources200ResponseResources.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesResources200ResponseResourcesBlockStorageInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesResources200ResponseResourcesLoadBalancerInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetKubernetesVersions200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetLoadBalancerForwardingRule200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetNetwork200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetNodepools200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetReservedIp200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetSnapshot200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetSshKey200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetStartupScript200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.GetVpc200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.HaltBaremetalsRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.HaltInstancesRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Instance.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.InstanceV6NetworksInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Invoice.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Iso.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.IsoPublic.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListApplications200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListAvailablePlansRegion200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListBackups200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListBaremetals200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListBillingHistory200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListBlocks200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListDnsDomainRecords200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListDnsDomains200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListFirewallGroupRules200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListFirewallGroups200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListInstanceIpv6Reverse200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListInstanceIpv6Reverse200ResponseReverseIpv6sInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListInstancePrivateNetworks200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListInstanceVpcs200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListInstances200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListInvoices200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListIsos200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListKubernetesClusters200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListLoadBalancerForwardingRules200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListLoadBalancers200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListMetalPlans200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListNetworks200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListObjectStorageClusters200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListObjectStorages200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListOs200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListPlans200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListPublicIsos200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListRegions200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListReservedIps200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListSnapshots200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListSshKeys200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListStartupScripts200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListUsers200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ListVpcs200Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Loadbalancer.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.LoadbalancerFirewallRule.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.LoadbalancerFirewallRulesInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.LoadbalancerForwardRulesInner.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.LoadbalancerGenericInfo.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.LoadbalancerGenericInfoStickySessions.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.LoadbalancerHealthCheck.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Meta.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.MetaLinks.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Network.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.NodepoolInstances.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Nodepools.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ObjectStorage.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Os.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Plans.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.PlansMetal.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.PostFirewallsFirewallGroupIdRules201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.PostFirewallsFirewallGroupIdRulesRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.PostInstancesInstanceIdIpv4ReverseDefaultRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.PrivateNetworks.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.PutSnapshotsSnapshotIdRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.RebootInstancesRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.RegenerateObjectStorageKeys201Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.RegenerateObjectStorageKeys201ResponseS3Credentials.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Region.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ReinstallInstanceRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.ReservedIp.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.RestoreInstance202Response.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.RestoreInstance202ResponseStatus.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.RestoreInstanceRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Snapshot.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Ssh.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.StartInstancesRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.StartKubernetesClusterUpgradeRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Startup.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateBaremetalRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateBlockRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateDnsDomainRecordRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateDnsDomainRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateDnsDomainSoaRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateFirewallGroupRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateInstanceRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateKubernetesClusterRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateLoadBalancerRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateLoadBalancerRequestHealthCheck.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateNetworkRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateNodepoolRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateNodepoolRequest1.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateObjectStorageRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateSshKeyRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateStartupScriptRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateUserRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UpdateVpcRequest.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.User.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.UserUser.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.VkeCluster.CustomTypeAdapterFactory())
            .registerTypeAdapterFactory(new org.openapitools.vultrapi.client.model.Vpc.CustomTypeAdapterFactory())
            .create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     */
    public static void setGson(Gson gson) {
        JSON.gson = gson;
    }

    public static void setLenientOnJson(boolean lenientOnJson) {
        isLenientOnJson = lenientOnJson;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public static String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            // Fallback processing when failed to parse JSON form response body:
            // return the response body string directly for the String return type;
            if (returnType.equals(String.class)) {
                return (T) body;
            } else {
                throw (e);
            }
        }
    }

    /**
     * Gson TypeAdapter for Byte Array type
     */
    public static class ByteArrayAdapter extends TypeAdapter<byte[]> {

        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(ByteString.of(value).base64());
            }
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String bytesAsBase64 = in.nextString();
                    ByteString byteString = ByteString.decodeBase64(bytesAsBase64);
                    return byteString.toByteArray();
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    if (date.endsWith("+0000")) {
                        date = date.substring(0, date.length()-5) + "Z";
                    }
                    return OffsetDateTime.parse(date, formatter);
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateTypeAdapter() {
            this(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public LocalDateTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDate.parse(date, formatter);
            }
        }
    }

    public static void setOffsetDateTimeFormat(DateTimeFormatter dateFormat) {
        offsetDateTimeTypeAdapter.setFormat(dateFormat);
    }

    public static void setLocalDateFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setFormat(dateFormat);
    }

    /**
     * Gson TypeAdapter for java.sql.Date type
     * If the dateFormat is null, a simple "yyyy-MM-dd" format will be used
     * (more efficient than SimpleDateFormat).
     */
    public static class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {

        private DateFormat dateFormat;

        public SqlDateTypeAdapter() {}

        public SqlDateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, java.sql.Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = date.toString();
                }
                out.value(value);
            }
        }

        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    try {
                        if (dateFormat != null) {
                            return new java.sql.Date(dateFormat.parse(date).getTime());
                        }
                        return new java.sql.Date(ISO8601Utils.parse(date, new ParsePosition(0)).getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
            }
        }
    }

    /**
     * Gson TypeAdapter for java.util.Date type
     * If the dateFormat is null, ISO8601Utils will be used.
     */
    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private DateFormat dateFormat;

        public DateTypeAdapter() {}

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = ISO8601Utils.format(date, true);
                }
                out.value(value);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                switch (in.peek()) {
                    case NULL:
                        in.nextNull();
                        return null;
                    default:
                        String date = in.nextString();
                        try {
                            if (dateFormat != null) {
                                return dateFormat.parse(date);
                            }
                            return ISO8601Utils.parse(date, new ParsePosition(0));
                        } catch (ParseException e) {
                            throw new JsonParseException(e);
                        }
                }
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static void setDateFormat(DateFormat dateFormat) {
        dateTypeAdapter.setFormat(dateFormat);
    }

    public static void setSqlDateFormat(DateFormat dateFormat) {
        sqlDateTypeAdapter.setFormat(dateFormat);
    }
}
