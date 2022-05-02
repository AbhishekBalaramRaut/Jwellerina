package com.jwellerina.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jwellerina.documents.Category;


@Configuration
public interface CategoryRepository extends MongoRepository<Category, Integer> {


	
}