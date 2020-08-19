package ae.gov.sdg.paperless.services.cache.common.models;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheStringHashCollectionResponse extends BaseResponse {

    @ApiModelProperty(value = "Key values for persisting data.", example = "{\n" +
            "	\"john\" : \"1234\",\n" +
            "	\"davis\" : \"0361\",\n" +
            "   \"sean\" : \"8295\"\n" +
            "}")
    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}
