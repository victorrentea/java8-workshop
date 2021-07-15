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

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.function.Consumer;

@Service
class CsvExporter {
   @Value("${export.folder.out}")
   private File folder;
   public void exportFile(String fileName, Consumer<Writer> contentWriter) {
      File file = new File(folder, fileName);
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

@Component
@RequiredArgsConstructor
class OrderExportContent {
   private final OrderRepo orderRepo;
   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate())
          .forEach(Unchecked.consumer(writer::write));
   }
}

@Component
@RequiredArgsConstructor
class UserExportContent {
   private final UserRepo userRepo;
   @SneakyThrows
   public void writeContent(Writer writer) {
      writer.write("username;fullName\n");
      userRepo.findAll().stream()
          .map(u -> u.getUsername() + ";" + u.getFullName())
          .forEach(Unchecked.consumer(writer::write));
   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   private final CsvExporter exportFile;
   private final OrderExportContent orderExportContent;
   private final UserExportContent userExportContent;

   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   public void run(String... args) throws Exception {
      exportFile.exportFile("orders.csv", orderExportContent::writeContent);
      exportFile.exportFile("users.csv", userExportContent::writeContent);

      System.out.println("-------------------");
   }
}

interface ConsumerThrowing<T> {
   void accept(T t) throws Exception;
}

