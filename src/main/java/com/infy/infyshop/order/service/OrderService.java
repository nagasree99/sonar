package com.infy.infyshop.order.service;

import java.util.List;

import com.infy.infyshop.order.dto.OrderDTO;
import com.infy.infyshop.order.entity.Order;

public interface OrderService {

	String addOrder(Order orderDTO);

	String deleteOrder(Integer orderId);

	List<OrderDTO> viewOrderByBuyer(Integer buyerId);

	List<OrderDTO> viewOrder();
	
}
