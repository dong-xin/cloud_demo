package com.example.feign;

import com.example.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-provider", fallback = EchoClientFallback.class, configuration = FeignConfiguration.class)
public interface EchoClient {
    @GetMapping("/discovery/echo/{str}")
    String echo(@PathVariable("str") String str);

    @GetMapping("/discovery/divide")
    String divide(@RequestParam("a") Integer a, @RequestParam("b") Integer b);

    default String divide(Integer a) {
        return divide(a, 0);
    }

    @GetMapping("/discovery/notFound")
    String notFound();
}
