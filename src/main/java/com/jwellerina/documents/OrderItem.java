package com.jwellerina.documents;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection="orderitem")
public class OrderItem {

	private Integer id;
	private String status;
	private String quantity;
	
	@DBRef
    private Item item;
}
