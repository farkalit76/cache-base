package ae.gov.sdg.paperless.services.cache.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheStringHashRequest extends CacheStringRequest {

    @ApiModelProperty(
            value = "Collection for persisting data.",
            example = "users_otp")
    @JsonProperty("keySpace")
    private String keySpace;

    public String getKeySpace() {
        return keySpace;
    }

    public void setKeySpace(String keySpace) {
        this.keySpace = keySpace;
    }
}
