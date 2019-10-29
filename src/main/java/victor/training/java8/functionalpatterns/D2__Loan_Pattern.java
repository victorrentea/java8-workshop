package victor.training.java8.functionalpatterns;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
class FileExporter {
	public File exportFile(String fileName, Consumer<Writer> contentWriter) throws Exception {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			contentWriter.accept(writer);
			return file;
		} catch (Exception e) {
			// TODO send email notification
			System.err.println("Gotcha!" + e); // TERROR-Driven Development
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		FileExporter exporter = new FileExporter();
		OrderExportContentWriter orderContent = new OrderExportContentWriter();
		UserExportContentWriter userContent = new UserExportContentWriter();
		exporter.exportFile("orders.csv", orderContent::write);
		exporter.exportFile("users.csv", userContent::write);

		//@Test
		StringWriter sw = new StringWriter();
		orderContent.write(sw);
		String actualExport = sw.toString();
	}

}
class OrderExportContentWriter {
	private OrderRepo repo;

	@SneakyThrows
	public void write(Writer writer) {
		writer.write("OrderID;Date\n");
		repo.findByActiveTrue()
				.map(o -> o.getId() + ";" + o.getCreationDate())
				.forEach(Unchecked.consumer(writer::write));
	}

}
class UserExportContentWriter {
	public UserRepo userRepo;

	@SneakyThrows
	public void write(Writer writer) {
		writer.write("uid;last\n");
		userRepo.findAll().stream()
			.map(u -> u.getUsername() + ";" + u.getLastName())
			.forEach(Unchecked.consumer(writer::write));
	}
}

