package ae.gov.sdg.paperless.services.common.exceptions;

import java.net.SocketTimeoutException;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import ae.gov.sdg.paperless.services.cache.common.util.CommonConstants;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends AbstractExceptionHandler {

	private Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	/**
	 * Handle ExternalApiException
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ExternalApiException.class)
	public ResponseEntity<?> entityNotRespondingExceptionHandler(ExternalApiException ex, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXTERNAL_API_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, ex.getSeverity());
		log.error(CommonConstants.EXTERNAL_API_ERR_CODE, ex);
		MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXTERNAL_API_ERR_CODE, ex.getSeverity());
		return new ResponseEntity<ErrorResponse>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle IllegalArgumentException
	 *
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> invalidArgumentExceptionHandler(IllegalArgumentException ex, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXTERNAL_API_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.LOW);
		log.error(CommonConstants.INVALID_REQUEST_ERR_CODE, ex);
		MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.INVALID_REQUEST_ERR_CODE, Severity.LOW);
		return new ResponseEntity<ErrorResponse>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
     * Handle SocketTimeoutException
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<?> entityNotRespondingExceptionHandler(SocketTimeoutException ex, WebRequest request) {
        MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.SOCKET_TIMEOUT_ERR_CODE);
        MDC.put(CommonConstants.ERROR_SEVERITY, Severity.CRITICAL);
        log.error(CommonConstants.SOCKET_TIMEOUT_ERR_CODE, ex);
        MDC.remove(CommonConstants.ERROR_SEVERITY);
        MDC.remove(CommonConstants.LOG_ERROR_CODE);
        ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.SOCKET_TIMEOUT_ERR_CODE, Severity.CRITICAL);
        return new ResponseEntity<ErrorResponse>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.NOT_FOUND_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.LOW);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.NOT_FOUND_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 *
	 * @param ex      MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.LOW);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex      HttpMediaTypeNotSupportedException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.LOW);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 *                validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.LOW);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.HIGH);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex      HttpMessageNotWritableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.HIGH);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Customize the response for HttpRequestMethodNotSupportedException.
	 * <p>
	 * This method logs a warning, sets the "Allow" header, and delegates to
	 * {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Customize the response for HttpMediaTypeNotAcceptableException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Customize the response for MissingPathVariableException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 * @since 4.2
	 */
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Customize the response for ServletRequestBindingException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Customize the response for ConversionNotSupportedException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Customize the response for TypeMismatchException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Customize the response for MissingServletRequestPartException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Customize the response for BindException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.EXCEPTION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, Severity.MEDIUM);
	    log.error(ex.getLocalizedMessage(), ex);
	    MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse apiError = populateErrorMessage(ex, CommonConstants.EXCEPTION_ERR_CODE);
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}
	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<Object> handleBusinessException(BusinessException ex) {

		MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.BUSINESS_VALIDATION_ERR_CODE);
		MDC.put(CommonConstants.ERROR_SEVERITY, ex.getSeverity());
		log.error(ex.getMessage(), ex);
		MDC.remove(CommonConstants.ERROR_SEVERITY);
		MDC.remove(CommonConstants.LOG_ERROR_CODE);
		ErrorResponse businessError = populateErrorMessage(ex, CommonConstants.BUSINESS_VALIDATION_ERR_CODE,ex.getSeverity() );
		return new ResponseEntity<>(businessError, HttpStatus.OK);
	}


}