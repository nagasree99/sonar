package order.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import order.entity.Order;
public interface OrderRepository extends JpaRepository<Order, Integer> {
	public Order findByOrderId(Integer orderId);
}
