package ae.gov.sdg.paperless.services.cache.common.models;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.json.simple.JSONObject;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheJsonHashCollectionResponse extends BaseResponse {

    @ApiModelProperty(value = "Key values for persisting data.", example = "{\n" +
            "	\"john\" : {\n" +
            "		    \"fName\":\"John\",\n" +
            "	        \"lName\": \"Davis\"\n" +
            "	}\n" +
            "}")
    private Map<String, JSONObject> data;

    public Map<String, JSONObject> getData() {
        return data;
    }

    public void setData(Map<String, JSONObject> data) {
        this.data = data;
    }

}
