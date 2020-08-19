package ae.gov.sdg.paperless.services.cache.provider.inmemory;

import java.util.HashSet;
import java.util.Set;

import static java.lang.System.currentTimeMillis;

public class GeneralCacheAttributes {

    private String name;
    private long creationTime;
    private String type;
    private long ttl;
    private boolean isJson;

    private static Set<GeneralCacheAttributes> CACHES_ATTRIBUTES = new HashSet<>();

    public GeneralCacheAttributes(String key, String type, long ttl, boolean isJson) {
        this.name = key;
        this.type = type;
        this.ttl = ttl;
        this.isJson = isJson;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExpired() {
        return (currentTimeMillis() - creationTime) > ttl * 60 * 1000;
    }

    public boolean isJson() {
        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

	public static Set<GeneralCacheAttributes> getCacheAttributes() {
		return CACHES_ATTRIBUTES;
	}

}
