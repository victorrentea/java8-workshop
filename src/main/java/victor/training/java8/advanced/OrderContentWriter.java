package victor.training.java8.advanced;

import io.vavr.Function3;
import io.vavr.Function5;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.IOException;
import java.io.Writer;

//format logic
@Component
@RequiredArgsConstructor
public class OrderContentWriter {
   private final OrderRepo orderRepo;

   public void writeContent(Writer writer) throws IOException {


//      Order order = orderRepo.findById(13l).get();//.orElseThrow(() -> new IllegalArgumentException("Nu am gasit Order id " + 13));


      writer.write("OrderID;Date\n");
      orderRepo.findByActiveTrue()
          .map(order -> order.getId() + ";" + order.getCreationDate())
          .forEach(Unchecked.consumer(writer::write));
   }

   public <A,B,C,D,E,R> Function3<C,D,E,R> partialAPply2(Function5<A,B,C,D,E,R> f5, A a, B b) {
      return f5.apply(a,b);
   }
}
record X(int i) {}
