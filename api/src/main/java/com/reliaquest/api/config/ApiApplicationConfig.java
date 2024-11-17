package com.reliaquest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Maintains common configurations
 */
@Configuration
public class ApiApplicationConfig {

    /**
     * Creates a RestTemplate bean. More configurations can be added here.
     * @return RestTemplate bean
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // Other rest template configurations can be added here
    }
}
