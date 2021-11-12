package victor.training.java.advanced.tricks;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Trap_toMap_nullKey {
   @Data
   private static class Person {
      private final Long id;
      private final String name;
   }
   public static void main(String[] args) {
      toMapWithNullValues();
   }
   private static void toMapWithNullValues() {
      List<Person> list = asList(new Person(1L, "John"), new Person(2L, null));
      Map<Long, String> map = list.stream().collect(Collectors.toMap(Person::getId, Person::getName));
      System.out.println(map);
   }
}
