package com.infy.infyshop.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.infyshop.order.entity.Order;

public interface OrderRepository extends CrudRepository<Order,Integer>{

	List<Order> findByBuyerId(Integer buyerId);
	
}
