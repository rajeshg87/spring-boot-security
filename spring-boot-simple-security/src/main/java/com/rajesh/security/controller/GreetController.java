package com.rajesh.security.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {
	
	@RequestMapping(value="/greet/{name}", method=RequestMethod.GET)
	public String greet(@PathVariable("name")String name) {
		return String.format("Welocme %s !", name);
	}

}
