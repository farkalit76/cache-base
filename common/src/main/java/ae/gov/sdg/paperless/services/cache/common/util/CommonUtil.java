package ae.gov.sdg.paperless.services.cache.common.util;


import java.util.Base64;

import org.springframework.util.StringUtils;

public class CommonUtil {

    /***
     * This method is used to encode string value in Base-64
     * @param str
     * @return "" is str value is null or base-64 encoded string
     */
    public static String encodeBase64String(String str) {
        return !StringUtils.isEmpty(str) ? Base64.getEncoder().encodeToString(str.getBytes()) : "";
    }

}