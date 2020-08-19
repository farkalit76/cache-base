package ae.gov.sdg.paperless.services.cache.provider.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.REDIS_STRING_TEMPLATE;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.STRING_CACHE_SERVICE;

@Component(value = STRING_CACHE_SERVICE)
public class RedisStringCacheServiceImpl extends RedisCacheServiceImpl<String> {

    @Resource(name = REDIS_STRING_TEMPLATE)
    private HashOperations<String, String, String> hashOperations;

    @Resource(name = REDIS_STRING_TEMPLATE)
    private ListOperations<String, String> listOperations;

    @Resource(name = REDIS_STRING_TEMPLATE)
    private SetOperations<String, String> setOperations;

    @Resource(name = REDIS_STRING_TEMPLATE)
    private ValueOperations<String, String> valueOperations;

    @Autowired
    @Qualifier(REDIS_STRING_TEMPLATE)
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public HashOperations<String, String, String> getHashOperations() {
        return hashOperations;
    }

    @Override
    public ListOperations<String, String> getListOperations() {
        return listOperations;
    }

    @Override
    public SetOperations<String, String> getSetOperations() {
        return setOperations;
    }

    @Override
    public ValueOperations<String, String> getValueOperations() {
        return valueOperations;
    }

    @Override
    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

}
