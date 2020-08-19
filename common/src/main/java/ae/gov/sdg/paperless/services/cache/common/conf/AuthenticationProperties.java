package ae.gov.sdg.paperless.services.cache.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author c_chandra.bommise
 * <p>
 * Properties for connecting to redis server.
 */
@ConfigurationProperties("commonservice.authentication")
@Configuration
public class AuthenticationProperties {

    private String userName;

    private String password;

    @Value("#{'${commonservice.authentication.roles}'.split(',')}")
    private String[] roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }


}
