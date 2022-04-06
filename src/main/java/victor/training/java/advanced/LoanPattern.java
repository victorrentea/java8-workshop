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
import org.springframework.transaction.annotation.Transactional;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

import javax.persistence.EntityManager;

@FunctionalInterface
interface ThrowingConsumer<T> {
   void accept(T t) throws Exception;
}
@Service
class FileExporter {
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ContentWriter {
      void writeContent(Writer writer) throws Exception;
   }

   @Transactional(readOnly = true)
   public void exportFile(String fileName, ContentWriter writeContentFunction) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         writeContentFunction.writeContent(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

}

class OrderExportContentWriter {
   @Autowired
   private OrderRepo orderRepo;
   @Autowired
   private EntityManager entityManager;

   public void writeOrders(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .peek(entityManager::detach)
          .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }
}


class UserExportContentWriter {
   @Autowired
   private UserRepo userRepo;

   public void writerUsers(Writer writer) throws IOException {
      writer.write("id;full_name\n");
      userRepo.findAll().stream()
         .map(user -> user.getId() + ";" + user.getFullName() + "\n")
         .forEach(Unchecked.consumer(writer::write));
   }
}

@RequiredArgsConstructor
//@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   private final UserExportContentWriter userExportContentWriter;
   private final OrderExportContentWriter orderExportContentWriter;

   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", writer -> orderExportContentWriter.writeOrders(writer));

      // TODO implement export of users too
      fileExporter.exportFile("users.csv", writer -> userExportContentWriter.writerUsers(writer));
   }
}

