package victor.training.java8.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.repo.OrderRepo;

import javax.persistence.EntityManager;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderContentWriterTest {
@Mock
   OrderRepo orderRepo;
@Mock
   EntityManager em;
@InjectMocks OrderContentWriter target;

   @Test
   void write() throws IOException {
      when(orderRepo.findByActiveTrue())
          .thenReturn(Stream.of(new Order().setId(1L).setCreationDate(LocalDate.now())));

      StringWriter writer = new StringWriter();
      target.write(writer);

      Assertions.assertThat(writer.toString()).isEqualTo("OrderID;Date\n" +
                                                         "1;2021-10-07\n");
   }
}