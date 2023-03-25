package com.example.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@LoadBalancerClient(value = "service-provider", configuration = LoadBalancerConfiguration.class)
public class SCLBConfiguration {
}
