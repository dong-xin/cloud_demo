package com.example.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    @LoadBalanced
    @Bean
    @SentinelRestTemplate(urlCleanerClass = UrlCleanerConfiguration.class, urlCleaner = "clean")
    public RestTemplate urlCleanedRestTemplate() {
        return new RestTemplate();
    }

    @LoadBalanced
    @Bean
    @SentinelRestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
