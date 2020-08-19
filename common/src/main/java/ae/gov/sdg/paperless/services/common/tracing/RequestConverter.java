package ae.gov.sdg.paperless.services.common.tracing;

import java.util.UUID;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class RequestConverter extends ClassicConverter {
	
    @Override
    public String convert(final ILoggingEvent event) {
        return UUID.randomUUID().toString();
    }

}
