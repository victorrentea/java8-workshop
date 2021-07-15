package victor.training.java8.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//@RegisterExtension(MockitoExtension.class)
public class OrderExportContentTest {
   @Mock
   private OrderRepo orderRepo;
   @InjectMocks OrderExportContent content;

   @Test
   void writeContent() {
      Mockito.when(orderRepo.findByActiveTrue())
          .thenReturn(Stream.of(
              new Order().setId(1L)
                  .setCreationDate(LocalDate.now())));

      StringWriter writer = new StringWriter();
      content.writeContent(writer);

      String actualExportContent = writer.toString();
      Assertions.assertThat(actualExportContent).isEqualTo("OrderId");
   }
}