package com.example;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/discovery")
public class DiscoveryController {
    Logger logger = LoggerFactory.getLogger(DiscoveryController.class);

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("index error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/sleep")
    public String sleep() {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        logger.info("请求参数：{}",string);
        return "hello Nacos Discovery " + string;
    }

    @GetMapping("/divide")
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        if (b == 0) {
            return String.valueOf(0);
        }
        else {
            return String.valueOf(a / b);
        }
    }

    @GetMapping("/zone")
    public String zone() {
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        logger.info("metadata：{}",metadata.get("zone"));
        return "provider zone " + metadata.get("zone");
    }
}
