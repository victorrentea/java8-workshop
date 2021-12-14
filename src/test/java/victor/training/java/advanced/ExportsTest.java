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
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportsTest {
   @Mock
   OrderRepo orderRepo;
   @InjectMocks
   Exports target;
   @Test
   void writeOrderContent() throws IOException {
      Order order = new Order()
          .setId(1L)
          .setCreationDate(LocalDate.parse("2021-12-14"));
      when(orderRepo.findByActiveTrue()).thenReturn(Stream.of(order));

      StringWriter writer = new StringWriter();
      target.writeOrderContent(writer);

      String actual = writer.toString();

      Assertions.assertThat(actual).isEqualTo("""
          OrderID;Date
          1;2021-12-14
          """);
   }

}