package victor.training.java8.functionalpatterns;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileExporterTest {

   @Test
   public void exportFile() throws Throwable {
      FileExporter fileExporter = new FileExporter();
      fileExporter.exportFile("a.txt", writer -> writer.write("text"));
      // TODO ma uit in folder sa vad un fisier cu textul "test"
   }
}