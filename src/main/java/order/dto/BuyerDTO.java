package order.dto;
public class BuyerDTO{
	int buyerId;
	String name;
	String password;
	String email;
	String phoneNo;
	int isPrivileged;
	int rewardPoints;
	int isActive;
	
	public BuyerDTO(int buyerId,String name,String password,String email,String phoneNo,int isPrivileged,int rewardPoints,int isActive) {
		super();
		this.buyerId=buyerId;
		this.name=name;
		this.password=password;
		this.email=email;
		this.phoneNo=phoneNo;
		this.isPrivileged=isPrivileged;
		this.rewardPoints=rewardPoints;
		this.isActive=isActive;
	}

	public BuyerDTO() {
		super();
		
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getIsPrivileged() {
		return isPrivileged;
	}

	public void setIsPrivileged(int isPrivileged) {
		this.isPrivileged = isPrivileged;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "BuyerDTO [buyerId=" + buyerId + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", isPrivileged=" + isPrivileged + ", rewardPoints=" + rewardPoints
				+ ", isActive=" + isActive + "]";
	}
	
}

