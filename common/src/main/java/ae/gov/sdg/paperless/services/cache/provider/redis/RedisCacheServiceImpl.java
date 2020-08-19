package ae.gov.sdg.paperless.services.cache.provider.redis;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import ae.gov.sdg.paperless.services.cache.common.conf.CacheConfigurationManager;
import ae.gov.sdg.paperless.services.cache.common.util.CommonUtil;
import ae.gov.sdg.paperless.services.cache.provider.store.CacheService;

@SuppressWarnings("unchecked")
public abstract class RedisCacheServiceImpl<T> implements CacheService<T> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RedisCacheServiceImpl.class);

    private static final String REDIS = "Redis";

    public abstract HashOperations<String, String, T> getHashOperations();

    public abstract ListOperations<String, T> getListOperations();

    public abstract SetOperations<String, T> getSetOperations();

    public abstract ValueOperations<String, T> getValueOperations();

    public abstract RedisTemplate<String, T> getRedisTemplate();

    @Autowired
    private CacheConfigurationManager cacheConfigManager;

    public String cacheProvider() {
        return REDIS;
    }

    @Override
    public boolean exists(String key) {
    	LOGGER.info("Start cache exists for key:{} ", CommonUtil.encodeBase64String(key));
        return getRedisTemplate().hasKey(key);
    }

    @Override
    public Map<String, String> metadata(String key) {
    	LOGGER.info("Start metadata for key:{} ", CommonUtil.encodeBase64String(key));
        final Long time = getRedisTemplate().getExpire(key, TimeUnit.MINUTES);
        final DataType type = getRedisTemplate().type(key);
        return of(new String[][]{
                {"ttl", time + ""},
                {"type", type.name()},
        }).collect(toMap(data -> data[0], data -> data[1]));
    }

    @Override
    public Set<String> dbKeys(String pattern) {
    	LOGGER.info("Start dbKeys for pattern:{} ",pattern);
        return getRedisTemplate().keys(pattern);
    }

    @Override
    public void add(String key, T value) {
    	String logKey = CommonUtil.encodeBase64String(key);
    	LOGGER.info("Start add for key:{}", logKey);
        final Duration duration = cacheConfigManager.expiryTimeForCache(key);
        add(key, value, duration.toMinutes());
        LOGGER.info("End add for key:{} with duration:{}", logKey, duration);
    }

    @Override
    public void add(String key, T value, long timeInMinutes) {
    	LOGGER.info("Start add for key:{} timeInMinutes:{}", CommonUtil.encodeBase64String(key), timeInMinutes);
        getValueOperations().set(key, value, timeInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public T get(String key) {
    	LOGGER.info("Start get for key:{}", CommonUtil.encodeBase64String(key));
        return getValueOperations().get(key);
    }

    @Override
    public void keyValuePut(String keySpace, String key, T value) {
		LOGGER.info("Start keyValuePut for keySpace:{} key:{}", CommonUtil.encodeBase64String(keySpace),
				CommonUtil.encodeBase64String(key));
        getHashOperations().put(keySpace, key, value);
        cacheExpiry(getRedisTemplate(), keySpace);
    }

    @Override
    public void keyValuePut(String keySpace, Map<String, T> value) {
    	LOGGER.info("Start keyValuePut for keySpace:{}", CommonUtil.encodeBase64String(keySpace));
        getHashOperations().putAll(keySpace, value);
        cacheExpiry(getRedisTemplate(), keySpace);
    }

    @Override
    public void keyValuePut(String keySpace, String key, T value, long timeInMinutes) {
		LOGGER.info("Start keyValuePut for keySpace:{} key:{} timeInMinutes:{}",
				CommonUtil.encodeBase64String(keySpace), CommonUtil.encodeBase64String(key), timeInMinutes);
        getHashOperations().put(keySpace, key, value);
        getRedisTemplate().expire(keySpace, timeInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public void keyValuePut(String keySpace, Map<String, T> value, long timeInMinutes) {
    	LOGGER.info("Start keyValuePut for keySpace:{} timeInMinutes:{}", CommonUtil.encodeBase64String(keySpace), timeInMinutes);
        getHashOperations().putAll(keySpace, value);
        getRedisTemplate().expire(keySpace, timeInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public T keyValueGet(String keySpace, String key) {
		LOGGER.info("Start keyValueGet for keySpace:{} key:{}", CommonUtil.encodeBase64String(keySpace),
				CommonUtil.encodeBase64String(key));
        return getHashOperations().get(keySpace, key);
    }

    @Override
    public Set<String> keys(String keySpace) {
    	LOGGER.info("Start keys for keySpace:{}", CommonUtil.encodeBase64String(keySpace));
        return getHashOperations().keys(keySpace);
    }

    @Override
    public Map<String, T> keyValueEntries(String keySpace) {
    	LOGGER.info("Start keyValueEntries for keySpace:{}", CommonUtil.encodeBase64String(keySpace));
        return getHashOperations().entries(keySpace);
    }

    @Override
    public Boolean delete(String keySpace) {
    	LOGGER.info("Start delete for keySpace:{}", CommonUtil.encodeBase64String(keySpace));
        return getRedisTemplate().delete(keySpace);
    }

    @Override
    public Long delete(String keySpace, String key) {
		LOGGER.info("Start delete for keySpace:{} key:{}", CommonUtil.encodeBase64String(keySpace),
				CommonUtil.encodeBase64String(key));
        return getHashOperations().delete(keySpace, key);
    }

    private void cacheExpiry(RedisTemplate<String, T> template, String keySpace) {
        final Duration duration = cacheConfigManager.expiryTimeForCache(keySpace);
        template.expire(keySpace, duration.toMinutes(), TimeUnit.MINUTES);
    }

    @Override
    public List<T> fetchAllFromQueue(String key) {
    	LOGGER.info("Start fetchAllFromQueue for key:{}", CommonUtil.encodeBase64String(key));
        Long size = getListOperations().size(key);
        return getListOperations().range(key, 0, size);
    }

    @Override
    public T fetchFromIndex(String key, Integer index) {
    	LOGGER.info("Start fetchFromIndex for key:{} index:{}", CommonUtil.encodeBase64String(key), index);
        return getListOperations().index(key, index);
    }

    @Override
    public void pushInQueue(String key, T value) {
    	LOGGER.info("Start pushInQueue for key:{} ", CommonUtil.encodeBase64String(key));
        getListOperations().rightPush(key, value);
        cacheExpiry(getRedisTemplate(), key);
    }

    @Override
    public void pushInQueue(String key, T... value) {
    	LOGGER.info("Start pushInQueue for key:{}", CommonUtil.encodeBase64String(key));
        getListOperations().rightPushAll(key, value);
        cacheExpiry(getRedisTemplate(), key);
    }

    @Override
    public void pushInQueue(String key, long timeInMinutes, T value) {
    	LOGGER.info("Start pushInQueue for key:{} timeInMinutes:{}", CommonUtil.encodeBase64String(key), timeInMinutes);
        getListOperations().rightPush(key, value);
        getRedisTemplate().expire(key, timeInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public void pushInQueue(String key, long timeInMinutes, T... value) {
    	LOGGER.info("Start pushInQueue for key:{} timeInMinutes:{}", CommonUtil.encodeBase64String(key), timeInMinutes);
        getListOperations().rightPushAll(key, value);
        getRedisTemplate().expire(key, timeInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public T popFromStart(String key) {
    	LOGGER.info("Start popFromStart for key:{}", CommonUtil.encodeBase64String(key));
        return getListOperations().leftPop(key);
    }

    @Override
    public T popFromEnd(String key) {
    	LOGGER.info("Start popFromEnd for key:{}", CommonUtil.encodeBase64String(key));
        return getListOperations().rightPop(key);
    }

    @Override
    public Long removeFromQueue(String key, T value, Integer number) {
    	LOGGER.info("Start removeFromQueue for key:{} value:{} number:{}", CommonUtil.encodeBase64String(key), value, number);
        if (number == null || number == 0) {
            number = 1;
        }
        return getListOperations().remove(key, number, value);
    }

    @Override
    public Long addToSet(String key, T... value) {
    	LOGGER.info("Start addToSet for key:{}", CommonUtil.encodeBase64String(key));
        Long count = getSetOperations().add(key, value);
        cacheExpiry(getRedisTemplate(), key);
        return count;
    }

    @Override
    public Long addToSet(String key, long timeInMinutes, T... value) {
    	LOGGER.info("Start addToSet for key:{} timeInMinutes:{}", CommonUtil.encodeBase64String(key), timeInMinutes);
        Long count = getSetOperations().add(key, value);
        getRedisTemplate().expire(key, timeInMinutes, TimeUnit.MINUTES);
        return count;
    }

    @Override
    public Long removeFromSet(String key, T... value) {
    	LOGGER.info("Start removeFromSet for key:{}", CommonUtil.encodeBase64String(key));
        return getSetOperations().remove(key, value);
    }

    @Override
    public T popFromSet(String key) {
    	LOGGER.info("Start popFromSet for key:{}", CommonUtil.encodeBase64String(key));
        return getSetOperations().pop(key);
    }

    @Override
    public Set<T> fetchAllFromSet(String key) {
    	LOGGER.info("Start fetchAllFromSet for key:{}", CommonUtil.encodeBase64String(key));
        return getSetOperations().members(key);
    }

}
