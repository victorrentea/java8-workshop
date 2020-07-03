package victor.training.java8.functionalpatterns;

import java.util.function.BiFunction;

import static org.mockito.Mockito.mock;

class Movie {
	enum Type {
		REGULAR(PriceService::computeRegularMoviePrice),
		NEW_RELEASE(PriceService::computeNewReleaseMoviePrice),
		CHILDREN(PriceService::computeChildrenMoviePrice);

		public final BiFunction<PriceService, Integer, Integer> priceAlgo;
		Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
			this.priceAlgo = priceAlgo;
		}
	}

	private final Type type;

	public Movie(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
}

class PriceService {
	public int computeRegularMoviePrice(int days) {
		return days + 1;
	}
	public int computeNewReleaseMoviePrice(int days) {
		return days *2;
	}
	public int computeChildrenMoviePrice(int days) {
		return 5;
	}

	public int computePrice(Movie movie, int days) {
		return movie.getType().priceAlgo.apply(this, days);
	}
}

public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
		PriceService priceService = new PriceService();
		System.out.println(priceService.computePrice(new Movie(Movie.Type.REGULAR), 2));
		System.out.println(priceService.computePrice(new Movie(Movie.Type.NEW_RELEASE), 2));
		System.out.println(priceService.computePrice(new Movie(Movie.Type.CHILDREN), 2));
		System.out.println("COMMIT now!");
	}
}
