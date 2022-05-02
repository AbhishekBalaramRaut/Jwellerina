package com.jwellerina.repositories;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jwellerina.documents.Order;

@Configuration
public interface OrderRepository extends MongoRepository<Order, Integer> {
	
	Order findByIdAndMobile(Integer id, String mobile);
	List<Order> findAllByMobile(String mobile);
}
