package ae.gov.sdg.paperless.services.cache.common.util;

import ae.gov.sdg.paperless.services.common.exceptions.Severity;

import static ae.gov.sdg.paperless.services.common.exceptions.Severity.LOW;

/**
 * @author c_chandra.bommise
 *         <p>
 *         Common constants
 */
public interface CommonConstants {

	String AUTHORIZATION = "Authorization";
	String CUSTOM_AUTHORIZATION = "Custom-Authorization";
	String CONTENT_TYPE = "Content-Type";
	String ACCEPT = "Accept";
	String APPLICATION_JSON = "application/json";
	String SCREENS = "screens";
	String HEADERS = "headers";
	String HTTPSTATUS = "status";
	String REDIS_TEMPLATE = "redisTemplate";
	String REST_TEMPLATE_BEAN = "genericRestTemplate";
	String CACHE_KEY_GENERATOR_BEAN = "cacheKeyGenerator";
	String UNIQUE_ID = "uniqueId";
	String REQUEST_URI = "uri";
	String HTTPMETHOD = "httpMethod";
	String POST = "POST";
	String GET = "GET";
	String DELETE = "DELETE";
	String PUT = "PUT";
	String UNDERSCORE = "_";
	String PUBLIC_CACHE = "public";
	String PRIVATE_CACHE = "private";
	String SEPARATOR_COLON = ":";
	Integer ONE = 1;
	String PATH_PARAMS = "pathParams";
	String FROMCACHE = "fromCache";
	String SLASH = "/";

	String CACHE = "CACHE";
	String REFRESH = "REFRESH";
	String NOCACHE = "NOCACHE";
	String LAST_UPDATED_TIME = "last-updated";
	String CACHE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	String REDIS_STRING_TEMPLATE = "redisStringTemplate";
	String GENERIC_REST_TEMPLATE = "genericRestTemplate";
	String JSON_CACHE_SERVICE = "jsonCacheService";
	String STRING_CACHE_SERVICE = "stringCacheService";
	String UTF_8 = "UTF-8";
	String BEARER = "Bearer ";
	String DUBAI_NOW_ACCESS_TOKEN = "DubaiNowToken";

	String X_TRACE_ID = "x-DN-Traceid";
	String X_SESSION_ID = "x-DN-Sid";
	String LOG_SPAN_ID = "spanId";
	String USER_AUTHORIZATION = "UserAuthorization";

	String EXCEPTION_ERR_CODE = "E1000"; // Any other exceptions - Null pointer exception, user info not valid - 500
	String CUSTOM_EXCEPTION_ERR_CODE = "E1001"; // HttpStatusCode, Transformation error - 500
	String INVALID_REQUEST_ERR_CODE = "E1002"; // Invalid Request - 400
	String UNAUTHORIZED_ERR_CODE = "E1003"; // Token eror - 401
	String NOT_FOUND_ERR_CODE = "E1004"; // URL Not Found - 404
	String SCREEN_EXCEPTION_ERR_CODE = "E1005"; // Process not found, Screen not found - 500
	String GENERIC_SCREEN_ERR_CODE = "E1006"; // Generic error template screen - 200
	String EXTERNAL_API_ERR_CODE = "E1007";
	String REDIS_CONNECTION_TIMEOUT_ERR_CODE = "E1008";
	String SOCKET_TIMEOUT_ERR_CODE = "E1009";

	String NO_DATA_FOUND_ERR_CODE = "E1101"; // No data returned from entity - 500
	String BUSINESS_VALIDATION_ERR_CODE = "B1000"; // Any business validation like user doesn't have valid emirates id
													// trying to get ejari contract. - 200
	
	String TRACE_LOGGING_ERR_CODE = "T0000";
	String LOG_ERROR_CODE = "errorCode";
	String ERROR_SEVERITY = "errorSeverity";
	
	String OTP = "otp";
	String ATTEMPTS_REMAINING = "attemptsRem";
	String TIME_IN_MILISECONDS = "timeInMillis";
	String MOBILE_NUMBER = "mobileNo";
	String TIME_LIMIT = "timelimit";
	String MESSAGE = "message";
	Severity BUSINESS_EXCEPTION_ERROR_SEVERITY = LOW;
	String APSECT_LOG_ERR_CODE = "E0000";
	String RESOURCE_LOADER_ERR_CODE = "E1011"; 
	String X_DN_APP_VERSION = "x-DN-AppVersion";
	String X_DN_PLATFORM = "x-DN-Platform";
}