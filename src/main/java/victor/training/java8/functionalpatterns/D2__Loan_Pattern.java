package victor.training.java8.functionalpatterns;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
class OrderExporter {
	
	private OrderRepo repo;


	@FunctionalInterface
	interface CheckedConsumer<T> {
		void accept(T t) throws  Throwable;
	}

			
	public File exportFile(String fileName) throws Exception {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			writer.write("OrderID;Date\n");


			repo.findByActiveTrue()
				.map(o -> o.getId() + ";" + o.getCreationDate())
//				.forEach(Unchecked.consumer(writer::write));
				.forEach(convert(writer::write));
			return file;
		} catch (Exception e) {
			// TODO send email notification
			System.err.println("Gotcha!" + e); // TERROR-Driven Development
			throw e;
		}
	}

	static private <T> Consumer<T> convert(CheckedConsumer<T> throwing) {
		return s -> {
			try {
				throwing.accept(s);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}


//	public static


}

