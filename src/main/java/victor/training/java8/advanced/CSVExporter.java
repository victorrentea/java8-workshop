package victor.training.java8.advanced;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.function.Consumer;

// Infrastuctura
@Service
public class CSVExporter {

   @Value("${export.folder.out}")
   private File folder;


//   interface ExportContentWriter {}

   public void exportFile(final String fileName, Consumer<Writer> contentWriter) {
      File file = new File(folder, fileName + ".csv");
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
