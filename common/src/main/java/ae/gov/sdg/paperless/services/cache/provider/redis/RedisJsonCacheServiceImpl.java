package ae.gov.sdg.paperless.services.cache.provider.redis;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.JSON_CACHE_SERVICE;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.REDIS_TEMPLATE;


@Component(value = JSON_CACHE_SERVICE)
public class RedisJsonCacheServiceImpl extends RedisCacheServiceImpl<JSONObject> {

    @Resource(name = REDIS_TEMPLATE)
    private HashOperations<String, String, JSONObject> hashOperations;

    @Resource(name = REDIS_TEMPLATE)
    private ListOperations<String, JSONObject> listOperations;

    @Resource(name = REDIS_TEMPLATE)
    private SetOperations<String, JSONObject> setOperations;

    @Resource(name = REDIS_TEMPLATE)
    private ValueOperations<String, JSONObject> valueOperations;

    @Autowired
    @Qualifier(REDIS_TEMPLATE)
    private RedisTemplate<String, JSONObject> redisTemplate;

    @Override
    public HashOperations<String, String, JSONObject> getHashOperations() {
        return hashOperations;
    }

    @Override
    public ListOperations<String, JSONObject> getListOperations() {
        return listOperations;
    }

    @Override
    public SetOperations<String, JSONObject> getSetOperations() {
        return setOperations;
    }

    @Override
    public ValueOperations<String, JSONObject> getValueOperations() {
        return valueOperations;
    }

    @Override
    public RedisTemplate<String, JSONObject> getRedisTemplate() {
        return redisTemplate;
    }
}
