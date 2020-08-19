package ae.gov.sdg.paperless.services.otp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class OtpValidationRequest {
    @ApiModelProperty(
            value = "TUUID as Key.",
            example = "3c66e73b-4bfe-4af1-b7f9-dcfddb13f6c2",
            required = false)
    @JsonProperty("key")
    private String key;

    @ApiModelProperty(
            value = "OTP",
            example = "3",
            required = true)
    @JsonProperty("userOtp")
    private String userOtp;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }


}
