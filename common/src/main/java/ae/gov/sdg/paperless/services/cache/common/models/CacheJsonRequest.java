package ae.gov.sdg.paperless.services.cache.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.json.simple.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheJsonRequest {

    @ApiModelProperty(
            value = "Key for persisting data.",
            example = "user")
    @JsonProperty("key")
    private String key;

    @ApiModelProperty(
            value = "The json value to be persisted in the cache for the provided key.",
            example = "{	\n" +
                    "		\"fName\": \"John\"\n" +
                    "	}")
    @JsonProperty("value")
    private JSONObject value;

    @ApiModelProperty(value = "Time in minutes for which the key will be available in cache.", required = false, example = "5")
    @JsonProperty("timeInMinutes")
    private Long timeInMinutes;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JSONObject getValue() {
        return value;
    }

    public void setValue(JSONObject value) {
        this.value = value;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

	@Override
	public String toString() {
		return "CacheJsonRequest [key=" + key + ", value=" + value + ", timeInMinutes=" + timeInMinutes + "]";
	}
    
    

}
