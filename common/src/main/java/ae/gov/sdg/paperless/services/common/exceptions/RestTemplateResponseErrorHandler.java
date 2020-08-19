package ae.gov.sdg.paperless.services.common.exceptions;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

@Component("restTemplateResponseErrorHandler")
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws ExternalApiException {
        try {
            super.handleError(response);

        } catch (HttpClientErrorException ex) {
            throw new ExternalApiException(ex, getSeverity(response));

        } catch (HttpServerErrorException ex) {
            throw new ExternalApiException(ex, Severity.HIGH);

        } catch (UnknownHttpStatusCodeException ex) {
            throw new ExternalApiException(ex, Severity.CRITICAL);

        } catch (IOException ex) {
            throw new ExternalApiException(ex, Severity.HIGH);
        }
    }

    private Severity getSeverity(ClientHttpResponse response) {

        try {
            if (response.getRawStatusCode() == 404) {
                return Severity.CRITICAL;
            } else if (response.getRawStatusCode() == 401) {
                return Severity.HIGH;
            }
        } catch (IOException e) {
        }
        return Severity.CRITICAL;
    }

}