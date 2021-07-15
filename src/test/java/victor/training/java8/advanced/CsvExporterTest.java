package victor.training.java8.advanced;

import org.jooq.lambda.Unchecked;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

   @Test
   void exportFile() {
      new CsvExporter().exportFile("file.txt",
          Unchecked.consumer(writer -> writer.write("sample")));
   }
}