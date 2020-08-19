package ae.gov.sdg.paperless.services.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenericAuthTokenCache {

    private static final Log log = LogFactory.getLog(GenericAuthTokenCache.class);

    private static GenericAuthTokenCache instance = new GenericAuthTokenCache();

    private Map<String, GenericAuthToken> cachedTokens = null;

    public static GenericAuthTokenCache getInstance() {
        return instance;
    }

    private GenericAuthTokenCache() {
        cachedTokens = new HashMap<String, GenericAuthToken>();
    }

    public synchronized GenericAuthToken getToken(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        GenericAuthToken token = cachedTokens.get(name);
        if (token != null && isExpiredToken(token)) {
            cachedTokens.remove(name);
            return null;
        }

        return token;
    }

    public synchronized void addToken(String name, GenericAuthToken token) {
        if (StringUtils.isEmpty(name) || token == null || isExpiredToken(token)) {
            return;
        }

        cachedTokens.put(name, token);
    }

    public synchronized void clearAllTokens() {
        cachedTokens.clear();
    }

    private boolean isExpiredToken(GenericAuthToken token) {
        if (token == null || token.getCreatedDate() == null || token.getExpiresIn() < 0) {
            log.error("The token Expired: " + token);
            return true;
        }

        Date current = new Date();
        Date expiryDate = DateUtils.addSeconds(token.getCreatedDate(), (int) token.getExpiresIn());

        return expiryDate.before(current);
    }

    public synchronized void clearToken(String name) {
        try {
            cachedTokens.remove(name);
        } catch (Exception ex) {

        }
    }
}