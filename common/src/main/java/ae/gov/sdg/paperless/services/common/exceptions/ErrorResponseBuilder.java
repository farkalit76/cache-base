package ae.gov.sdg.paperless.services.common.exceptions;

public class ErrorResponseBuilder {

    private boolean success = false;

    private ApiError error;

    public ErrorResponseBuilder() {
        super();
    }

    public ErrorResponseBuilder(ApiError error) {
        super();
        this.error = error;
    }

    public ErrorResponseBuilder setMethod(ApiError error) {
        this.error = error;
        return this;
    }

    public ErrorResponse build() {
        return new ErrorResponse(success, error);
    }

}
