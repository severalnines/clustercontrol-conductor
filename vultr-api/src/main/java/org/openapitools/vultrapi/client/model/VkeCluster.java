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
import org.openapitools.vultrapi.client.model.Nodepools;

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
 * VKE Cluster
 */
@ApiModel(description = "VKE Cluster")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class VkeCluster {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_DATE_CREATED = "date_created";
  @SerializedName(SERIALIZED_NAME_DATE_CREATED)
  private String dateCreated;

  public static final String SERIALIZED_NAME_CLUSTER_SUBNET = "cluster_subnet";
  @SerializedName(SERIALIZED_NAME_CLUSTER_SUBNET)
  private String clusterSubnet;

  public static final String SERIALIZED_NAME_SERVICE_SUBNET = "service_subnet";
  @SerializedName(SERIALIZED_NAME_SERVICE_SUBNET)
  private String serviceSubnet;

  public static final String SERIALIZED_NAME_IP = "ip";
  @SerializedName(SERIALIZED_NAME_IP)
  private String ip;

  public static final String SERIALIZED_NAME_ENDPOINT = "endpoint";
  @SerializedName(SERIALIZED_NAME_ENDPOINT)
  private String endpoint;

  public static final String SERIALIZED_NAME_VERSION = "version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private String version;

  public static final String SERIALIZED_NAME_REGION = "region";
  @SerializedName(SERIALIZED_NAME_REGION)
  private String region;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_NODE_POOLS = "node_pools";
  @SerializedName(SERIALIZED_NAME_NODE_POOLS)
  private List<Nodepools> nodePools = null;

  public VkeCluster() { 
  }

  public VkeCluster id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID for the VKE cluster
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "ID for the VKE cluster")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public VkeCluster label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * Label for your cluster
   * @return label
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Label for your cluster")

  public String getLabel() {
    return label;
  }


  public void setLabel(String label) {
    this.label = label;
  }


  public VkeCluster dateCreated(String dateCreated) {
    
    this.dateCreated = dateCreated;
    return this;
  }

   /**
   * Date of creation
   * @return dateCreated
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Date of creation")

  public String getDateCreated() {
    return dateCreated;
  }


  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }


  public VkeCluster clusterSubnet(String clusterSubnet) {
    
    this.clusterSubnet = clusterSubnet;
    return this;
  }

   /**
   * IP range that your pods will run on in this cluster
   * @return clusterSubnet
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "IP range that your pods will run on in this cluster")

  public String getClusterSubnet() {
    return clusterSubnet;
  }


  public void setClusterSubnet(String clusterSubnet) {
    this.clusterSubnet = clusterSubnet;
  }


  public VkeCluster serviceSubnet(String serviceSubnet) {
    
    this.serviceSubnet = serviceSubnet;
    return this;
  }

   /**
   * IP range that services will run on this cluster
   * @return serviceSubnet
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "IP range that services will run on this cluster")

  public String getServiceSubnet() {
    return serviceSubnet;
  }


  public void setServiceSubnet(String serviceSubnet) {
    this.serviceSubnet = serviceSubnet;
  }


  public VkeCluster ip(String ip) {
    
    this.ip = ip;
    return this;
  }

   /**
   * IP for your Kubernetes Clusters Control Plane
   * @return ip
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "IP for your Kubernetes Clusters Control Plane")

  public String getIp() {
    return ip;
  }


  public void setIp(String ip) {
    this.ip = ip;
  }


  public VkeCluster endpoint(String endpoint) {
    
    this.endpoint = endpoint;
    return this;
  }

   /**
   * Domain for your Kubernetes Clusters Control Plane
   * @return endpoint
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Domain for your Kubernetes Clusters Control Plane")

  public String getEndpoint() {
    return endpoint;
  }


  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }


  public VkeCluster version(String version) {
    
    this.version = version;
    return this;
  }

   /**
   * Version of Kubernetes this cluster is running on
   * @return version
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Version of Kubernetes this cluster is running on")

  public String getVersion() {
    return version;
  }


  public void setVersion(String version) {
    this.version = version;
  }


  public VkeCluster region(String region) {
    
    this.region = region;
    return this;
  }

   /**
   * Region this Kubernetes Cluster is running in
   * @return region
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Region this Kubernetes Cluster is running in")

  public String getRegion() {
    return region;
  }


  public void setRegion(String region) {
    this.region = region;
  }


  public VkeCluster status(String status) {
    
    this.status = status;
    return this;
  }

   /**
   * Status for VKE cluster
   * @return status
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Status for VKE cluster")

  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = status;
  }


  public VkeCluster nodePools(List<Nodepools> nodePools) {
    
    this.nodePools = nodePools;
    return this;
  }

  public VkeCluster addNodePoolsItem(Nodepools nodePoolsItem) {
    if (this.nodePools == null) {
      this.nodePools = new ArrayList<>();
    }
    this.nodePools.add(nodePoolsItem);
    return this;
  }

   /**
   * NodePools in this cluster
   * @return nodePools
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "NodePools in this cluster")

  public List<Nodepools> getNodePools() {
    return nodePools;
  }


  public void setNodePools(List<Nodepools> nodePools) {
    this.nodePools = nodePools;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VkeCluster vkeCluster = (VkeCluster) o;
    return Objects.equals(this.id, vkeCluster.id) &&
        Objects.equals(this.label, vkeCluster.label) &&
        Objects.equals(this.dateCreated, vkeCluster.dateCreated) &&
        Objects.equals(this.clusterSubnet, vkeCluster.clusterSubnet) &&
        Objects.equals(this.serviceSubnet, vkeCluster.serviceSubnet) &&
        Objects.equals(this.ip, vkeCluster.ip) &&
        Objects.equals(this.endpoint, vkeCluster.endpoint) &&
        Objects.equals(this.version, vkeCluster.version) &&
        Objects.equals(this.region, vkeCluster.region) &&
        Objects.equals(this.status, vkeCluster.status) &&
        Objects.equals(this.nodePools, vkeCluster.nodePools);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, dateCreated, clusterSubnet, serviceSubnet, ip, endpoint, version, region, status, nodePools);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VkeCluster {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    dateCreated: ").append(toIndentedString(dateCreated)).append("\n");
    sb.append("    clusterSubnet: ").append(toIndentedString(clusterSubnet)).append("\n");
    sb.append("    serviceSubnet: ").append(toIndentedString(serviceSubnet)).append("\n");
    sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
    sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    nodePools: ").append(toIndentedString(nodePools)).append("\n");
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
    openapiFields.add("label");
    openapiFields.add("date_created");
    openapiFields.add("cluster_subnet");
    openapiFields.add("service_subnet");
    openapiFields.add("ip");
    openapiFields.add("endpoint");
    openapiFields.add("version");
    openapiFields.add("region");
    openapiFields.add("status");
    openapiFields.add("node_pools");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to VkeCluster
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (VkeCluster.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in VkeCluster is not found in the empty JSON string", VkeCluster.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!VkeCluster.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `VkeCluster` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("id") != null && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      if (jsonObj.get("date_created") != null && !jsonObj.get("date_created").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `date_created` to be a primitive type in the JSON string but got `%s`", jsonObj.get("date_created").toString()));
      }
      if (jsonObj.get("cluster_subnet") != null && !jsonObj.get("cluster_subnet").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cluster_subnet` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cluster_subnet").toString()));
      }
      if (jsonObj.get("service_subnet") != null && !jsonObj.get("service_subnet").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `service_subnet` to be a primitive type in the JSON string but got `%s`", jsonObj.get("service_subnet").toString()));
      }
      if (jsonObj.get("ip") != null && !jsonObj.get("ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ip").toString()));
      }
      if (jsonObj.get("endpoint") != null && !jsonObj.get("endpoint").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `endpoint` to be a primitive type in the JSON string but got `%s`", jsonObj.get("endpoint").toString()));
      }
      if (jsonObj.get("version") != null && !jsonObj.get("version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("version").toString()));
      }
      if (jsonObj.get("region") != null && !jsonObj.get("region").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `region` to be a primitive type in the JSON string but got `%s`", jsonObj.get("region").toString()));
      }
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      JsonArray jsonArraynodePools = jsonObj.getAsJsonArray("node_pools");
      if (jsonArraynodePools != null) {
        // ensure the json data is an array
        if (!jsonObj.get("node_pools").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `node_pools` to be an array in the JSON string but got `%s`", jsonObj.get("node_pools").toString()));
        }

        // validate the optional field `node_pools` (array)
        for (int i = 0; i < jsonArraynodePools.size(); i++) {
          Nodepools.validateJsonObject(jsonArraynodePools.get(i).getAsJsonObject());
        };
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!VkeCluster.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'VkeCluster' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<VkeCluster> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(VkeCluster.class));

       return (TypeAdapter<T>) new TypeAdapter<VkeCluster>() {
           @Override
           public void write(JsonWriter out, VkeCluster value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public VkeCluster read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of VkeCluster given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of VkeCluster
  * @throws IOException if the JSON string is invalid with respect to VkeCluster
  */
  public static VkeCluster fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, VkeCluster.class);
  }

 /**
  * Convert an instance of VkeCluster to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

