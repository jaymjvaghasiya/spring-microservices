package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
