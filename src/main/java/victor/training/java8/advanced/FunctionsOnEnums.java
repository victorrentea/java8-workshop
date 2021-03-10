package victor.training.java8.advanced;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
class PriceCalculators {
//   @Value("${extra.factor}")
//   int factor = 2;

   public static int computeRegularMoviePrice(int days) {
      return days + 1;
   }
   public static int computeNewReleaseMoviePrice(int days) {
      return days * 2 ;
   }
   public static int computeChildrenMoviePrice(int days) {
      return 5;
   }
   public static int computEldersMoviePrice(int days) {
      return 5;
   }
}

enum MovieType {
   REGULAR(PriceCalculators::computeRegularMoviePrice),
   NEW_RELEASE(PriceCalculators::computeNewReleaseMoviePrice),
   CHILDREN(PriceCalculators::computeChildrenMoviePrice),
   ELDERS(PriceCalculators::computEldersMoviePrice)
   ;

   private final Function<Integer, Integer> priceAlgo;

   MovieType(Function<Integer, Integer> priceAlgo) {
      this.priceAlgo = priceAlgo;
   }

   public Function<Integer, Integer> getPriceAlgo() {
      return priceAlgo;
   }
}

public class FunctionsOnEnums {

   public static void main(String[] args) {
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public static int computePrice(MovieType type, int days) {
      return type.getPriceAlgo().apply(days);
   }

}
