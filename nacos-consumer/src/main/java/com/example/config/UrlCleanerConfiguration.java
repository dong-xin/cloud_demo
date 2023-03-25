package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlCleanerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlCleanerConfiguration.class);

    private static final String URL_CLEAN_ECHO = ".*/echo/.*";

    public static String clean(String url) {
        LOGGER.info("enter urlCleaner");
        if (url.matches(URL_CLEAN_ECHO)) {
            LOGGER.info("change url");
            url = url.replaceAll("/echo/.*", "/echo/{str}");
        }
        return url;
    }
}
