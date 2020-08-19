package ae.gov.sdg.paperless.services.cache.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.json.simple.JSONObject;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheJsonHashCollectionRequest {

    @ApiModelProperty(
            value = "Collection for persisting data.",
            example = "users")
    @JsonProperty("keySpace")
    private String keySpace;

    @ApiModelProperty(value = "Key values for persisting data.", example = "{\n" +
            "	\"john\" : {\n" +
            "		    \"fName\":\"John\",\n" +
            "	        \"lName\": \"Davis\"\n" +
            "	}\n" +
            "}")
    private Map<String, JSONObject> cacheMap;

    @ApiModelProperty(value = "Time in minutes for which the key will be available in cache.", required = false, example = "5")
    @JsonProperty("timeInMinutes")
    private Long timeInMinutes;

    public String getKeySpace() {
        return keySpace;
    }

    public void setKeySpace(String keySpace) {
        this.keySpace = keySpace;
    }

    public Map<String, JSONObject> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<String, JSONObject> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

	@Override
	public String toString() {
		return "CacheJsonHashCollectionRequest [keySpace=" + keySpace + ", cacheMap=" + cacheMap + ", timeInMinutes="
				+ timeInMinutes + "]";
	}
    
    
}
