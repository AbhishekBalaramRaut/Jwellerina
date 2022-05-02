package com.jwellerina.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jwellerina.documents.Customer;

@Configuration
public interface CustomerRepository extends MongoRepository<Customer, Integer> {

	Customer findOneByUsername(String name);
	
	Customer findOneByEmail(String email);
	Customer findOneByMobile(String mobile);

	
}
