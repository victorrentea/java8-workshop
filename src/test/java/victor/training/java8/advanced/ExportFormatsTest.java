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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.rmi.MarshalException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ExportFormatsTest {
@Mock
   OrderRepo orderRepo;
@InjectMocks ExportFormats exportFormats;
   @Test
   void writeOrdersContent() throws IOException {
      Mockito.when(orderRepo.findByActiveTrue()).thenReturn(Stream.of(new Order().setId(1L).setCreationDate(LocalDate.parse("2021-11-12"))));

      Writer writer = new StringWriter();
      exportFormats.writeOrdersContent(writer);

      String actual = writer.toString();

      String tenKB = "x".repeat(5_000);

      Assertions.assertThat(actual).isEqualTo("""
          OrderID;Date
          1;2021-11-12
          """);
   }
}