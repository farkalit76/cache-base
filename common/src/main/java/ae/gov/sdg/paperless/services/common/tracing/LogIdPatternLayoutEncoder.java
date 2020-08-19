package ae.gov.sdg.paperless.services.common.tracing;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
 
public class LogIdPatternLayoutEncoder extends PatternLayoutEncoder {
 
    public static final String REQUEST_PATTERN = "logId";
 
    @Override
	public void start() {
        final PatternLayout patternLayout = new PatternLayout();
        patternLayout.getDefaultConverterMap().put(REQUEST_PATTERN, RequestConverter.class.getName());
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.setOutputPatternAsHeader(outputPatternAsHeader);
        patternLayout.start();
        this.layout = patternLayout;
        this.started = true;
    }
}