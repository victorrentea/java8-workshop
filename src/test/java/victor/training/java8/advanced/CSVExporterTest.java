package victor.training.java8.advanced;

import org.jooq.lambda.Unchecked;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVExporterTest {

   @Test
   void exportFile() {
      CSVExporter exporter = new CSVExporter();

      exporter.exportFile("a", writer -> writer.write("dummy"));
//      exporter.exportFile("a", writer -> {throw new IllegalArgumentException("intentionat");});
      // TIDO verifia ca in folderul corect este un fisier care .... contine "dummy"
   }
}