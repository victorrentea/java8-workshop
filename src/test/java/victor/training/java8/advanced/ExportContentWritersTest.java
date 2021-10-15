package victor.training.java8.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExportContentWritersTest {
@Mock
private OrderRepo orderRepo;
@InjectMocks
private ExportContentWriters target;

   @Test
   void writeOrderContent() throws IOException {
      Order order = new Order().setId(13L).setCreationDate(LocalDate.parse("2021-10-15"));
      Mockito.when(orderRepo.findByActiveTrue()).thenReturn(Stream.of(order));

      StringWriter writer = new StringWriter();

      target.writeOrderContent(writer);

      String content = writer.toString();

      Assertions.assertThat(content).isEqualTo("OrderID;Date\n" +
                                               "13;2021-10-15\n");


   }
}