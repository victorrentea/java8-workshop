package victor.training.java.language;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class Records {

//   {
//      var v = extremeFP2();
//   }

   // TODO Use-case: Tuples (see Stream Wreck)
//   public <T extends Consumer<String> & Supplier<String>> T extremeFP2() {
//
//   }
   record ProductOrderCounts(String productName, int count) {
   }

   record CustomerId(long id) {
   }

   record CustomerProductOrderHistory(CustomerId customerId, List<ProductOrderCounts> products) {
   }

   //   public Map<CustomerId, List<ProductOrderCounts>> extremeFP() {
   public List<CustomerProductOrderHistory> extremeFP() {
      // takes 500 ms.
      Long customerId = 1L;
      Integer product1Count = 2;
      Integer product2Count = 4;
      CustomerProductOrderHistory elem = new CustomerProductOrderHistory(
          new CustomerId(customerId),
          List.of(
              new ProductOrderCounts("Table", product1Count),
              new ProductOrderCounts("Chair", product2Count)
          ));
      return List.of(elem);
   }

//   void lackOfAbstractions() {
//      Map<Long, List<Tuple2<String, Integer>>> map = extremeFP();
//      var mapMistakeVar = extremeFP();
//
//      for (var longListEntry : map.entrySet()) { // ok var
//      }
//
//      Map<Long, List<Tuple2<String, Integer>>> map2bis = new HashMap<>();
//      var map2 = new HashMap<Long, List<Tuple2<String, Integer>>>();

   @Test
   void lackOfAbstractions() {
//      Map<CustomerId, List<ProductOrderCounts>> map = extremeFP();
//      for (Long cid : map.keySet()) {  // Sonar told us that loopin on entries is better than on keySet()
//         List<Tuple2<String, Integer>> value = map.get(cid); // O(1)

//      for (var entry : map.entrySet()) { // code smell
//         CustomerId customerId = entry.getKey();

      List<CustomerProductOrderHistory> map = extremeFP();

      for (CustomerProductOrderHistory history : map) {

         String pl = history.products().stream()
             .map(t -> t.count() + " of " + t.productName())
             .collect(joining(" and "));
         System.out.println("cid=" + history.customerId().id() + " bought " + pl);
      }
   }

   @Test
   void immutables() {
      List<Integer> numbers = IntStream.range(1, 10).boxed().toList();
      Immutable obj = new Immutable("John", new Other("halo"), numbers);

      String original = obj.toString();
      System.out.println(obj);

      unkownFierceCode(obj);

      System.out.println(obj);

      assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
   }

   private static void unkownFierceCode(Immutable obj) {
      // TODO what can go wrong here ?
   }
}

// methods: add extra, overriding generated
// constructor:
// inheritance:


class Immutable {
   private final String name;
   private final Other other;
   private final List<Integer> list;

   public Immutable(String name, Other other, List<Integer> list) {
      this.name = name;
      this.other = other;
      this.list = list;
   }

   public String getName() {
      return name;
   }

   public Other getOther() {
      return other;
   }

   public List<Integer> getList() {
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

class Other {
   private String data;

   public Other(String data) {
      this.data = data;
   }

   public String getData() {
      return data;
   }

   public Other setData(String data) {
      this.data = data;
      return this;
   }

   @Override
   public String toString() {
      return "Other{" +
             "data='" + data + '\'' +
             '}';
   }
}