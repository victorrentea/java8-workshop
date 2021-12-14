package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

@RequiredArgsConstructor
@Service
class FileExporter {
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ContentWriteFunction {
      void writeContent(Writer writer) throws IOException;
   }

   public void exportFile(String fileName, ContentWriteFunction contentWriter) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         // start tx ; setup stuff
         contentWriter.writeContent(writer);
//end; clean stuff
         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }
}
@RequiredArgsConstructor
@Service
class Exports {
   private final OrderRepo orderRepo;
   public void writeOrderContent(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
         .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
         .forEach(Unchecked.consumer(writer::write));
   }

   private final UserRepo userRepo;
   public void writeUserContent(Writer writer) throws IOException {
      writer.write("username;fullname\n");
      userRepo.findAll().stream()
         .map(u->u.getUsername() + ";" +u.getFullName() + "\n")
         .forEach(Unchecked.consumer(writer::write));
   }

}
// how much refactoring is fine?

@RequiredArgsConstructor
//@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   private final Exports exports;
   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", exports::writeOrderContent);

      fileExporter.exportFile("users.csv", exports::writeUserContent);

      // TODO implement export of users too "just like you exported orders"
   }
}

