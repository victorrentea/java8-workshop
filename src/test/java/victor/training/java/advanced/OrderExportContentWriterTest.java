package victor.training.java.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.repo.OrderRepo;
import victor.training.java.stream.order.repo.OrderRepository;

import javax.persistence.EntityManager;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderExportContentWriterTest {

   @Mock
   OrderRepo orderRepo;
   @Mock
   EntityManager entityManager;
   @InjectMocks
   OrderExportContentWriter target;
   @Test
   void writeOrders() throws IOException {
      when(orderRepo.findByActiveTrue()).thenReturn(Stream.of(new Order()
          .setId(1L)
          .setCreationDate(LocalDate.now())));
      StringWriter stringWriter = new StringWriter();

      target.writeOrders(stringWriter);

      String content = stringWriter.toString();
      assertThat(content).isEqualTo("OrderID;Date\n" +
                                    "1;2022-04-06\n");
   }
}