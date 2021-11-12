package victor.training.java8.advanced;

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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Timed;
import victor.training.java8.advanced.repo.OrderRepo;

interface MyConsumer<T> {
   void accept(T t) throws Exception;
}
@Service
class OrderExporter {
   @Autowired
   private OrderRepo orderRepo;
   @Value("${export.folder.out}")
   private File folder;

//   @Timed(micrometer)
   public void exportFile() {
      File file = new File(folder, "orders.csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) { // ~ anaology to any resource that needs cleaning / releasing : sockets, jms connections, jdbc conn
         System.out.println("Starting export to: " + file.getAbsolutePath());

         writer.write("OrderID;Date\n");


         orderRepo.findByActiveTrue()
             .map(o -> o.getId() + ";" + o.getCreationDate())
//				.forEach(conversion(writer::write));
             .forEach(Unchecked.consumer(writer::write));

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis()-t0));
      }
   }

   // UTIL wornderful idea
   public static <T> Consumer<T> conversion(MyConsumer<T> f) {
      return input -> {
         try {
            f.accept(input);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      };
   }

   //   @SneakyThrows // maybe NOT
   private void javaSucks(Writer writer, String str) {
      try {
         writer.write(str);
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

