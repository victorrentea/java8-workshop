package victor.training.java8.functionalpatterns;

/* "I call it my billion-dollar mistake.
 * It was the invention of the null reference in 1965..."
 *  -- Sir Charles Antony Richard  */

// Get a discount line to print in UI

import java.util.Optional;

class DiscountService {

   public String getDiscountLine(Customer customer) {
      return customer.getMemberCard()
          .flatMap(this::getApplicableDiscountPercentage)
          .map(integer -> "Discount%: " + integer)
          .orElse("");
   }

   private Optional<Integer> getApplicableDiscountPercentage(MemberCard memberCard) {
      if (memberCard.getFidelityPoints() > 100) {
         return Optional.of(5);
      }
      if (memberCard.getFidelityPoints() > 50) {
         return Optional.of(3);
      }
      return Optional.empty();
   }


   // test: 60, 10, no MemberCard
   public static void main(String[] args) {
      DiscountService service = new DiscountService();
      System.out.println(service.getDiscountLine(new Customer(new MemberCard(60))));
      System.out.println(service.getDiscountLine(new Customer(new MemberCard(10))));
      System.out.println(service.getDiscountLine(new Customer()));
   }
}


// VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
class Customer {
   private MemberCard memberCard;

   public Customer() {
   }

   public Customer(MemberCard profile) {
      this.memberCard = profile;
   }


   public Optional<MemberCard> getMemberCard() {
      return Optional.ofNullable(memberCard);
   }

class MemberCard {
   private final int fidelityPoints;

   public MemberCard(int fidelityPoints) {
      this.fidelityPoints = fidelityPoints;
   }

   public int getFidelityPoints() {
      return fidelityPoints;
   }

}
