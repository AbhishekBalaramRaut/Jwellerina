package com.jwellerina.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

	private String to;
	private String subject;
	private String message;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Email req " +
				"from "+ to +" ; " +
				"Subject "+ subject +" ; " +
				"message "+ message +" ; ";
		
	}
	
}
