package victor.training.java.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.java.advanced.model.Order;

import java.util.List;
import java.util.stream.Stream;

public interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub

//   @Query("""
//          SELECT o
//          FROM Order o
//          asdasdsadas dasd asd asd as asd asd sdas dsa das """)
   Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}

