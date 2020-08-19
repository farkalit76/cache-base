package ae.gov.sdg.paperless.services.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sdg.service.sms")
public class SmsServiceConfig {

	private String url;

	private String clientid;

	private String clientsecret;
	
	private String userauthorization;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getClientsecret() {
		return clientsecret;
	}

	public void setClientsecret(String clientsecret) {
		this.clientsecret = clientsecret;
	}

	public String getUserauthorization() {
		return userauthorization;
	}

	public void setUserauthorization(String userauthorization) {
		this.userauthorization = userauthorization;
	}



}
