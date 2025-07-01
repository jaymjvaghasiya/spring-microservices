package com.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.dto.InventoryResponse;
import com.dto.OrderLineItemsDto;
import com.dto.OrderRequest;
import com.modal.Order;
import com.modal.OrderLineItems;
import com.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	
	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
			.stream()
			.map(this::mapToDto)
			.toList();
		
		order.setOrderLineItemsList(orderLineItems);
		 
		List<String> skuCodes = order.getOrderLineItemsList().stream()
									.map(OrderLineItems::getSkuCode)
									.toList();
		
		// stock
		InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
								.uri("http://inventory-service/api/inventory", uriBuilder->uriBuilder.queryParam("skuCode", skuCodes).build())
								.retrieve()
								.bodyToMono(InventoryResponse[].class)
								.block();
		System.out.println("inventoryResponseArray : " + inventoryResponseArray[0].isInStock());
		Boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
				.allMatch(InventoryResponse::isInStock);
		
		if(allProductsInStock) {			
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Product is not in stock, Please try letter.");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
