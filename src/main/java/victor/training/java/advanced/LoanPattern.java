package victor.training.java.advanced;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import victor.training.java.advanced.repo.OrderRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.function.Consumer;

@Service
class FileExporter {
   @Autowired
   private OrderRepo orderRepo;
   @Value("${export.folder.out}")
   private File folder;

   @FunctionalInterface
   public interface ConsumerChecked<T> {
      void accept(T t) throws Exception;
   }
   public void exportFile() {
      File file = new File(folder, "orders.csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         writer.write("OrderID;Date\n");

         orderRepo.findByActiveTrue()
             .map(order -> order.getId() + ";" + order.getCreationDate() + "\n")
             .forEach(Unchecked.consumer(writer::write));

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

   // 2nd level higher order function : primeste si intoarce fct: evita sa scrii TU asa ceva: greu debug
   public static <T>  Consumer<T> wrapChecked(ConsumerChecked<T> consumerChecked) {
      return s -> {
         try {
            consumerChecked.accept(s);
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

   public void run(String... args) throws Exception {
      fileExporter.exportFile();

      // TODO implement export of users too
   }
}

