package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import io.vavr.control.Either;
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
import org.springframework.transaction.annotation.Transactional;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

import javax.persistence.EntityManager;

@FunctionalInterface
 interface MyThrowingConsumer<T> {
   void accept(T t) throws Exception;
}
@Service
class FileExporter {
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   public interface ExportFileContentWriter {
      void writeContent(Writer writer) throws IOException;
   }

   @Transactional
   public void exportFile(String fileName, ExportFileContentWriter contentWriter) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.writeContent(writer);
//         writeContent(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

//   protected abstract  void writeContent(Writer writer) ; // going towards "TemplateMethod design anti-pattern"


}
@Component
class OrderContentWriter {
   @Autowired
   private EntityManager entityManager;
   @Autowired
   private OrderRepo orderRepo;

   public void writeOrders(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
              .peek(entityManager::detach)
              .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
              .forEach(Unchecked.consumer(writer::write));
   }

   public static <T> Consumer<T> convert(MyThrowingConsumer<T> target) {
      return s -> {
         try {
            target.accept(s);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      };
   }

}
@Component
class UserContentWriter {
   @Autowired
   private UserRepo userRepo;

   @SneakyThrows
   public void writeUsers(Writer writer) {
     writer.write("username;fullname\n");
      userRepo.findAll().stream()
              .map(u -> u.getUsername() + ";" + u.getFullName() + "\n")
              .forEach(Unchecked.consumer(s -> writer.write(s)));
   }
}

@RequiredArgsConstructor
@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   private final OrderContentWriter orderContentWriter;
   private final UserContentWriter userContentWriter;

//   Either<SuccessfulResult, DomainError> doStuff() throws InfrastructureException {}

   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", orderContentWriter::writeOrders);

      fileExporter.exportFile("users.csv", userContentWriter::writeUsers);

//      fileExporter.exportFile("a", );
      // TODO implement export of users too
   }
}

