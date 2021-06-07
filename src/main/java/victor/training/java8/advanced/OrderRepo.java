package victor.training.java8.advanced;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}

@Slf4j
class OrderExporter {
	
	private OrderRepo repo;
			
	public File exportFile(String fileName) throws IOException {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			writer.write("OrderID;Date\n");

			repo.findByActiveTrue()
				.map(o -> o.getId() + ";" + o.getCreationDate())
				.forEach(Unchecked.consumer(writer::write));
			return file;
		} catch (Exception e) {
			// TODO send email notification
			log.debug("Gotcha!", e); // TERROR-Driven Development
			throw e;
		}
	}

}

interface ConsumerChecked<T> {
	void accept(T t) throws Exception;
}


@Data
class Product {
	private Long id;
	private boolean deleted;
}

@Data
class Order {
	private Long id;
	private List<OrderLine> orderLines;
	private LocalDate creationDate;
}

@Data
class OrderLine {
	private Product product;
	private int itemCount;
}

interface ProductRepo {
	List<Long> getHiddenProductIds();
}



//	public static <T> Consumer<T> convertFunction(ConsumerChecked<T> original) {
////		return t -> {
////			try {
////				original.accept(t);
////			} catch (Exception e) {
////				throw new RuntimeException(e);
////			}
////		};
//	}