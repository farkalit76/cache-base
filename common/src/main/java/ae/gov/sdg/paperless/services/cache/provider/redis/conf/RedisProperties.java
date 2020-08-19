package ae.gov.sdg.paperless.services.cache.provider.redis.conf;

import ae.gov.sdg.paperless.services.cache.common.util.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

/**
 * @author c_chandra.bommise
 * <p>
 * Properties for connecting to redis server.
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:redisconfig.yml")
public class RedisProperties {
    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActivePoolConnections;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdlePoolConnections;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdlePoolConnections;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitPoolTime;

    @Value("${spring.redis.timeout}")
    private int redisTimeout;

    @Value("${spring.redis.jedis.pool.time-to-live}")
    private String ttl;

    @Value("#{'${spring.redis.conf.type}'}")
    private RedisConfigType redisConfType;

    @Value("${spring.redis.sentinel.master}")
    private String sentinelMaster;

    @Value("${spring.redis.sentinel.nodes}")
    private Set<String> sentinelNodes;

    @Value("${spring.redis.cluster.nodes}")
    private Set<String> clusterNodes;

    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActivePoolConnections() {
        return maxActivePoolConnections;
    }

    public void setMaxActivePoolConnections(int maxActivePoolConnections) {
        this.maxActivePoolConnections = maxActivePoolConnections;
    }

    public int getMaxIdlePoolConnections() {
        return maxIdlePoolConnections;
    }

    public void setMaxIdlePoolConnections(int maxIdlePoolConnections) {
        this.maxIdlePoolConnections = maxIdlePoolConnections;
    }

    public int getMinIdlePoolConnections() {
        return minIdlePoolConnections;
    }

    public void setMinIdlePoolConnections(int minIdlePoolConnections) {
        this.minIdlePoolConnections = minIdlePoolConnections;
    }

    public long getMaxWaitPoolTime() {
        return maxWaitPoolTime;
    }

    public void setMaxWaitPoolTime(long maxWaitPoolTime) {
        this.maxWaitPoolTime = maxWaitPoolTime;
    }

    public int getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(int redisTimeout) {
        this.redisTimeout = redisTimeout;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getSentinelMaster() {
        return sentinelMaster;
    }

    public void setSentinelMaster(String sentinalMaster) {
        this.sentinelMaster = sentinalMaster;
    }

    public Set<String> getSentinelNodes() {
        return sentinelNodes;
    }

    public void setSentinelNodes(Set<String> sentinelNodes) {
        this.sentinelNodes = sentinelNodes;
    }

    public Set<String> getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(Set<String> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public RedisConfigType getRedisConfType() {
        return redisConfType;
    }

    public void setRedisConfType(RedisConfigType redisConfType) {
        this.redisConfType = redisConfType;
    }

    public boolean isSentinel() {
        return RedisConfigType.Sentinel == this.redisConfType;
    }

    public boolean isCluster() {
        return RedisConfigType.Cluster == this.redisConfType;
    }

    public boolean isStandalone() {
        return RedisConfigType.Standalone == this.redisConfType;
    }


    public int getDatabase() {
        return database;
    }

    public RedisProperties setDatabase(int database) {
        this.database = database;
        return this;
    }
}
