package victor.training.java8.advanced;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.repo.UserRepo;

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


class ExportCLient {
	public static void main(String[] args) throws Exception {
		FileExporter exporter = new FileExporter();
		exporter.exportFile("order.csv", new OrderExportContent()::writeOrderContent);
		exporter.exportFile("users.csv", new UserExportContent()::writeUserContent);
	}
}
@Slf4j
class FileExporter {
	@FunctionalInterface
	public interface ContentWriter {
		void writeContent(Writer writer) throws Exception;
	}

	public File exportFile(String fileName, ContentWriter contentWriter) throws Exception {
//	public File exportFile(String fileName, Consumer<Writer> contentWriter) throws IOException {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			contentWriter.writeContent(writer);

			return file;
		} catch (Exception e) {
			// TODO send email notification
			log.debug("Gotcha!", e); // TERROR-Driven Development
			throw e;
		}
	}
}
class OrderExportContent {
	private OrderRepo repo;
//	@SneakyThrows
	public void writeOrderContent(Writer writer) throws IOException {
		writer.write("OrderID;Date\n");
		repo.findByActiveTrue()
			.map(o -> o.getId() + ";" + o.getCreationDate())
			.forEach(Unchecked.consumer(writer::write));
	}

}
class UserExportContent {
	private UserRepo userRepo;
//	@SneakyThrows
	public void writeUserContent(Writer writer) throws IOException {
		writer.write("username;fullname\n");
		userRepo.findAll().stream()
			.map(u -> u.getId() + ";" + u.getFullName())
			.forEach(Unchecked.consumer(writer::write));
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