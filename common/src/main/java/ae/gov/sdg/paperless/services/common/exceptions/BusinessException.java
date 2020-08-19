package ae.gov.sdg.paperless.services.common.exceptions;

import static ae.gov.sdg.paperless.services.cache.common.util.CommonConstants.BUSINESS_EXCEPTION_ERROR_SEVERITY;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Severity severity = BUSINESS_EXCEPTION_ERROR_SEVERITY;

	public Severity getSeverity() {
		return severity;
	}

	public BusinessException() {
		super();
	}

	public BusinessException(Severity severity) {
		super();
		this.severity = severity;
	}

	public BusinessException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public BusinessException(String message, Severity severity) {
		super(message);
		this.severity = severity;
	}

	/**
	 * @param cause
	 */
	public BusinessException(Throwable cause, Severity severity) {
		super(cause);
		this.severity = severity;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause, Severity severity) {
		super(message, cause);
		this.severity = severity;
	}

}
