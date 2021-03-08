package victor.training.java8.advanced;

class DiscountService {
	public String getDiscountLine(Customer customer) {
		return "Discount: " + getApplicableDiscountPercentage(customer.getMemberCard());
	}
		
	private Integer getApplicableDiscountPercentage(MemberCard card) { 
		if (card.getFidelityPoints() >= 100) {
			return 5;
		}
		if (card.getFidelityPoints() >= 50) {
			return 3;
		}
		return null;
	}
		
	// test: 60, 10, no MemberCard
	public static void main(String[] args) {
		DiscountService service = new DiscountService();
		System.out.println(service.getDiscountLine(new Customer(new MemberCard(60))));
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
