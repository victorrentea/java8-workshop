package victor.training.java8.advanced;

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
import victor.training.java8.advanced.repo.OrderRepo;
import victor.training.java8.advanced.repo.UserRepo;

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
   // GUNOI
   @Value("${export.folder.out}")
   private File folder;

   public void exportFile(String fileName, Consumer<Writer> contentWriter) {
      File file = new File(folder, fileName);
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         contentWriter.accept(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis() - t0));
      }
   }

}

@Component
class ExportContentWriters {
   @Autowired
   private OrderRepo orderRepo;
   @Autowired
   private UserRepo userRepo;
   // FORMAT LOGIC CRITIC PT APP CARE PRIMESC PRIMESC ACESTE FISIER
   @SneakyThrows
   public void writeOrderContent(Writer writer)  {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
          .forEach(Unchecked.consumer(writer::write));
   }

   @SneakyThrows
   public void writeUserContent(Writer writer)  {
      writer.write("username;fullname\n");
      userRepo.findAll().stream()
          .map(u -> u.getUsername() + ";"+u.getFullName() + "\n")
          .forEach(Unchecked.consumer(writer::write));
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
   private final ExportContentWriters exportContentWriters;

   public void run(String... args) throws Exception {
      orderExporter.exportFile("orders.csv", exportContentWriters::writeOrderContent);
      orderExporter.exportFile("users.csv", exportContentWriters::writeUserContent);
   }
}

