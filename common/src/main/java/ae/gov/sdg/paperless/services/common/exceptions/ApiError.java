package ae.gov.sdg.paperless.services.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @ApiModelProperty(
            value = "Indicator of the operation whether successful or not.",
            example = "E1001")
    @JsonProperty("errorCode")
    private String errorCode;

    @ApiModelProperty(
            value = "Indicator of the operation whether successful or not.",
            example = "Connection timeout")
    @JsonProperty("errorMessage")
    private String errorMessage;

    @ApiModelProperty(
            value = "Indicator of the operation whether successful or not.",
            example = "HIGH")
    @JsonProperty("errorSeverity")
    private String errorSeverity;

    private ApiError() {
    }

    public ApiError(String errorCode) {
        this();
        this.errorCode = errorCode;
    }

    public ApiError(String errorCode, Throwable ex) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = "Unexpected error";
    }

    public ApiError(String errorCode, String errorMessage) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ApiError(String errorCode, String errorMessage, String errorSeverity) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorSeverity = errorSeverity;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

}

