package victor.training.exile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.model.User;
import victor.training.java8.advanced.repo.OrderRepo;
import victor.training.java8.advanced.repo.UserRepo;


@Service
class FileExporter {
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ContentWriter {
      void writeContent(Writer writer) throws Exception;
   }

   public void exportFile(String fileName, ContentWriter contentWriter) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.writeContent(writer);
         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

}
@Service
@RequiredArgsConstructor
class OrderExportContentWriter {
   private final OrderRepo orderRepo;
   public void writeOrders(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");

      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate())
          .forEach(Unchecked.consumer(writer::write));
   }
}
@Service
@RequiredArgsConstructor
class UserExportContentWriter {
   private final UserRepo userRepo;
   public void writerUsers(Writer writer) throws IOException {
      writer.write("USerId;Full Name\n");

      Function<User, String> f = user -> user.getId() + ";" + user.getFullName();
//      Object f2 = (User user) -> user.getId() + ";" + user.getFullName();
      userRepo.findAll().stream()
          .map(f)
          .forEach(Unchecked.consumer(writer::write));
   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   private final OrderExportContentWriter orderExportContentWriter;
   private final UserExportContentWriter userExportContentWriter;

//   private User fromResultSet(ResultSet rs) {
//      return null;
//   }
   public void run(String... args) throws Exception {
//      new JdbcTemplate().query("SELECT * FROM USERS", (rs, rowNum) -> fromResultSet(rs));


      fileExporter.exportFile("orders.csv", orderExportContentWriter::writeOrders);

      System.out.println("-------------------");
//       TODO CR: also export users, "the same way you exported the orders"
      fileExporter.exportFile("users.csv", userExportContentWriter::writerUsers);
   }
}

