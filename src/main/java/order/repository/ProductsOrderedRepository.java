package order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import order.entity.ProdOrderCompositeKey;
import order.entity.ProductsOrdered;

public interface ProductsOrderedRepository extends JpaRepository<ProductsOrdered, ProdOrderCompositeKey>{
	public List<ProductsOrdered> findByOrderId(Integer orderId);
}