package victor.training.java8;

import lombok.Data;
import lombok.Value;

public class Record {

   public static void main(String[] args) {

      Person person = new Person("Nume", "telefon");


      System.out.println(person.getName());

   }
}

// evit @Data, dar iubesc @Value
@Value
class Person {
   String name;
   String phone;
}

