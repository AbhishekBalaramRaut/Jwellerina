package com.jwellerina.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwellerina.documents.Customer;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.model.JwtRequest;
import com.jwellerina.model.JwtResponse;
import com.jwellerina.model.ServerResponse;
import com.jwellerina.repositories.CustomerRepository;
import com.jwellerina.service.JwtTokenUtil;
import com.jwellerina.service.JwtUserDetailsService;
import com.jwellerina.service.SignUpService;
import com.jwellerina.utils.ErrorCodeConstants;
import com.jwellerina.utils.GeneralConstants;
import com.jwellerina.utils.UtilityFunctions;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private SignUpService signUpService;


	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	UtilityFunctions utilityFunctions;
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ServerResponse signIn(@RequestBody CustomerDto customer) throws Exception {

		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = signUpService.signIn(customer);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.SIGN_IN_FAILED,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}


	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ServerResponse createCustomer(@RequestBody CustomerDto customer) throws Exception {
		Map<String, Object> resultJson = new HashMap<>();		
		String code = null;
		
		try {
			resultJson = signUpService.singUp(customer);
			code = (String) resultJson.get(GeneralConstants.CODE);
			if(!code.equalsIgnoreCase(GeneralConstants.SUCCESS_CODE)) {
				 return utilityFunctions.prepareResponse( code,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return utilityFunctions.prepareResponse(ErrorCodeConstants.SIGN_UP_FAILED,null);
		}
		
		return utilityFunctions.prepareResponse( GeneralConstants.SUCCESS_CODE,resultJson.get(GeneralConstants.DATA));
	}
}