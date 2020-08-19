package ae.gov.sdg.paperless.services.cache.provider.inmemory;

import ae.gov.sdg.paperless.services.cache.common.util.CacheLifeTimeConfigurer;
import ae.gov.sdg.paperless.services.cache.provider.store.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static ae.gov.sdg.paperless.services.cache.provider.inmemory.GeneralCacheAttributes.getCacheAttributes;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;


public abstract class InmemoryCacheServiceImpl<T> implements CacheService<T> {
    private static final Logger log = LoggerFactory.getLogger(CacheLifeTimeConfigurer.class);
    public static final String BASIC = "Basic";
    public static final String MAP = "Map";
    private static final String QUEUE = "Queue";
    private static final String SET = "Set";
    private final boolean isJson;
    private long DEFAULT_TTL = 30;

    private final Map<String, T> basicCache = new HashMap<>();
    private final Map<String, Map<String, T>> mapCache = new HashMap<>();
    private final Map<String, List<T>> queueCache = new HashMap<>();
    private final Map<String, Set<T>> setCache = new HashMap<>();

    public InmemoryCacheServiceImpl(boolean isJson) {
        this.isJson = isJson;
        initializeCache();
    }

    private void initializeCache() {
        Thread cleanUpThread = new Thread(new Cleanup());
        cleanUpThread.setDaemon(true);
        cleanUpThread.start();
    }

    @Override
    public boolean exists(String key) {
        return keysFromAttributes()
                .contains(key);
    }

    @Override
    public Map<String, String> metadata(String key) {
    	Optional<GeneralCacheAttributes> cacheAttr = cacheAttribute(key);
    	if(cacheAttr.isPresent()) {
    		final GeneralCacheAttributes o = cacheAttr.get();
	        return of(new String[][]{
	                {"Type", o.getType()},
	                {"TTL", o.getTtl() + ""},
	                {"Creation", o.getCreationTime() + ""},
	                {"Expired", o.isExpired() + ""},
	
	        }).collect(toMap(data -> data[0], data -> data[1]));
    	} else {
    		return null;
    	}
    }

    @Override
    public Set<String> dbKeys(String pattern) {
        return keysFromAttributes()
                .stream()
                .filter(k -> pattern.equals("*") || k.startsWith(pattern))
                .collect(toSet());
    }

    private Set<String> keysFromAttributes() {
        return getCacheAttributes().stream()
                .map(GeneralCacheAttributes::getName)
                .collect(toSet());
    }

    @Override
    public void add(String key, T value) {
        add(key, value, DEFAULT_TTL);
    }

    @Override
    public void add(String key, T value, long timeInMinutes) {
        propagateCacheAttributes(key, BASIC, timeInMinutes);
        basicCache.put(key, value);
    }

    private void propagateCacheAttributes(String key, String type, long ttl) {
        final GeneralCacheAttributes o = cacheAttribute(key)
                .orElse(new GeneralCacheAttributes(key, type, ttl, isJson));
        o.setCreationTime(currentTimeMillis());
        getCacheAttributes().add(o);
    }

    @Override
    public T get(String key) {
        return basicCache.get(key);
    }

    @Override
    public void keyValuePut(String keySpace, String key, T value) {
        keyValuePut(keySpace, key, value, DEFAULT_TTL);
    }

    @Override
    public void keyValuePut(String keySpace, Map<String, T> value) {
        keyValuePut(keySpace, value, DEFAULT_TTL);
    }

    @Override
    public void keyValuePut(String keySpace, String key, T value, long timeInMinutes) {
        propagateCacheAttributes(keySpace, MAP, timeInMinutes);
        final Map<String, T> o = ofNullable(mapCache.get(keySpace)).orElse(new HashMap<>());
        o.put(key, value);
        mapCache.put(keySpace, o);
    }

    @Override
    public void keyValuePut(String keySpace, Map<String, T> values, long timeInMinutes) {
        propagateCacheAttributes(keySpace, MAP, timeInMinutes);
        final Map<String, T> o = ofNullable(mapCache.get(keySpace)).orElse(new HashMap<>());
        o.putAll(values);
        mapCache.put(keySpace, o);
    }

    @Override
    public T keyValueGet(String keySpace, String key) {
        final Map<String, T> d = mapCache.get(keySpace);
        if (d == null) {
            return null;
        }
        return d.getOrDefault(key, null);
    }

    @Override
    public Set<String> keys(String keySpace) {
        final Map<String, T> d = mapCache.get(keySpace);
        if (d == null) {
            return null;
        }
        return d.keySet();
    }

    @Override
    public Map<String, T> keyValueEntries(String keySpace) {
        return mapCache.get(keySpace);
    }

