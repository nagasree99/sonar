package order.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import order.dto.BuyerDTO;
import order.dto.CartDTO;
import order.dto.OrderDTO;
import order.dto.ProductDTO;
import order.dto.ProductsOrderedDTO;
import order.entity.Order;
import order.entity.ProductsOrdered;
import order.repository.OrderRepository;
import order.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class OrderController{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	Environment environment;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Value("${user.uri}")
	public String userUrl;
	
	@Value("${product.uri}")
	public String procuctUrl;
	
//fetches all order details
	@GetMapping(value="/orders")
	public List<OrderDTO> getAllOrders(){
		logger.info("Fetching all orders");
		return orderService.getAllOrders();
	}
	
//fetches specific order details by orderid
	@GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> getSpecificOrders(@PathVariable int orderId) {
	     logger.info("Fetching details of Order {}", orderId);
	     ResponseEntity<OrderDTO> responceEntity;
			try {
				OrderDTO orderDTO=orderService.getSpecificOrder(orderId);
				responceEntity=new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
			}
			catch(Exception e) {
				ResponseStatusException res =new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()), e);
				throw res;
			}
			return responceEntity;
    	}
	
//create a new order
	@PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public  String addOrderDetails(@RequestBody Order order) {
		logger.info("Adding orderDetails");
		orderService.addOrderDetails(order);
		String string="ADDED SUCCESSFULLY";
		return string;
    }

//place an order
	@PostMapping(value="/placeOrder")
 	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO){
 		int flag=0;
 		RestTemplate restTemplate = new RestTemplate();
 		int buyerId = orderDTO.getBuyerId();
 		String cartUrl = userUrl+"getcart/{buyerId}";
 		String productUrl = procuctUrl+"prodId/{prodId}";
 		CartDTO cartDTOs[] = restTemplate.getForObject(cartUrl, CartDTO[].class, buyerId);
 		double amount = 0.0;
 		for (CartDTO cartDTO : cartDTOs) {
 			int prodId = cartDTO.getProdId();
 			ProductDTO productDTO = restTemplate.getForObject(productUrl, ProductDTO.class, prodId);
 			int price = (int) productDTO.getPrice();
 			int quantity = cartDTO.getQuantity();
 			System.out.println("Quantity:"+quantity);
 			System.out.println("Stock:"+productDTO.getStock());
 			System.out.println("Amount:"+amount);
 			amount += (price*quantity);
 			if(quantity>=productDTO.getStock()) {
 				flag=1;
 				break;
 			}
 			if(orderDTO.getAddress().length()>=100) {
 				flag=2;
 				break;
 			}
 			
 		}
 			
 		BuyerDTO buyerDTO = restTemplate.getForObject(userUrl+"buyer/{buyerId}", BuyerDTO.class, buyerId);
 		System.out.println(buyerDTO.getRewardPoints());
 		double rewardpoints = buyerDTO.getRewardPoints();
 		
 		
 		if(rewardpoints>0) {
 			amount = amount - rewardpoints/4;
 			rewardpoints = 0;
 		}
 		System.out.println(amount);
 		ResponseEntity<String> response=null;
 		
 		//Save to Order details table
 		if(flag==0) {
 			OrderDTO neworderDTO = new OrderDTO();
 			neworderDTO.setBuyerId(orderDTO.getBuyerId());
 			neworderDTO.setAddress(orderDTO.getAddress());
 			neworderDTO.setAmount(amount);
 			neworderDTO.setDate(LocalDate.now());
 			neworderDTO.setStatus("Order Placed successfully");
 			int updatedRewards = (int) ((amount/100));
 			BuyerDTO buyerDTO1 = new BuyerDTO();
 			buyerDTO1.setRewardPoints(updatedRewards);
 			restTemplate.put(userUrl+"buyer/updaterewards/{buyerid}", buyerDTO1, buyerId);
 			Order order = neworderDTO.createEntity();
 			orderRepository.save(order);
 			response = new ResponseEntity<String>("Order placed successfully", HttpStatus.OK);
 			}
 		else if(flag==1) {
 			response = new ResponseEntity<String>("Ordered quantity is more than the stock available", HttpStatus.BAD_REQUEST);
 		}
 		else if(flag==2) {
 			response = new ResponseEntity<String>("Address should be less than 100 characters", HttpStatus.BAD_REQUEST);
 		}
 		
 		return response;
 	}
	
