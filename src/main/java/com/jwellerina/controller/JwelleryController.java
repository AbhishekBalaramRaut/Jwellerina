package com.jwellerina.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwellerina.documents.Customer;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.model.ServerResponse;
import com.jwellerina.repositories.CustomerRepository;
import com.jwellerina.service.ItemsService;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;
import com.jwellerina.utils.UtilityFunctions;

@RestController
public class JwelleryController {


	@Autowired
	UtilityFunctions utilityFunctions;
	
	@Autowired
	ItemsService itemsService;
	
	@GetMapping("/welcome")
	public ServerResponse welcome() {
		CustomerDto customer = new CustomerDto();
		customer.setEmail("abcd@gmail.com");
		return utilityFunctions.prepareResponse("201", customer);
	}
	
	@PostMapping("/customer")
	public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customer) {
	//	String email = customer.get
		Customer con = null;// this.consumerRepository.save(customer);
		return ResponseEntity.ok(con);
	}
	
	


}
