package com.jwellerina.documents;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection="order")
public class Order {

	private Integer id;
	private String mobile;
	private String status;
	private Date orderDate;
	private Boolean payment;
	
	@DBRef
    private Collection<OrderItem> items;
}
