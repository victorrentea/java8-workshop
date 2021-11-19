package victor.training.java.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.repo.OrderRepo;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.stream.Stream;

import static java.time.LocalDate.parse;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExportsTest {
   @Mock
   OrderRepo orderRepo;
   @InjectMocks Exports target;

   @Test
   void writeOrders() throws IOException {
      Order order = new Order().setId(1L).setCreationDate(parse("2021-11-19"));
      Mockito.when(orderRepo.findByActiveTrue())
              .thenReturn(Stream.of(order));
      Writer writer = new StringWriter();

      target.writeOrders(writer);

      String actual = writer.toString();
      Assertions.assertThat(actual).isEqualTo("""
          OrderID;Date
          1;2021-11-19
          """);
   }
}