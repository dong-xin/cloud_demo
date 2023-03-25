package com.example.config;

import com.example.feign.EchoClient;
import com.example.feign.EchoClientFallback;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {
    @Bean
    public EchoClient echoClientFallback() {
        return new EchoClientFallback();
    }
}
