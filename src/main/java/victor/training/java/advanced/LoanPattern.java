package victor.training.java.advanced;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.advanced.repo.UserRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;
//   @FunctionalInterface
//   public interface ConsumerChecked<T> {
//      void accept(T t) throws Exception;
//   }
   // 2nd level higher order function : primeste si intoarce fct: evita sa scrii TU asa ceva: greu debug
//   public static <T>  Consumer<T> wrapChecked(ConsumerChecked<T> consumerChecked) {
//      return s -> {
//         try {
//            consumerChecked.accept(s);
//         } catch (Exception e) {
//            throw new RuntimeException(e);
//         }
//      };
//   }

@Service
class FileExporter {

   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   interface ExportFileWriter {
      void writeContent(Writer writer) throws IOException;
   }

   public void exportFile(String fileName, ExportFileWriter contentWriter) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.writeContent(writer);

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
class OrderExportContentService {
   private final OrderRepo orderRepo;

   public void writeContent(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(order -> order.getId() + ";" + order.getCreationDate() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }

}
@RequiredArgsConstructor
@Service
class UserExportContentService {
   private final UserRepo userRepo;

   public void writeUserContent(Writer writer) throws IOException {
      writer.write("username;fullname\n");
      userRepo.findAll().stream()
          .map(user -> user.getUsername() + ";" + user.getFullName() + "\n")
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
   private final OrderExportContentService orderExportContentService;
   private final UserExportContentService userExportContentService;

   public void run(String... args) throws Exception {
      fileExporter.exportFile("orders.csv", orderExportContentService::writeContent);

      fileExporter.exportFile("users.csv", userExportContentService::writeUserContent);
      // TODO implement export of users too
   }
}

