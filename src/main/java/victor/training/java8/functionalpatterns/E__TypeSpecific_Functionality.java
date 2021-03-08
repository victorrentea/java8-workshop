package victor.training.java8.functionalpatterns;

import static org.mockito.Mockito.mock;

enum MovieType {
	REGULAR(7) {
		public int computePrice(int days) {
			return days +1 ;
		}
	},
	NEW_RELEASE(4) {
		public int computePrice(int days) {
			return days * 2;
		}
	},
	CHILDREN(14) {
		public int computePrice(int days) {
			return 5;
		}
	};
	private final int maxDays;
	MovieType(int maxDays) {
		this.maxDays = maxDays;
	}
	public int getMaxDays() {
		return maxDays;
	}
	public abstract int computePrice(int days);
}
class Movie {

	private final MovieType type;

	public Movie(MovieType type) {
		this.type = type;
	}

	public int computePrice(int days) {
		return type.computePrice(days);
	}
	public int maxNumberOfRentDays() {
		return type.getMaxDays();
	}
}

public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
		System.out.println(new Movie(MovieType.REGULAR).computePrice(2));
		System.out.println(new Movie(MovieType.NEW_RELEASE).computePrice(2));
		System.out.println(new Movie(MovieType.CHILDREN).computePrice(2));
		System.out.println("COMMIT now!");
	}
}