// Fetches all productOrdered details
    @GetMapping(value = "/orderedProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductsOrderedDTO> getAllProductsOrdered() {
   	 	logger.info("Fetching all ProductsOrdered");
   	 	return orderService.getAllProductsOrdered();
    	}
   	
	
	@PostMapping(value = "/prodorders", produces = MediaType.APPLICATION_JSON_VALUE)
    public  String addProductOrderedDetails(@RequestBody ProductsOrdered order) {
   	 	logger.info("Adding orderDetails");
   	 	orderService.addProductOrderedDetails(order);
   	 	String string="ADDED SUCCESSFULLY";
   	 		return string;
    	}
    
  //reorder an order
	@PostMapping(value="/reOrder/{buyerId}/{orderId}")
	public String reorder(@PathVariable Integer buyerId,@PathVariable Integer orderId)
	{
		return orderService.reOrder(orderId, buyerId);
	}
    
    
    @GetMapping(value = "/vieworders/{prodId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getSpecificViewOrder (@PathVariable int prodId) {
		logger.info("Fetching details of order {}");
		ResponseEntity<List<OrderDTO>> responceEntity=null;
		try {
		List<ProductsOrderedDTO> poductsOrderedDTO =orderService.getAllViewProdsOrdered(prodId);
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for(ProductsOrderedDTO product:poductsOrderedDTO) {
			OrderDTO orderDTO=orderService.getSpecificOrder(product.getOrderId());
			if(orderDTO!=null) {
			orderDTOs.add(orderDTO);
			}
		}
		responceEntity=new ResponseEntity<List<OrderDTO>>(orderDTOs, HttpStatus.OK);
		}
		catch(Exception e) {
			ResponseStatusException rsc =new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
			throw rsc;
		}
		return responceEntity;
	}
    

    
////////////Fetching from Cart table	////////////    
    
    @GetMapping(value = "/order/cart", produces = MediaType.APPLICATION_JSON_VALUE)
		public List<CartDTO> getAllCarts() {
			logger.info("Fetching all ProductsOrdered");
			List<CartDTO> cartDTO=new RestTemplate().getForObject(userUrl,List.class);
				return cartDTO;
			}	
    
    @PostMapping(value = "/order/cart")
		public List<CartDTO> addSpecificCart(@RequestBody CartDTO plan) {
			logger.info("Adding cartDetails");	
			List<CartDTO> cartDTO=new RestTemplate().postForObject(userUrl,plan,List.class);
				return cartDTO;
			}
    
    @DeleteMapping(value = "/order/cart/{buyerId}/{proId}")
		public void deleteSpecificCart(@PathVariable Integer buyerId , @PathVariable Integer prodId) {
			logger.info("Detching details of cart {}", buyerId , prodId);
		new RestTemplate().delete(userUrl+"/"+buyerId+"/"+prodId);
			}
    
//    @GetMapping(value= "/order/cart/{buyerId}/{prodId}",produces=MediaType.APPLICATION_JSON_VALUE)
//      public CartDTO getSpecificCart(@PathVariable Integer buyerId,@PathVariable  Integer prodId){
//   	   logger.info("Fetching details of cart {}", buyerId , prodId);
//   	   CartDTO cartDTO=new RestTemplate().getForObject(userUrl+"/"+buyerId+"/"+prodId,CartDTO.class);
//   	   		return cartDTO;
//      		}
// 	
      


}