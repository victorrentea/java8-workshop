package victor.training.java8.functionalpatterns;

/* "I call it my billion-dollar mistake. 
 * It was the invention of the null reference in 1965..."
 *  -- Sir Charles Antony Richard  */

// Get a discount line to print in UI

class DiscountService {

		
	// test: 60, 10, no MemberCard
	public static void main(String[] args) {
		
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
	public MemberCard getMemberCard() {
		return memberCard;
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
