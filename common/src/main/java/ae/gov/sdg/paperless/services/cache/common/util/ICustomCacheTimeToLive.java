package ae.gov.sdg.paperless.services.cache.common.util;

import java.time.Duration;

/**
 * @author c_chandra.bommise
 * <p>
 * Interface for deriving custom cache expire time.
 */
public interface ICustomCacheTimeToLive {

    Duration getDurationInMillis();

}
