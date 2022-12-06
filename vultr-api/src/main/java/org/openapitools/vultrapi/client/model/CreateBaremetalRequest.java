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
 * CreateBaremetalRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class CreateBaremetalRequest {
  public static final String SERIALIZED_NAME_REGION = "region";
  @SerializedName(SERIALIZED_NAME_REGION)
  private String region;

  public static final String SERIALIZED_NAME_PLAN = "plan";
  @SerializedName(SERIALIZED_NAME_PLAN)
  private String plan;

  public static final String SERIALIZED_NAME_SCRIPT_ID = "script_id";
  @SerializedName(SERIALIZED_NAME_SCRIPT_ID)
  private String scriptId;

  public static final String SERIALIZED_NAME_ENABLE_IPV6 = "enable_ipv6";
  @SerializedName(SERIALIZED_NAME_ENABLE_IPV6)
  private Boolean enableIpv6;

  public static final String SERIALIZED_NAME_SSHKEY_ID = "sshkey_id";
  @SerializedName(SERIALIZED_NAME_SSHKEY_ID)
  private List<String> sshkeyId = null;

  public static final String SERIALIZED_NAME_USER_DATA = "user_data";
  @SerializedName(SERIALIZED_NAME_USER_DATA)
  private String userData;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_ACTIVATION_EMAIL = "activation_email";
  @SerializedName(SERIALIZED_NAME_ACTIVATION_EMAIL)
  private Boolean activationEmail;

  public static final String SERIALIZED_NAME_HOSTNAME = "hostname";
  @SerializedName(SERIALIZED_NAME_HOSTNAME)
  private String hostname;

  public static final String SERIALIZED_NAME_TAG = "tag";
  @SerializedName(SERIALIZED_NAME_TAG)
  private String tag;

  public static final String SERIALIZED_NAME_RESERVED_IPV4 = "reserved_ipv4";
  @SerializedName(SERIALIZED_NAME_RESERVED_IPV4)
  private String reservedIpv4;

  public static final String SERIALIZED_NAME_OS_ID = "os_id";
  @SerializedName(SERIALIZED_NAME_OS_ID)
  private Integer osId;

  public static final String SERIALIZED_NAME_SNAPSHOT_ID = "snapshot_id";
  @SerializedName(SERIALIZED_NAME_SNAPSHOT_ID)
  private String snapshotId;

  public static final String SERIALIZED_NAME_APP_ID = "app_id";
  @SerializedName(SERIALIZED_NAME_APP_ID)
  private Integer appId;

  public static final String SERIALIZED_NAME_IMAGE_ID = "image_id";
  @SerializedName(SERIALIZED_NAME_IMAGE_ID)
  private String imageId;

  public static final String SERIALIZED_NAME_PERSISTENT_PXE = "persistent_pxe";
  @SerializedName(SERIALIZED_NAME_PERSISTENT_PXE)
  private Boolean persistentPxe;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = null;

  public CreateBaremetalRequest() { 
  }

  public CreateBaremetalRequest region(String region) {
    
    this.region = region;
    return this;
  }

   /**
   * The [Region id](#operation/list-regions) to create the instance.
   * @return region
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "The [Region id](#operation/list-regions) to create the instance.")

  public String getRegion() {
    return region;
  }


  public void setRegion(String region) {
    this.region = region;
  }


  public CreateBaremetalRequest plan(String plan) {
    
    this.plan = plan;
    return this;
  }

   /**
   * The [Bare Metal plan id](#operation/list-metal-plans) to use for this instance.
   * @return plan
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "The [Bare Metal plan id](#operation/list-metal-plans) to use for this instance.")

  public String getPlan() {
    return plan;
  }


  public void setPlan(String plan) {
    this.plan = plan;
  }


  public CreateBaremetalRequest scriptId(String scriptId) {
    
    this.scriptId = scriptId;
    return this;
  }

   /**
   * The [Startup Script id](#operation/list-startup-scripts) to use for this instance.
   * @return scriptId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Startup Script id](#operation/list-startup-scripts) to use for this instance.")

  public String getScriptId() {
    return scriptId;
  }


  public void setScriptId(String scriptId) {
    this.scriptId = scriptId;
  }


  public CreateBaremetalRequest enableIpv6(Boolean enableIpv6) {
    
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


  public CreateBaremetalRequest sshkeyId(List<String> sshkeyId) {
    
    this.sshkeyId = sshkeyId;
    return this;
  }

  public CreateBaremetalRequest addSshkeyIdItem(String sshkeyIdItem) {
    if (this.sshkeyId == null) {
      this.sshkeyId = new ArrayList<>();
    }
    this.sshkeyId.add(sshkeyIdItem);
    return this;
  }

   /**
   * The [SSH Key id](#operation/list-ssh-keys) to install on this instance.
   * @return sshkeyId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [SSH Key id](#operation/list-ssh-keys) to install on this instance.")

  public List<String> getSshkeyId() {
    return sshkeyId;
  }


  public void setSshkeyId(List<String> sshkeyId) {
    this.sshkeyId = sshkeyId;
  }


  public CreateBaremetalRequest userData(String userData) {
    
    this.userData = userData;
    return this;
  }

   /**
   * The user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for this Instance.
   * @return userData
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user-supplied, base64 encoded [user data](https://www.vultr.com/docs/manage-instance-user-data-with-the-vultr-metadata-api) for this Instance.")

  public String getUserData() {
    return userData;
  }


  public void setUserData(String userData) {
    this.userData = userData;
  }


  public CreateBaremetalRequest label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * The user-supplied label.
   * @return label
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user-supplied label.")

  public String getLabel() {
    return label;
  }


  public void setLabel(String label) {
    this.label = label;
  }


  public CreateBaremetalRequest activationEmail(Boolean activationEmail) {
    
    this.activationEmail = activationEmail;
    return this;
  }

   /**
   * Notify by email after deployment.  * true * false (default)
   * @return activationEmail
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Notify by email after deployment.  * true * false (default)")

  public Boolean getActivationEmail() {
    return activationEmail;
  }


  public void setActivationEmail(Boolean activationEmail) {
    this.activationEmail = activationEmail;
  }


  public CreateBaremetalRequest hostname(String hostname) {
    
    this.hostname = hostname;
    return this;
  }

   /**
   * The user-supplied hostname to use when deploying this instance.
   * @return hostname
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user-supplied hostname to use when deploying this instance.")

  public String getHostname() {
    return hostname;
  }


  public void setHostname(String hostname) {
    this.hostname = hostname;
  }


  public CreateBaremetalRequest tag(String tag) {
    
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


  public CreateBaremetalRequest reservedIpv4(String reservedIpv4) {
    
    this.reservedIpv4 = reservedIpv4;
    return this;
  }

   /**
   * The [Reserved IP id](#operation/list-reserved-ips) for this instance.
   * @return reservedIpv4
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Reserved IP id](#operation/list-reserved-ips) for this instance.")

  public String getReservedIpv4() {
    return reservedIpv4;
  }


  public void setReservedIpv4(String reservedIpv4) {
    this.reservedIpv4 = reservedIpv4;
  }


  public CreateBaremetalRequest osId(Integer osId) {
    
    this.osId = osId;
    return this;
  }

   /**
   * If supplied, deploy the instance using this [Operating System id](#operation/list-os).
   * @return osId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "If supplied, deploy the instance using this [Operating System id](#operation/list-os).")

  public Integer getOsId() {
    return osId;
  }


  public void setOsId(Integer osId) {
    this.osId = osId;
  }


  public CreateBaremetalRequest snapshotId(String snapshotId) {
    
    this.snapshotId = snapshotId;
    return this;
  }

   /**
   * If supplied, deploy the instance using this [Snapshot ID](#operation/list-snapshots).
   * @return snapshotId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "If supplied, deploy the instance using this [Snapshot ID](#operation/list-snapshots).")

  public String getSnapshotId() {
    return snapshotId;
  }


  public void setSnapshotId(String snapshotId) {
    this.snapshotId = snapshotId;
  }


  public CreateBaremetalRequest appId(Integer appId) {
    
    this.appId = appId;
    return this;
  }

   /**
   * If supplied, deploy the instance using this [Application id](#operation/list-applications).
   * @return appId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "If supplied, deploy the instance using this [Application id](#operation/list-applications).")

  public Integer getAppId() {
    return appId;
  }


  public void setAppId(Integer appId) {
    this.appId = appId;
  }


  public CreateBaremetalRequest imageId(String imageId) {
    
    this.imageId = imageId;
    return this;
  }

   /**
   * If supplied, deploy the instance using this [Application image_id](#operation/list-applications).
   * @return imageId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "If supplied, deploy the instance using this [Application image_id](#operation/list-applications).")

  public String getImageId() {
    return imageId;
  }


  public void setImageId(String imageId) {
    this.imageId = imageId;
  }


  public CreateBaremetalRequest persistentPxe(Boolean persistentPxe) {
    
    this.persistentPxe = persistentPxe;
    return this;
  }

   /**
   * Enable persistent PXE.  * true * false (default)
   * @return persistentPxe
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Enable persistent PXE.  * true * false (default)")

  public Boolean getPersistentPxe() {
    return persistentPxe;
  }


  public void setPersistentPxe(Boolean persistentPxe) {
    this.persistentPxe = persistentPxe;
  }


  public CreateBaremetalRequest tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public CreateBaremetalRequest addTagsItem(String tagsItem) {
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
    CreateBaremetalRequest createBaremetalRequest = (CreateBaremetalRequest) o;
    return Objects.equals(this.region, createBaremetalRequest.region) &&
        Objects.equals(this.plan, createBaremetalRequest.plan) &&
        Objects.equals(this.scriptId, createBaremetalRequest.scriptId) &&
        Objects.equals(this.enableIpv6, createBaremetalRequest.enableIpv6) &&
        Objects.equals(this.sshkeyId, createBaremetalRequest.sshkeyId) &&
        Objects.equals(this.userData, createBaremetalRequest.userData) &&
        Objects.equals(this.label, createBaremetalRequest.label) &&
        Objects.equals(this.activationEmail, createBaremetalRequest.activationEmail) &&
        Objects.equals(this.hostname, createBaremetalRequest.hostname) &&
        Objects.equals(this.tag, createBaremetalRequest.tag) &&
        Objects.equals(this.reservedIpv4, createBaremetalRequest.reservedIpv4) &&
        Objects.equals(this.osId, createBaremetalRequest.osId) &&
        Objects.equals(this.snapshotId, createBaremetalRequest.snapshotId) &&
        Objects.equals(this.appId, createBaremetalRequest.appId) &&
        Objects.equals(this.imageId, createBaremetalRequest.imageId) &&
        Objects.equals(this.persistentPxe, createBaremetalRequest.persistentPxe) &&
        Objects.equals(this.tags, createBaremetalRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, plan, scriptId, enableIpv6, sshkeyId, userData, label, activationEmail, hostname, tag, reservedIpv4, osId, snapshotId, appId, imageId, persistentPxe, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateBaremetalRequest {\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
    sb.append("    scriptId: ").append(toIndentedString(scriptId)).append("\n");
    sb.append("    enableIpv6: ").append(toIndentedString(enableIpv6)).append("\n");
    sb.append("    sshkeyId: ").append(toIndentedString(sshkeyId)).append("\n");
    sb.append("    userData: ").append(toIndentedString(userData)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    activationEmail: ").append(toIndentedString(activationEmail)).append("\n");
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    reservedIpv4: ").append(toIndentedString(reservedIpv4)).append("\n");
    sb.append("    osId: ").append(toIndentedString(osId)).append("\n");
    sb.append("    snapshotId: ").append(toIndentedString(snapshotId)).append("\n");
    sb.append("    appId: ").append(toIndentedString(appId)).append("\n");
    sb.append("    imageId: ").append(toIndentedString(imageId)).append("\n");
    sb.append("    persistentPxe: ").append(toIndentedString(persistentPxe)).append("\n");
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
    openapiFields.add("region");
    openapiFields.add("plan");
    openapiFields.add("script_id");
    openapiFields.add("enable_ipv6");
    openapiFields.add("sshkey_id");
    openapiFields.add("user_data");
    openapiFields.add("label");
    openapiFields.add("activation_email");
    openapiFields.add("hostname");
    openapiFields.add("tag");
    openapiFields.add("reserved_ipv4");
    openapiFields.add("os_id");
    openapiFields.add("snapshot_id");
    openapiFields.add("app_id");
    openapiFields.add("image_id");
    openapiFields.add("persistent_pxe");
    openapiFields.add("tags");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("region");
    openapiRequiredFields.add("plan");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to CreateBaremetalRequest
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (CreateBaremetalRequest.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateBaremetalRequest is not found in the empty JSON string", CreateBaremetalRequest.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateBaremetalRequest.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateBaremetalRequest` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateBaremetalRequest.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (jsonObj.get("region") != null && !jsonObj.get("region").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `region` to be a primitive type in the JSON string but got `%s`", jsonObj.get("region").toString()));
      }
      if (jsonObj.get("plan") != null && !jsonObj.get("plan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `plan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("plan").toString()));
      }
      if (jsonObj.get("script_id") != null && !jsonObj.get("script_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `script_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("script_id").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("sshkey_id") != null && !jsonObj.get("sshkey_id").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `sshkey_id` to be an array in the JSON string but got `%s`", jsonObj.get("sshkey_id").toString()));
      }
      if (jsonObj.get("user_data") != null && !jsonObj.get("user_data").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `user_data` to be a primitive type in the JSON string but got `%s`", jsonObj.get("user_data").toString()));
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      if (jsonObj.get("hostname") != null && !jsonObj.get("hostname").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hostname` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hostname").toString()));
      }
      if (jsonObj.get("tag") != null && !jsonObj.get("tag").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `tag` to be a primitive type in the JSON string but got `%s`", jsonObj.get("tag").toString()));
      }
      if (jsonObj.get("reserved_ipv4") != null && !jsonObj.get("reserved_ipv4").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `reserved_ipv4` to be a primitive type in the JSON string but got `%s`", jsonObj.get("reserved_ipv4").toString()));
      }
      if (jsonObj.get("snapshot_id") != null && !jsonObj.get("snapshot_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `snapshot_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("snapshot_id").toString()));
      }
      if (jsonObj.get("image_id") != null && !jsonObj.get("image_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `image_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("image_id").toString()));
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
       if (!CreateBaremetalRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateBaremetalRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateBaremetalRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateBaremetalRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateBaremetalRequest>() {
           @Override
           public void write(JsonWriter out, CreateBaremetalRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateBaremetalRequest read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateBaremetalRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateBaremetalRequest
  * @throws IOException if the JSON string is invalid with respect to CreateBaremetalRequest
  */
  public static CreateBaremetalRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateBaremetalRequest.class);
  }

 /**
  * Convert an instance of CreateBaremetalRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

