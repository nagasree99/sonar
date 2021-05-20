package com.infy.infyshop.order.dto;

public class SubscribedproductDTO {
	int buyerid;
	int prodid;
	int quantity;
	
	
	public SubscribedproductDTO() {
		super();
	}
	
	public SubscribedproductDTO( int buyerid, int prodid, int quantity) {
		super();
		this.buyerid = buyerid;
		this.prodid = prodid;
		this.quantity = quantity;
	}

	
	

	public int getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(int buyerid) {
		this.buyerid = buyerid;
	}

	public int getProdid() {
		return prodid;
	}

	public void setProdid(int prodid) {
		this.prodid = prodid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

		@Override
		public String toString() {
			return "SubscribedproductDTO [ buyerid=" + buyerid + ", prodid=" + prodid
					+ ", quantity=" + quantity + "]";
		}
	
}	