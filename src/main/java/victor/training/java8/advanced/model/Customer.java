package victor.training.java8.advanced.model;

import lombok.NonNull;

import java.util.Optional;

// @Entity - The HOLY ENTITY !

public class Customer {
//   @NonNull
   private String name;
   private MemberCard memberCard;

   public Customer() {
   }

   public Customer(MemberCard profile) {
      this.memberCard = profile;
   }

   public Optional<MemberCard> getMemberCard() {
      return Optional.ofNullable(memberCard);
   }
}
