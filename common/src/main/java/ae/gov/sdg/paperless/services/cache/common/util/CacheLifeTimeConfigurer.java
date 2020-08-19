package ae.gov.sdg.paperless.services.cache.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * @author c_chandra.bommise
 * <p>
 * Derives the expire duration of cache based on which keys in the cache gets expired.
 */
public class CacheLifeTimeConfigurer {

    private static final Logger log = LoggerFactory.getLogger(CacheLifeTimeConfigurer.class);

    /**
     * @param expression
     * @return Get the duration of cache expire.
     */
    public static Duration getTimeToLive(String expression) {
        log.debug("Time to live entry:" + expression);
        if (CacheLifeTimeType.infinite.name().equals(expression)) {
            return Duration.ofMillis(-1);
        } else {
            String[] array = expression.split(CommonConstants.SEPARATOR_COLON);

            if (expression.startsWith(CacheLifeTimeType.milliseconds.name())) {
                return Duration.ofMillis(Long.valueOf(array[array.length - 1]));

            } else if (expression.startsWith(CacheLifeTimeType.seconds.name())) {
                return Duration.ofSeconds(Long.valueOf(array[array.length - 1]));

            } else if (expression.startsWith(CacheLifeTimeType.minutes.name())) {
                return Duration.ofMinutes(Long.valueOf(array[array.length - 1]));

            } else if (expression.startsWith(CacheLifeTimeType.hours.name())) {
                return Duration.ofHours(Long.valueOf(array[array.length - 1]));

            } else if (expression.startsWith(CacheLifeTimeType.days.name())) {
                return Duration.ofDays(Long.valueOf(array[array.length - 1]));

            } else if (expression.startsWith(CacheLifeTimeType.weeks.name())) {
                return Duration.ofDays(Long.valueOf(array[array.length - 1]) * 7);

            } else if (expression.startsWith(CacheLifeTimeType.months.name())) {
                return Duration.ofDays(Long.valueOf(array[array.length - 1]) * 30);

            } else if (expression.startsWith(CacheLifeTimeType.infinite.name())) {
                return Duration.ofSeconds(Long.valueOf(array[array.length - 1]));

            } else if (expression.startsWith(CacheLifeTimeType.custom.name())) {
                try {
                    ICustomCacheTimeToLive ttl =
                            (ICustomCacheTimeToLive) Class.forName(array[array.length - 1]).newInstance();
                    return ttl.getDurationInMillis();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    log.error("Error while initializing caches due to improper cache configuration setup.");
                    throw new RuntimeException("Error while initializing caches due to improper ttl expression.");
                }
            }
        }
        return Duration.ofMillis(1);
    }

}
