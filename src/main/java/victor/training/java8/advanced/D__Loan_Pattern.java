package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.model.Order;

class OrderExporter {
   private OrderRepo orderRepo;
   private File exportFolder = new File("/apps/export"); // injected eg @Value

   public File exportFile() {
      File file = new File(exportFolder, "orders.csv");
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

public class D__Loan_Pattern {
   public static void main(String[] args) throws Exception {
      new OrderExporter().exportFile();
      // TODO export users
   }
}

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
   Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
