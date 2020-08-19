package ae.gov.sdg.paperless.services.otp;

import ae.gov.sdg.paperless.services.cache.provider.store.CacheService;
import ae.gov.sdg.paperless.services.common.GenericAuthenticationService;
import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import ae.gov.sdg.paperless.services.common.tracing.TraceLog;
import ae.gov.sdg.paperless.services.otp.exceptions.*;
import ae.gov.sdg.paperless.services.sms.SmsService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.*;
import static ae.gov.sdg.paperless.services.common.exceptions.BaseResponse.successResponse;

/**
 * The Class OtpServiceImpl.
 */
@Service
@SuppressWarnings("unchecked")
public class OtpService {

    @Autowired
    @Qualifier(JSON_CACHE_SERVICE)
    private CacheService<JSONObject> cacheService;
    @Autowired
    private SmsService smsService;
    @Autowired
    protected GenericAuthenticationService genAuthService;

    @Value("${sdg.service.otp.length}")
    private int otpLength;
    @Value("${sdg.service.otp.timeLimit}")
    private int otpTimeLimit;
    @Value("${sdg.service.otp.remAtmpt}")
    private int otpRemainingAtmp;

    /**
     * Generate otp.
     *
     * @param request the request
     * @return the string
     * @throws OtpSendException the otp send exception
     */
    @TraceLog
    public OtpResponse generateOtp(OtpRequest request) {
        final String token = UUID.randomUUID().toString();
        generateOTPForToken(token, otpLength, request);
        return new OtpResponse(token, true);
    }

    // Generates a random int with n digits
    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, (double)n - 1);
        return m + new Random().nextInt(9 * m); // SecureRandom
    }

    /**
     * Generate OTP for token.
     *
     * @param key     the key
     * @param length  the length
     * @param request the request
     * @return the otp
     * @throws OtpSendException the otp send exception
     */
    private int generateOTPForToken(String key, Integer length, OtpRequest request) {
        final int otp = generateRandomDigits(length);
        final long timeLimit = getOtpTimeLimit(request.getOtpTimeLimit());
        final String message = request.getMessage().replaceAll("\\{otp\\}", otp + "");
        JSONObject obj = new JSONObject();
        obj.put(OTP, String.valueOf(otp));
        obj.put(ATTEMPTS_REMAINING, otpRemainingAtmp);
        obj.put(TIME_IN_MILISECONDS, new Date().getTime() + (timeLimit * 60 * 1000));
        obj.put(MESSAGE, message);
        obj.put(MOBILE_NUMBER, request.getMobileNum());
        obj.put(TIME_LIMIT, timeLimit);
        cacheService.add(key, obj, timeLimit + 1);
        smsService.sendOtpToUser(message, request.getMobileNum());
        return otp;
    }

    /**
     * Gets the otp time limit.
     *
     * @param timeLimit the time limit
     * @return the otp time limit
     */
    private long getOtpTimeLimit(long timeLimit) {
        if (timeLimit > 0) {
            return timeLimit;
        }
        return otpTimeLimit;
    }

    /**
     * Validate otp.
     *
     * @param
     * @return true, if successful
     */
    @TraceLog
    public BaseResponse validateOtp(OtpValidationRequest request) {
        JSONObject obj = cacheService.get(request.getKey());
        if (obj == null) {
            throw new OtpNotFoundException("UUID not found");
        } else {
            String otp = (String) obj.get(OTP);
            Integer attemptsRemaining = (Integer) obj.get(ATTEMPTS_REMAINING);
            if (attemptsRemaining <= 0) {
                throw new AttemptsExceededException("Exceeded number of attempts");
            }
            if (!request.getUserOtp().equals(otp)) {
                obj.put(ATTEMPTS_REMAINING, attemptsRemaining - 1);
                Long timeLimit = (Long) obj.get(TIME_LIMIT);
                cacheService.add(request.getKey(), obj, timeLimit + 1);
                throw new InvalidOtpException("Invalid OTP");
            } else {
                Long time = (Long) obj.get(TIME_IN_MILISECONDS);
                Long currentTime = new Date().getTime();
                if (time < currentTime) {
                    throw new OtpExpiredException("OTP timeout");
                }
            }
        }
        return successResponse();
    }

}
