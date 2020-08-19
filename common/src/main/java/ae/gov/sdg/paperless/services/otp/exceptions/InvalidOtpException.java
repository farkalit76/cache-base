package ae.gov.sdg.paperless.services.otp.exceptions;

import ae.gov.sdg.paperless.services.common.exceptions.BusinessException;

public class InvalidOtpException extends BusinessException {

    private static final long serialVersionUID = -1760258596674127108L;

    public InvalidOtpException(String errorMsg) {
        super(errorMsg);
    }
}
