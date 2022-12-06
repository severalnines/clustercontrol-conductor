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
 * Bare Metal information.
 */
@ApiModel(description = "Bare Metal information.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class Baremetal {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_OS = "os";
  @SerializedName(SERIALIZED_NAME_OS)
  private String os;

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private String ram;

  public static final String SERIALIZED_NAME_DISK = "disk";
  @SerializedName(SERIALIZED_NAME_DISK)
  private String disk;

  public static final String SERIALIZED_NAME_MAIN_IP = "main_ip";
  @SerializedName(SERIALIZED_NAME_MAIN_IP)
  private String mainIp;

  public static final String SERIALIZED_NAME_CPU_COUNT = "cpu_count";
  @SerializedName(SERIALIZED_NAME_CPU_COUNT)
  private Integer cpuCount;

  public static final String SERIALIZED_NAME_REGION = "region";
  @SerializedName(SERIALIZED_NAME_REGION)
  private String region;

  public static final String SERIALIZED_NAME_DEFAULT_PASSWORD = "default_password";
  @SerializedName(SERIALIZED_NAME_DEFAULT_PASSWORD)
  private String defaultPassword;

  public static final String SERIALIZED_NAME_DATE_CREATED = "date_created";
  @SerializedName(SERIALIZED_NAME_DATE_CREATED)
  private String dateCreated;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_NETMASK_V4 = "netmask_v4";
  @SerializedName(SERIALIZED_NAME_NETMASK_V4)
  private String netmaskV4;

  public static final String SERIALIZED_NAME_GATEWAY_V4 = "gateway_v4";
  @SerializedName(SERIALIZED_NAME_GATEWAY_V4)
  private String gatewayV4;

  public static final String SERIALIZED_NAME_PLAN = "plan";
  @SerializedName(SERIALIZED_NAME_PLAN)
  private String plan;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_TAG = "tag";
  @SerializedName(SERIALIZED_NAME_TAG)
  private String tag;

  public static final String SERIALIZED_NAME_OS_ID = "os_id";
  @SerializedName(SERIALIZED_NAME_OS_ID)
  private Integer osId;

  public static final String SERIALIZED_NAME_APP_ID = "app_id";
  @SerializedName(SERIALIZED_NAME_APP_ID)
  private Integer appId;

  public static final String SERIALIZED_NAME_IMAGE_ID = "image_id";
  @SerializedName(SERIALIZED_NAME_IMAGE_ID)
  private String imageId;

  public static final String SERIALIZED_NAME_V6_NETWORK = "v6_network";
  @SerializedName(SERIALIZED_NAME_V6_NETWORK)
  private String v6Network;

  public static final String SERIALIZED_NAME_V6_MAIN_IP = "v6_main_ip";
  @SerializedName(SERIALIZED_NAME_V6_MAIN_IP)
  private String v6MainIp;

  public static final String SERIALIZED_NAME_V6_NETWORK_SIZE = "v6_network_size";
  @SerializedName(SERIALIZED_NAME_V6_NETWORK_SIZE)
  private Integer v6NetworkSize;

  public static final String SERIALIZED_NAME_MAC_ADDRESS = "mac_address";
  @SerializedName(SERIALIZED_NAME_MAC_ADDRESS)
  private Integer macAddress;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = null;

  public Baremetal() { 
  }

  public Baremetal id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * A unique ID for the Bare Metal instance.
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A unique ID for the Bare Metal instance.")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public Baremetal os(String os) {
    
    this.os = os;
    return this;
  }

   /**
   * The [Operating System name](#operation/list-os).
   * @return os
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Operating System name](#operation/list-os).")

  public String getOs() {
    return os;
  }


  public void setOs(String os) {
    this.os = os;
  }


  public Baremetal ram(String ram) {
    
    this.ram = ram;
    return this;
  }

   /**
   * Text description of the instances&#39; RAM.
   * @return ram
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Text description of the instances' RAM.")

  public String getRam() {
    return ram;
  }


  public void setRam(String ram) {
    this.ram = ram;
  }


  public Baremetal disk(String disk) {
    
    this.disk = disk;
    return this;
  }

   /**
   * Text description of the instances&#39; disk configuration.
   * @return disk
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Text description of the instances' disk configuration.")

  public String getDisk() {
    return disk;
  }


  public void setDisk(String disk) {
    this.disk = disk;
  }


  public Baremetal mainIp(String mainIp) {
    
    this.mainIp = mainIp;
    return this;
  }

   /**
   * The main IPv4 address.
   * @return mainIp
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The main IPv4 address.")

  public String getMainIp() {
    return mainIp;
  }


  public void setMainIp(String mainIp) {
    this.mainIp = mainIp;
  }


  public Baremetal cpuCount(Integer cpuCount) {
    
    this.cpuCount = cpuCount;
    return this;
  }

   /**
   * Number of CPUs.
   * @return cpuCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of CPUs.")

  public Integer getCpuCount() {
    return cpuCount;
  }


  public void setCpuCount(Integer cpuCount) {
    this.cpuCount = cpuCount;
  }


  public Baremetal region(String region) {
    
    this.region = region;
    return this;
  }

   /**
   * The [Region id](#operation/list-regions) where the instance is located.
   * @return region
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Region id](#operation/list-regions) where the instance is located.")

  public String getRegion() {
    return region;
  }


  public void setRegion(String region) {
    this.region = region;
  }


  public Baremetal defaultPassword(String defaultPassword) {
    
    this.defaultPassword = defaultPassword;
    return this;
  }

   /**
   * The default password assigned at deployment.
   * @return defaultPassword
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The default password assigned at deployment.")

  public String getDefaultPassword() {
    return defaultPassword;
  }


  public void setDefaultPassword(String defaultPassword) {
    this.defaultPassword = defaultPassword;
  }


  public Baremetal dateCreated(String dateCreated) {
    
    this.dateCreated = dateCreated;
    return this;
  }

   /**
   * The date this instance was created.
   * @return dateCreated
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The date this instance was created.")

  public String getDateCreated() {
    return dateCreated;
  }


  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }


  public Baremetal status(String status) {
    
    this.status = status;
    return this;
  }

   /**
   * The current status.  * active * pending * suspended
   * @return status
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The current status.  * active * pending * suspended")

  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = status;
  }


  public Baremetal netmaskV4(String netmaskV4) {
    
    this.netmaskV4 = netmaskV4;
    return this;
  }

   /**
   * The IPv4 netmask in dot-decimal notation.
   * @return netmaskV4
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IPv4 netmask in dot-decimal notation.")

  public String getNetmaskV4() {
    return netmaskV4;
  }


  public void setNetmaskV4(String netmaskV4) {
    this.netmaskV4 = netmaskV4;
  }


  public Baremetal gatewayV4(String gatewayV4) {
    
    this.gatewayV4 = gatewayV4;
    return this;
  }

   /**
   * The IPv4 gateway address.
   * @return gatewayV4
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IPv4 gateway address.")

  public String getGatewayV4() {
    return gatewayV4;
  }


  public void setGatewayV4(String gatewayV4) {
    this.gatewayV4 = gatewayV4;
  }


  public Baremetal plan(String plan) {
    
    this.plan = plan;
    return this;
  }

   /**
   * The [Bare Metal Plan id](#operation/list-metal-plans) used by this instance.
   * @return plan
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Bare Metal Plan id](#operation/list-metal-plans) used by this instance.")

  public String getPlan() {
    return plan;
  }


  public void setPlan(String plan) {
    this.plan = plan;
  }


  public Baremetal label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * The user-supplied label for this instance.
   * @return label
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user-supplied label for this instance.")

  public String getLabel() {
    return label;
  }


  public void setLabel(String label) {
    this.label = label;
  }


  public Baremetal tag(String tag) {
    
    this.tag = tag;
    return this;
  }

   /**
   * Use &#x60;tags&#x60; instead. The user-supplied tag for this instance.
   * @return tag
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Use `tags` instead. The user-supplied tag for this instance.")

  public String getTag() {
    return tag;
  }


  public void setTag(String tag) {
    this.tag = tag;
  }


  public Baremetal osId(Integer osId) {
    
    this.osId = osId;
    return this;
  }

   /**
   * The [Operating System id](#operation/list-os).
   * @return osId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Operating System id](#operation/list-os).")

  public Integer getOsId() {
    return osId;
  }


  public void setOsId(Integer osId) {
    this.osId = osId;
  }


  public Baremetal appId(Integer appId) {
    
    this.appId = appId;
    return this;
  }

   /**
   * The [Application id](#operation/list-applications).
   * @return appId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Application id](#operation/list-applications).")

  public Integer getAppId() {
    return appId;
  }


  public void setAppId(Integer appId) {
    this.appId = appId;
  }


  public Baremetal imageId(String imageId) {
    
    this.imageId = imageId;
    return this;
  }

   /**
   * The [Application image_id](#operation/list-applications).
   * @return imageId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Application image_id](#operation/list-applications).")

  public String getImageId() {
    return imageId;
  }


  public void setImageId(String imageId) {
    this.imageId = imageId;
  }


  public Baremetal v6Network(String v6Network) {
    
    this.v6Network = v6Network;
    return this;
  }

   /**
   * The IPv6 network size in bits.
   * @return v6Network
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IPv6 network size in bits.")

  public String getV6Network() {
    return v6Network;
  }


  public void setV6Network(String v6Network) {
    this.v6Network = v6Network;
  }


  public Baremetal v6MainIp(String v6MainIp) {
    
    this.v6MainIp = v6MainIp;
    return this;
  }

   /**
   * The main IPv6 network address.
   * @return v6MainIp
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The main IPv6 network address.")

  public String getV6MainIp() {
    return v6MainIp;
  }


  public void setV6MainIp(String v6MainIp) {
    this.v6MainIp = v6MainIp;
  }


  public Baremetal v6NetworkSize(Integer v6NetworkSize) {
    
    this.v6NetworkSize = v6NetworkSize;
    return this;
  }

   /**
   * The IPv6 subnet.
   * @return v6NetworkSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IPv6 subnet.")

  public Integer getV6NetworkSize() {
    return v6NetworkSize;
  }


  public void setV6NetworkSize(Integer v6NetworkSize) {
    this.v6NetworkSize = v6NetworkSize;
  }


  public Baremetal macAddress(Integer macAddress) {
    
    this.macAddress = macAddress;
    return this;
  }

   /**
   * The MAC address for a Bare Metal server
   * @return macAddress
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The MAC address for a Bare Metal server")

  public Integer getMacAddress() {
    return macAddress;
  }


  public void setMacAddress(Integer macAddress) {
    this.macAddress = macAddress;
  }


  public Baremetal tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public Baremetal addTagsItem(String tagsItem) {
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
    Baremetal baremetal = (Baremetal) o;
    return Objects.equals(this.id, baremetal.id) &&
        Objects.equals(this.os, baremetal.os) &&
        Objects.equals(this.ram, baremetal.ram) &&
        Objects.equals(this.disk, baremetal.disk) &&
        Objects.equals(this.mainIp, baremetal.mainIp) &&
        Objects.equals(this.cpuCount, baremetal.cpuCount) &&
        Objects.equals(this.region, baremetal.region) &&
        Objects.equals(this.defaultPassword, baremetal.defaultPassword) &&
        Objects.equals(this.dateCreated, baremetal.dateCreated) &&
        Objects.equals(this.status, baremetal.status) &&
        Objects.equals(this.netmaskV4, baremetal.netmaskV4) &&
        Objects.equals(this.gatewayV4, baremetal.gatewayV4) &&
        Objects.equals(this.plan, baremetal.plan) &&
        Objects.equals(this.label, baremetal.label) &&
        Objects.equals(this.tag, baremetal.tag) &&
        Objects.equals(this.osId, baremetal.osId) &&
        Objects.equals(this.appId, baremetal.appId) &&
        Objects.equals(this.imageId, baremetal.imageId) &&
        Objects.equals(this.v6Network, baremetal.v6Network) &&
        Objects.equals(this.v6MainIp, baremetal.v6MainIp) &&
        Objects.equals(this.v6NetworkSize, baremetal.v6NetworkSize) &&
        Objects.equals(this.macAddress, baremetal.macAddress) &&
        Objects.equals(this.tags, baremetal.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, os, ram, disk, mainIp, cpuCount, region, defaultPassword, dateCreated, status, netmaskV4, gatewayV4, plan, label, tag, osId, appId, imageId, v6Network, v6MainIp, v6NetworkSize, macAddress, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Baremetal {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    os: ").append(toIndentedString(os)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
    sb.append("    disk: ").append(toIndentedString(disk)).append("\n");
    sb.append("    mainIp: ").append(toIndentedString(mainIp)).append("\n");
    sb.append("    cpuCount: ").append(toIndentedString(cpuCount)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    defaultPassword: ").append(toIndentedString(defaultPassword)).append("\n");
    sb.append("    dateCreated: ").append(toIndentedString(dateCreated)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    netmaskV4: ").append(toIndentedString(netmaskV4)).append("\n");
    sb.append("    gatewayV4: ").append(toIndentedString(gatewayV4)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    osId: ").append(toIndentedString(osId)).append("\n");
    sb.append("    appId: ").append(toIndentedString(appId)).append("\n");
    sb.append("    imageId: ").append(toIndentedString(imageId)).append("\n");
    sb.append("    v6Network: ").append(toIndentedString(v6Network)).append("\n");
    sb.append("    v6MainIp: ").append(toIndentedString(v6MainIp)).append("\n");
    sb.append("    v6NetworkSize: ").append(toIndentedString(v6NetworkSize)).append("\n");
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("os");
    openapiFields.add("ram");
    openapiFields.add("disk");
    openapiFields.add("main_ip");
    openapiFields.add("cpu_count");
    openapiFields.add("region");
    openapiFields.add("default_password");
    openapiFields.add("date_created");
    openapiFields.add("status");
    openapiFields.add("netmask_v4");
    openapiFields.add("gateway_v4");
    openapiFields.add("plan");
    openapiFields.add("label");
    openapiFields.add("tag");
    openapiFields.add("os_id");
    openapiFields.add("app_id");
    openapiFields.add("image_id");
    openapiFields.add("v6_network");
    openapiFields.add("v6_main_ip");
    openapiFields.add("v6_network_size");
    openapiFields.add("mac_address");
    openapiFields.add("tags");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Baremetal
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Baremetal.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Baremetal is not found in the empty JSON string", Baremetal.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Baremetal.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Baremetal` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("id") != null && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if (jsonObj.get("os") != null && !jsonObj.get("os").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `os` to be a primitive type in the JSON string but got `%s`", jsonObj.get("os").toString()));
      }
      if (jsonObj.get("ram") != null && !jsonObj.get("ram").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ram` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ram").toString()));
      }
      if (jsonObj.get("disk") != null && !jsonObj.get("disk").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `disk` to be a primitive type in the JSON string but got `%s`", jsonObj.get("disk").toString()));
      }
      if (jsonObj.get("main_ip") != null && !jsonObj.get("main_ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `main_ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("main_ip").toString()));
      }
      if (jsonObj.get("region") != null && !jsonObj.get("region").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `region` to be a primitive type in the JSON string but got `%s`", jsonObj.get("region").toString()));
      }
      if (jsonObj.get("default_password") != null && !jsonObj.get("default_password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `default_password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("default_password").toString()));
      }
      if (jsonObj.get("date_created") != null && !jsonObj.get("date_created").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `date_created` to be a primitive type in the JSON string but got `%s`", jsonObj.get("date_created").toString()));
      }
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (jsonObj.get("netmask_v4") != null && !jsonObj.get("netmask_v4").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `netmask_v4` to be a primitive type in the JSON string but got `%s`", jsonObj.get("netmask_v4").toString()));
      }
      if (jsonObj.get("gateway_v4") != null && !jsonObj.get("gateway_v4").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `gateway_v4` to be a primitive type in the JSON string but got `%s`", jsonObj.get("gateway_v4").toString()));
      }
      if (jsonObj.get("plan") != null && !jsonObj.get("plan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `plan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("plan").toString()));
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      if (jsonObj.get("tag") != null && !jsonObj.get("tag").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `tag` to be a primitive type in the JSON string but got `%s`", jsonObj.get("tag").toString()));
      }
      if (jsonObj.get("image_id") != null && !jsonObj.get("image_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `image_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("image_id").toString()));
      }
      if (jsonObj.get("v6_network") != null && !jsonObj.get("v6_network").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `v6_network` to be a primitive type in the JSON string but got `%s`", jsonObj.get("v6_network").toString()));
      }
      if (jsonObj.get("v6_main_ip") != null && !jsonObj.get("v6_main_ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `v6_main_ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("v6_main_ip").toString()));
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
       if (!Baremetal.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Baremetal' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Baremetal> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Baremetal.class));

       return (TypeAdapter<T>) new TypeAdapter<Baremetal>() {
           @Override
           public void write(JsonWriter out, Baremetal value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Baremetal read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Baremetal given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Baremetal
  * @throws IOException if the JSON string is invalid with respect to Baremetal
  */
  public static Baremetal fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Baremetal.class);
  }

 /**
  * Convert an instance of Baremetal to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

