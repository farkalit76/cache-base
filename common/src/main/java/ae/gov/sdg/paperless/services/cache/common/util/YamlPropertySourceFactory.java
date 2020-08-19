package ae.gov.sdg.paperless.services.cache.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class YamlPropertySourceFactory implements PropertySourceFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties effectiveProperties = new Properties();
        
        // First, load all properties from the default resource
        Resource defaultResource = resource.getResource();
        if (defaultResource.exists()) {
            log.info("Activating default configurations: {}.", resource.getResource().getFilename());
            effectiveProperties.putAll(this.loadYamlIntoProperties(defaultResource));
        
        }
        
        // Next, if profile-specific properties exist, merge them in and override the default values
        Resource profileResource = this.findResourceBasedOnActiveProfiles(resource);
        if (profileResource == null) 
            Assert.isTrue(defaultResource.exists(), String.format("The YAML file %s does not exist.", defaultResource.getFilename()));
        else {
            log.info("Activating profile-specific configurations: {}.", profileResource.getFilename());
            effectiveProperties.putAll(this.loadYamlIntoProperties(profileResource));
            
        }
        
        return new PropertiesPropertySource((StringUtils.isNotBlank(name)) ? name : resource.getResource().getFilename(), effectiveProperties);
    }

    private Properties loadYamlIntoProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();
        
        return factory.getObject();
    }
    
    private Resource findResourceBasedOnActiveProfiles(EncodedResource resource) {
        String   resourceName   = resource.getResource().getFilename();
        String[] activeProfiles = this.getActiveRuntimeProfiles();
        
        for (String activeProfile : activeProfiles) {
            String resourceNameRelativeToActiveProfile = resourceName.replaceFirst("\\.yml", String.format("-%s.yml", activeProfile));
            
            Resource potentialResource = new ClassPathResource(resourceNameRelativeToActiveProfile);
            if (potentialResource.exists())
                return potentialResource;
        }
        
        return null;
    }
  
    private String[] getActiveRuntimeProfiles() {
        String profileConfig = System.getProperty("spring.profiles.active");
        return StringUtils.isBlank(profileConfig) ? new String[] {} : profileConfig.split(","); 
    }
}