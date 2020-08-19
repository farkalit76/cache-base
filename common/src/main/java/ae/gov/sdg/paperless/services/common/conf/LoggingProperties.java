package ae.gov.sdg.paperless.services.common.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author c_chandra.bommise
 * <p>
 * Properties for logging.
 */
@Component
@ConfigurationProperties(prefix = "sdg.logging")
public class LoggingProperties {
	
    private boolean logLevelVerbose;

	public boolean isLogLevelVerbose() {
		return logLevelVerbose;
	}

	public void setLogLevelVerbose(boolean logLevelVerbose) {
		this.logLevelVerbose = logLevelVerbose;
	}
	
	
	
}