    @Override
    public Boolean delete(String keySpace) {
    	Optional<GeneralCacheAttributes> cacheAttr = cacheAttribute(keySpace);
    	GeneralCacheAttributes gc = cacheAttr.orElse(null);
        if (gc == null) {
            return false;
        }
        getCacheAttributes().remove(gc);
        switch (gc.getType()) {
            case BASIC:
                basicCache.remove(keySpace);
                break;
            case MAP:
                mapCache.remove(keySpace);
                break;
            case QUEUE:
                queueCache.remove(keySpace);
                break;
            case SET:
                setCache.remove(keySpace);
                break;
        }
        return true;
    }

    private Optional<GeneralCacheAttributes> cacheAttribute(String keySpace) {
        return getCacheAttributes().stream()
                .filter(c -> c.getName().equals(keySpace))
                .findFirst();
    }

    @Override
    public Long delete(String keySpace, String key) {
        return mapCache.get(keySpace).remove(key) != null ? 1l : 0l;
    }

    @Override
    public void pushInQueue(String key, T value) {
        pushInQueue(key, DEFAULT_TTL, value);
    }

    @Override
    public void pushInQueue(String key, T... value) {
        pushInQueue(key, DEFAULT_TTL, value);
    }

    @Override
    public void pushInQueue(String key, long timeInMinutes, T value) {
        propagateCacheAttributes(key, QUEUE, timeInMinutes);
        final List<T> o = ofNullable(queueCache.get(key)).orElse(new ArrayList<>());
        o.add(value);
        queueCache.put(key, o);
    }

    @Override
    public void pushInQueue(String key, long timeInMinutes, T... value) {
        propagateCacheAttributes(key, QUEUE, timeInMinutes);
        final List<T> o = ofNullable(queueCache.get(key)).orElse(new ArrayList<>());
        o.addAll(asList(value));
        queueCache.put(key, o);
    }

    @Override
    public T popFromEnd(String key) {
        final List<T> d = queueCache.get(key);
        if (d == null) {
            return null;
        }
        return d.remove(d.size() - 1);
    }

    @Override
    public T popFromStart(String key) {
        final List<T> d = queueCache.get(key);
        if (d == null) {
            return null;
        }
        return d.remove(0);
    }

    @Override
    public T fetchFromIndex(String key, Integer index) {
        final List<T> d = queueCache.get(key);
        if (d == null) {
            return null;
        }
        return d.get(index);
    }

    @Override
    public List<T> fetchAllFromQueue(String key) {
        return queueCache.get(key);
    }

    @Override
    public Long removeFromQueue(String key, T value, Integer number) {
        final List<T> d = queueCache.get(key);
        if (d == null) {
            return 0l;
        }
        d.remove(value);
        return 1l;
    }

    @Override
    public Long addToSet(String key, T... value) {
        return addToSet(key, DEFAULT_TTL, value);
    }

    @Override
    public Long addToSet(String key, long timeInMinutes, T... value) {
        propagateCacheAttributes(key, SET, timeInMinutes);
        final Set<T> o = ofNullable(setCache.get(key)).orElse(new HashSet<>());
        o.addAll(asList(value));
        setCache.put(key, o);
        return 1l;
    }

    @Override
    public Long removeFromSet(String key, T... value) {
        final Set<T> d = setCache.get(key);
        if (d == null) {
            return 0l;
        }
        d.removeAll(asList(value));
        return 1l;
    }

    @Override
    public T popFromSet(String key) {
        final Set<T> d = setCache.get(key);
        if (d == null) {
            return null;
        }
        Optional<T> value = d.stream().findFirst();
        T e = value.orElse(null);
        d.remove(e);
        return e;
    }

    @Override
    public Set<T> fetchAllFromSet(String key) {
        return setCache.get(key);
    }

    private class Cleanup implements Runnable {

        public Cleanup() {
        }

        @Override
        public void run() {
        	boolean cleanUp = true;
            while (cleanUp) {
                try {
                    SECONDS.sleep(15);
                    cleanUp = executeDelete();
                    log.info("Cleaning up cache");
                } catch (InterruptedException ie) {
                	Thread.currentThread().interrupt();
                	cleanUp = false;
                } 
            }
        }

        private boolean executeDelete() {
        	boolean itemsExists = true;
            final Set<GeneralCacheAttributes> expiredCache = getCacheAttributes().stream()
                    .filter(c -> c.isJson() == isJson)
                    .filter(GeneralCacheAttributes::isExpired).collect(toSet());
            expiredCache.forEach((c) -> {
                log.info("Delete Cache - {} from memory", c.getName());
                getCacheAttributes().remove(c);
                switch (c.getType()) {
                    case BASIC:
                        basicCache.remove(c.getName());
                        break;
                    case MAP:
                        mapCache.remove(c.getName());
                        break;
                    case QUEUE:
                        queueCache.remove(c.getName());
                        break;
                    case SET:
                        setCache.remove(c.getName());
                        break;
                }
            });
            if(CollectionUtils.isEmpty(expiredCache)) {
            	itemsExists = false;
				return itemsExists;
            }
			return itemsExists;

        }
    }
}
