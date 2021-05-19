package order.dto;

import order.entity.ProductsOrdered;

public class ProductsOrderedDTO {
	Integer orderId;
	Integer prodId;
	Integer sellerId;
	Integer quantity;
	String status;
	double price;
		
	public  ProductsOrderedDTO() {
		super();
	}
	
	public  ProductsOrderedDTO(Integer orderId ,Integer prodId ,Integer sellerId ,Integer quantity ,String status ,double price) {
		this.orderId=orderId;
		this.prodId=prodId;
		this.sellerId=sellerId;
		this.quantity=quantity;
		this.status=status;
		this.price=price;
	}

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
		
		
	public static ProductsOrderedDTO valueOf(ProductsOrdered productsOrdered) {
		ProductsOrderedDTO ProdOrderDTO= new ProductsOrderedDTO();
		ProdOrderDTO.setSellerId(productsOrdered.getSellerId());
		ProdOrderDTO.setOrderId(productsOrdered.getOrderId());
		ProdOrderDTO.setPrice(productsOrdered.getPrice());
		ProdOrderDTO.setProdId(productsOrdered.getProdId());
		ProdOrderDTO.setQuantity(productsOrdered.getQuantity());
		ProdOrderDTO.setStatus(productsOrdered.getStatus());
		return ProdOrderDTO;
	}
		
	public ProductsOrdered createEntity() {
		ProductsOrdered productsOrdered = new ProductsOrdered();
		productsOrdered.setOrderId(this.getOrderId());
		productsOrdered.setPrice(this.getPrice());
		productsOrdered.setProdId(this.getProdId());
		productsOrdered.setQuantity(this.getQuantity());
		productsOrdered.setSellerId(this.getSellerId());
		productsOrdered.setStatus(this.getStatus());
		return productsOrdered;
	}
	@Override
	public String toString() {
		return "ProdOrderedDTO [Orderid=" + orderId + ",Prodid=" + prodId +",Sellerid=" + sellerId + ",Price=" + price +",Status=" + status + ",Quantity=" + quantity + "]";
	}
		
	}



