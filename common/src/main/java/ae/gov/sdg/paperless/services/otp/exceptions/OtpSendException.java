package ae.gov.sdg.paperless.services.otp.exceptions;

import ae.gov.sdg.paperless.services.common.exceptions.BusinessException;

public class OtpSendException extends BusinessException {
	private static final long serialVersionUID = -767334942233791339L;

	public OtpSendException(String message) {
        super(message);
    }
}
