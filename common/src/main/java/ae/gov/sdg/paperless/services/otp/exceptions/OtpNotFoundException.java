package ae.gov.sdg.paperless.services.otp.exceptions;

import ae.gov.sdg.paperless.services.common.exceptions.BusinessException;

public class OtpNotFoundException extends BusinessException {

    private static final long serialVersionUID = 7195213272155233583L;

    public OtpNotFoundException(String errorMsg) {
        super(errorMsg);
    }


}
