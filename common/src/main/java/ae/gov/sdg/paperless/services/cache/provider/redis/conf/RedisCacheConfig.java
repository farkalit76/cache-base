package ae.gov.sdg.paperless.services.cache.provider.redis.conf;

import ae.gov.sdg.paperless.services.cache.common.util.CommonConstants;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties
public class RedisCacheConfig {

    private static final String SEPARATOR_COLON = ":";

    Logger log = LoggerFactory.getLogger(RedisCacheConfig.class);

    @Autowired
    private RedisProperties properties;

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean(CommonConstants.REDIS_TEMPLATE)
    public RedisTemplate<String, JSONObject> redisTemplate() {
        RedisTemplate<String, JSONObject> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(CommonConstants.REDIS_STRING_TEMPLATE)
    public RedisTemplate<String, String> redisStringTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(stringRedisSerializer());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(stringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * @return Create connection to redis with required configurations.
     */
    @Bean
    protected JedisConnectionFactory jedisConnectionFactory() {
        log.debug("jedisConnectionFactory entry");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //Maximum number of active connections that can be allocated from this pool at the same time
        poolConfig.setMaxTotal(properties.getMaxActivePoolConnections());
        //Number of connections to Redis that just sit there and do nothing
        poolConfig.setMaxIdle(properties.getMaxIdlePoolConnections());
        //Minimum number of idle connections to Redis - these can be seen as always open and ready to serve
        poolConfig.setMinIdle(properties.getMinIdlePoolConnections());
        //The maximum number of milliseconds that the pool will wait (when there are no available connections) for a connection to be returned before throwing an exception
        poolConfig.setMaxWaitMillis(properties.getMaxWaitPoolTime());
        //The minimum amount of time an object may sit idle in the pool before it is eligible for eviction by the idle object evictor
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        //The minimum amount of time a connection may sit idle in the pool before it is eligible for eviction by the idle connection evictor
        poolConfig.setSoftMinEvictableIdleTimeMillis(Duration.ofSeconds(10).toMillis());
        //Idle connection checking period
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(5).toMillis());
        //Maximum number of connections to test in each idle check
        poolConfig.setNumTestsPerEvictionRun(3);
        //Tests whether connection is dead when connection retrieval method is called
        poolConfig.setTestOnBorrow(true);
        //Tests whether connection is dead when returning a connection to the pool
        poolConfig.setTestOnReturn(true);
        //Tests whether connections are dead during idle periods
        poolConfig.setTestWhileIdle(true);
        poolConfig.setBlockWhenExhausted(false);

        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(properties.getRedisTimeout()));
        jedisClientConfiguration.usePooling().poolConfig(poolConfig);
        JedisConnectionFactory connectionFactory = null;
        if (properties.isSentinel()) {
            connectionFactory = new JedisConnectionFactory(redisSentinelConfig(), jedisClientConfiguration.build());
        } else if (properties.isCluster()) {
            connectionFactory = new JedisConnectionFactory(redisClusterConfig(), jedisClientConfiguration.build());
        } else {
            connectionFactory = new JedisConnectionFactory(redisStandaloneConfig(), jedisClientConfiguration.build());
        }
        log.debug("jedisConnectionFactory exit");
        return connectionFactory;
    }

    /**
     * Configuration of redis standalone.
     *
     * @return
     */
    private RedisStandaloneConfiguration redisStandaloneConfig() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(properties.getHost());
        standaloneConfig.setPort(properties.getPort());
        standaloneConfig.setPassword(RedisPassword.of(properties.getPassword()));
        standaloneConfig.setDatabase(properties.getDatabase());
        return standaloneConfig;
    }


    /**
     * Configuration of redis sentinal.
     *
     * @return
     */
    private RedisSentinelConfiguration redisSentinelConfig() {
        Set<RedisNode> sentinalNodes = properties.getSentinelNodes().stream().collect(Collectors.mapping(sentinelString -> getRedislNode(sentinelString), Collectors.toSet()));
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master(properties.getSentinelMaster());
        sentinelConfig.setSentinels(sentinalNodes);
        sentinelConfig.setPassword(RedisPassword.of(properties.getPassword()));
        return sentinelConfig;
    }


    /**
     * Configuration of redis cluster.
     *
     * @return
     */
    private RedisClusterConfiguration redisClusterConfig() {
        Set<RedisNode> clusterNodes = properties.getClusterNodes().stream().collect(Collectors.mapping(sentinelString -> getRedislNode(sentinelString), Collectors.toSet()));
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.setClusterNodes(clusterNodes);
        clusterConfiguration.setMaxRedirects(properties.getMaxRedirects());
        clusterConfiguration.setPassword(properties.getPassword());
        return clusterConfiguration;
    }

    /**
     * Prepare redis node based on string configuration
     *
     * @param
     * @return
     */
    private RedisNode getRedislNode(String redisHostPort) {
        String[] str = redisHostPort.split(SEPARATOR_COLON);
        RedisNode node = new RedisNode(str[0], Integer.valueOf(str[1]));
        return node;
    }

}
