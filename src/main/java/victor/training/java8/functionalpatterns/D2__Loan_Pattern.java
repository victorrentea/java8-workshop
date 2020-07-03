package victor.training.java8.functionalpatterns;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.jooq.lambda.Unchecked.consumer;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
// technical infrastructyre
class Exporter {

	@FunctionalInterface
	interface ContentWriter {
		void writeContent(Writer writer) throws IOException;
	}

	public void export(String fileName, ContentWriter contentWriter) {
		File file = new File("/common/folder/path/" + fileName);
		try (FileWriter writer = new FileWriter(file)) {
//			if (export==USERS)  { /user } ekse { /rder}
			contentWriter.writeContent(writer); // exporterul iti 'imprumuta' writerul sa-ti faci tu treaba. Are el grija insa de deschidere, inchidere, erorir...
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
	public void writeContent(Writer writer) throws IOException {
		writer.write("id;creation_date\n");
		orderRepo.findByActiveTrue()
			.map(order -> order.getId() + ";" + order.getCreationDate() + "\n")
			.forEach(consumer(writer::write));
	}
}
class UserExportFormat {
	private UserRepo userRepo;
//	@SneakyThrows
	public void writeContent(Writer writer) throws IOException {
		writer.write("username;last_name\n");
		userRepo.findAll().stream()
			.map(user -> user.getUsername() + ";" + user.getLastName() + "\n")
			.forEach(consumer(writer::write));
	}
}
class Client {
	public static void main(String[] args) throws IOException {
		Exporter exporter = new Exporter();

		// dintr-un @Test:
		exporter.export("file.txt", writer -> writer.write("X"));
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

