package ae.gov.sdg.paperless.services.cache.common.conf;

import ae.gov.sdg.paperless.services.cache.common.util.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "dubainow.cache.ttl")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:cacheconfig.yml")
public class CacheConfigurationProperties {

    private Map<String, String> publiccache = new HashMap<String, String>();

    private String defaultcache;

    public Map<String, String> getPubliccache() {
        return publiccache;
    }

    public void setPubliccache(Map<String, String> publiccache) {
        this.publiccache = publiccache;
    }

    public String getDefaultcache() {
        return defaultcache;
    }

    public void setDefaultcache(String defaultcache) {
        this.defaultcache = defaultcache;
    }
}