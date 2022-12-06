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
 * CreateNodepoolsRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class CreateNodepoolsRequest {
  public static final String SERIALIZED_NAME_NODE_QUANTITY = "node_quantity";
  @SerializedName(SERIALIZED_NAME_NODE_QUANTITY)
  private Integer nodeQuantity;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_PLAN = "plan";
  @SerializedName(SERIALIZED_NAME_PLAN)
  private String plan;

  public static final String SERIALIZED_NAME_TAG = "tag";
  @SerializedName(SERIALIZED_NAME_TAG)
  private String tag;

  public static final String SERIALIZED_NAME_AUTO_SCALER = "auto_scaler";
  @SerializedName(SERIALIZED_NAME_AUTO_SCALER)
  private Boolean autoScaler;

  public static final String SERIALIZED_NAME_MIN_NODES = "min_nodes";
  @SerializedName(SERIALIZED_NAME_MIN_NODES)
  private Integer minNodes;

  public static final String SERIALIZED_NAME_MAX_NODES = "max_nodes";
  @SerializedName(SERIALIZED_NAME_MAX_NODES)
  private Integer maxNodes;

  public CreateNodepoolsRequest() { 
  }

  public CreateNodepoolsRequest nodeQuantity(Integer nodeQuantity) {
    
    this.nodeQuantity = nodeQuantity;
    return this;
  }

   /**
   * Number of instances in this nodepool
   * @return nodeQuantity
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Number of instances in this nodepool")

  public Integer getNodeQuantity() {
    return nodeQuantity;
  }


  public void setNodeQuantity(Integer nodeQuantity) {
    this.nodeQuantity = nodeQuantity;
  }


  public CreateNodepoolsRequest label(String label) {
    
    this.label = label;
    return this;
  }

   /**
   * Label for the nodepool. You cannot change the label after a nodepool is created. You cannot have duplicate node pool labels in the same cluster.
   * @return label
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Label for the nodepool. You cannot change the label after a nodepool is created. You cannot have duplicate node pool labels in the same cluster.")

  public String getLabel() {
    return label;
  }


  public void setLabel(String label) {
    this.label = label;
  }


  public CreateNodepoolsRequest plan(String plan) {
    
    this.plan = plan;
    return this;
  }

   /**
   * Plan that this nodepool will use
   * @return plan
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Plan that this nodepool will use")

  public String getPlan() {
    return plan;
  }


  public void setPlan(String plan) {
    this.plan = plan;
  }


  public CreateNodepoolsRequest tag(String tag) {
    
    this.tag = tag;
    return this;
  }

   /**
   * Tag for node pool
   * @return tag
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Tag for node pool")

  public String getTag() {
    return tag;
  }


  public void setTag(String tag) {
    this.tag = tag;
  }


  public CreateNodepoolsRequest autoScaler(Boolean autoScaler) {
    
    this.autoScaler = autoScaler;
    return this;
  }

   /**
   * Option to use the auto scaler with your cluster. Default false.
   * @return autoScaler
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Option to use the auto scaler with your cluster. Default false.")

  public Boolean getAutoScaler() {
    return autoScaler;
  }


  public void setAutoScaler(Boolean autoScaler) {
    this.autoScaler = autoScaler;
  }


  public CreateNodepoolsRequest minNodes(Integer minNodes) {
    
    this.minNodes = minNodes;
    return this;
  }

   /**
   * Auto scaler field for minimum nodes you want for your cluster. Default 1.
   * @return minNodes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Auto scaler field for minimum nodes you want for your cluster. Default 1.")

  public Integer getMinNodes() {
    return minNodes;
  }


  public void setMinNodes(Integer minNodes) {
    this.minNodes = minNodes;
  }


  public CreateNodepoolsRequest maxNodes(Integer maxNodes) {
    
    this.maxNodes = maxNodes;
    return this;
  }

   /**
   * Auto scaler field for maximum nodes you want for your cluster. Default 1.
   * @return maxNodes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Auto scaler field for maximum nodes you want for your cluster. Default 1.")

  public Integer getMaxNodes() {
    return maxNodes;
  }


  public void setMaxNodes(Integer maxNodes) {
    this.maxNodes = maxNodes;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateNodepoolsRequest createNodepoolsRequest = (CreateNodepoolsRequest) o;
    return Objects.equals(this.nodeQuantity, createNodepoolsRequest.nodeQuantity) &&
        Objects.equals(this.label, createNodepoolsRequest.label) &&
        Objects.equals(this.plan, createNodepoolsRequest.plan) &&
        Objects.equals(this.tag, createNodepoolsRequest.tag) &&
        Objects.equals(this.autoScaler, createNodepoolsRequest.autoScaler) &&
        Objects.equals(this.minNodes, createNodepoolsRequest.minNodes) &&
        Objects.equals(this.maxNodes, createNodepoolsRequest.maxNodes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeQuantity, label, plan, tag, autoScaler, minNodes, maxNodes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNodepoolsRequest {\n");
    sb.append("    nodeQuantity: ").append(toIndentedString(nodeQuantity)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
    sb.append("    autoScaler: ").append(toIndentedString(autoScaler)).append("\n");
    sb.append("    minNodes: ").append(toIndentedString(minNodes)).append("\n");
    sb.append("    maxNodes: ").append(toIndentedString(maxNodes)).append("\n");
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
    openapiFields.add("node_quantity");
    openapiFields.add("label");
    openapiFields.add("plan");
    openapiFields.add("tag");
    openapiFields.add("auto_scaler");
    openapiFields.add("min_nodes");
    openapiFields.add("max_nodes");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("node_quantity");
    openapiRequiredFields.add("label");
    openapiRequiredFields.add("plan");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to CreateNodepoolsRequest
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (CreateNodepoolsRequest.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateNodepoolsRequest is not found in the empty JSON string", CreateNodepoolsRequest.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateNodepoolsRequest.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateNodepoolsRequest` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateNodepoolsRequest.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      if (jsonObj.get("plan") != null && !jsonObj.get("plan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `plan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("plan").toString()));
      }
      if (jsonObj.get("tag") != null && !jsonObj.get("tag").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `tag` to be a primitive type in the JSON string but got `%s`", jsonObj.get("tag").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateNodepoolsRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateNodepoolsRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateNodepoolsRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateNodepoolsRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateNodepoolsRequest>() {
           @Override
           public void write(JsonWriter out, CreateNodepoolsRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateNodepoolsRequest read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateNodepoolsRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateNodepoolsRequest
  * @throws IOException if the JSON string is invalid with respect to CreateNodepoolsRequest
  */
  public static CreateNodepoolsRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateNodepoolsRequest.class);
  }

 /**
  * Convert an instance of CreateNodepoolsRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

