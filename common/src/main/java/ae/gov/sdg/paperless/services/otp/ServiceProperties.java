package ae.gov.sdg.paperless.services.otp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sdg.service")
public class ServiceProperties {

    @Value("${sdg.service.ids.oauth2.token.api}")
    private String oauthTokenApiUrl;

    public String getOauthTokenApiUrl() {
        return oauthTokenApiUrl;
    }

    public void setOauthTokenApiUrl(String oauthTokenApiUrl) {
        this.oauthTokenApiUrl = oauthTokenApiUrl;
    }
}
