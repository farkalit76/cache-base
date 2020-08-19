package ae.gov.sdg.paperless.services.cache.common.models;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheHashKeysResponse extends BaseResponse {

    @ApiModelProperty(value = "Key values for persisting data.", example = "")
    private Set<String> data;

    public Set<String> getData() {
        return data;
    }

    public void setData(Set<String> data) {
        this.data = data;
    }

}
