package com.jwellerina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwellerina.repositories.ConsumerRepository;
import com.jwellerina.documents.Consumer;

@RestController
public class JwelleryController {

	@Autowired
	ConsumerRepository consumerRepository;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Hello, this is Jwellerina";
	}
	
	@PostMapping("/customer")
	public ResponseEntity<?> addCustomer(@RequestBody Consumer consumer) {
		
		Consumer con = this.consumerRepository.save(consumer);
		return ResponseEntity.ok(con);
	}
	
	@GetMapping("/customer")
	public ResponseEntity<?> getCustomers() {
		return ResponseEntity.ok(this.consumerRepository.findAll());
	}
}
