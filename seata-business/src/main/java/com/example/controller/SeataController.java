package com.example.controller;

import com.example.feign.OrderService;
import com.example.feign.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SeataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeataController.class);

    private static final String SUCCESS = "SUCCESS";

    private static final String FAIL = "FAIL";

    private static final String USER_ID = "U100001";

    private static final String COMMODITY_CODE = "C00321";

    private static final int ORDER_COUNT = 2;

    private final RestTemplate restTemplate;

    private final OrderService orderService;

    private final StorageService storageService;

    public SeataController(RestTemplate restTemplate, OrderService orderService,
                           StorageService storageService) {
        this.restTemplate = restTemplate;
        this.orderService = orderService;
        this.storageService = storageService;
    }

    @GlobalTransactional(timeoutMills = 300000, name = "business-tx")
    @GetMapping(value = "/seata/rest", produces = "application/json")
    public String rest() {
		LOGGER.info("business Service Begin ... xid: " + RootContext.getXID());

        String result = restTemplate.getForObject(
                "http://127.0.0.1:18082/storage/" + COMMODITY_CODE + "/" + ORDER_COUNT,
                String.class);

        if (!SUCCESS.equals(result)) {
            throw new RuntimeException();
        }

        String url = "http://127.0.0.1:18083/order";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("userId", USER_ID);
        map.add("commodityCode", COMMODITY_CODE);
        map.add("orderCount", ORDER_COUNT + "");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                map, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception exx) {
            throw new RuntimeException("mock error");
        }
        result = response.getBody();
        if (!SUCCESS.equals(result)) {
            throw new RuntimeException();
        }

        return SUCCESS;
    }

    @GlobalTransactional(timeoutMills = 300000, name = "business-tx")
    @GetMapping(value = "/seata/feign", produces = "application/json")
    public String feign() {
		LOGGER.info("business Service Begin ... xid: " + RootContext.getXID());

        String result = storageService.storage(COMMODITY_CODE, ORDER_COUNT);
        if (!SUCCESS.equals(result)) {
            throw new RuntimeException();
        }

		result = orderService.order(USER_ID, COMMODITY_CODE, ORDER_COUNT);

        if (!SUCCESS.equals(result)) {
            throw new RuntimeException();
        }

        return SUCCESS;
    }
}
