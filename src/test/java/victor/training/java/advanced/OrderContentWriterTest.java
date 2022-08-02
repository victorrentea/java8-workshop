package victor.training.java.advanced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.advanced.model.Order;
import victor.training.java.advanced.repo.OrderRepo;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderContentWriterTest {
    @Mock
    OrderRepo orderRepo;
    @InjectMocks
    OrderContentWriter orderContentWriter;

    @Test
    void writeOrders() throws IOException {
        Order order = new Order().setId(1L)
                .setCreationDate(LocalDate.parse("2022-08-02"));
        when(orderRepo.findByActiveTrue()).thenReturn(Stream.of(order));
        StringWriter writer = new StringWriter();

        orderContentWriter.writeOrders(writer);

        assertThat(writer.toString()).isEqualTo("""
                OrderID;Date
                1;2022-08-02
                """);
    }
}