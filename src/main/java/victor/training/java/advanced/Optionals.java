package victor.training.java.advanced;

import victor.training.java.advanced.model.Customer;
import victor.training.java.advanced.model.MemberCard;

import java.util.HashMap;
import java.util.Map;

public class Optionals {
	public static void main(String[] args) {
		// test: 60, 10, no MemberCard
		System.out.println(getDiscountLine(new Customer(new MemberCard(60))));

		//
	}

	public static String getDiscountLine(Customer customer) {
		return "Discount: " + getApplicableDiscountPercentage(customer.getMemberCard()).getGlobalPercentage();
	}

	private static Discount getApplicableDiscountPercentage(MemberCard card) {
		if (card.getFidelityPoints() >= 100) {
			return new Discount(5);
		}
		if (card.getFidelityPoints() >= 50) {
			return new Discount(3);
		}
		return null;
	}
}

class Discount {
	private final int globalPercentage;
	private Map<String, Integer> categoryDiscounts = new HashMap<>();

	Discount(int globalPercentage) {
		this.globalPercentage = globalPercentage;
	}

	public int getGlobalPercentage() {
		return globalPercentage;
	}

	public Discount setCategoryDiscounts(Map<String, Integer> categoryDiscounts) {
		this.categoryDiscounts = categoryDiscounts;
		return this;
	}

	public Map<String, Integer> getCategoryDiscounts() {
		return categoryDiscounts;
	}
}
