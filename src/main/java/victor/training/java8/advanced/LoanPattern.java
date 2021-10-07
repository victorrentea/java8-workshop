package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.repo.OrderRepo;
import victor.training.java8.advanced.repo.UserRepo;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
class CsvExporter {
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ContentWriter {
      void writeContent(Writer writer) throws IOException;
   }
   @Transactional
   public void exportFile(String fileName, ContentWriter contentWriter) {
      File file = new File(folder, fileName); // change here
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.writeContent(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis()-t0));
      }
   }


}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }
   private final CsvExporter csvExporter;

   public void run(String... args) throws Exception {
      csvExporter.exportFile("orders.csv", orderContentWriter::write);

      System.out.println("-------------------");
      csvExporter.exportFile("users.csv", userContentWriter::write);

   }

   private final OrderContentWriter orderContentWriter;
private final UserContentWriter userContentWriter;
}

@Component
@RequiredArgsConstructor
class OrderContentWriter {
   private final OrderRepo orderRepo;
   private final EntityManager em;
   public void write(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .peek(em::detach)
          .map(o -> o.getId() + ";" + o.getCreationDate()+"\n")
          .forEach(Unchecked.consumer(writer::write));
   }

}
@Component
@RequiredArgsConstructor
class UserContentWriter {
   private final UserRepo userRepo;
   public void write(Writer writer) throws IOException {
      writer.write("username;fullname\n");
      userRepo.findAll().stream()
          .map(u -> u.getUsername() + ";" + u.getFullName() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }

}

