package victor.training.java8.stream;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
          .distinct()
          .forEach(e -> System.out.println("Out: "+ e));
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