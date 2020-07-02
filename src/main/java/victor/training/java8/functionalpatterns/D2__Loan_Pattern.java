package victor.training.java8.functionalpatterns;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
class OrderExporter {
	
	private OrderRepo repo;


}

