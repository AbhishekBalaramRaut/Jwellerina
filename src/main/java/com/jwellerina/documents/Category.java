package com.jwellerina.documents;

import java.util.Collection;

import javax.persistence.OneToMany;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection="category")
public class Category {

	private Integer id;
	private String name;
	private String description;
	private String image;
	private String categoryCode;
	
	@DBRef
    private Collection<Item> items;
}
