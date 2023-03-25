package com.example;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class ConfigListener {
    Logger logger = LoggerFactory.getLogger(ConfigListener.class);

    /**
     * Nacos dataId.
     */
    public static final String DATA_ID = "nacos-config.yaml";

    /**
     * Nacos group.
     */
    public static final String GROUP = "DEFAULT_GROUP";

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @PostConstruct
    public void init() throws NacosException {
        ConfigService configService = nacosConfigManager.getConfigService();

        configService.addListener(DATA_ID, GROUP, new Listener() {
            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.info("[dataId]:[" + DATA_ID + "],Configuration changed to:"
                        + configInfo);
            }
        });
    }
}
