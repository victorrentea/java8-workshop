package victor.training.java8.advanced;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.IOException;
import java.io.Writer;

//format logic
@Component
@RequiredArgsConstructor
public class OrderContentWriter {
   private final OrderRepo orderRepo;

   public void writeContent(Writer writer) throws IOException {
      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(order -> order.getId() + ";" + order.getCreationDate())
          .forEach(Unchecked.consumer(writer::write));
   }
}
