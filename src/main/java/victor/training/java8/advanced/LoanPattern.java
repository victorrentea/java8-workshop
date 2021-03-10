package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Consumer;

import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.CsvExporter.ContentWriter;
import victor.training.java8.advanced.repo.OrderRepo;

import static java.util.Arrays.asList;


class CsvExporter {
   private File folder = new File("/apps/export"); // injected eg @Value

   @FunctionalInterface
   interface ContentWriter {
      void writeContent(Writer writer) throws Exception;
   }

   public File exportFile(String fileName, ContentWriter contentWriter) {
      File file = new File(folder, fileName);
      try (Writer writer = new FileWriter(file)) {

         contentWriter.writeContent(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
         return file;
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         if (!file.delete()) {
            System.err.println("Could not delete export file: " + file.getAbsolutePath());
         }
      }
   }
}

// garbage - infrastructural sh*t
// separate class --------------------
// the actual format.
class OrderExportWriter {
   private OrderRepo orderRepo;

   @SneakyThrows
   public void writeContent(Consumer<List<String>> rowSink) {

      rowSink.accept(asList("OrderId", "Date"));

   }

   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("OrderID;Date\n");

      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate())
          .forEach(Unchecked.consumer(writer::write));
   }
}

interface UserRepo extends JpaRepository<User, Long> {
}

class UserExportWriter {
   private UserRepo userRepo;

   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("Username;User Role\n");

      userRepo.findAll().stream()
          .map(user -> user.getName() + ";" + user.getRoles())
          .forEach(Unchecked.consumer(writer::write));
   }
}

public class LoanPattern {
   public static void main(String[] args) throws Exception {
      OrderExportWriter orderExportWriter = new OrderExportWriter();
      CsvExporter csvExporter = new CsvExporter();
      csvExporter.exportFile("orders.csv", writer -> {
         method(orderExportWriter, writer);
      });
      csvExporter.exportFile("users.csv", orderExportWriter::writeContent);

//      csvExporter.exportFile("a", new ContentWriter() {
//             @Override
//             public void writeContent(Writer writer) throws Exception {
//
//             }
//          }

          // TODO export users just like you export
   }

   private static void method(OrderExportWriter orderExportWriter, Writer writer) {
      System.out.println("More");
      orderExportWriter.writeContent(writer);
   }
}

