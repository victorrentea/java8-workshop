package victor.training.java8.functionalpatterns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.mockito.Mockito.mock;

@Service
class MoviePriceCalculator {

	@Value("${prop}")
	private int delta;

	public int computePrice(MovieType type, int days) {
		return type.getPriceFunction().apply(this, days);
	}

	public int computeRegularPrice(int days) {
		// dar daca "1" vine din DB sau dintr-un fisier de propr
		return days + 1;
	}
	public int computeNewReleasePrice(int days) {
		// + 20 de linii de logica -- prea multa : ce cauta asta in enuM !?!
//			if (true) {
//				if (true) {
//					for () {
//						try
//
//					}
//				}
//			}
		return days * 2;
	}
	public int computeChildrenPrice(int days) {
		return 5;
	}
	public int computeBabaciPrice(int days) {
		return 3;
	}



}

// Utilizari: vine un mesaj pe vreo coada/ protocol si are un cod de Messagetype --> enum --> functie de handling
// exportere
// formattere pe tipuri diferite de entitati


enum MovieType {
	REGULAR(7, MoviePriceCalculator::computeRegularPrice ) ,
	NEW_RELEASE(4, MoviePriceCalculator::computeNewReleasePrice) ,
	CHILDREN(14, MoviePriceCalculator::computeChildrenPrice) ,

	BABACI(3, MoviePriceCalculator::computeBabaciPrice );

	public final int maxDays;
	private final BiFunction<MoviePriceCalculator, Integer, Integer> priceFunction;

	MovieType(int maxDays, BiFunction<MoviePriceCalculator, Integer, Integer> priceFunction) {
		this.maxDays = maxDays;
		this.priceFunction = priceFunction;
	}
	public BiFunction<MoviePriceCalculator, Integer, Integer> getPriceFunction() {
		return priceFunction;
	}

	public int getMaxDays() {
		return maxDays;
	}
}
class Movie {

	private final MovieType type;

	public Movie(MovieType type) {
		this.type = type;
	}

//	public int computePrice(int days) {
//		return type.computePrice(days);
//	}
	public int maxNumberOfRentDays() {
		return type.getMaxDays();
	}
}

public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
		MoviePriceCalculator calculator = new MoviePriceCalculator();
		System.out.println(calculator.computePrice(MovieType.REGULAR, 2));
		System.out.println(calculator.computePrice(MovieType.NEW_RELEASE, 2));
		System.out.println(calculator.computePrice(MovieType.CHILDREN, 2));
		System.out.println("COMMIT now!");
	}
}
