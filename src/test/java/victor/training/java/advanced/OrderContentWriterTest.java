package victor.training.java.advanced;

import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.InjectService;
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
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderContentWriterTest {
    @Mock
    OrderRepo orderRepo;
    @InjectMocks
    OrderContentWriter target;
    @Test
    void writeOrders() throws IOException {
        StringWriter writer = new StringWriter();
        Mockito.when(orderRepo.findByActiveTrue()).thenReturn(
                Stream.of(new Order().setId(1L).setCreationDate(LocalDate.parse("2021-01-01")))
        );

        target.writeOrders(writer);

        assertThat(writer.toString()).isEqualTo(
                """
                        OrderID;Date
                        1;2021-01-01
                        """);
    }
}