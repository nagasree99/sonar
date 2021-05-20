package com.infy.infyshop.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.infyshop.order.entity.ProductsOrdered;
import com.infy.infyshop.order.entity.ProductsOrderedId;

public interface ProductsOrderedRepository extends CrudRepository<ProductsOrdered,ProductsOrderedId>{

	List<ProductsOrdered> findByBuyerId(Integer buyerId);
	
}
