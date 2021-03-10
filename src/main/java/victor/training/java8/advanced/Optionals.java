package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Customer;
import victor.training.java8.advanced.model.MemberCard;

import java.util.Optional;

public class Optionals {
	public static void main(String[] args) {
		// test: 60, 10, no MemberCard
		System.out.println(getDiscountLine(new Customer(new MemberCard(60))));
		System.out.println(getDiscountLine(new Customer(new MemberCard(10))));
		System.out.println(getDiscountLine(new Customer()));

		//
	}

	public static String getDiscountLine(Customer customer) {
		return customer.getMemberCard()
			.flatMap(Optionals::getApplicableDiscountPercentage)
			.map(per -> "Discount: " + per)
			.orElse("");
	}

	private static Optional<Integer> getApplicableDiscountPercentage(MemberCard card) {
		if (card == null) {
			return Optional.empty();
		}
		if (card.getFidelityPoints() >= 100) {
			return Optional.of(5);
		}
		if (card.getFidelityPoints() >= 50) {
			return Optional.of(3);
		}
		return Optional.empty();
	}
}

