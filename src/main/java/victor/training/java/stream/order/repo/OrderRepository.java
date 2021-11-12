package victor.training.java.stream.order.repo;

import victor.training.java.stream.order.entity.Order;

public interface OrderRepository {
	void save(Order order);
}
