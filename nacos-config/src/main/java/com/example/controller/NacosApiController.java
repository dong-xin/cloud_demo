package com.example.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/nacos")
public class NacosApiController {
    Logger logger = LoggerFactory.getLogger(NacosApiController.class);

    /**
     * Nacos group.
     */
    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    @Autowired
    private NacosConfigManager nacosConfigManager;

    /**
     * Get configuration information.
     * 获取Nacos配置文件内容
     * @param dataId dataId
     * @param group group
     * @return config
     */
    @RequestMapping("/getConfig")
    public String getConfig(@RequestParam("dataId") String dataId,
                            @RequestParam(value = "group", required = false) String group) throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.getConfig(dataId, group, 2000);
    }

    /**
     * Publish configuration.
     * 发布配置文件内容
     * @param dataId dataId
     * @param group group
     * @param content content
     * @return boolean
     */
    @RequestMapping("/publishConfig")
    public boolean publishConfig(@RequestParam("dataId") String dataId,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam("content") String content) throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.publishConfig(dataId, group, content);
    }

    /**
     * Delete configuration.
     * 删除配置文件
     * @param dataId dataId
     * @param group group
     * @return boolean
     */
    @RequestMapping("/remoteConfig")
    public boolean remoteConfig(@RequestParam("dataId") String dataId,
                                @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        return configService.removeConfig(dataId, group);
    }

    /**
     * Add listener configuration information.
     * 添加监听配置文件
     * @param dataId dataId
     * @param group group
     */
    @RequestMapping("/listener")
    public String listenerConfig(@RequestParam("dataId") String dataId,
                                 @RequestParam(value = "group", required = false) String group)
            throws NacosException {
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        ConfigService configService = nacosConfigManager.getConfigService();
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return Executors.newSingleThreadExecutor();
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.info("[Listen for configuration changes]:{}", configInfo);
            }
        });
        return "Add Lister successfully!";
    }
}
