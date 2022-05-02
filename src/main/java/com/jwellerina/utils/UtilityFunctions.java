package com.jwellerina.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.jwellerina.documents.Customer;
import com.jwellerina.model.CustomerDto;
import com.jwellerina.model.ServerResponse;
import com.jwellerina.repositories.CustomerRepository;
import com.jwellerina.service.JwtTokenUtil;

@Component
public class UtilityFunctions {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public CustomerDto getCurrentProfile(String token) {
		String username = jwtTokenUtil.getUsernameFromToken(token);	 
		return modelMapper.map(customerRepository.findOneByUsername(username), CustomerDto.class);	
	}
	
	public ServerResponse prepareResponse(String code, Object result) {
		ServerResponse res = new ServerResponse();
		res.setCode(code);
		
		String message = GeneralConstants.BLANK;
		try {
			message = messageSource.getMessage(code, null, null);
			res.setMessage(message);
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
			res.setMessage("No Message available");
		}

		res.setResult(result);
		return res;
	}
}
