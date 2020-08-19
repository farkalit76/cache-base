package ae.gov.sdg.paperless.services.otp.exceptions;

import ae.gov.sdg.paperless.services.common.exceptions.BusinessException;

public class OtpExpiredException extends BusinessException {

    private static final long serialVersionUID = -3335303925377302010L;

    public OtpExpiredException(String errorMsg) {
        super(errorMsg);
    }

}
