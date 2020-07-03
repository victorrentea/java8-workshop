package victor.training.java8.stream;

import lombok.ToString;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StatfulStepInStream {
   public static void main(String[] args) {
      List<Customer> customers = Arrays.asList(1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8)
          .stream()
          .map(Customer::new)
          .collect(Collectors.toList());

      customers.stream()
          .peek(e -> System.out.println("Peek: " + e))
//          .sorted(Comparator.comparing(Customer::getId))
//          .distinct()
          .filter(new DistinctWithExtractor<>(Customer::getId))
          .forEach(e -> System.out.println("Out: " + e));
   }

}

class DistinctWithExtractor<T, E> implements Predicate<T> {
   private final Function<T, E> distinctKeyExtractor;
   private final Set<E> idsSeen = new HashSet<>();

   public DistinctWithExtractor(Function<T, E> distinctKeyExtractor) {
      this.distinctKeyExtractor = distinctKeyExtractor;
   }

   @Override
   public boolean test(T c) {
      return idsSeen.add(distinctKeyExtractor.apply(c));
   }
}


@ToString
class Customer {
   private final long id;

   Customer(long id) {
      this.id = id;
   }

   public long getId() {
      return id;
   }
}