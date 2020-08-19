package ae.gov.sdg.paperless.services.cache.provider.inmemory;

import org.springframework.stereotype.Component;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.STRING_CACHE_SERVICE;

@Component(value = STRING_CACHE_SERVICE)
public class InmemoryCacheStringServiceImpl extends InmemoryCacheServiceImpl<String> {

    public InmemoryCacheStringServiceImpl() {
        super(false);
    }
}
