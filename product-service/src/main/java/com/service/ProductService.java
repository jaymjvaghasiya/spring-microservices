package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.ProductRequest;
import com.dto.ProductResponse;
import com.modal.Product;
import com.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		
		productRepository.save(product);
		log.info("Product {} is saved.", product.getId());
	}

	public List<ProductResponse> getAllProduct() {
		List<Product> products = productRepository.findAll();
		
//		products.stream().map(product -> mapToProductResponse(product));
		return products.stream().map(this::mapToProductResponse).toList();
	}
	
	public ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
