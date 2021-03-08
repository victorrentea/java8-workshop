package victor.training.java8.functionalpatterns;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.stream.Stream;

import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.springframework.data.jpa.repository.JpaRepository;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
class FileExporter {


	@FunctionalInterface
	interface ContentWriterFunction {
		void writeContent(Writer writer) throws Exception;
	}
	public File exportFile(String fileName, ContentWriterFunction contentWriterFunction) throws Throwable {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			// ------------ taie aici -------------
			contentWriterFunction.writeContent(writer);

			// In Reactive Programming sau Programare Functionala : Akka Actors, Scala
			// Ai nevoie sa-ti inroduci noi clase mici sa -ti explice codul.
//			Tuple2<Tuple3<String,Long,String>, Map<String, Tuple2<Integer, Integer>>>
//			Tuple2<Address, Map<String, PhoneNumber>>
//			Tuple2<Address, PhoneNumbers>

			// ------------ taie aici -------------
			return file;
		} catch (Throwable e) {
			// TODO send email notification
			System.err.println("Gotcha!" + e); // TERROR-Driven Development
			throw e;
		} finally {
//			move to "PROCESSED folder"
		}
	}
}

class OrderFormatWriter {
	private OrderRepo repo;

	public void writeExportContents(Writer writer) throws IOException {
		writer.write("OrderID;Date\n");

		repo.findByActiveTrue()
			.map(o -> o.getId() + ";" + o.getCreationDate())
			.forEach(Unchecked.consumer(writer::write));
	}
}
class UserFormatWriter {
	private UserRepo repo;

	public void writeExportContents(Writer writer) throws IOException {
		writer.write("Username;FirstName\n");

		repo.findAll().stream()
			.map(user -> user.getUsername() + ";" + user.getFirstName())
			.forEach(Unchecked.consumer(writer::write));
	}
}

class UserCode {
	public static void main(String[] args) throws Throwable {

		FileExporter fileExporter = new FileExporter();
		OrderFormatWriter formatWriter = new OrderFormatWriter();

		// Loan pattern: the infra code "loans" you the writer to write to.

//		 Your Writer is managed by the 'surrounding' infra code

		fileExporter.exportFile("orders.csv", formatWriter::writeExportContents);


		UserFormatWriter userFormatWriter = new UserFormatWriter();

		fileExporter.exportFile("orders.csv", userFormatWriter::writeExportContents);



//		fileExporter.exportFile("file.csv", )

//		JdbcTemplate jdbcTemplate  = new JdbcTemplate();

//		jdbcTemplate.queryForObject("aaaa", new RowMapper<User>() {
//			@Override
//			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//				user.name = rs.getString("name");
//				return null;
//			}
//		})
	}
}

