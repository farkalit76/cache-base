package ae.gov.sdg.paperless.services.main;

import ae.gov.sdg.paperless.services.cache.common.conf.CacheStoreConfig;
import ae.gov.sdg.paperless.services.common.CommonConfig;
import ae.gov.sdg.paperless.services.otp.OtpConfig;
import ae.gov.sdg.paperless.services.sms.SmsConfig;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.SpringApplication.run;


@SpringBootApplication
@Import({CommonConfig.class, CacheStoreConfig.class, OtpConfig.class, SmsConfig.class})
public class Boot {

    public static void main(String[] args) {
        run(Boot.class, args);
        System.out.println("----- Cache Store Loaded -----");
    }

}
