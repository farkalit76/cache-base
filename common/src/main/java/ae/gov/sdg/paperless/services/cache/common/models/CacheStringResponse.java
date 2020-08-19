package ae.gov.sdg.paperless.services.cache.common.models;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheStringResponse extends BaseResponse {

    @ApiModelProperty(
            value = "The response retrieved for the provided key based on operation used.",
            example = "123456")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
