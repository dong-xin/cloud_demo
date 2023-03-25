package com.example.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.cloud.nacos.config")
@Component
@Data
public class NacosModel {
    /**
     * Nacos server address.
     */
    private String serverAddr;

    /**
     * Data Id prefix.
     */
    private String prefix;

    /**
     * Nacos group.
     */
    private String group;

    /**
     * Nacos namespace.
     */
    private String namespace;

    private String name;

    private String age;
}
