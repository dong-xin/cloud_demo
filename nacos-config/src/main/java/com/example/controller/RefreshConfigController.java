package com.example.controller;

import com.example.model.NacosModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/nacosConfig")
public class RefreshConfigController {
    @Autowired
    private NacosModel model;

    @GetMapping("/config")
    public Map<String,String> getConfig(){
        Map<String, String> result = new HashMap<>();
        //NacosModel model = new NacosModel();
        result.put("serverAddr", model.getServerAddr());
        result.put("prefix", model.getPrefix());
        result.put("group", model.getGroup());
        result.put("namespace", model.getNamespace());
        result.put("name", model.getName());
        result.put("age", model.getAge());
        return result;
    }
}
