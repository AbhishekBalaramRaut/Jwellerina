package com.jwellerina.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jwellerina.documents.OrderItem;

@Configuration
public interface OrderItemRepository extends MongoRepository<OrderItem, Integer> {
	
}
