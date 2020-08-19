package ae.gov.sdg.paperless.services.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * SwaggerConfig.java
 * Purpose: Configuration class to dynamically generate service APIs, it is accessible via
 * <br/>link http://{host}:{port}/{context-path}/v2/api-docs
 *
 * @author c_chandra.bommise
 * @version 1.0
 */
@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    /**
     * Create Docket document which is responsible to render service API on Swagger UI.
     *
     * @return Docket document.
     */
    @Bean
    public Docket journeyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ae.gov.sdg.paperless.services"))
                .paths(regex("^(?=\\/api\\/)(.*(cachestore|otp)).*"))
                .build()
                .apiInfo(metadata())
                .forCodeGeneration(true);

    }

    /**
     * Create default meta information for services documentation.
     *
     * @return API Meta-Info
     */
    private ApiInfo metadata() {

        ApiInfo info = new ApiInfo("DubaiNow Common Services API documentation",
                "Common Services Microservice APIs", "1.0", "urn:tos",
                ApiInfo.DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());

        return info;
    }

}