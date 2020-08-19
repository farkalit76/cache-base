package ae.gov.sdg.paperless.services.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @ApiModelProperty(
            value = "Indicator of the operation whether successful or not.",
            example = "false")
    @JsonProperty("success")
    private boolean success;

    @ApiModelProperty(
            value = "Error object in case of error.")
    @JsonProperty("error")
    private ApiError error;

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(boolean success, ApiError error) {
        super();
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

}
