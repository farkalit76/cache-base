package ae.gov.sdg.paperless.services.cache.common.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ae.gov.sdg.paperless.services.cache.common", "${sdg.cache.packages}"})
public class CacheStoreConfig {
}
