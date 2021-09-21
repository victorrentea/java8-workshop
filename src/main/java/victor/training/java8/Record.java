package victor.training.java8;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

public class Record {


   public static void main(String[] args) {
      Person person = new Person("Nume", "DOE", "telefon");

      System.out.println(person.firstName());
      System.out.println(person);

      Person person2 = person.withPhone("alttelefon");

      FluentSetter fluentSetter = new FluentSetter()
          .setA("a")
          .setB("b")
          .setC("c");

//      ItemStatsForAttack build = new PersonImBuilder()
//          .name("aaa")
//          .phone("pp")
//          .build();
   }
}

class InventoryItem {
    // 80 de campuri

}
// evit @Data, dar iubesc @Value
@Value
@Builder
class ItemStatsForAttack {
   String name;
   String phone;
   String attr1;
   String attr2;
//   public Person(name, phone) {if}
}


// unde folosim Record
/// in DTO nu prea merita
// in Hibernate NU VOR MERGE IN VECII VECILOR (amin)
// JSON supporta, Mongo suporta, JAXB nu suporta inca
//
// immutabilitatea merita unde obiectele respective
// trec prin mii de LOC de logica.

// !~~ DDD
// cand vine DTO din exteriorul Bounded Contextului tau. (din API extern)
// nu propagi DTO extern in codu tau, ci il mapezi la un Value Object imutabil
// si-l plimbi prin codul tau ca record sau @Value

@Data
class FluentSetter {
   private String a;
   private String b;
   private String c;
}

/*final */record Person(String firstName, String lastName, String phone) {
   Person {
      if (firstName == null) {
         throw new IllegalArgumentException();
      }
   }
   public Person(String csv) {
      this(csv.split(";")[0], "DOE", csv.split(";")[1]);
   }

   public Person withPhone(String newPhone) {
      return new Person(firstName, lastName, newPhone);
   }
   // constructor
   // hashcode equals
   // tostring
   // getter
}
