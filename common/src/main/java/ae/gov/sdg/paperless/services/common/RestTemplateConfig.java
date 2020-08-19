package ae.gov.sdg.paperless.services.common;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.GENERIC_REST_TEMPLATE;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.X_SESSION_ID;
import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.X_TRACE_ID;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import ae.gov.sdg.paperless.services.common.tracing.HttpRequestHeadersAppender;

/**
 * RestTemplateConfig.java
 * Purpose: Generate REST client templates to be injected into consumer components.
 *
 * @author c_chandra.bommise
 * @version 1.0
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    @Qualifier("restTemplateResponseErrorHandler")
    private ResponseErrorHandler restTemplateResponseErrorHandler;

    /**
     * Method for REST template generation
     *
     * @return Generates REST template.
     */
    @Bean
    @Qualifier(GENERIC_REST_TEMPLATE)
    public RestTemplate genericRestTemplate() {
    	final List<String> headers = addCommonHeaders();
        RestTemplate restTemplate = buildCommonAttributes(headers);
        return restTemplate;
    }

    @Bean
    @Qualifier("genericRestTemplateWithoutBasicAuth")
    public RestTemplate genericRestTemplateWithoutBasicAuth() {
    	final List<String> headers = addCommonHeaders();
        RestTemplate restTemplate = buildCommonAttributes(headers);
        return restTemplate;
    }

    /**
     * Helper method to set common attributes between different REST templates producers.
     * @param headers 
     *
     * @return REST template with default configurations like (timeout, interceptors, converters ...etc)
     */
    private RestTemplate buildCommonAttributes(List<String> headers) {

        SimpleClientHttpRequestFactory clientConfig = new SimpleClientHttpRequestFactory();
        clientConfig.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(60));
        clientConfig.setReadTimeout((int) TimeUnit.SECONDS.toMillis(60));

        ObjectMapper nonNullMapper = new ObjectMapper().setSerializationInclusion(
                JsonInclude.Include.NON_NULL);

        RestTemplate restTemplate = new RestTemplate(
                new BufferingClientHttpRequestFactory(clientConfig));

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(nonNullMapper));
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getInterceptors().add(new HttpRequestHeadersAppender(headers));
        return restTemplate;
    }
    
    /**
     * Add common request headers
     * 
     * @return
     */
    private List<String> addCommonHeaders() {
		final List<String> headers = new ArrayList<>();
    	headers.add(X_SESSION_ID);
    	headers.add(X_TRACE_ID);
		return headers;
	}
}
