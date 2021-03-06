package victor.training.java8.advanced;

import victor.training.java8.advanced.model.Customer;
import victor.training.java8.advanced.model.MemberCard;

import java.io.BufferedReader;
import java.io.Reader;

public class Optionals {
	public static void main(String[] args) {
		// test: 60, 10, no MemberCard
		System.out.println(getDiscountLine(new Customer(new MemberCard(60))));

		//
	}

	public static String getDiscountLine(Customer customer) {
		return "Discount: " + getApplicableDiscountPercentage(customer.getMemberCard());
	}

	private static Integer getApplicableDiscountPercentage(MemberCard card) {
		if (card.getFidelityPoints() >= 100) {
			return 5;
		}
		if (card.getFidelityPoints() >= 50) {
			return 3;
		}
		return null;
	}
}

