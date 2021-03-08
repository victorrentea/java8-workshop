package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.model.Order;

import java.util.stream.Stream;

public interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
   Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
