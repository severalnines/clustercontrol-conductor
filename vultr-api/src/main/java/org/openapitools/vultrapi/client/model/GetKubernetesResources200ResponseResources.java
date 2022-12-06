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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.openapitools.vultrapi.client.model.GetKubernetesResources200ResponseResourcesBlockStorageInner;
import org.openapitools.vultrapi.client.model.GetKubernetesResources200ResponseResourcesLoadBalancerInner;

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
 * GetKubernetesResources200ResponseResources
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class GetKubernetesResources200ResponseResources {
  public static final String SERIALIZED_NAME_BLOCK_STORAGE = "block_storage";
  @SerializedName(SERIALIZED_NAME_BLOCK_STORAGE)
  private Set<GetKubernetesResources200ResponseResourcesBlockStorageInner> blockStorage = null;

  public static final String SERIALIZED_NAME_LOAD_BALANCER = "load_balancer";
  @SerializedName(SERIALIZED_NAME_LOAD_BALANCER)
  private Set<GetKubernetesResources200ResponseResourcesLoadBalancerInner> loadBalancer = null;

  public GetKubernetesResources200ResponseResources() { 
  }

  public GetKubernetesResources200ResponseResources blockStorage(Set<GetKubernetesResources200ResponseResourcesBlockStorageInner> blockStorage) {
    
    this.blockStorage = blockStorage;
    return this;
  }

  public GetKubernetesResources200ResponseResources addBlockStorageItem(GetKubernetesResources200ResponseResourcesBlockStorageInner blockStorageItem) {
    if (this.blockStorage == null) {
      this.blockStorage = new LinkedHashSet<>();
    }
    this.blockStorage.add(blockStorageItem);
    return this;
  }

   /**
   * Get blockStorage
   * @return blockStorage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Set<GetKubernetesResources200ResponseResourcesBlockStorageInner> getBlockStorage() {
    return blockStorage;
  }


  public void setBlockStorage(Set<GetKubernetesResources200ResponseResourcesBlockStorageInner> blockStorage) {
    this.blockStorage = blockStorage;
  }


  public GetKubernetesResources200ResponseResources loadBalancer(Set<GetKubernetesResources200ResponseResourcesLoadBalancerInner> loadBalancer) {
    
    this.loadBalancer = loadBalancer;
    return this;
  }

  public GetKubernetesResources200ResponseResources addLoadBalancerItem(GetKubernetesResources200ResponseResourcesLoadBalancerInner loadBalancerItem) {
    if (this.loadBalancer == null) {
      this.loadBalancer = new LinkedHashSet<>();
    }
    this.loadBalancer.add(loadBalancerItem);
    return this;
  }

   /**
   * Get loadBalancer
   * @return loadBalancer
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Set<GetKubernetesResources200ResponseResourcesLoadBalancerInner> getLoadBalancer() {
    return loadBalancer;
  }


  public void setLoadBalancer(Set<GetKubernetesResources200ResponseResourcesLoadBalancerInner> loadBalancer) {
    this.loadBalancer = loadBalancer;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetKubernetesResources200ResponseResources getKubernetesResources200ResponseResources = (GetKubernetesResources200ResponseResources) o;
    return Objects.equals(this.blockStorage, getKubernetesResources200ResponseResources.blockStorage) &&
        Objects.equals(this.loadBalancer, getKubernetesResources200ResponseResources.loadBalancer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blockStorage, loadBalancer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetKubernetesResources200ResponseResources {\n");
    sb.append("    blockStorage: ").append(toIndentedString(blockStorage)).append("\n");
    sb.append("    loadBalancer: ").append(toIndentedString(loadBalancer)).append("\n");
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
    openapiFields.add("block_storage");
    openapiFields.add("load_balancer");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to GetKubernetesResources200ResponseResources
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (GetKubernetesResources200ResponseResources.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in GetKubernetesResources200ResponseResources is not found in the empty JSON string", GetKubernetesResources200ResponseResources.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!GetKubernetesResources200ResponseResources.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `GetKubernetesResources200ResponseResources` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      JsonArray jsonArrayblockStorage = jsonObj.getAsJsonArray("block_storage");
      if (jsonArrayblockStorage != null) {
        // ensure the json data is an array
        if (!jsonObj.get("block_storage").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `block_storage` to be an array in the JSON string but got `%s`", jsonObj.get("block_storage").toString()));
        }

        // validate the optional field `block_storage` (array)
        for (int i = 0; i < jsonArrayblockStorage.size(); i++) {
          GetKubernetesResources200ResponseResourcesBlockStorageInner.validateJsonObject(jsonArrayblockStorage.get(i).getAsJsonObject());
        };
      }
      JsonArray jsonArrayloadBalancer = jsonObj.getAsJsonArray("load_balancer");
      if (jsonArrayloadBalancer != null) {
        // ensure the json data is an array
        if (!jsonObj.get("load_balancer").isJsonArray()) {
          throw new IllegalArgumentException(String.format("Expected the field `load_balancer` to be an array in the JSON string but got `%s`", jsonObj.get("load_balancer").toString()));
        }

        // validate the optional field `load_balancer` (array)
        for (int i = 0; i < jsonArrayloadBalancer.size(); i++) {
          GetKubernetesResources200ResponseResourcesLoadBalancerInner.validateJsonObject(jsonArrayloadBalancer.get(i).getAsJsonObject());
        };
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!GetKubernetesResources200ResponseResources.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'GetKubernetesResources200ResponseResources' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<GetKubernetesResources200ResponseResources> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(GetKubernetesResources200ResponseResources.class));

       return (TypeAdapter<T>) new TypeAdapter<GetKubernetesResources200ResponseResources>() {
           @Override
           public void write(JsonWriter out, GetKubernetesResources200ResponseResources value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public GetKubernetesResources200ResponseResources read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of GetKubernetesResources200ResponseResources given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of GetKubernetesResources200ResponseResources
  * @throws IOException if the JSON string is invalid with respect to GetKubernetesResources200ResponseResources
  */
  public static GetKubernetesResources200ResponseResources fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, GetKubernetesResources200ResponseResources.class);
  }

 /**
  * Convert an instance of GetKubernetesResources200ResponseResources to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

