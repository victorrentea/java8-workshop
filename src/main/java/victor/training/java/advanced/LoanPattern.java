package victor.training.java.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java.advanced.repo.OrderRepo;

import javax.persistence.EntityManager;


@FunctionalInterface
interface ConsumeruMeu<T> {
   void accept(T t) throws Exception;
}
@Service
class FileExporter {
   @Autowired
   private OrderRepo orderRepo;
   @Value("${export.folder.out}")
   private File folder;
   EntityManager em;

   @Transactional // necesar pentru a-i spune lui spring sa aloce o connex JDBC pentru intreaga durata
   // a acestei metode, pentru ca REsultSet sa poata fi traversat
   public void exportFile() {
      File file = new File(folder, "orders.csv");
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {
         System.out.println("Starting export to: " + file.getAbsolutePath());

         writer.write("OrderID;Date\n");

         orderRepo.findByActiveTrue()
//              .peek(entity -> em.detach(entity)) // avoid mem leak by telling hib to remove that @Entity from the PersistenceContext
             .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
             .forEach(Unchecked.consumer(writer::write)) // org.jooq:jool
//             .forEach(UtilFPShmecher.higherOrder(writer::write))
         ;


         System.out.println("File export completed: " + file.getAbsolutePath());
      } catch (Exception e) {
         System.out.println("Imagine: Send Error Notification Email");
         throw new RuntimeException("Error exporting data", e);
      } finally {
         System.out.println("Export finished in: " + (System.currentTimeMillis()-t0));
      }
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
   public void run(String... args) throws Exception {
      fileExporter.exportFile();

      // TODO implement export of users too
   }
}

