package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import victor.training.java8.advanced.repo.OrderRepo;

//@RefresScope
@Service
class OrderExporter {
   @Autowired
   private OrderRepo orderRepo;
   @Value("${export.folder.out}")
   private File folder;

   public File exportFile() {
      File file = new File(folder, "orders.csv");
      try (Writer writer = new FileWriter(file)) {
         writer.write("OrderID;Date\n");

//			orderRepo.findByActiveTrue()
//				.map(o -> o.getId() + ";" + o.getCreationDate())
//				.forEach(writer::write);
         System.out.println("File export completed: " + file.getAbsolutePath());
         return file;
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         if (!file.delete()) {
            System.err.println("Could not delete export file: " + file.getAbsolutePath());
         }
      }
   }
}

@RequiredArgsConstructor
@SpringBootApplication
public class LoanPattern implements CommandLineRunner {
   private final OrderExporter orderExporter;

   public static void main(String[] args) {
       SpringApplication.run(LoanPattern.class, args);
   }


   @Override
   public void run(String... args) throws Exception {

   }
}

