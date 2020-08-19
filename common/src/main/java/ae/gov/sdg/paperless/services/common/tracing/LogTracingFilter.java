package ae.gov.sdg.paperless.services.common.tracing;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.*;

@Component
public class LogTracingFilter implements Filter {

    private static final String MS_NAME = "CS";
    private static final String LOG_SPAN_ID = "spanId";

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            String spanId = new StringBuilder(MS_NAME).append(RandomStringUtils.randomAlphabetic(6)).toString();
            MDC.put(LOG_SPAN_ID, spanId);
            String sessionId = request.getHeader(X_SESSION_ID);
            String requestCid = request.getHeader(X_TRACE_ID);
            final String appVersion = request.getHeader(X_DN_APP_VERSION);
			final String platform = request.getHeader(X_DN_PLATFORM);
            // add cid to the MDC
            MDC.put(X_TRACE_ID, requestCid);
            MDC.put(X_SESSION_ID, sessionId);
            MDC.put(X_DN_APP_VERSION, appVersion);
			MDC.put(X_DN_PLATFORM, platform);
        }

        try {
            // call filter(s) upstream for the real processing of the request
            chain.doFilter(req, res);
        } finally {
            // remove the cid from the MDC
            MDC.remove(X_TRACE_ID);
            MDC.remove(X_SESSION_ID);
            MDC.remove(LOG_SPAN_ID);
            MDC.remove(X_DN_APP_VERSION);
            MDC.remove(X_DN_PLATFORM);
        }
    }

    @Override
    public void destroy() {
    }

}