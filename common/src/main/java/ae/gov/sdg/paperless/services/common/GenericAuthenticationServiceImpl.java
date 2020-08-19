package ae.gov.sdg.paperless.services.common;

import ae.gov.sdg.paperless.services.otp.ServiceProperties;
import ae.gov.sdg.paperless.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GenericAuthenticationServiceImpl implements GenericAuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(GenericAuthenticationServiceImpl.class);

    @Autowired
    @Qualifier("genericRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private ServiceProperties config;

    private GenericAuthTokenCache tokensCache = GenericAuthTokenCache.getInstance();

    @Override
    public GenericAuthToken getOAuthAccessToken(String tokenName, String username, String password) {

        log.info("getGenericOauthAccessToken...");
        GenericAuthToken token = tokensCache.getToken(tokenName);
        if (token != null) {
            return token;
        }

        RestUtil.addAuthentication(restTemplate, username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "client_credentials");
        params.add("scope", "openid");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(params,
                headers);

        try {
            ResponseEntity<GenericAuthToken> response = restTemplate.exchange(config.getOauthTokenApiUrl(),
                    HttpMethod.POST, entity, GenericAuthToken.class, params);
            token = response.getBody();
            tokensCache.addToken(tokenName, token);

            return token;
        } catch (Exception e) {
            log.error("getOauthAccessToken() Error --> ", e);
            return null;
        }
    }
}