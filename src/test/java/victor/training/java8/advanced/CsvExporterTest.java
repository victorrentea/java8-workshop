package victor.training.java8.advanced;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

   @Test
   void exportFile() {
      new CsvExporter().exportFile("some.txt", writer -> {
         try {
            writer.write("X");
         } catch (IOException e) {
            e.printStackTrace();
         }
      });

      // TODO check that a file some.txt was created in the correct folder, containing "X"
   }
}