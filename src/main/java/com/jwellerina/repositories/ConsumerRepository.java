package com.jwellerina.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jwellerina.documents.Consumer;

@Configuration
public interface ConsumerRepository extends MongoRepository<Consumer, Integer> {

}
