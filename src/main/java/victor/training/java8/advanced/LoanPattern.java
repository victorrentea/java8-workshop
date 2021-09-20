package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.repo.OrderRepo;
import victor.training.java8.advanced.repo.UserRepo;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

// Infrastuctura
@Service
class CSVExporter {

   @Value("${export.folder.out}")
   private File folder;

   public void exportFile(final String fileName, Consumer<Writer> contentWriter) {
      File file = new File(folder, fileName + ".csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.accept(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }
}
//format logic
@Component
class OrderContentWriter {
   @Autowired
   private OrderRepo orderRepo;
    // checked (by javac, JVM(runtime) nu-i pasa.) vs runtime
   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(order -> order.getId() + ";" + order.getCreationDate())
          .forEach(Unchecked.consumer(writer::write));
   }
}


class UserContentWriter {
   @Autowired
   private UserRepo userRepo;
   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("Username;fullname\n");
      List<String> lines = userRepo.findAll().stream()
          .map(user -> user.getUsername() + ";" + user.getFullName()+"\n")
          .collect(Collectors.toList());
      lines.forEach(Unchecked.consumer(writer::write));
   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   private final CSVExporter CSVExporter;
   private final OrderContentWriter orderContentWriter;
   private final UserContentWriter userContentWriter;

   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   @Autowired
   private UserRepo userRepo;

   public void run(String... args) throws Exception {
      CSVExporter.exportFile("orders", orderContentWriter::writeContent);

      System.out.println("-------------------");
      CSVExporter.exportFile("users", userContentWriter::writeContent);
   }
}

