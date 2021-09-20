package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.*;
import java.util.function.Consumer;

@Service
class OrderExporter {
   @Autowired
   private OrderRepo orderRepo;
   @Value("${export.folder.out}")
   private File folder;

   public void exportFile() {
      File file = new File(folder, "orders.csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         writer.write("OrderID;Date\n");

         orderRepo.findByActiveTrue()
             .map(order -> order.getId() + ";" + order.getCreationDate())
             .forEach(    Unchecked.consumer( str -> writer.write(str) )   );
         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

   private static <T> Consumer<T> convert(ConsumerCareArunca<T> deFaptDeInvocat) {
      return s -> {
         try {
            deFaptDeInvocat.write(s);
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      };
   }

   private void writeToFile(Writer writer, String s) {
      try {
         writer.write(s);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   interface ConsumerCareArunca<T> {
      void write(T str) throws IOException;
   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   private final OrderExporter orderExporter;

   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   public void run(String... args) throws Exception {
      orderExporter.exportFile();

      System.out.println("-------------------");
   }
}

