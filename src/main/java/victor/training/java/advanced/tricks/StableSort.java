package victor.training.java.advanced.tricks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparing;

public class StableSort {
   public static void main(String[] args) {
      System.out.println("first click on the 'name' column -> ASC");
      fetchDataFromDb().stream()
          .sorted(comparing(Record::getFirstName))
          .forEach(System.out::println);

      System.out.println("\nsecond click on the 'name' column -> DESC");
      fetchDataFromDb().stream()
          .sorted(comparing(Record::getFirstName).reversed())
          .forEach(System.out::println);

      // UX sucks. TODO Fix it: the order within the "Alfred" subgroup is variable
   }

   private static List<Record> fetchDataFromDb() {
      List<Record> list = Arrays.asList(
          new Record(1L, "Alfred", "12345"),
          new Record(2L, "Alfred", "12346"),
          new Record(3L, "Alfred", "12347"),
          new Record(4L, "Alfred", "12348"),
          new Record(5L, "Bob", "12350"));
      Collections.shuffle(list); // simulate random result order from datastore
      return list;
   }
}

class Record {
   private Long id;
   private String firstName;
   private String cnp;

   public Record(Long id, String firstName, String cnp) {
      this.id = id;
      this.firstName = firstName;
      this.cnp = cnp;
   }

   public String getFirstName() {
      return firstName;
   }

   public Long getId() {
      return id;
   }

   public String getCnp() {
      return cnp;
   }

   @Override
   public String toString() {
      return "Record{" +
          "id=" + id +
          ", firstName='" + firstName + '\'' +
          ", cnp='" + cnp + '\'' +
          '}';
   }
}
