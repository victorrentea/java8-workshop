package victor.training.java17;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class Records {

   // Hibernate != records  at best @Embeddable
   // Mongo stores records OK
   // Jackson (JSON) will support records


   // TODO Use-case: Tuples (see Stream Wreck)
//   public Map<CustomerId, List<ProductBoughtCount>> extremeFP() {
   public List<CustomerProducts> extremeFP() {
      Long customerId = 1L;
      Integer product1Count = 2;
      Integer product2Count = 4;
      return List.of(new CustomerProducts(new CustomerId(customerId), List.of(
          new ProductBoughtCount(new ProductName("Table"), product1Count),
          new ProductBoughtCount(new ProductName("Chair"), product2Count)
      )));
   }

   record ProductName(String name) {}
   record CustomerId(Long id) {}
   record ProductBoughtCount(ProductName productName, int count) {}
   record CustomerProducts(CustomerId id, List<ProductBoughtCount> products) {}

   class UserFactoryForGarbageData {}
   @Test
   void lackOfAbstractions() {
      var user1 = new UserFactoryForGarbageData();  // OK

      // the same, no gain
      Map<String, List<Long>> user2var = new HashMap<>();
      var user2 = new HashMap<String, List<Long>>();

      // NEVER ! CODE REVIEW CRASH
//      var map = extremeFP(); // var never in production . only in tests

      // customerId -> [{productName->count}]
//      Map<CustomerId, List<ProductBoughtCount>> map = extremeFP(); // lacking abstraction
      List<CustomerProducts> list = extremeFP();
      // Joke: try "var" above :)


      // sonar tells you to iterate over entries
      // I (victor) tell you that you are having a design isue
      // iterating over entries of keySet is a code smell IF you only do that thing with the map
//      for (CustomerId customerId : map.keySet()) {
//         List<ProductBoughtCount> value = map.get(customerId);

      for (CustomerProducts customerProducts : list) {
         String pl = customerProducts.products.stream()
             .map(t -> t.count + " of " + t.productName.name)
             .collect(joining(" and "));
         System.out.println("cid=" + customerProducts.id.id + " bought " + pl);
      }
   }

   @Test
   void immutables() {
      List<Integer> numbers = IntStream.range(1, 10).boxed().toList();
      Immutable17 obj = new Immutable17("John", new Other("halo"), ImmutableList.copyOf(numbers));

      String original = obj.toString();
      System.out.println(obj);

      unkownFierceCode(obj);

      System.out.println(obj);

      assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
   }

   private static void unkownFierceCode(Immutable17 obj) {
      // TODO what can go wrong here ?
//      obj.list().clear();
//      obj.list().add(1);
   }
}
record AnotherSuper(int x) {
}

interface SomeInterface {
   String name();
}

record Immutable17(
    String name,
    Other other,
    ImmutableList<Integer> list) /*extends AnotherSuper*/ implements SomeInterface {

   public Immutable17 {
      Objects.requireNonNull(name);
   }

   public Immutable17(String name, Other other) {
      this(name, other, ImmutableList.copyOf(emptyList()));
   }

   public void extraMethod() {
      // if you find yourself adding a LOT of methods to a record >>> make it a class.
   }

   @Override
   public String name() {
      return name.toUpperCase();
   }
}


@Value // big fan of
// almost an enemy of @Data
class ImmutableLombok {
   @NonNull
   String name;
   Other other;
   ImmutableList<Integer> list;
}

class Immutable {
   private final String name;
   private final Other other;
   private final ImmutableList<Integer> list; // hibenrate says NO

   public Immutable(String name, Other other, ImmutableList<Integer> list) {
//      if (name == null) {
//         throw new IllegalArgumentException();
//      }
      this.name = Objects.requireNonNull(name);
      this.other = other;
      this.list = list;
   }

   public String getName() {
      return name;
   }

   public Other getOther() {
      return other;
   }

   // 90%
//   public List<Integer> getList() {
//      return Collections.unmodifiableList(list);
//   }

   public ImmutableList<Integer> getList() {
      return list;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Immutable immutable = (Immutable) o;
      return Objects.equals(name, immutable.name) && Objects.equals(other, immutable.other) && Objects.equals(list, immutable.list);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, other, list);
   }

   @Override
   public String toString() {
      return "Immutable{" +
             "name='" + name + '\'' +
             ", other=" + other +
             ", list=" + list +
             '}';
   }
}

record Other(String data) {

}