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
 * Plans for Bare Metal instances.
 */
@ApiModel(description = "Plans for Bare Metal instances.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class PlansMetal {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_CPU_COUNT = "cpu_count";
  @SerializedName(SERIALIZED_NAME_CPU_COUNT)
  private Integer cpuCount;

  public static final String SERIALIZED_NAME_CPU_MODEL = "cpu_model";
  @SerializedName(SERIALIZED_NAME_CPU_MODEL)
  private String cpuModel;

  public static final String SERIALIZED_NAME_CPU_THREADS = "cpu_threads";
  @SerializedName(SERIALIZED_NAME_CPU_THREADS)
  private Integer cpuThreads;

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private Integer ram;

  public static final String SERIALIZED_NAME_DISK = "disk";
  @SerializedName(SERIALIZED_NAME_DISK)
  private String disk;

  public static final String SERIALIZED_NAME_BANDWIDTH = "bandwidth";
  @SerializedName(SERIALIZED_NAME_BANDWIDTH)
  private Integer bandwidth;

  public static final String SERIALIZED_NAME_LOCATIONS = "locations";
  @SerializedName(SERIALIZED_NAME_LOCATIONS)
  private List<String> locations = null;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_MONTHLY_COST = "monthly_cost";
  @SerializedName(SERIALIZED_NAME_MONTHLY_COST)
  private Integer monthlyCost;

  public static final String SERIALIZED_NAME_DISK_COUNT = "disk_count";
  @SerializedName(SERIALIZED_NAME_DISK_COUNT)
  private Integer diskCount;

  public PlansMetal() { 
  }

  public PlansMetal id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * A unique ID for the Bare Metal Plan.
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A unique ID for the Bare Metal Plan.")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public PlansMetal cpuCount(Integer cpuCount) {
    
    this.cpuCount = cpuCount;
    return this;
  }

   /**
   * The number of CPUs in this Plan.
   * @return cpuCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The number of CPUs in this Plan.")

  public Integer getCpuCount() {
    return cpuCount;
  }


  public void setCpuCount(Integer cpuCount) {
    this.cpuCount = cpuCount;
  }


  public PlansMetal cpuModel(String cpuModel) {
    
    this.cpuModel = cpuModel;
    return this;
  }

   /**
   * The CPU model type for this instance.
   * @return cpuModel
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The CPU model type for this instance.")

  public String getCpuModel() {
    return cpuModel;
  }


  public void setCpuModel(String cpuModel) {
    this.cpuModel = cpuModel;
  }


  public PlansMetal cpuThreads(Integer cpuThreads) {
    
    this.cpuThreads = cpuThreads;
    return this;
  }

   /**
   * The numner of supported threads for this instance.
   * @return cpuThreads
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The numner of supported threads for this instance.")

  public Integer getCpuThreads() {
    return cpuThreads;
  }


  public void setCpuThreads(Integer cpuThreads) {
    this.cpuThreads = cpuThreads;
  }


  public PlansMetal ram(Integer ram) {
    
    this.ram = ram;
    return this;
  }

   /**
   * The amount of RAM in MB.
   * @return ram
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The amount of RAM in MB.")

  public Integer getRam() {
    return ram;
  }


  public void setRam(Integer ram) {
    this.ram = ram;
  }


  public PlansMetal disk(String disk) {
    
    this.disk = disk;
    return this;
  }

   /**
   * The disk size in GB.
   * @return disk
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The disk size in GB.")

  public String getDisk() {
    return disk;
  }


  public void setDisk(String disk) {
    this.disk = disk;
  }


  public PlansMetal bandwidth(Integer bandwidth) {
    
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * The monthly bandwidth quota in GB.
   * @return bandwidth
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The monthly bandwidth quota in GB.")

  public Integer getBandwidth() {
    return bandwidth;
  }


  public void setBandwidth(Integer bandwidth) {
    this.bandwidth = bandwidth;
  }


  public PlansMetal locations(List<String> locations) {
    
    this.locations = locations;
    return this;
  }

  public PlansMetal addLocationsItem(String locationsItem) {
    if (this.locations == null) {
      this.locations = new ArrayList<>();
    }
    this.locations.add(locationsItem);
    return this;
  }

   /**
   * An array of Regions where this plan is valid for use.
   * @return locations
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "An array of Regions where this plan is valid for use.")

  public List<String> getLocations() {
    return locations;
  }


  public void setLocations(List<String> locations) {
    this.locations = locations;
  }


  public PlansMetal type(String type) {
    
    this.type = type;
    return this;
  }

   /**
   * The plan type.  * SSD
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The plan type.  * SSD")

  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public PlansMetal monthlyCost(Integer monthlyCost) {
    
    this.monthlyCost = monthlyCost;
    return this;
  }

   /**
   * The monthly cost in US Dollars.
   * @return monthlyCost
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The monthly cost in US Dollars.")

  public Integer getMonthlyCost() {
    return monthlyCost;
  }


  public void setMonthlyCost(Integer monthlyCost) {
    this.monthlyCost = monthlyCost;
  }


  public PlansMetal diskCount(Integer diskCount) {
    
    this.diskCount = diskCount;
    return this;
  }

   /**
   * The number of disks that this plan offers.
   * @return diskCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "The number of disks that this plan offers.")

  public Integer getDiskCount() {
    return diskCount;
  }


  public void setDiskCount(Integer diskCount) {
    this.diskCount = diskCount;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlansMetal plansMetal = (PlansMetal) o;
    return Objects.equals(this.id, plansMetal.id) &&
        Objects.equals(this.cpuCount, plansMetal.cpuCount) &&
        Objects.equals(this.cpuModel, plansMetal.cpuModel) &&
        Objects.equals(this.cpuThreads, plansMetal.cpuThreads) &&
        Objects.equals(this.ram, plansMetal.ram) &&
        Objects.equals(this.disk, plansMetal.disk) &&
        Objects.equals(this.bandwidth, plansMetal.bandwidth) &&
        Objects.equals(this.locations, plansMetal.locations) &&
        Objects.equals(this.type, plansMetal.type) &&
        Objects.equals(this.monthlyCost, plansMetal.monthlyCost) &&
        Objects.equals(this.diskCount, plansMetal.diskCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cpuCount, cpuModel, cpuThreads, ram, disk, bandwidth, locations, type, monthlyCost, diskCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlansMetal {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cpuCount: ").append(toIndentedString(cpuCount)).append("\n");
    sb.append("    cpuModel: ").append(toIndentedString(cpuModel)).append("\n");
    sb.append("    cpuThreads: ").append(toIndentedString(cpuThreads)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
    sb.append("    disk: ").append(toIndentedString(disk)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    locations: ").append(toIndentedString(locations)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    monthlyCost: ").append(toIndentedString(monthlyCost)).append("\n");
    sb.append("    diskCount: ").append(toIndentedString(diskCount)).append("\n");
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
    openapiFields.add("cpu_count");
    openapiFields.add("cpu_model");
    openapiFields.add("cpu_threads");
    openapiFields.add("ram");
    openapiFields.add("disk");
    openapiFields.add("bandwidth");
    openapiFields.add("locations");
    openapiFields.add("type");
    openapiFields.add("monthly_cost");
    openapiFields.add("disk_count");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to PlansMetal
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (PlansMetal.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in PlansMetal is not found in the empty JSON string", PlansMetal.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!PlansMetal.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PlansMetal` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("id") != null && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if (jsonObj.get("cpu_model") != null && !jsonObj.get("cpu_model").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cpu_model` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cpu_model").toString()));
      }
      if (jsonObj.get("disk") != null && !jsonObj.get("disk").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `disk` to be a primitive type in the JSON string but got `%s`", jsonObj.get("disk").toString()));
      }
      // ensure the json data is an array
      if (jsonObj.get("locations") != null && !jsonObj.get("locations").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `locations` to be an array in the JSON string but got `%s`", jsonObj.get("locations").toString()));
      }
      if (jsonObj.get("type") != null && !jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PlansMetal.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PlansMetal' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PlansMetal> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PlansMetal.class));

       return (TypeAdapter<T>) new TypeAdapter<PlansMetal>() {
           @Override
           public void write(JsonWriter out, PlansMetal value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PlansMetal read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PlansMetal given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PlansMetal
  * @throws IOException if the JSON string is invalid with respect to PlansMetal
  */
  public static PlansMetal fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PlansMetal.class);
  }

 /**
  * Convert an instance of PlansMetal to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

