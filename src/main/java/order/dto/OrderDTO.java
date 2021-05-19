package order.dto;

import java.time.LocalDate;
import java.util.List;

import order.entity.Order;

public class OrderDTO {
	private int orderId;
	private int buyerId;
	private double amount;
	private LocalDate date;
	private String address;
	private String status;
	private List<ProductsOrderedDTO> orderedProducts;
	
	public OrderDTO() {
		super();
	}
	
	public OrderDTO(int orderId,int buyerId,double amount,LocalDate date,String address,String status) {
		this.orderId=orderId;
		this.buyerId=buyerId;
		this.amount=amount;
		this.date=date;
		this.address=address;
		this.status=status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductsOrderedDTO> getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(List<ProductsOrderedDTO> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", buyerId=" + buyerId + ", amount=" + amount + ", date=" + date
				+ ", address=" + address + ", status=" + status + "]";
	}
	
	
	public static OrderDTO valueOf(Order order) {
		OrderDTO orderDTO= new OrderDTO();
		orderDTO.setAddress(order.getAddress());
		orderDTO.setAmount(order.getAmount());
		orderDTO.setBuyerId(order.getBuyerId());
		orderDTO.setDate(order.getDate());
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setStatus(order.getStatus());
		return orderDTO;
	}
		
	//Converts DTO into Entity
	public Order createEntity() {
		Order order = new Order();
		order.setOrderId(this.getOrderId());
		order.setBuyerId(this.getBuyerId());
		order.setAmount(this.getAmount());
		order.setAddress(this.getAddress());
		order.setDate(this.getDate());
		order.setStatus(this.getStatus());
		return order;
	}
}
