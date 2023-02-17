package victor.training.java.java17;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class Records {

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
         System.out.println("cid=" + cid + " got " + pl);
      }
   }

   @Test
   void immutables() {
//      List<Integer> numbers = IntStream.range(1, 10).boxed().collect(Collectors.toList()); // intoarce mutable list
      List<Integer> numbers = IntStream.range(1, 10).boxed().toList(); // java 17 immutable list
      Immutable obj = new Immutable("John", new Other("halo"), numbers);

      String original = obj.toString();
      System.out.println(obj);

      unknownFierceCode(obj);

      System.out.println(obj);

      assertThat(original).describedAs("State should not change!").isEqualTo(obj.toString());
   }

   private static void unknownFierceCode(Immutable obj) {
      // TODO what can go wrong here ?
      obj.list().add(1);
   }
}

// -- RECORD --
// methods: add extra, overriding generated
// constructor:
// inheritance:


// un fel de @Value din Lombok
record Immutable(String name, Other other, List<Integer> list) {
   // campuri finale private
   // constructor cu toti param
   // getter
   // hash/equals pe toate campurile
   // toString

//   @Override
//   public Optional<String> name() {
//      return name;
//   }


   // hands-on practic:
   // JSON Jackson stie sa marshall/unmarshall -> Dto = record
   // Hibernate = NU va stii vreodata
   // Mongo,/nosql = STIU

}




final class Other {
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