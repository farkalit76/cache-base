package ae.gov.sdg.paperless.services.cache.provider.redis.exceptions;

import ae.gov.sdg.paperless.services.cache.common.util.CommonConstants;
import ae.gov.sdg.paperless.services.common.exceptions.AbstractExceptionHandler;
import ae.gov.sdg.paperless.services.common.exceptions.ErrorResponse;
import ae.gov.sdg.paperless.services.common.exceptions.Severity;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import redis.clients.jedis.exceptions.JedisException;


@Order(1)
@ControllerAdvice
public class RedisExceptionHandler extends AbstractExceptionHandler {

    private static final String REDIS_CONNECTION_FAILURE_EXCEPTION = "Redis connection failure exception";
    private Logger log = LoggerFactory.getLogger(RedisExceptionHandler.class);

    /**
     * Handle JedisConnectionException
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(JedisException.class)
    public ResponseEntity<?> connectionTimeoutExceptionHandler(JedisException ex, WebRequest request) {
        MDC.put(CommonConstants.LOG_ERROR_CODE, CommonConstants.REDIS_CONNECTION_TIMEOUT_ERR_CODE);
        MDC.put(CommonConstants.ERROR_SEVERITY, Severity.CRITICAL);
        log.error(CommonConstants.EXTERNAL_API_ERR_CODE, ex);
        MDC.remove(CommonConstants.ERROR_SEVERITY);
        MDC.remove(CommonConstants.LOG_ERROR_CODE);
        ErrorResponse apiError = populateErrorMessage(REDIS_CONNECTION_FAILURE_EXCEPTION, CommonConstants.REDIS_CONNECTION_TIMEOUT_ERR_CODE, Severity.HIGH);
        return new ResponseEntity<ErrorResponse>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}