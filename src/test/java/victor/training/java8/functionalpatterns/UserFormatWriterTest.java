package victor.training.java8.functionalpatterns;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class UserFormatWriterTest extends TestCase {

   @Test
   public void testWriteOrderExport() throws IOException {
      UserFormatWriter userFormatWriter = new UserFormatWriter();
      Writer writer = new StringWriter();
      userFormatWriter.writeExportContents(writer);

      String actualWrittenText = writer.toString();
      assertEquals("Username;s..\nrand1", actualWrittenText);

   }
}