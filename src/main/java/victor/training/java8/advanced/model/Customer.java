package victor.training.java8.advanced.model;

import com.sun.istack.NotNull;

import java.util.Optional;

import static java.util.Optional.ofNullable;

// @Entity - The HOLY ENTITY !
public class Customer {
   private String name; // NOT NULL in db
   private MemberCard memberCard; // reflection

   public Customer() {
   }

   public Customer(MemberCard profile) {
      this.memberCard = profile;
   }

   public String getName() {
      return name;
   }

   public Optional<MemberCard> getMemberCard() {
      return ofNullable(memberCard);
   }

//   {
//      Customer c = new Customer();
//      repo.save(c)
//   }
}
