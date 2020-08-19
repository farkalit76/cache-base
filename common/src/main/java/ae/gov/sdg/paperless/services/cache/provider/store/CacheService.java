package ae.gov.sdg.paperless.services.cache.provider.store;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public interface CacheService<T> {

    String IN_MEMORY = "In memory";

    default String cacheProvider() {
        return IN_MEMORY;
    }

    boolean exists(String key);

    Map<String, String> metadata(String key);

    Set<String> dbKeys(String pattern);

    // Value operations
    // POST /add key,value in body
    void add(String key, T value);

    // POST /add key,value, ttl in body
    void add(String key, T value, long timeInMinutes);

    // GET queryparam
    T get(String key);

    // Hash Operations
    // POST /addKeyValue keySpace,key,value in body
    void keyValuePut(String keySpace, String key, T value);

    // POST /addKeyValue keySpace,key,value in body
    void keyValuePut(String keySpace, Map<String, T> value);

    // POST /addKeyValue keySpace,key,value in body
    void keyValuePut(String keySpace, String key, T value, long timeInMinutes);

    // POST /addKeyValue keySpace,key,value in body
    void keyValuePut(String keySpace, Map<String, T> value, long timeInMinutes);

    // GET /getkeyvalue
    T keyValueGet(String keySpace, String key);

    // GET /keys
    Set<String> keys(String keySpace);

    // GET /entries
    Map<String, T> keyValueEntries(String keySpace);

    // DELETE
    Boolean delete(String keySpace);

    // DELETE /key
    Long delete(String keySpace, String key);

    // List Operations
    //POST /pushInQueue
    void pushInQueue(String key, T value);

    void pushInQueue(String key, T... value);

    void pushInQueue(String key, long timeInMinutes, T value);

    void pushInQueue(String key, long timeInMinutes, T... value);

    T popFromEnd(String key);

    T popFromStart(String key);

    T fetchFromIndex(String key, Integer index);

    List<T> fetchAllFromQueue(String key);

    Long removeFromQueue(String key, T value, Integer number);

    // Set operations
    Long addToSet(String key, T... value);

    Long addToSet(String key, long timeInMinutes, T... value);

    Long removeFromSet(String key, T... value);

    T popFromSet(String key);

    Set<T> fetchAllFromSet(String key);

}
