package ae.gov.sdg.paperless.services.cache.common.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static ae.gov.sdg.paperless.services.cache.common.util.CacheLifeTimeConfigurer.getTimeToLive;

@Component
public class CacheConfigurationManager {

    @Autowired
    private CacheConfigurationProperties cacheConfiguration;

    public Duration expiryTimeForCache(String cacheName) {
        final Map<String, String> publicCacheTtls = cacheConfiguration.getPubliccache();
        Duration timeToLive;
        if (publicCacheTtls.containsKey(cacheName)) {
            timeToLive = getTimeToLive(publicCacheTtls.get(cacheName));
        } else {
            timeToLive = getTimeToLive(cacheConfiguration.getDefaultcache());
        }
        return timeToLive;

    }

}
