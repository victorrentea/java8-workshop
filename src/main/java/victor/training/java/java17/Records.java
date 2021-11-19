package victor.training.java.java17;

import com.google.common.collect.ImmutableList;
import org.checkerframework.checker.units.qual.A;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class Records {

   private static record ProductCounts(String productName, int count) {}
   private static record CustomerId(Long id) {} // might eat memory if many > 10k +

   public Map<CustomerId, List<ProductCounts>> extremeFP() {
      CustomerId customerId = new CustomerId(1L);
      Integer product1Count = 2;
      Integer product2Count = 4;
      return Map.of(customerId, List.of(
          new ProductCounts("Table", product1Count),
          new ProductCounts("Chair", product2Count)
      ));
   }
   
   @Test
   void lackOfAbstractions() {
      Map<CustomerId, List<ProductCounts>> map = extremeFP();
      // Joke: try "var" above :)

      for (CustomerId cid : map.keySet()) {
         String pl = map.get(cid).stream()
             .map(t -> t.count() + " of " + t.productName())
             .collect(joining(", "));
         System.out.println("cid=" + cid.id() + " bought " + pl);
      }
   }











   @Test
   void immutables() {
      List<Integer> numbers = new ArrayList<>(IntStream.range(1, 10).boxed().toList());
      Immutable obj = new Immutable(Optional.of("John"), new Other("halo"), ImmutableList.copyOf(numbers));

      String original = obj.toString();
      System.out.println(obj);

      unkownFierceCode(obj);

      System.out.println(obj);

      assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
   }

   private static void unkownFierceCode(Immutable obj) {
      // TODO what can go wrong here ?
//      obj.list().add(-1);
   }
}

// -- RECORD --
// methods: add extra, overriding generated
// constructor: +overloaded
// inheritance:

interface I {
   Other other();
}
record Immutable(Optional<String> name,
                 Other other,
                 ImmutableList<Integer> list) implements I {

   Immutable {
      Objects.requireNonNull(other);
   }


   public String getStuff() {
      return name + other.getData();
   }

   @Override
   public Optional<String> name() {
      return name.map(String::toUpperCase);
   }
}

//@Value // i love @Value, but i hate @Data
//class ImmutableLombok {
//   String name;
//   Other other;
//   ImmutableList<Integer> list;
//}

class Other {
   private final String data;

   public Other(String data) {
      this.data = data;
   }

   public String getData() {
      return data;
   }

   @Override
   public String toString() {
      return "Other{" +
             "data='" + data + '\'' +
             '}';
   }
}