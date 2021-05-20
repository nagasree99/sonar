package com.infy.infyshop.order.controller;

import java.util.List;

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

import com.infy.infyshop.order.dto.ProductsOrderedDTO;
import com.infy.infyshop.order.dto.SubscribedproductDTO;
import com.infy.infyshop.order.service.ProductsOrderedService;

@RestController
@RequestMapping(value="productsordered")
public class ProductsOrderedController {
	
	@Autowired
	ProductsOrderedService productsOrderedService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestBody ProductsOrderedDTO productsOrderedDTO){
		SubscribedproductDTO[] subscribedproductsDTO = new RestTemplate().getForObject("http://localhost:9200/api/subscriptions/"+productsOrderedDTO.getBuyerId(), SubscribedproductDTO[].class);
		SubscribedproductDTO subscribedproductDTO = null;
		for(SubscribedproductDTO subscribedProduct:subscribedproductsDTO) {
			if(subscribedProduct.getProdid()==productsOrderedDTO.getProdId()) {
				subscribedproductDTO=subscribedProduct;
			}
		}
		if(subscribedproductDTO==null) {
			return new ResponseEntity<>("Invalid product",HttpStatus.OK);
		}
		if(subscribedproductDTO.getQuantity()<productsOrderedDTO.getQuantity()) {
			return new ResponseEntity<>("Invalid quantity",HttpStatus.OK);
		}
		String message = productsOrderedService.addProduct(productsOrderedDTO);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteProduct(@RequestBody ProductsOrderedDTO productsOrderedDTO){
		return new ResponseEntity<>(productsOrderedService.deleteProduct(productsOrderedDTO),HttpStatus.OK);
	}
	
	@GetMapping("/view/{buyerId}")
	public ResponseEntity<List<ProductsOrderedDTO>> viewProductByBuyerId(@PathVariable Integer buyerId){
		return new ResponseEntity<>(productsOrderedService.viewProductByBuyerId(buyerId),HttpStatus.OK);
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<ProductsOrderedDTO>> viewProduct(){
		return new ResponseEntity<>(productsOrderedService.viewProduct(),HttpStatus.OK);
	}
}