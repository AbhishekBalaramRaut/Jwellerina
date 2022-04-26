package com.jwellerina.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwelleryController {

	@GetMapping("/welcome")
	public String welcome() {
		return "Hello, this is Jwellerina";
	}
}
