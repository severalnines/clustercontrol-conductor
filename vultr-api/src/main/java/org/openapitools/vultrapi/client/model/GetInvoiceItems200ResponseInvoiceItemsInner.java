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
import java.math.BigDecimal;

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
 * GetInvoiceItems200ResponseInvoiceItemsInner
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-09T10:22:59.586709-04:00[America/New_York]")
public class GetInvoiceItems200ResponseInvoiceItemsInner {
  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_PRODUCT = "product";
  @SerializedName(SERIALIZED_NAME_PRODUCT)
  private String product;

  public static final String SERIALIZED_NAME_START_DATE = "start_date";
  @SerializedName(SERIALIZED_NAME_START_DATE)
  private String startDate;

  public static final String SERIALIZED_NAME_END_DATE = "end_date";
  @SerializedName(SERIALIZED_NAME_END_DATE)
  private String endDate;

  public static final String SERIALIZED_NAME_UNITS = "units";
  @SerializedName(SERIALIZED_NAME_UNITS)
  private BigDecimal units;

  public static final String SERIALIZED_NAME_UNIT_TYPE = "unit_type";
  @SerializedName(SERIALIZED_NAME_UNIT_TYPE)
  private String unitType;

  public static final String SERIALIZED_NAME_UNIT_PRICE = "unit_price";
  @SerializedName(SERIALIZED_NAME_UNIT_PRICE)
  private BigDecimal unitPrice;

  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private BigDecimal total;

  public GetInvoiceItems200ResponseInvoiceItemsInner() { 
  }

  public GetInvoiceItems200ResponseInvoiceItemsInner description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Invoice item description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Invoice item description")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner product(String product) {
    
    this.product = product;
    return this;
  }

   /**
   * Product name
   * @return product
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Product name")

  public String getProduct() {
    return product;
  }


  public void setProduct(String product) {
    this.product = product;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner startDate(String startDate) {
    
    this.startDate = startDate;
    return this;
  }

   /**
   * Start date of item
   * @return startDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Start date of item")

  public String getStartDate() {
    return startDate;
  }


  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner endDate(String endDate) {
    
    this.endDate = endDate;
    return this;
  }

   /**
   * End date of item
   * @return endDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "End date of item")

  public String getEndDate() {
    return endDate;
  }


  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner units(BigDecimal units) {
    
    this.units = units;
    return this;
  }

   /**
   * Number of units item consumed in billing period
   * @return units
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Number of units item consumed in billing period")

  public BigDecimal getUnits() {
    return units;
  }


  public void setUnits(BigDecimal units) {
    this.units = units;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner unitType(String unitType) {
    
    this.unitType = unitType;
    return this;
  }

   /**
   * Unit type. Options include \&quot;hours\&quot;, \&quot;overage\&quot;, and \&quot;discount\&quot;
   * @return unitType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Unit type. Options include \"hours\", \"overage\", and \"discount\"")

  public String getUnitType() {
    return unitType;
  }


  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner unitPrice(BigDecimal unitPrice) {
    
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Price per unit in dollars
   * @return unitPrice
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Price per unit in dollars")

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }


  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }


  public GetInvoiceItems200ResponseInvoiceItemsInner total(BigDecimal total) {
    
    this.total = total;
    return this;
  }

   /**
   * Total amount due in dollars
   * @return total
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Total amount due in dollars")

  public BigDecimal getTotal() {
    return total;
  }


  public void setTotal(BigDecimal total) {
    this.total = total;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetInvoiceItems200ResponseInvoiceItemsInner getInvoiceItems200ResponseInvoiceItemsInner = (GetInvoiceItems200ResponseInvoiceItemsInner) o;
    return Objects.equals(this.description, getInvoiceItems200ResponseInvoiceItemsInner.description) &&
        Objects.equals(this.product, getInvoiceItems200ResponseInvoiceItemsInner.product) &&
        Objects.equals(this.startDate, getInvoiceItems200ResponseInvoiceItemsInner.startDate) &&
        Objects.equals(this.endDate, getInvoiceItems200ResponseInvoiceItemsInner.endDate) &&
        Objects.equals(this.units, getInvoiceItems200ResponseInvoiceItemsInner.units) &&
        Objects.equals(this.unitType, getInvoiceItems200ResponseInvoiceItemsInner.unitType) &&
        Objects.equals(this.unitPrice, getInvoiceItems200ResponseInvoiceItemsInner.unitPrice) &&
        Objects.equals(this.total, getInvoiceItems200ResponseInvoiceItemsInner.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, product, startDate, endDate, units, unitType, unitPrice, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetInvoiceItems200ResponseInvoiceItemsInner {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    units: ").append(toIndentedString(units)).append("\n");
    sb.append("    unitType: ").append(toIndentedString(unitType)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("product");
    openapiFields.add("start_date");
    openapiFields.add("end_date");
    openapiFields.add("units");
    openapiFields.add("unit_type");
    openapiFields.add("unit_price");
    openapiFields.add("total");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to GetInvoiceItems200ResponseInvoiceItemsInner
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (GetInvoiceItems200ResponseInvoiceItemsInner.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in GetInvoiceItems200ResponseInvoiceItemsInner is not found in the empty JSON string", GetInvoiceItems200ResponseInvoiceItemsInner.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!GetInvoiceItems200ResponseInvoiceItemsInner.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `GetInvoiceItems200ResponseInvoiceItemsInner` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if (jsonObj.get("description") != null && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (jsonObj.get("product") != null && !jsonObj.get("product").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `product` to be a primitive type in the JSON string but got `%s`", jsonObj.get("product").toString()));
      }
      if (jsonObj.get("start_date") != null && !jsonObj.get("start_date").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `start_date` to be a primitive type in the JSON string but got `%s`", jsonObj.get("start_date").toString()));
      }
      if (jsonObj.get("end_date") != null && !jsonObj.get("end_date").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `end_date` to be a primitive type in the JSON string but got `%s`", jsonObj.get("end_date").toString()));
      }
      if (jsonObj.get("unit_type") != null && !jsonObj.get("unit_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `unit_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("unit_type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!GetInvoiceItems200ResponseInvoiceItemsInner.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'GetInvoiceItems200ResponseInvoiceItemsInner' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<GetInvoiceItems200ResponseInvoiceItemsInner> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(GetInvoiceItems200ResponseInvoiceItemsInner.class));

       return (TypeAdapter<T>) new TypeAdapter<GetInvoiceItems200ResponseInvoiceItemsInner>() {
           @Override
           public void write(JsonWriter out, GetInvoiceItems200ResponseInvoiceItemsInner value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public GetInvoiceItems200ResponseInvoiceItemsInner read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of GetInvoiceItems200ResponseInvoiceItemsInner given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of GetInvoiceItems200ResponseInvoiceItemsInner
  * @throws IOException if the JSON string is invalid with respect to GetInvoiceItems200ResponseInvoiceItemsInner
  */
  public static GetInvoiceItems200ResponseInvoiceItemsInner fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, GetInvoiceItems200ResponseInvoiceItemsInner.class);
  }

 /**
  * Convert an instance of GetInvoiceItems200ResponseInvoiceItemsInner to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
