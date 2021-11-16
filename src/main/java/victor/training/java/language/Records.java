package victor.training.java.language;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class Records {

   // TODO Use-case: Tuples (see Stream Wreck)
   public Map<Long, List<Tuple2<String, Integer>>> extremeFP() {
      Long customerId = 1L;
      Integer product1Count = 2;
      Integer product2Count = 4;
      return Map.of(customerId, List.of(
          Tuple.tuple("Table", product1Count),
          Tuple.tuple("Chair", product2Count)
      ));
   }
   
   @Test
   void lackOfAbstractions() {
      Map<Long, List<Tuple2<String, Integer>>> map = extremeFP();
      // Joke: try "var" above :)

      for (Long cid : map.keySet()) {
         String pl = map.get(cid).stream()
             .map(t -> t.v2 + " of " + t.v1)
             .collect(joining(", "));
         System.out.println("cid=" + cid + " bought " + pl);
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