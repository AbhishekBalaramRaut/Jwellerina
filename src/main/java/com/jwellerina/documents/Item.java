package com.jwellerina.documents;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection="item")
public class Item {

	private Integer id;
	private String name;
	private String description;
	private String image;
	private double price;
	
	private Integer categoryId;
}
