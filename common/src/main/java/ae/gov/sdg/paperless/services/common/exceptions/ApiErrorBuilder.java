package ae.gov.sdg.paperless.services.common.exceptions;

public class ApiErrorBuilder {

    private String errorCode;

    private String errorMessage;

    private String errorSeverity;

    public ApiErrorBuilder() {
        super();
    }

    public ApiErrorBuilder(String errorCode, String errorMessage, String errorSeverity) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorSeverity = errorSeverity;
    }

    public ApiErrorBuilder setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ApiErrorBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ApiErrorBuilder setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
        return this;
    }

    public ApiError build() {
        return new ApiError(errorCode, errorMessage, errorSeverity);
    }

}
