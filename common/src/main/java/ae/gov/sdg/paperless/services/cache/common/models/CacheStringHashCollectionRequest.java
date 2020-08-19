package ae.gov.sdg.paperless.services.cache.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheStringHashCollectionRequest {

    @ApiModelProperty(
            value = "Collection for persisting data.",
            example = "users_otp")
    @JsonProperty("keySpace")
    private String keySpace;

    @ApiModelProperty(value = "Key values for persisting data.", example = "{\n" +
            "	\"john\" : \"1234\",\n" +
            "	\"davis\" : \"0361\",\n" +
            "   \"sean\" : \"8295\"\n" +
            "}")
    private Map<String, String> cacheMap;

    @ApiModelProperty(value = "Time in minutes for which the key will be available in cache.", required = false, example = "5")
    @JsonProperty("timeInMinutes")
    private Long timeInMinutes;

    public String getKeySpace() {
        return keySpace;
    }

    public void setKeySpace(String keySpace) {
        this.keySpace = keySpace;
    }

    public Map<String, String> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<String, String> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
