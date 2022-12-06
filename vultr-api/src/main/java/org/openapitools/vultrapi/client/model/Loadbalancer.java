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
import org.openapitools.vultrapi.client.model.LoadbalancerFirewallRulesInner;
import org.openapitools.vultrapi.client.model.LoadbalancerForwardRulesInner;
import org.openapitools.vultrapi.client.model.LoadbalancerGenericInfo;
import org.openapitools.vultrapi.client.model.LoadbalancerHealthCheck;

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
 * Load Balancer information.
 */
@ApiModel(description = "Load Balancer information.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class Loadbalancer {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_DATE_CREATED = "date_created";
  @SerializedName(SERIALIZED_NAME_DATE_CREATED)
  private String dateCreated;

  public static final String SERIALIZED_NAME_REGION = "region";
  @SerializedName(SERIALIZED_NAME_REGION)
  private String region;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_IPV4 = "ipv4";
  @SerializedName(SERIALIZED_NAME_IPV4)
  private String ipv4;

  public static final String SERIALIZED_NAME_IPV6 = "ipv6";
  @SerializedName(SERIALIZED_NAME_IPV6)
  private String ipv6;

  public static final String SERIALIZED_NAME_GENERIC_INFO = "generic_info";
  @SerializedName(SERIALIZED_NAME_GENERIC_INFO)
  private LoadbalancerGenericInfo genericInfo;

  public static final String SERIALIZED_NAME_HEALTH_CHECK = "health_check";
  @SerializedName(SERIALIZED_NAME_HEALTH_CHECK)
  private LoadbalancerHealthCheck healthCheck;

  public static final String SERIALIZED_NAME_HAS_SSL = "has_ssl";
  @SerializedName(SERIALIZED_NAME_HAS_SSL)
  private Boolean hasSsl;

  public static final String SERIALIZED_NAME_FORWARD_RULES = "forward_rules";
  @SerializedName(SERIALIZED_NAME_FORWARD_RULES)
  private List<LoadbalancerForwardRulesInner> forwardRules = null;

  public static final String SERIALIZED_NAME_INSTANCES = "instances";
  @SerializedName(SERIALIZED_NAME_INSTANCES)
  private List<String> instances = null;

  public static final String SERIALIZED_NAME_FIREWALL_RULES = "firewall_rules";
  @SerializedName(SERIALIZED_NAME_FIREWALL_RULES)
  private List<LoadbalancerFirewallRulesInner> firewallRules = null;

  public Loadbalancer() { 
  }

  public Loadbalancer id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * A unique ID for the Load Balancer.
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A unique ID for the Load Balancer.")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public Loadbalancer dateCreated(String dateCreated) {
    
    this.dateCreated = dateCreated;
    return this;
  }

   /**
   * Date this Load Balancer was created.
   * @return dateCreated
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Date this Load Balancer was created.")

  public String getDateCreated() {
    return dateCreated;
  }


  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }


  public Loadbalancer region(String region) {
    
    this.region = region;
    return this;
  }

   /**
   * The [Region id](#operation/list-regions) where the Load Balancer is located.
   * @return region
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Region id](#operation/list-regions) where the Load Balancer is located.")

  public String getRegion() {
    return region;
  }


  public void setRegion(String region) {
    this.region = region;
  }


  public Loadbalancer label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * The user-supplied label for this load-balancer.
   * @return label
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The user-supplied label for this load-balancer.")

  public String getLabel() {
    return label;
  }


  public void setLabel(String label) {
    this.label = label;
  }


  public Loadbalancer status(String status) {
    
    this.status = status;
    return this;
  }

   /**
   * The current status.  * active
   * @return status
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The current status.  * active")

  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = status;
  }


  public Loadbalancer ipv4(String ipv4) {
    
    this.ipv4 = ipv4;
    return this;
  }

   /**
   * The IPv4 address of this Load Balancer.
   * @return ipv4
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IPv4 address of this Load Balancer.")

  public String getIpv4() {
    return ipv4;
  }


  public void setIpv4(String ipv4) {
    this.ipv4 = ipv4;
  }


  public Loadbalancer ipv6(String ipv6) {
    
    this.ipv6 = ipv6;
    return this;
  }

   /**
   * The IPv6 address of this Load Balancer.
   * @return ipv6
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The IPv6 address of this Load Balancer.")

  public String getIpv6() {
    return ipv6;
  }


  public void setIpv6(String ipv6) {
    this.ipv6 = ipv6;
  }


  public Loadbalancer genericInfo(LoadbalancerGenericInfo genericInfo) {
    
    this.genericInfo = genericInfo;
    return this;
  }

   /**
   * Get genericInfo
   * @return genericInfo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LoadbalancerGenericInfo getGenericInfo() {
    return genericInfo;
  }


  public void setGenericInfo(LoadbalancerGenericInfo genericInfo) {
    this.genericInfo = genericInfo;
  }


  public Loadbalancer healthCheck(LoadbalancerHealthCheck healthCheck) {
    
    this.healthCheck = healthCheck;
    return this;
  }

   /**
   * Get healthCheck
   * @return healthCheck
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LoadbalancerHealthCheck getHealthCheck() {
    return healthCheck;
  }


  public void setHealthCheck(LoadbalancerHealthCheck healthCheck) {
    this.healthCheck = healthCheck;
  }


  public Loadbalancer hasSsl(Boolean hasSsl) {
    
    this.hasSsl = hasSsl;
    return this;
  }

   /**
   * Indicates if this Load Balancer has an SSL certificate installed.
   * @return hasSsl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Indicates if this Load Balancer has an SSL certificate installed.")

  public Boolean getHasSsl() {
    return hasSsl;
  }


  public void setHasSsl(Boolean hasSsl) {
    this.hasSsl = hasSsl;
  }


  public Loadbalancer forwardRules(List<LoadbalancerForwardRulesInner> forwardRules) {
    
    this.forwardRules = forwardRules;
    return this;
  }

  public Loadbalancer addForwardRulesItem(LoadbalancerForwardRulesInner forwardRulesItem) {
    if (this.forwardRules == null) {
      this.forwardRules = new ArrayList<>();
    }
    this.forwardRules.add(forwardRulesItem);
    return this;
  }

   /**
   * An array of forwarding rule objects.
   * @return forwardRules
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An array of forwarding rule objects.")

  public List<LoadbalancerForwardRulesInner> getForwardRules() {
    return forwardRules;
  }


  public void setForwardRules(List<LoadbalancerForwardRulesInner> forwardRules) {
    this.forwardRules = forwardRules;
  }


  public Loadbalancer instances(List<String> instances) {
    
    this.instances = instances;
    return this;
  }

  public Loadbalancer addInstancesItem(String instancesItem) {
    if (this.instances == null) {
      this.instances = new ArrayList<>();
    }
    this.instances.add(instancesItem);
    return this;
  }

   /**
   * Array of [Instance ids](#operation/list-instances) attached to this Load Balancer.
   * @return instances
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Array of [Instance ids](#operation/list-instances) attached to this Load Balancer.")

  public List<String> getInstances() {
    return instances;
  }


  public void setInstances(List<String> instances) {
    this.instances = instances;
  }


  public Loadbalancer firewallRules(List<LoadbalancerFirewallRulesInner> firewallRules) {
    
    this.firewallRules = firewallRules;
    return this;
  }

  public Loadbalancer addFirewallRulesItem(LoadbalancerFirewallRulesInner firewallRulesItem) {
    if (this.firewallRules == null) {
      this.firewallRules = new ArrayList<>();
    }
    this.firewallRules.add(firewallRulesItem);
    return this;
  }

   /**
   * An array of firewall rule objects.
   * @return firewallRules
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An array of firewall rule objects.")

  public List<LoadbalancerFirewallRulesInner> getFirewallRules() {
    return firewallRules;
  }


  public void setFirewallRules(List<LoadbalancerFirewallRulesInner> firewallRules) {
    this.firewallRules = firewallRules;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Loadbalancer loadbalancer = (Loadbalancer) o;
    return Objects.equals(this.id, loadbalancer.id) &&
        Objects.equals(this.dateCreated, loadbalancer.dateCreated) &&
        Objects.equals(this.region, loadbalancer.region) &&
        Objects.equals(this.label, loadbalancer.label) &&
        Objects.equals(this.status, loadbalancer.status) &&
        Objects.equals(this.ipv4, loadbalancer.ipv4) &&
        Objects.equals(this.ipv6, loadbalancer.ipv6) &&
        Objects.equals(this.genericInfo, loadbalancer.genericInfo) &&
        Objects.equals(this.healthCheck, loadbalancer.healthCheck) &&
        Objects.equals(this.hasSsl, loadbalancer.hasSsl) &&
        Objects.equals(this.forwardRules, loadbalancer.forwardRules) &&
        Objects.equals(this.instances, loadbalancer.instances) &&
        Objects.equals(this.firewallRules, loadbalancer.firewallRules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dateCreated, region, label, status, ipv4, ipv6, genericInfo, healthCheck, hasSsl, forwardRules, instances, firewallRules);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Loadbalancer {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dateCreated: ").append(toIndentedString(dateCreated)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    ipv4: ").append(toIndentedString(ipv4)).append("\n");
    sb.append("    ipv6: ").append(toIndentedString(ipv6)).append("\n");
    sb.append("    genericInfo: ").append(toIndentedString(genericInfo)).append("\n");
    sb.append("    healthCheck: ").append(toIndentedString(healthCheck)).append("\n");
    sb.append("    hasSsl: ").append(toIndentedString(hasSsl)).append("\n");
    sb.append("    forwardRules: ").append(toIndentedString(forwardRules)).append("\n");
    sb.append("    instances: ").append(toIndentedString(instances)).append("\n");
    sb.append("    firewallRules: ").append(toIndentedString(firewallRules)).append("\n");
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
    openapiFields.add("date_created");
    openapiFields.add("region");
    openapiFields.add("label");
    openapiFields.add("status");
    openapiFields.add("ipv4");
    openapiFields.add("ipv6");
    openapiFields.add("generic_info");
    openapiFields.add("health_check");
    openapiFields.add("has_ssl");
    openapiFields.add("forward_rules");
    openapiFields.add("instances");
    openapiFields.add("firewall_rules");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Loadbalancer
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Loadbalancer.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Loadbalancer is not found in the empty JSON string", Loadbalancer.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Loadbalancer.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Loadbalancer` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("id") != null && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if (jsonObj.get("date_created") != null && !jsonObj.get("date_created").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `date_created` to be a primitive type in the JSON string but got `%s`", jsonObj.get("date_created").toString()));
      }
      if (jsonObj.get("region") != null && !jsonObj.get("region").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `region` to be a primitive type in the JSON string but got `%s`", jsonObj.get("region").toString()));
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (jsonObj.get("ipv4") != null && !jsonObj.get("ipv4").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ipv4` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ipv4").toString()));
      }
      if (jsonObj.get("ipv6") != null && !jsonObj.get("ipv6").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ipv6` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ipv6").toString()));
      }
      // validate the optional field `generic_info`
      if (jsonObj.getAsJsonObject("generic_info") != null) {
        LoadbalancerGenericInfo.validateJsonObject(jsonObj.getAsJsonObject("generic_info"));
      }
      // validate the optional field `health_check`
      if (jsonObj.getAsJsonObject("health_check") != null) {
        LoadbalancerHealthCheck.validateJsonObject(jsonObj.getAsJsonObject("health_check"));
      }
      JsonArray jsonArrayforwardRules = jsonObj.getAsJsonArray("forward_rules");
      if (jsonArrayforwardRules != null) {
        // ensure the json data is an array
        if (!jsonObj.get("forward_rules").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `forward_rules` to be an array in the JSON string but got `%s`", jsonObj.get("forward_rules").toString()));
        }

        // validate the optional field `forward_rules` (array)
        for (int i = 0; i < jsonArrayforwardRules.size(); i++) {
          LoadbalancerForwardRulesInner.validateJsonObject(jsonArrayforwardRules.get(i).getAsJsonObject());
        };
      }
      // ensure the json data is an array
      if (jsonObj.get("instances") != null && !jsonObj.get("instances").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `instances` to be an array in the JSON string but got `%s`", jsonObj.get("instances").toString()));
      }
      JsonArray jsonArrayfirewallRules = jsonObj.getAsJsonArray("firewall_rules");
      if (jsonArrayfirewallRules != null) {
        // ensure the json data is an array
        if (!jsonObj.get("firewall_rules").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `firewall_rules` to be an array in the JSON string but got `%s`", jsonObj.get("firewall_rules").toString()));
        }

        // validate the optional field `firewall_rules` (array)
        for (int i = 0; i < jsonArrayfirewallRules.size(); i++) {
          LoadbalancerFirewallRulesInner.validateJsonObject(jsonArrayfirewallRules.get(i).getAsJsonObject());
        };
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Loadbalancer.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Loadbalancer' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Loadbalancer> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Loadbalancer.class));

       return (TypeAdapter<T>) new TypeAdapter<Loadbalancer>() {
           @Override
           public void write(JsonWriter out, Loadbalancer value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Loadbalancer read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Loadbalancer given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Loadbalancer
  * @throws IOException if the JSON string is invalid with respect to Loadbalancer
  */
  public static Loadbalancer fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Loadbalancer.class);
  }

 /**
  * Convert an instance of Loadbalancer to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

