package victor.training.java8.functionalpatterns;

/* "I call it my billion-dollar mistake. 
 * It was the invention of the null reference in 1965..."
 *  -- Sir Charles Antony Richard  */

// Get a discount line to print in UI

import java.util.Optional;

import static java.util.Optional.*;

class DiscountService {
	public String getDiscountLine(Customer customer) {

//		a.getB().map(B::getC).map(C::getD).orElse("")
//		a.getB().getC().getD();

		Optional<MemberCard> memberCardOpt = customer.getMemberCard();
		return memberCardOpt
				.flatMap(this::getApplicableDiscountPercentage)
				.map(discount -> "Discount: " + discount)
				.orElse("");
	}
	private Optional<Integer> getApplicableDiscountPercentage(MemberCard card) {
		if (card == null) {
			return Optional.empty();
		}
		if (card.getFidelityPoints() >= 100) {
			return of(5);
		}
		if (card.getFidelityPoints() >= 50) {
			return of(3);
		}
		return empty();
	}
		
	// test: 60, 10, no MemberCard
	public static void main(String[] args) {
		DiscountService d = new DiscountService();
		System.out.println(d.getDiscountLine(new Customer(new MemberCard(60))));
		System.out.println(d.getDiscountLine(new Customer(new MemberCard(10))));
		System.out.println(d.getDiscountLine(new Customer()));
	}
}








// VVVVVVVVV ==== HOLY ENTITY ==== VVVVVVVVV
class Customer {
	private MemberCard memberCard;
	public Customer() {
	}
	public Customer(MemberCard profile) {
		this.memberCard = profile;
	}
	public Optional<MemberCard> getMemberCard() {
		return ofNullable(memberCard);
	}
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
