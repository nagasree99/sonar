package com.infy.infyshop.order.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infy.infyshop.order.dto.CartDTO;
import com.infy.infyshop.order.dto.OrderDTO;
import com.infy.infyshop.order.dto.SubscribedproductDTO;
import com.infy.infyshop.order.entity.Order;
import com.infy.infyshop.order.service.OrderService;

@RestController
@RequestMapping(value="/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<String> addOrder(@RequestBody @Valid Order orderDTO) {
		return new ResponseEntity<>(orderService.addOrder(orderDTO),HttpStatus.OK);
	}
	
	@GetMapping(value="/delete/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
		return new ResponseEntity<>(orderService.deleteOrder(orderId),HttpStatus.OK);
	}
	
	@GetMapping(value="/view")
	public ResponseEntity<List<OrderDTO>> viewOrder() {
		return new ResponseEntity<>(orderService.viewOrder(),HttpStatus.OK);
	}
	
	@GetMapping(value="/view/buyer/{buyerId}")
	public ResponseEntity<List<OrderDTO>> viewOrderByBuyer(@PathVariable Integer buyerId) {
		return new ResponseEntity<>(orderService.viewOrderByBuyer(buyerId),HttpStatus.OK);
	}
	
	@PostMapping(value="/cart/add")
	public ResponseEntity<String> addProductsToCart(@RequestBody CartDTO cartDTO){
		SubscribedproductDTO[] subscribedproductsDTO = new RestTemplate().getForObject("http://localhost:9200/api/subscriptions/"+cartDTO.getBuyerId(), SubscribedproductDTO[].class);
		SubscribedproductDTO subscribedproductDTO = null;
		for(SubscribedproductDTO subscribedProduct:subscribedproductsDTO) {
			if(subscribedProduct.getProdid()==cartDTO.getProdId()) {
				subscribedproductDTO=subscribedProduct;
			}
		}
		if(subscribedproductDTO==null) {
			return new ResponseEntity<>("Invalid product",HttpStatus.OK);
		}
		if(subscribedproductDTO.getQuantity()<cartDTO.getQuantity()) {
			return new ResponseEntity<>("Invalid quantity",HttpStatus.OK);
		}
		String message = new RestTemplate().postForObject("http://localhost:9100/cart/add", cartDTO, String.class);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@PostMapping(value="/cart/delete")
	public ResponseEntity<String> deleteProductsToCart(@RequestBody CartDTO cartDTO){
		String message = new RestTemplate().postForObject("http://localhost:9100/cart/delete", cartDTO, String.class);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
}
