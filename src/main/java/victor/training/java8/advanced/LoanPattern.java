package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.repo.OrderRepo;


interface CheckedConsumer<T> {

   void accept(T t) throws Exception;
}

class OrderExporter {
   private OrderRepo orderRepo;
   @Value("${out.folder}")
   private File folder;// = new File("/apps/export"); // injected eg @Value

   // aici
   @Transactional(readOnly = true)
   public File exportFile() {
      File file = new File(folder, "orders.csv");
      try (Writer writer = new FileWriter(file)) {
         writer.write("OrderID;Date\n"); // header

         orderRepo.findByActiveTrue()
				.map(o -> o.getId() + ";" + o.getCreationDate()+"\n")
				.forEach(Unchecked.consumer(writer::write)); // jool
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

      // in general, evitati sa scrieti functii ca asta; greu de debug, de inteles
   public static Consumer<String> convert(CheckedConsumer<String> checkedConsumer) {
      return s -> {
         try {
            System.out.println("a");
            checkedConsumer.accept(s);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      };
   }
}

public class LoanPattern {
   public static void main(String[] args) throws Exception {
      new OrderExporter().exportFile();
      // TODO export users la fel cum exporti si orderurile: la fel cu err handling, notificari daca ok, cu tot ce am discutat pentru exportl de oderi
   }
}

