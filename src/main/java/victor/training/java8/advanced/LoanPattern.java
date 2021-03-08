package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import victor.training.java8.advanced.repo.OrderRepo;

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

public class LoanPattern {
   public static void main(String[] args) throws Exception {
      new OrderExporter().exportFile();
      // TODO export users
   }
}

