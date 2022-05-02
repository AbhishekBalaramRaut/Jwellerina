package com.jwellerina.model;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
	
	private Long id;
	
	private String username;
	private String password;
	
	private String email;

	private String mobile;
	private String name;

}
