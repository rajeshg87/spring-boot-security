package com.rajesh.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String hello(String name) {
		return "Hello "+name;
	}
	
	@GetMapping("/admin/hello")
	public String helloAdmin(String name) {
		return "Hello Admin "+name;
	}

}
