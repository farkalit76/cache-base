package ae.gov.sdg.paperless.services.common.tracing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ae.gov.sdg.paperless.services.common.conf.LoggingProperties;
import ae.gov.sdg.paperless.util.LogMessage;
import ae.gov.sdg.paperless.util.LogMessageBuilder;
 
@Aspect
@Component
public class TraceAspectLogger {
    private static final Logger logger = LoggerFactory.getLogger(TraceAspectLogger.class);
    
    @Autowired
    private LoggingProperties loggingProperties;
 
    @Around("execution(* ae.gov.sdg.paperless..*(..)) && @annotation(ae.gov.sdg.paperless.services.common.tracing.TraceLog)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object response = null;
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        try {
        	StringBuilder builder = new StringBuilder();
        	builder.append("Starting - ").append(method);
        	if(loggingProperties.isLogLevelVerbose()) {
	            if (args != null && args.length > 0) {
	            	builder.append(" with parameters: ");
		                for (Object obj : args) {
		                    builder.append(obj);
		                }
	            } 
        	}
            LogMessage messageBuilder = new LogMessageBuilder().setRequestBody(builder.toString()).build();
            logger.info(messageBuilder.toString());
            response = joinPoint.proceed();
            logger.info("Ending - " + method);
            return response;
        } catch (Exception e) {
            logger.error("Exception while invoking method - " + method);
            throw e;
        }
    }
}