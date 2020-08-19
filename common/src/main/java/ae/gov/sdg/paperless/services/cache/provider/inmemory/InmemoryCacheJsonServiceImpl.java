package ae.gov.sdg.paperless.services.cache.provider.inmemory;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.JSON_CACHE_SERVICE;

@Component(value = JSON_CACHE_SERVICE)
public class InmemoryCacheJsonServiceImpl extends InmemoryCacheServiceImpl<JSONObject> {

    public InmemoryCacheJsonServiceImpl() {
        super(true);
    }
}
