package com.jwellerina.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponse {

	private String code;
	private String message;
	private Object result;
}
