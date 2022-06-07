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
		System.out.println(getDiscountLine(new Customer(new MemberCard(40))));
		System.out.println(getDiscountLine(new Customer()));
	}

	public static String getDiscountLine(Customer customer) {
		return customer.getMemberCard()
				.flatMap(card->getApplicableDiscountPercentage(card))
				.map(discount -> "Discount: " + discount.getGlobalPercentage())
				.orElse("");
	}

//	public void caller() {
//		method();
//		if stuff  {methodWithStuff()};
//	}
//	public void method(Long productId) {
//		// code based on productId
//
//	}
//	public void methodWithStuff(Long productId, MoreStuff stuff) {
//		//more logic with stuff
//	}
	// biz logic method
	private static Optional<Discount> getApplicableDiscountPercentage(MemberCard card) {
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
	private final int globalPercentage;
	private Map<String, Integer> categoryDiscounts = new HashMap<>();
}
