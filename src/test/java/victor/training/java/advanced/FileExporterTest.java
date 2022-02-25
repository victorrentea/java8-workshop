package victor.training.java.advanced;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileExporterTest {

   @Test
   void test() {
       new FileExporter().exportFile("a.txt", writer -> writer.write("X"));
       // TODO verifica ca fisierul a fost creat in folderul care trebuie
   }
}