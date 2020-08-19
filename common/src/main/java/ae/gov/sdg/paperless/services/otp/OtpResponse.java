package ae.gov.sdg.paperless.services.otp;

import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

public class OtpResponse extends BaseResponse {
    @ApiModelProperty(value = "UUID for the created OTP", example = "{\n" +
            "	\"UUID\" : \"3c66e73b-4bfe-4af1-b7f9-dcfddb13f6c2\",\n" +
            "}")
    private final String UUID;

    public OtpResponse(String uuid, boolean success) {
        super(success);
        this.UUID = uuid;
    }

    public String getUUID() {
        return UUID;
    }

}
