package ae.gov.sdg.paperless.services.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }

    protected ErrorResponse populateErrorMessage(Exception ex, String errorCode) {
        ApiError apiError = new ApiError(errorCode);
        apiError.setErrorMessage(ex.getMessage());
        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(apiError);
        return errorResponseBuilder.build();
    }

    protected ErrorResponse populateErrorMessage(Exception ex, String errorCode, Severity errorSeverity) {
        ApiError apiError = new ApiError(errorCode);
        apiError.setErrorSeverity(errorSeverity.name());
        apiError.setErrorMessage(ex.getMessage());
        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(apiError);
        return errorResponseBuilder.build();
    }

    protected ErrorResponse populateErrorMessage(String message, String errorCode) {
        ApiError apiError = new ApiError(errorCode);
        apiError.setErrorMessage(message);
        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(apiError);
        return errorResponseBuilder.build();
    }

    protected ErrorResponse populateErrorMessage(String message, String errorCode, Severity errorSeverity) {
        ApiError apiError = new ApiError(errorCode);
        apiError.setErrorSeverity(errorSeverity.name());
        apiError.setErrorMessage(message);
        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(apiError);
        return errorResponseBuilder.build();
    }

}