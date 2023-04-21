package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@GetMapping
	@ResponseBody
	public String index(){
		return "{\"index\":\"xxl job executor running.\"}";
	}
}
