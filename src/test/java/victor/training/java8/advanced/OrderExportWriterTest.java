package victor.training.java8.advanced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.StringWriter;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderExportWriterTest {
   @Mock
   OrderRepo orderRepo;
   @InjectMocks
   OrderExportWriter orderExportWriter;


   @Test
   void writeContent() {
      Writer writer = new StringWriter();
      orderExportWriter.writeContent(writer);

      assertThat(writer.toString())
          .contains("OrderID;Date");

   }
}