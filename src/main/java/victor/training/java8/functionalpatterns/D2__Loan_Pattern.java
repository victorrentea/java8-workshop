package victor.training.java8.functionalpatterns;

import java.io.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.jooq.lambda.Unchecked.consumer;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
// technical infrastructyre
class Exporter {
	public void export(String fileName, Consumer<Writer> contentWriter) {
		File file = new File("/common/folder/path/" + fileName);
		try (FileWriter writer = new FileWriter(file)) {
			contentWriter.accept(writer); // exporterul iti 'imprumuta' writerul sa-ti faci tu treaba. Are el grija insa de deschidere, inchidere, erorir...
		} catch (IOException e) {
			// fac ceva inteligent
			// Terror-Driven Development: o loghezi de frica. din neincredere in colegi.
			// Nu ai incredere ca cineva ti-o prinde daca o rearunci
			e.printStackTrace(); //sau log.debug(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
// format, lucruri mai fine
class OrderExportFormat {
	private OrderRepo orderRepo;

//	@SneakyThrows
	public void writeContent(Writer writer) {
		try {
			writer.write("id;creation_date\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		orderRepo.findByActiveTrue()
			.map(order -> order.getId() + ";" + order.getCreationDate() + "\n")
			.forEach(consumer(writer::write));
	}
}
class UserExportFormat {
	private UserRepo userRepo;
	@SneakyThrows
	public void writeContent(Writer writer) {
		writer.write("username;last_name\n");
		userRepo.findAll().stream()
			.map(user -> user.getUsername() + ";" + user.getLastName() + "\n")
			.forEach(consumer(writer::write));
	}
}
class Client {
	public static void main(String[] args) {
		Exporter exporter = new Exporter();

		// dintr-un @Test:
		exporter.export("file.txt", consumer(writer -> writer.write("X")));
		// TODO assert ca a fost creat un fisire file.txt cu continutul "X" in folderul tinta.

		OrderExportFormat orderFormat = new OrderExportFormat();
		exporter.export("orders.csv", orderFormat::writeContent);

		// dintr-un @Test:
		StringWriter inMemWriter = new StringWriter();
		orderFormat.writeContent(inMemWriter);
		String actualFormat = inMemWriter.toString();// obtii tot ce a scris formatul
		// TODO assertEquals 2020-01-05

		UserExportFormat userFormat = new UserExportFormat();
		exporter.export("orders.csv", userFormat::writeContent);
	}

}

