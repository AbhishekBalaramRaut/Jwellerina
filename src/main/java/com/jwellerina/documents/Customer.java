package com.jwellerina.documents;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection="customers")
public class Customer {

	private Long id;
	
	private String username;
	private String password;
	
	@Field
	@Indexed(unique = true)
	private String email;
	
	@Field
	@Indexed(unique = true)
	private String mobile;
	private String name;
	
	
	
}
