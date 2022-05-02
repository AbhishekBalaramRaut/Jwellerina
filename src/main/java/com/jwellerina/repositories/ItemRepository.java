package com.jwellerina.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jwellerina.documents.Item;


@Configuration
public interface ItemRepository extends MongoRepository<Item, Integer> {


	
}