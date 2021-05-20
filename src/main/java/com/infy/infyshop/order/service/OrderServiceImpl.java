package com.infy.infyshop.order.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.infyshop.order.dto.OrderDTO;
import com.infy.infyshop.order.entity.Order;
import com.infy.infyshop.order.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public String addOrder(Order orderDTO) {
		Order order = new Order();
		order.setBuyerId(orderDTO.getBuyerId());
		order.setAmount(orderDTO.getAmount());
		order.setDate(orderDTO.getDate());
		order.setAddress(orderDTO.getAddress());
		order.setStatus(orderDTO.getStatus());
		orderRepository.save(order);
		return "Order "+order.getOrderId()+" for buyer "+order.getBuyerId()+" added successfully";
	}

	@Override
	public String deleteOrder(Integer orderId) {
		orderRepository.deleteById(orderId);
		return "Order number "+orderId+" deleted successfully";
	}

	@Override
	public List<OrderDTO> viewOrderByBuyer(Integer buyerId) {
		List<Order> orders = orderRepository.findByBuyerId(buyerId);
		List<OrderDTO> ordersDTO = new ArrayList<>();
		for(Order order:orders) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setBuyerId(order.getBuyerId());
			orderDTO.setAmount(order.getAmount());
			orderDTO.setDate(order.getDate());
			orderDTO.setAddress(order.getAddress());
			orderDTO.setStatus(order.getStatus());
			ordersDTO.add(orderDTO);
		}
		return ordersDTO;
	}

	@Override
	public List<OrderDTO> viewOrder() {
		List<Order> orders = (List<Order>) orderRepository.findAll();
		List<OrderDTO> ordersDTO = new ArrayList<>();
		for(Order order:orders) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setBuyerId(order.getBuyerId());
			orderDTO.setAmount(order.getAmount());
			orderDTO.setDate(order.getDate());
			orderDTO.setAddress(order.getAddress());
			orderDTO.setStatus(order.getStatus());
			ordersDTO.add(orderDTO);
		}
		return ordersDTO;
	}
}
