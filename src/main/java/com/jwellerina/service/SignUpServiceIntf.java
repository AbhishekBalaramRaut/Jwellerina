package com.jwellerina.service;

import java.util.Map;

import com.jwellerina.model.CustomerDto;

public interface SignUpServiceIntf {

	public Map<String,Object> singUp(CustomerDto customerDto);
	public Map<String,Object> signIn(CustomerDto customerDto);
}
