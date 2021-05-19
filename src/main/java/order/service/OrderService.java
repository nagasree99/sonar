package order.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import order.dto.OrderDTO;
import order.dto.ProductsOrderedDTO;
import order.entity.Order;
import order.entity.ProductsOrdered;
import order.repository.OrderRepository;
import order.repository.ProductsOrderedRepository;

@Service
public class OrderService{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductsOrderedRepository productsOrderedRepository;
	
//fetching all order details
	public List<OrderDTO> getAllOrders(){
		List<Order> orders=orderRepository.findAll();
		List<OrderDTO> orderDTOs=new ArrayList<>();
		for(Order order:orders) {
			OrderDTO orderDTO=OrderDTO.valueOf(order);
			orderDTOs.add(orderDTO);
		}
		logger.info("Order details:{}",orderDTOs);
		return orderDTOs;
	}

//fetching specific order details by orderid
	public OrderDTO getSpecificOrder(int orderId) throws Exception{
		
		OrderDTO orderDTO=null;
		
			Optional<Order> optionalOrder=orderRepository.findById(orderId);
			if(optionalOrder.isPresent()) {
				Order order=optionalOrder.get();
				orderDTO=OrderDTO.valueOf(order);
			}
			else {
				throw new Exception("OrderService.Order_NOT_FOUND");
			}
		
		
		logger.info("Order details:{}",orderDTO);
		return orderDTO;
		
	}
	
//Place order
	public String addOrderDetails(Order order) {
		logger.info("Adding order details:{}");
		orderRepository.save(order);
		String s="Order placed successfully";
		return s;
	}
	
//deleting order
	public String deleteSpecificOrder(int orderId) {
		logger.info("Deleting order details:{}",orderId);
		orderRepository.deleteById(orderId);
		String s="Order deleted successfully";
		return s;
	}
	
//ordered products details
	public List<ProductsOrderedDTO> getAllProductsOrdered(){
		List<ProductsOrdered> productsOrderedList=productsOrderedRepository.findAll();
		List<ProductsOrderedDTO> productOrderedDTOs=new ArrayList<>();
		for(ProductsOrdered productsOrdered:productsOrderedList) {
			ProductsOrderedDTO productOrderedDTO=ProductsOrderedDTO.valueOf(productsOrdered);
			productOrderedDTOs.add(productOrderedDTO);
		}
		logger.info("Order details:{}",productOrderedDTOs);
		return productOrderedDTOs;
	}
//specific product ordered details
	public List<ProductsOrderedDTO> getProductsOrdered(int orderId) {
		List<ProductsOrdered> productsOrdered = productsOrderedRepository.findByOrderId(orderId);
		List<ProductsOrderedDTO> productDTOs = new ArrayList<>();
		for(ProductsOrdered products : productsOrdered) {
			ProductsOrderedDTO productsDTO = ProductsOrderedDTO.valueOf(products);
			productDTOs.add(productsDTO);
		}
		return productDTOs;
	}

//Add product
	public String addProductOrderedDetails(ProductsOrdered order) {
		logger.info("Adding Productorder details : {}");
		System.out.println(order);
		productsOrderedRepository.save(order);
		String s="Successfully Added";
		return s;
		
	}
	
//reorder
	public String reOrder(int orderId,int buyerId) {
		Order order = orderRepository.findByOrderId(orderId);
		if(order!=null) {
			OrderDTO orderDTO = OrderDTO.valueOf(order);
			if(orderDTO.getBuyerId()==buyerId) {
				List<ProductsOrdered> productsOrdered=productsOrderedRepository.findByOrderId(orderId);
				List<ProductsOrderedDTO> productList=new ArrayList<>();
				for(ProductsOrdered product:productsOrdered) {
					ProductsOrderedDTO productDTO=ProductsOrderedDTO.valueOf(product);
					productList.add(productDTO);
				}
				
				OrderDTO newOrderDTO = new OrderDTO();
				newOrderDTO.setBuyerId(orderDTO.getBuyerId());
				newOrderDTO.setAddress(orderDTO.getAddress());
				newOrderDTO.setAmount(orderDTO.getAmount());
				newOrderDTO.setDate(LocalDate.now());
				newOrderDTO.setStatus("Order Placed");
				
				Order order1 = newOrderDTO.createEntity();
		        orderRepository.save(order1);
		        
		        for(ProductsOrderedDTO product1: productList) {
		        	ProductsOrderedDTO newProductOrderedDTO = new ProductsOrderedDTO();
			        newProductOrderedDTO.setOrderId(order1.getOrderId());
			        newProductOrderedDTO.setProdId(product1.getProdId());
			        newProductOrderedDTO.setSellerId(product1.getSellerId());
			        newProductOrderedDTO.setQuantity(product1.getQuantity());
			        newProductOrderedDTO.setPrice(product1.getPrice());
			        newProductOrderedDTO.setStatus("Order Placed");
			        
			        ProductsOrdered productOrdered = newProductOrderedDTO.createEntity();
			        productsOrderedRepository.save(productOrdered);
		        }
		        return " Reorder Successfull";
			}
		}
		return "Order not found for the buyer!!";
	}
	
//get order by orderid
	public OrderDTO getOrderDetails(int orderId) {
		Order order = orderRepository.findByOrderId(orderId);
		OrderDTO orderDTO = new OrderDTO();
		if(order!=null) {
			orderDTO = OrderDTO.valueOf(order);
			List<ProductsOrderedDTO> productsOrderedDTOs = getProductsOrdered(orderId);
			orderDTO.setOrderedProducts(productsOrderedDTOs);
		}
		return orderDTO;
	}
	
//to view all products ordered
	public List<ProductsOrderedDTO> getAllViewProdsOrdered(int prodId) {

		List<ProductsOrdered> productOrders = productsOrderedRepository.findByOrderId(prodId);	
		List<ProductsOrderedDTO> orderDTOs = new ArrayList<>();
		for (ProductsOrdered product : productOrders) {
			ProductsOrderedDTO orderDTO = ProductsOrderedDTO.valueOf(product);
			orderDTOs.add(orderDTO);
		}
		logger.info("Order details based on prodid : {}", orderDTOs);
		return orderDTOs;
	}

	
}