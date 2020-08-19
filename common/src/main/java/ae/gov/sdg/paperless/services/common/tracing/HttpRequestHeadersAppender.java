package ae.gov.sdg.paperless.services.common.tracing;

import java.io.IOException;
import java.util.List;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class HttpRequestHeadersAppender implements ClientHttpRequestInterceptor {
	private final List<String> appendHeaders;
	
	public HttpRequestHeadersAppender(final List<String> appendHeaders) {
		this.appendHeaders = appendHeaders;
	}

	@Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
	    final HttpHeaders headers = request.getHeaders();
		appendHeaders.forEach(appendHeader -> {
			headers.add(appendHeader, MDC.get(appendHeader));
		});
        return execution.execute(request, body);
    }
}