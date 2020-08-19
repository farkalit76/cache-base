package ae.gov.sdg.paperless.services.otp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class OtpRequest {

    @ApiModelProperty(
            value = "The mobile number of user.",
            example = "05********",
            required = false)
    @JsonProperty("mobileNum")
    private String mobileNum;

    @ApiModelProperty(
            value = "The message template to be sent.",
            example = "Your otp is {otp}",
            required = true)
    @JsonProperty("message")
    private String message;

    @ApiModelProperty(
            value = "The otp Time Limit to be sent in minutes.",
            example = "3",
            required = false)
    @JsonProperty("otpTimeLimit")
    private long otpTimeLimit;

    public long getOtpTimeLimit() {
        return otpTimeLimit;
    }

    public void setOtpTimeLimit(long otpTimeLimit) {
        this.otpTimeLimit = otpTimeLimit;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
