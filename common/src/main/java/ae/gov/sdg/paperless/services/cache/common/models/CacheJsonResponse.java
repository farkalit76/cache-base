package ae.gov.sdg.paperless.services.cache.common.models;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.json.simple.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheJsonResponse extends BaseResponse {

    @ApiModelProperty(
            value = "The json response retrieved for the provided key based on operation used.",
            example = "{	\n" +
                    "		\"test\": \"1\"\n" +
                    "	}")
    @JsonProperty("data")
    private JSONObject data;

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

}
