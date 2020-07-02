package victor.training.java8.functionalpatterns;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.stream.Stream;

import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
class OrderExporter {
	
	private OrderRepo repo;


	public void exportOrders(String fileName) {
		File file = new File("/common/folder/path/" + fileName);
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("id;creation_date\n");
			repo.findByActiveTrue()
				.map(order -> order.getId() + ";" + order.getCreationDate() + "\n")
				.forEach(Unchecked.consumer(writer::write));
		} catch (IOException e) {
			// fac ceva inteligent
			// Terror-Driven Development: o loghezi de frica. din neincredere in colegi.
			// Nu ai incredere ca cineva ti-o prinde daca o rearunci
			e.printStackTrace(); //sau log.debug(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}


}

