package com.opensource.component.rest.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnProperty(
        value = {"component.restTemplate.logging.enabled"},
        matchIfMissing = true
)
@ConditionalOnClass({RestTemplate.class})
public class LoggingWebClientAutoConfiguration {

    @Bean
    RestTemplateCustomizer loggingRestTemplateCustomizer() {
        return new LoggingRestTemplateCustomizer();
    }
}
