package victor.training.java8.advanced;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

class FileExporterTest {

   @Test
   void exportFile() {
      new FileExporter().exportFile("a.txt", this::m);
      // TODO check a file was created containing X
   }

   @SneakyThrows
   private void m(Writer writer) {
      writer.write("x");
   }
}