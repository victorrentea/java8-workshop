package victor.training.java.advanced.model;

import java.util.Optional;

// @Entity - The HOLY ENTITY !
public class Customer {
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
