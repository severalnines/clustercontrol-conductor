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


package org.openapitools.vultrapi.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.vultrapi.client.JSON;

/**
 * UpdateInstanceRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class UpdateInstanceRequest {
  public static final String SERIALIZED_NAME_APP_ID = "app_id";
  @SerializedName(SERIALIZED_NAME_APP_ID)
  private Integer appId;

  public static final String SERIALIZED_NAME_IMAGE_ID = "image_id";
  @SerializedName(SERIALIZED_NAME_IMAGE_ID)
  private String imageId;

  public static final String SERIALIZED_NAME_BACKUPS = "backups";
  @SerializedName(SERIALIZED_NAME_BACKUPS)
  private String backups;

  public static final String SERIALIZED_NAME_FIREWALL_GROUP_ID = "firewall_group_id";
  @SerializedName(SERIALIZED_NAME_FIREWALL_GROUP_ID)
  private String firewallGroupId;

  public static final String SERIALIZED_NAME_ENABLE_IPV6 = "enable_ipv6";
  @SerializedName(SERIALIZED_NAME_ENABLE_IPV6)
  private Boolean enableIpv6;

  public static final String SERIALIZED_NAME_OS_ID = "os_id";
  @SerializedName(SERIALIZED_NAME_OS_ID)
  private String osId;

  public static final String SERIALIZED_NAME_USER_DATA = "user_data";
  @SerializedName(SERIALIZED_NAME_USER_DATA)
  private String userData;

  public static final String SERIALIZED_NAME_TAG = "tag";
  @SerializedName(SERIALIZED_NAME_TAG)
  private String tag;

  public static final String SERIALIZED_NAME_PLAN = "plan";
  @SerializedName(SERIALIZED_NAME_PLAN)
  private String plan;

  public static final String SERIALIZED_NAME_DDOS_PROTECTION = "ddos_protection";
  @SerializedName(SERIALIZED_NAME_DDOS_PROTECTION)
  private Boolean ddosProtection;

  public static final String SERIALIZED_NAME_ATTACH_PRIVATE_NETWORK = "attach_private_network";
  @SerializedName(SERIALIZED_NAME_ATTACH_PRIVATE_NETWORK)
  private List<String> attachPrivateNetwork = null;

  public static final String SERIALIZED_NAME_ATTACH_VPC = "attach_vpc";
  @SerializedName(SERIALIZED_NAME_ATTACH_VPC)
  private List<String> attachVpc = null;

  public static final String SERIALIZED_NAME_DETACH_PRIVATE_NETWORK = "detach_private_network";
  @SerializedName(SERIALIZED_NAME_DETACH_PRIVATE_NETWORK)
  private List<String> detachPrivateNetwork = null;

  public static final String SERIALIZED_NAME_DETACH_VPC = "detach_vpc";
  @SerializedName(SERIALIZED_NAME_DETACH_VPC)
  private List<String> detachVpc = null;

  public static final String SERIALIZED_NAME_ENABLE_PRIVATE_NETWORK = "enable_private_network";
  @SerializedName(SERIALIZED_NAME_ENABLE_PRIVATE_NETWORK)
  private Boolean enablePrivateNetwork;

  public static final String SERIALIZED_NAME_ENABLE_VPC = "enable_vpc";
  @SerializedName(SERIALIZED_NAME_ENABLE_VPC)
  private Boolean enableVpc;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = null;

  public UpdateInstanceRequest() { 
  }

  public UpdateInstanceRequest appId(Integer appId) {
    
    this.appId = appId;
    return this;
  }

   /**
   * Reinstall the instance with this [Application id](#operation/list-applications).
   * @return appId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Reinstall the instance with this [Application id](#operation/list-applications).")

  public Integer getAppId() {
    return appId;
  }


  public void setAppId(Integer appId) {
    this.appId = appId;
  }


  public UpdateInstanceRequest imageId(String imageId) {
    
    this.imageId = imageId;
    return this;
  }

   /**
   * Reinstall the instance with this [Application image_id](#operation/list-applications).
   * @return imageId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Reinstall the instance with this [Application image_id](#operation/list-applications).")

  public String getImageId() {
    return imageId;
  }


  public void setImageId(String imageId) {
    this.imageId = imageId;
  }


  public UpdateInstanceRequest backups(String backups) {
    
    this.backups = backups;
    return this;
  }

   /**
   * Enable automatic backups for the instance.  * enabled * disabled
   * @return backups
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Enable automatic backups for the instance.  * enabled * disabled")

  public String getBackups() {
    return backups;
  }


  public void setBackups(String backups) {
    this.backups = backups;
  }


  public UpdateInstanceRequest firewallGroupId(String firewallGroupId) {
    
    this.firewallGroupId = firewallGroupId;
    return this;
  }

   /**
   * The [Firewall Group id](#operation/list-firewall-groups) to attach to this Instance.
   * @return firewallGroupId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Firewall Group id](#operation/list-firewall-groups) to attach to this Instance.")

  public String getFirewallGroupId() {
    return firewallGroupId;
  }


  public void setFirewallGroupId(String firewallGroupId) {
    this.firewallGroupId = firewallGroupId;
  }


  public UpdateInstanceRequest enableIpv6(Boolean enableIpv6) {
    
    this.enableIpv6 = enableIpv6;
    return this;
  }

   /**
   * Enable IPv6.  * true * false
   * @return enableIpv6
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Enable IPv6.  * true * false")

  public Boolean getEnableIpv6() {
    return enableIpv6;
  }


  public void setEnableIpv6(Boolean enableIpv6) {
    this.enableIpv6 = enableIpv6;
  }


  public UpdateInstanceRequest osId(String osId) {
    
    this.osId = osId;
    return this;
  }

   /**
   * Reinstall the instance with this [ISO id](#operation/list-isos).
   * @return osId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Reinstall the instance with this [ISO id](#operation/list-isos).")

  public String getOsId() {
    return osId;
  }


  public void setOsId(String osId) {
    this.osId = osId;
  }


  public UpdateInstanceRequest userData(String userData) {
    
    this.userData = userData;
    return this;
  }

   /**
   * The user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) to attach to this instance.
   * @return userData
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) to attach to this instance.")

  public String getUserData() {
    return userData;
  }


  public void setUserData(String userData) {
    this.userData = userData;
  }


  public UpdateInstanceRequest tag(String tag) {
    
    this.tag = tag;
    return this;
  }

   /**
   * Use &#x60;tags&#x60; instead. The user-supplied tag.
   * @return tag
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Use `tags` instead. The user-supplied tag.")

  public String getTag() {
    return tag;
  }


  public void setTag(String tag) {
    this.tag = tag;
  }


  public UpdateInstanceRequest plan(String plan) {
    
    this.plan = plan;
    return this;
  }

   /**
   * Upgrade the instance with this [Plan id](#operation/list-plans).
   * @return plan
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Upgrade the instance with this [Plan id](#operation/list-plans).")

  public String getPlan() {
    return plan;
  }


  public void setPlan(String plan) {
    this.plan = plan;
  }


  public UpdateInstanceRequest ddosProtection(Boolean ddosProtection) {
    
    this.ddosProtection = ddosProtection;
    return this;
  }

   /**
   * Enable DDoS Protection (there is an additional charge for this).  * true * false
   * @return ddosProtection
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Enable DDoS Protection (there is an additional charge for this).  * true * false")

  public Boolean getDdosProtection() {
    return ddosProtection;
  }


  public void setDdosProtection(Boolean ddosProtection) {
    this.ddosProtection = ddosProtection;
  }


  public UpdateInstanceRequest attachPrivateNetwork(List<String> attachPrivateNetwork) {
    
    this.attachPrivateNetwork = attachPrivateNetwork;
    return this;
  }

  public UpdateInstanceRequest addAttachPrivateNetworkItem(String attachPrivateNetworkItem) {
    if (this.attachPrivateNetwork == null) {
      this.attachPrivateNetwork = new ArrayList<>();
    }
    this.attachPrivateNetwork.add(attachPrivateNetworkItem);
    return this;
  }

   /**
   * Use &#x60;attach_vpc&#x60; instead. An array of [Private Network ids](#operation/list-networks) to attach to this Instance. This parameter takes precedence over &#x60;enable_private_network&#x60;. Please choose one parameter.
   * @return attachPrivateNetwork
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Use `attach_vpc` instead. An array of [Private Network ids](#operation/list-networks) to attach to this Instance. This parameter takes precedence over `enable_private_network`. Please choose one parameter.")

  public List<String> getAttachPrivateNetwork() {
    return attachPrivateNetwork;
  }


  public void setAttachPrivateNetwork(List<String> attachPrivateNetwork) {
    this.attachPrivateNetwork = attachPrivateNetwork;
  }


  public UpdateInstanceRequest attachVpc(List<String> attachVpc) {
    
    this.attachVpc = attachVpc;
    return this;
  }

  public UpdateInstanceRequest addAttachVpcItem(String attachVpcItem) {
    if (this.attachVpc == null) {
      this.attachVpc = new ArrayList<>();
    }
    this.attachVpc.add(attachVpcItem);
    return this;
  }

   /**
   * An array of [VPC IDs](#operation/list-vpcs) to attach to this Instance. This parameter takes precedence over &#x60;enable_vpc&#x60;. Please choose one parameter.
   * @return attachVpc
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An array of [VPC IDs](#operation/list-vpcs) to attach to this Instance. This parameter takes precedence over `enable_vpc`. Please choose one parameter.")

  public List<String> getAttachVpc() {
    return attachVpc;
  }


  public void setAttachVpc(List<String> attachVpc) {
    this.attachVpc = attachVpc;
  }


  public UpdateInstanceRequest detachPrivateNetwork(List<String> detachPrivateNetwork) {
    
    this.detachPrivateNetwork = detachPrivateNetwork;
    return this;
  }

  public UpdateInstanceRequest addDetachPrivateNetworkItem(String detachPrivateNetworkItem) {
    if (this.detachPrivateNetwork == null) {
      this.detachPrivateNetwork = new ArrayList<>();
    }
    this.detachPrivateNetwork.add(detachPrivateNetworkItem);
    return this;
  }

   /**
   * Use &#x60;detach_vpc&#x60; instead. An array of [Private Network ids](#operation/list-networks) to detach from this Instance. This parameter takes precedence over &#x60;enable_private_network&#x60;.
   * @return detachPrivateNetwork
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Use `detach_vpc` instead. An array of [Private Network ids](#operation/list-networks) to detach from this Instance. This parameter takes precedence over `enable_private_network`.")

  public List<String> getDetachPrivateNetwork() {
    return detachPrivateNetwork;
  }


  public void setDetachPrivateNetwork(List<String> detachPrivateNetwork) {
    this.detachPrivateNetwork = detachPrivateNetwork;
  }


  public UpdateInstanceRequest detachVpc(List<String> detachVpc) {
    
    this.detachVpc = detachVpc;
    return this;
  }

  public UpdateInstanceRequest addDetachVpcItem(String detachVpcItem) {
    if (this.detachVpc == null) {
      this.detachVpc = new ArrayList<>();
    }
    this.detachVpc.add(detachVpcItem);
    return this;
  }

   /**
   * An array of [VPC IDs](#operation/list-vpcs) to detach from this Instance. This parameter takes precedence over &#x60;enable_vpc&#x60;.
   * @return detachVpc
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An array of [VPC IDs](#operation/list-vpcs) to detach from this Instance. This parameter takes precedence over `enable_vpc`.")

  public List<String> getDetachVpc() {
    return detachVpc;
  }


  public void setDetachVpc(List<String> detachVpc) {
    this.detachVpc = detachVpc;
  }


  public UpdateInstanceRequest enablePrivateNetwork(Boolean enablePrivateNetwork) {
    
    this.enablePrivateNetwork = enablePrivateNetwork;
    return this;
  }

   /**
   * Use &#x60;enable_vpc&#x60; instead.  If &#x60;true&#x60;, private networking support will be added to the new server.  This parameter attaches a single network. When no network exists in the region, it will be automatically created.  If there are multiple private networks in the instance&#39;s region, use &#x60;attach_private_network&#x60; instead to specify a network.
   * @return enablePrivateNetwork
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Use `enable_vpc` instead.  If `true`, private networking support will be added to the new server.  This parameter attaches a single network. When no network exists in the region, it will be automatically created.  If there are multiple private networks in the instance's region, use `attach_private_network` instead to specify a network.")

  public Boolean getEnablePrivateNetwork() {
    return enablePrivateNetwork;
  }


  public void setEnablePrivateNetwork(Boolean enablePrivateNetwork) {
    this.enablePrivateNetwork = enablePrivateNetwork;
  }


  public UpdateInstanceRequest enableVpc(Boolean enableVpc) {
    
    this.enableVpc = enableVpc;
    return this;
  }

   /**
   * If &#x60;true&#x60;, VPC support will be added to the new server.  This parameter attaches a single VPC. When no VPC exists in the region, it will be automatically created.  If there are multiple VPCs in the instance&#39;s region, use &#x60;attach_vpc&#x60; instead to specify a VPC.
   * @return enableVpc
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "If `true`, VPC support will be added to the new server.  This parameter attaches a single VPC. When no VPC exists in the region, it will be automatically created.  If there are multiple VPCs in the instance's region, use `attach_vpc` instead to specify a VPC.")

  public Boolean getEnableVpc() {
    return enableVpc;
  }


  public void setEnableVpc(Boolean enableVpc) {
    this.enableVpc = enableVpc;
  }


  public UpdateInstanceRequest label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * The user supplied label
   * @return label
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user supplied label")

  public String getLabel() {
    return label;
  }


  public void setLabel(String label) {
    this.label = label;
  }


  public UpdateInstanceRequest tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public UpdateInstanceRequest addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Tags to apply to the instance
   * @return tags
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Tags to apply to the instance")

  public List<String> getTags() {
    return tags;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateInstanceRequest updateInstanceRequest = (UpdateInstanceRequest) o;
    return Objects.equals(this.appId, updateInstanceRequest.appId) &&
        Objects.equals(this.imageId, updateInstanceRequest.imageId) &&
        Objects.equals(this.backups, updateInstanceRequest.backups) &&
        Objects.equals(this.firewallGroupId, updateInstanceRequest.firewallGroupId) &&
        Objects.equals(this.enableIpv6, updateInstanceRequest.enableIpv6) &&
        Objects.equals(this.osId, updateInstanceRequest.osId) &&
        Objects.equals(this.userData, updateInstanceRequest.userData) &&
        Objects.equals(this.tag, updateInstanceRequest.tag) &&
        Objects.equals(this.plan, updateInstanceRequest.plan) &&
        Objects.equals(this.ddosProtection, updateInstanceRequest.ddosProtection) &&
        Objects.equals(this.attachPrivateNetwork, updateInstanceRequest.attachPrivateNetwork) &&
        Objects.equals(this.attachVpc, updateInstanceRequest.attachVpc) &&
        Objects.equals(this.detachPrivateNetwork, updateInstanceRequest.detachPrivateNetwork) &&
        Objects.equals(this.detachVpc, updateInstanceRequest.detachVpc) &&
        Objects.equals(this.enablePrivateNetwork, updateInstanceRequest.enablePrivateNetwork) &&
        Objects.equals(this.enableVpc, updateInstanceRequest.enableVpc) &&
        Objects.equals(this.label, updateInstanceRequest.label) &&
        Objects.equals(this.tags, updateInstanceRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appId, imageId, backups, firewallGroupId, enableIpv6, osId, userData, tag, plan, ddosProtection, attachPrivateNetwork, attachVpc, detachPrivateNetwork, detachVpc, enablePrivateNetwork, enableVpc, label, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateInstanceRequest {\n");
    sb.append("    appId: ").append(toIndentedString(appId)).append("\n");
    sb.append("    imageId: ").append(toIndentedString(imageId)).append("\n");
    sb.append("    backups: ").append(toIndentedString(backups)).append("\n");
    sb.append("    firewallGroupId: ").append(toIndentedString(firewallGroupId)).append("\n");
    sb.append("    enableIpv6: ").append(toIndentedString(enableIpv6)).append("\n");
    sb.append("    osId: ").append(toIndentedString(osId)).append("\n");
    sb.append("    userData: ").append(toIndentedString(userData)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
    sb.append("    ddosProtection: ").append(toIndentedString(ddosProtection)).append("\n");
    sb.append("    attachPrivateNetwork: ").append(toIndentedString(attachPrivateNetwork)).append("\n");
    sb.append("    attachVpc: ").append(toIndentedString(attachVpc)).append("\n");
    sb.append("    detachPrivateNetwork: ").append(toIndentedString(detachPrivateNetwork)).append("\n");
    sb.append("    detachVpc: ").append(toIndentedString(detachVpc)).append("\n");
    sb.append("    enablePrivateNetwork: ").append(toIndentedString(enablePrivateNetwork)).append("\n");
    sb.append("    enableVpc: ").append(toIndentedString(enableVpc)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("app_id");
    openapiFields.add("image_id");
    openapiFields.add("backups");
    openapiFields.add("firewall_group_id");
    openapiFields.add("enable_ipv6");
    openapiFields.add("os_id");
    openapiFields.add("user_data");
    openapiFields.add("tag");
    openapiFields.add("plan");
    openapiFields.add("ddos_protection");
    openapiFields.add("attach_private_network");
    openapiFields.add("attach_vpc");
    openapiFields.add("detach_private_network");
    openapiFields.add("detach_vpc");
    openapiFields.add("enable_private_network");
    openapiFields.add("enable_vpc");
    openapiFields.add("label");
    openapiFields.add("tags");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to UpdateInstanceRequest
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (UpdateInstanceRequest.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in UpdateInstanceRequest is not found in the empty JSON string", UpdateInstanceRequest.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UpdateInstanceRequest.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UpdateInstanceRequest` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("image_id") != null && !jsonObj.get("image_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `image_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("image_id").toString()));
      }
      if (jsonObj.get("backups") != null && !jsonObj.get("backups").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `backups` to be a primitive type in the JSON string but got `%s`", jsonObj.get("backups").toString()));
      }
      if (jsonObj.get("firewall_group_id") != null && !jsonObj.get("firewall_group_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `firewall_group_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("firewall_group_id").toString()));
      }
      if (jsonObj.get("os_id") != null && !jsonObj.get("os_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `os_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("os_id").toString()));
      }
      if (jsonObj.get("user_data") != null && !jsonObj.get("user_data").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `user_data` to be a primitive type in the JSON string but got `%s`", jsonObj.get("user_data").toString()));
      }
      if (jsonObj.get("tag") != null && !jsonObj.get("tag").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `tag` to be a primitive type in the JSON string but got `%s`", jsonObj.get("tag").toString()));
      }
      if (jsonObj.get("plan") != null && !jsonObj.get("plan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `plan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("plan").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("attach_private_network") != null && !jsonObj.get("attach_private_network").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `attach_private_network` to be an array in the JSON string but got `%s`", jsonObj.get("attach_private_network").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("attach_vpc") != null && !jsonObj.get("attach_vpc").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `attach_vpc` to be an array in the JSON string but got `%s`", jsonObj.get("attach_vpc").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("detach_private_network") != null && !jsonObj.get("detach_private_network").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `detach_private_network` to be an array in the JSON string but got `%s`", jsonObj.get("detach_private_network").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("detach_vpc") != null && !jsonObj.get("detach_vpc").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `detach_vpc` to be an array in the JSON string but got `%s`", jsonObj.get("detach_vpc").toString()));
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UpdateInstanceRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UpdateInstanceRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UpdateInstanceRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UpdateInstanceRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<UpdateInstanceRequest>() {
           @Override
           public void write(JsonWriter out, UpdateInstanceRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UpdateInstanceRequest read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UpdateInstanceRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdateInstanceRequest
  * @throws IOException if the JSON string is invalid with respect to UpdateInstanceRequest
  */
  public static UpdateInstanceRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdateInstanceRequest.class);
  }

 /**
  * Convert an instance of UpdateInstanceRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

