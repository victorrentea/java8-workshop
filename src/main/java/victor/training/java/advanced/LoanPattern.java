package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
interface ConsumeruMeu<T> {
   void accept(T t) throws Exception;
}
@Service
class FileExporter {

   @Value("${export.folder.out}")
   private File folder;
   EntityManager em;

   @Transactional // necesar pentru a-i spune lui spring sa aloce o connex JDBC pentru intreaga durata
   // a acestei metode, pentru ca REsultSet sa poata fi traversat
   public void exportFile(String fileName, Consumer<Writer> contentWriteFunction) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());
         contentWriteFunction.accept(writer);
         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }
}
@Service
class OrderContentWriter {
   @Autowired
   private OrderRepo orderRepo;
//   @SneakyThrows // pacaleste javac sa nu mai tipe la unhandled checked exceptions
   public void writeContent(Writer writer) throws IOException{
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
          .forEach(Unchecked.consumer(writer::write)); // org.jooq:jool
   }
}
@Service
class UserContentWriter {
   @Autowired
   private UserRepo userRepo;
//   @SneakyThrows // pacaleste javac sa nu mai tipe la unhandled checked exceptions
   public void writeContent(Writer writer) throws IOException{
      writer.write("userid;fullname\n");
      userRepo.findAll().stream()
          .map(u -> u.getId() + ";" + u.getFullName() + "\n")
          .forEach(Unchecked.consumer(writer::write)); // org.jooq:jool
   }
}
class UtilFPShmecher {

   // cheme functia data param si sa rearunce orice ex checked ca un runtime
   public static <T>  Consumer<T> higherOrder(ConsumeruMeu<T> consumer) {
      return string -> {
         try {
            consumer.accept(string);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      };
   }
}

@RequiredArgsConstructor
//@SpringBootApplication // enable on demand.
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final FileExporter fileExporter;
   private final OrderContentWriter orderContentWriter;
   private final UserContentWriter userContentWriter;
   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", Unchecked.consumer(orderContentWriter::writeContent));

      fileExporter.exportFile("users.csv", Unchecked.consumer(userContentWriter::writeContent));

      // TODO implement export of users too
   }
}

