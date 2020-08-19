package ae.gov.sdg.paperless.services.otp.exceptions;

import ae.gov.sdg.paperless.services.common.exceptions.BusinessException;

public class AttemptsExceededException extends BusinessException {

    private static final long serialVersionUID = -7906915559251500029L;

    public AttemptsExceededException(String errorMsg) {
        super(errorMsg);
    }

}
