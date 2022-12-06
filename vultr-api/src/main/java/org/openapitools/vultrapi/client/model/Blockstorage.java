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
 * Block Storage information.
 */
@ApiModel(description = "Block Storage information.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class Blockstorage {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_COST = "cost";
  @SerializedName(SERIALIZED_NAME_COST)
  private Integer cost;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_SIZE_GB = "size_gb";
  @SerializedName(SERIALIZED_NAME_SIZE_GB)
  private Integer sizeGb;

  public static final String SERIALIZED_NAME_REGION = "region";
  @SerializedName(SERIALIZED_NAME_REGION)
  private String region;

  public static final String SERIALIZED_NAME_ATTACHED_TO_INSTANCE = "attached_to_instance";
  @SerializedName(SERIALIZED_NAME_ATTACHED_TO_INSTANCE)
  private String attachedToInstance;

  public static final String SERIALIZED_NAME_DATE_CREATED = "date_created";
  @SerializedName(SERIALIZED_NAME_DATE_CREATED)
  private String dateCreated;

  public static final String SERIALIZED_NAME_LABEL = "label";
  @SerializedName(SERIALIZED_NAME_LABEL)
  private String label;

  public static final String SERIALIZED_NAME_MOUNT_ID = "mount_id";
  @SerializedName(SERIALIZED_NAME_MOUNT_ID)
  private String mountId;

  public Blockstorage() { 
  }

  public Blockstorage id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * A unique ID for the Block Storage.
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A unique ID for the Block Storage.")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public Blockstorage cost(Integer cost) {
    
    this.cost = cost;
    return this;
  }

   /**
   * The monthly cost of this Block Storage.
   * @return cost
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The monthly cost of this Block Storage.")

  public Integer getCost() {
    return cost;
  }


  public void setCost(Integer cost) {
    this.cost = cost;
  }


  public Blockstorage status(String status) {
    
    this.status = status;
    return this;
  }

   /**
   * The current status of this Block Storage.  * active
   * @return status
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The current status of this Block Storage.  * active")

  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = status;
  }


  public Blockstorage sizeGb(Integer sizeGb) {
    
    this.sizeGb = sizeGb;
    return this;
  }

   /**
   * Size of the Block Storage in GB.
   * @return sizeGb
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Size of the Block Storage in GB.")

  public Integer getSizeGb() {
    return sizeGb;
  }


  public void setSizeGb(Integer sizeGb) {
    this.sizeGb = sizeGb;
  }


  public Blockstorage region(String region) {
    
    this.region = region;
    return this;
  }

   /**
   * The [Region id](#operation/list-regions) where the Block Storage is located.
   * @return region
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Region id](#operation/list-regions) where the Block Storage is located.")

  public String getRegion() {
    return region;
  }


  public void setRegion(String region) {
    this.region = region;
  }


  public Blockstorage attachedToInstance(String attachedToInstance) {
    
    this.attachedToInstance = attachedToInstance;
    return this;
  }

   /**
   * The [Instance id](#operation/list-instances) with this Block Storage attached.
   * @return attachedToInstance
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The [Instance id](#operation/list-instances) with this Block Storage attached.")

  public String getAttachedToInstance() {
    return attachedToInstance;
  }


  public void setAttachedToInstance(String attachedToInstance) {
    this.attachedToInstance = attachedToInstance;
  }


  public Blockstorage dateCreated(String dateCreated) {
    
    this.dateCreated = dateCreated;
    return this;
  }

   /**
   * The date this Block Storage was created.
   * @return dateCreated
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The date this Block Storage was created.")

  public String getDateCreated() {
    return dateCreated;
  }


  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }


  public Blockstorage label(String label) {
    
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


  public Blockstorage mountId(String mountId) {
    
    this.mountId = mountId;
    return this;
  }

   /**
   * An ID associated with the instance, when mounted the ID can be found in /dev/disk/by-id prefixed with virtio.
   * @return mountId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An ID associated with the instance, when mounted the ID can be found in /dev/disk/by-id prefixed with virtio.")

  public String getMountId() {
    return mountId;
  }


  public void setMountId(String mountId) {
    this.mountId = mountId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Blockstorage blockstorage = (Blockstorage) o;
    return Objects.equals(this.id, blockstorage.id) &&
        Objects.equals(this.cost, blockstorage.cost) &&
        Objects.equals(this.status, blockstorage.status) &&
        Objects.equals(this.sizeGb, blockstorage.sizeGb) &&
        Objects.equals(this.region, blockstorage.region) &&
        Objects.equals(this.attachedToInstance, blockstorage.attachedToInstance) &&
        Objects.equals(this.dateCreated, blockstorage.dateCreated) &&
        Objects.equals(this.label, blockstorage.label) &&
        Objects.equals(this.mountId, blockstorage.mountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cost, status, sizeGb, region, attachedToInstance, dateCreated, label, mountId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Blockstorage {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    sizeGb: ").append(toIndentedString(sizeGb)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    attachedToInstance: ").append(toIndentedString(attachedToInstance)).append("\n");
    sb.append("    dateCreated: ").append(toIndentedString(dateCreated)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    mountId: ").append(toIndentedString(mountId)).append("\n");
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
    openapiFields.add("cost");
    openapiFields.add("status");
    openapiFields.add("size_gb");
    openapiFields.add("region");
    openapiFields.add("attached_to_instance");
    openapiFields.add("date_created");
    openapiFields.add("label");
    openapiFields.add("mount_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Blockstorage
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Blockstorage.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Blockstorage is not found in the empty JSON string", Blockstorage.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Blockstorage.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Blockstorage` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("id") != null && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (jsonObj.get("region") != null && !jsonObj.get("region").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `region` to be a primitive type in the JSON string but got `%s`", jsonObj.get("region").toString()));
      }
      if (jsonObj.get("attached_to_instance") != null && !jsonObj.get("attached_to_instance").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `attached_to_instance` to be a primitive type in the JSON string but got `%s`", jsonObj.get("attached_to_instance").toString()));
      }
      if (jsonObj.get("date_created") != null && !jsonObj.get("date_created").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `date_created` to be a primitive type in the JSON string but got `%s`", jsonObj.get("date_created").toString()));
      }
      if (jsonObj.get("label") != null && !jsonObj.get("label").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `label` to be a primitive type in the JSON string but got `%s`", jsonObj.get("label").toString()));
      }
      if (jsonObj.get("mount_id") != null && !jsonObj.get("mount_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `mount_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("mount_id").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Blockstorage.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Blockstorage' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Blockstorage> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Blockstorage.class));

       return (TypeAdapter<T>) new TypeAdapter<Blockstorage>() {
           @Override
           public void write(JsonWriter out, Blockstorage value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Blockstorage read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Blockstorage given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Blockstorage
  * @throws IOException if the JSON string is invalid with respect to Blockstorage
  */
  public static Blockstorage fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Blockstorage.class);
  }

 /**
  * Convert an instance of Blockstorage to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

