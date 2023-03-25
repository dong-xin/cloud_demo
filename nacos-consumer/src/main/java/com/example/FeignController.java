package com.example;

import com.example.feign.EchoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class FeignController {
    @Resource
    private RestTemplate urlCleanedRestTemplate;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private EchoClient echoClient;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${provider.server:}")
    private String serviceAddress;

    @Value("${provider.prefix:}")
    private String servicePrefix;

    @Value("${spring.cloud.loadbalancer.zone:}")
    private String zone;

    @GetMapping("/echo-rest/{str}")
    public String rest(@PathVariable String str) {
        return urlCleanedRestTemplate.getForObject(serviceAddress + servicePrefix + "/echo/" + str,
                        String.class);
    }

    @GetMapping("/echo-feign/{str}")
    public String feign(@PathVariable String str) {
        return echoClient.echo(str);
    }

    //add
    @GetMapping("/zone")
    public String zone() {
        return "consumer zone " + zone + "\n" + urlCleanedRestTemplate
                .getForObject(serviceAddress + servicePrefix + "/zone", String.class);
    }

    @GetMapping("/index")
    public String index() {
        return restTemplate.getForObject(serviceAddress + servicePrefix + "/" , String.class);
    }

    @GetMapping("/test")
    public String test() {
        return restTemplate.getForObject(serviceAddress + servicePrefix + "/test", String.class);
    }

    @GetMapping("/sleep")
    public String sleep() {
        return restTemplate.getForObject(serviceAddress + servicePrefix + "/sleep", String.class);
    }

    @GetMapping("/notFound-feign")
    public String notFound() {
        return echoClient.notFound();
    }

    @GetMapping("/divide-feign")
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return echoClient.divide(a, b);
    }

    @GetMapping("/divide-feign2")
    public String divide(@RequestParam Integer a) {
        return echoClient.divide(a);
    }

    @GetMapping("/services/{service}")
    public Object client(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }

    @GetMapping("/services")
    public Object services() {
        return discoveryClient.getServices();
    }
}
