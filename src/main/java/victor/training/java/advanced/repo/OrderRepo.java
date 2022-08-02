package victor.training.java.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java.advanced.model.Order;

import java.util.Iterator;
import java.util.stream.Stream;

public interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
   Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
