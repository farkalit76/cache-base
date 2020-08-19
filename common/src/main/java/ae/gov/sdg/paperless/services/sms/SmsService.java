package ae.gov.sdg.paperless.services.sms;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.APPLICATION_JSON;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.AUTHORIZATION;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.BEARER;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.CONTENT_TYPE;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.DUBAI_NOW_ACCESS_TOKEN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ae.gov.sdg.paperless.services.cache.common.util.CommonConstants;
import ae.gov.sdg.paperless.services.common.GenericAuthToken;
import ae.gov.sdg.paperless.services.common.GenericAuthenticationService;
import ae.gov.sdg.paperless.services.otp.exceptions.OtpSendException;

@Service
@SuppressWarnings("unchecked")
public class SmsService {

    @Autowired
    @Qualifier("genericRestTemplateWithoutBasicAuth")
    private RestTemplate restTemplate;

    @Autowired
    private SmsServiceConfig serviceProperties;

    @Autowired
    private GenericAuthenticationService genAuthService;

    public void sendOtpToUser(String message, String mobileNum){
        final GenericAuthToken dubaiNowAccessInfo = genAuthService.getOAuthAccessToken(DUBAI_NOW_ACCESS_TOKEN,
        		serviceProperties.getClientid(), serviceProperties.getClientsecret());
        final String accessToken = BEARER + dubaiNowAccessInfo.getAccessToken();
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(serviceProperties.getUrl().concat("/send"));

        String json = "{" +
                "\"smsdetails\": " +
                "{" +
                "\"account\": \"Main\"," +
                "\"urname\": \"Ducont\"," +
                "\"mobile\": \"" + mobileNum + "\"," +
                "\"message\": \"" + message + "\"," + 
                "\"drmsgid\": \"1\"" +
                "}" +
                "}";

        RequestEntity requestEntity = RequestEntity.post(
                builder.build().toUri())
                .accept(MediaType.ALL)
                .header(AUTHORIZATION, accessToken)
                .header(CommonConstants.USER_AUTHORIZATION, serviceProperties.getUserauthorization())
                .header(CONTENT_TYPE, APPLICATION_JSON).body(json);
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new OtpSendException("Error while sending SMS");
        }
    }

}
