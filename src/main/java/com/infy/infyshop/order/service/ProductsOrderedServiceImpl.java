package com.infy.infyshop.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.infyshop.order.dto.ProductsOrderedDTO;
import com.infy.infyshop.order.entity.ProductsOrdered;
import com.infy.infyshop.order.repository.ProductsOrderedRepository;

@Service
public class ProductsOrderedServiceImpl implements ProductsOrderedService{

	@Autowired
	ProductsOrderedRepository productsOrderedRepository;
	
	@Override
	public String addProduct(ProductsOrderedDTO productsOrderedDTO) {
		ProductsOrdered productsOrdered = new ProductsOrdered();
		productsOrdered.setBuyerId(productsOrderedDTO.getBuyerId());
		productsOrdered.setProdId(productsOrderedDTO.getProdId());
		productsOrdered.setSellerId(productsOrderedDTO.getSellerId());
		productsOrdered.setQuantity(productsOrderedDTO.getQuantity());
		productsOrderedRepository.save(productsOrdered);
		return "Product added succesfully";
	}
	
	@Override
	public String deleteProduct(ProductsOrderedDTO productsOrderedDTO) {
		ProductsOrdered productsOrdered = new ProductsOrdered();
		productsOrdered.setBuyerId(productsOrderedDTO.getBuyerId());
		productsOrdered.setProdId(productsOrderedDTO.getProdId());
		productsOrdered.setSellerId(productsOrderedDTO.getSellerId());
		productsOrdered.setQuantity(productsOrderedDTO.getQuantity());
		productsOrderedRepository.delete(productsOrdered);
		return "Product deleted succesfully";
	}

	@Override
	public List<ProductsOrderedDTO> viewProductByBuyerId(Integer buyerId) {
		List<ProductsOrdered> productsOrderedList = new ArrayList<>();
		List<ProductsOrderedDTO> productsOrderedListDTO = new ArrayList<>();
		productsOrderedList = productsOrderedRepository.findByBuyerId(buyerId);
		for(ProductsOrdered productsOrdered : productsOrderedList) {
			ProductsOrderedDTO productsOrderedDTO = new ProductsOrderedDTO();
			productsOrderedDTO.setBuyerId(productsOrdered.getBuyerId());
			productsOrderedDTO.setProdId(productsOrdered.getProdId());
			productsOrderedDTO.setSellerId(productsOrdered.getSellerId());
			productsOrderedDTO.setQuantity(productsOrdered.getQuantity());
			productsOrderedListDTO.add(productsOrderedDTO);
		}
		return productsOrderedListDTO;
	}

	@Override
	public List<ProductsOrderedDTO> viewProduct() {
		List<ProductsOrdered> productsOrderedList = new ArrayList<>();
		List<ProductsOrderedDTO> productsOrderedListDTO = new ArrayList<>();
		productsOrderedList = (List<ProductsOrdered>) productsOrderedRepository.findAll();
		for(ProductsOrdered productsOrdered : productsOrderedList) {
			ProductsOrderedDTO productsOrderedDTO = new ProductsOrderedDTO();
			productsOrderedDTO.setBuyerId(productsOrdered.getBuyerId());
			productsOrderedDTO.setProdId(productsOrdered.getProdId());
			productsOrderedDTO.setSellerId(productsOrdered.getSellerId());
			productsOrderedDTO.setQuantity(productsOrdered.getQuantity());
			productsOrderedListDTO.add(productsOrderedDTO);
		}
		return productsOrderedListDTO;
	}
	
}