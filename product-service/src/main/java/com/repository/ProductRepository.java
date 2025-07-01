package com.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.modal.Product;


public interface ProductRepository extends MongoRepository<Product, String> {
	
}
