package ae.gov.sdg.paperless.services.otp;

import ae.gov.sdg.paperless.services.otp.exceptions.OtpSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import ae.gov.sdg.paperless.services.common.exceptions.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class OtpController handles the API call for OTP Services
 */
@RestController
@RequestMapping("/api/otp")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OtpController {

	@Autowired
	private OtpService otpService;

	/**
	 * Generate otp.
	 *
	 * @param request the request
	 * @return the OtpResponse ResponseEntity
	 * @throws OtpSendException the otp send exception
	 */
	@ApiOperation(value = "Generate Otp for specific User", 
			notes = "Generate Otp for specific User, and returns UUID",
			tags = "otp-controller", response = ResponseEntity.class)
	@ApiResponses({
			@ApiResponse(code = 400, message = "Bad Request error received from the client",
					response = ErrorResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API",
					response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Server Error",
					response = ErrorResponse.class) })

	@PostMapping("/send")
	public ResponseEntity<OtpResponse> generateOtp(
			@ApiParam(value = "generate otp request.", required = true) @RequestBody OtpRequest request)
			throws OtpSendException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		OtpResponse response = otpService.generateOtp(request);
		return new ResponseEntity<OtpResponse>(response, headers, HttpStatus.OK);
	}

	/**
	 * Validate otp.
	 *
	 * @param
	 * @return true, if successful
	 */
	@ApiOperation(value = "Validate the OTP for the user",
			notes = "Validate the OTP for the user",
			tags = "otp-controller", response = Boolean.class)
	@ApiResponses({
			@ApiResponse(code = 400, message = "Bad Request error received from the client",
					response = ErrorResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", 
					response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Server Error", 
					response = ErrorResponse.class) })
	@PostMapping("/validate")
	public ResponseEntity<BaseResponse> validateOtp(
			@ApiParam(value = "validate otp request.", required = true) @RequestBody OtpValidationRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		BaseResponse response = otpService.validateOtp(request);
		return new ResponseEntity<BaseResponse>(response, headers, HttpStatus.OK);

	}

}
