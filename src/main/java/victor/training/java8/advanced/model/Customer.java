package victor.training.java8.advanced.model;

import java.util.Optional;

import static java.util.Optional.ofNullable;

// @Entity - The HOLY ENTITY !
public class Customer {
   private Long id;
   private MemberCard memberCard;

   public Customer() {
   }

   public Long getId() {
      return id;
   }

   public Customer(MemberCard profile) {
      this.memberCard = profile;
   }

   public Optional<MemberCard> getMemberCard() {
      return ofNullable(memberCard);
   }
}
