package victor.training.java.advanced;

import lombok.Data;
import victor.training.java.advanced.model.Customer;
import victor.training.java.advanced.model.MemberCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@SuppressWarnings("ConstantConditions")
public class Optionals {
	public static void main(String[] args) {
		// test: 60, 10, no MemberCard
		System.out.println(getDiscountLine(new Customer(new MemberCard(60))));
		System.out.println(getDiscountLine(new Customer(new MemberCard(10))));
		System.out.println(getDiscountLine(new Customer(null)));
	}

	public static String getDiscountLine(Customer customer) {
		return customer.getMemberCard()
			.flatMap(Optionals::getApplicableDiscount)
			.map(discount -> "Discount: " + discount.getGlobalPercentage())
			.orElse("");
	}

	private static Optional<Discount> getApplicableDiscount(MemberCard card) {
		if (card == null) {
			return empty();
		}
		if (card.getFidelityPoints() >= 100) {
			return of(new Discount(5));
		}
		if (card.getFidelityPoints() >= 50) {
			return of(new Discount(3));
		}
		return empty();
	}
}

@Data
class Discount {
//	public static final Discount ZERO = new Discount(0);
	private final int globalPercentage;
	private Map<String, Integer> categoryDiscounts = new HashMap<>();
}
