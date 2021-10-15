package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;


@FunctionalInterface
 interface ConsumerCareArunca<T> {
   void accept(T t) throws Exception;
}
//@RefresScope
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
             .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
             .forEach(Unchecked.consumer(writer::write));
//             .forEach(imbraca(writer::write));

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

   public static <T> Consumer<T> imbraca(ConsumerCareArunca<T> consumerOrig) {
      // astfel de functii produc panica si dezordine in randurile devilor
      return s -> {
         try {
            consumerOrig.accept(s);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      };
   }

   private void writeSafely(Writer writer, String s) {
      try {
         writer.write(s);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(LoanPattern.class, args);
   }

   private final OrderExporter orderExporter;

   public void run(String... args) throws Exception {
      orderExporter.exportFile();
   }
}

