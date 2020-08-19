package ae.gov.sdg.paperless.services.common.exceptions;

public class ExternalApiException extends RuntimeException {

    private static final long serialVersionUID = 1622763433495491520L;

    private Severity severity = Severity.CRITICAL;

    public Severity getSeverity() {
        return severity;
    }

    public ExternalApiException() {
        super();
    }

    public ExternalApiException(Severity severity) {
        super();
        this.severity = severity;
    }

    /**
     * @param message
     */
    public ExternalApiException(String message, Severity severity) {
        super(message);
        this.severity = severity;
    }

    /**
     * @param cause
     */
    public ExternalApiException(Throwable cause, Severity severity) {
        super(cause);
        this.severity = severity;
    }

    /**
     * @param message
     * @param cause
     */
    public ExternalApiException(String message, Throwable cause, Severity severity) {
        super(message, cause);
        this.severity = severity;
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ExternalApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                                Severity serverity) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.severity = serverity;
    }

}