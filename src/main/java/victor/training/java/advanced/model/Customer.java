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

   public Customer setMemberCard(MemberCard memberCard) {
      this.memberCard = memberCard;
      return this;
   }

   public Optional<MemberCard> getMemberCard() {
      return Optional.ofNullable(memberCard);
   }
}
